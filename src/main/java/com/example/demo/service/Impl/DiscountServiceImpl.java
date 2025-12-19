package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DiscountService;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class DiscountServiceImpl implements DiscountService {

    private final DiscountApplicationRepository discountRepository;
    private final BundleRuleRepository bundleRuleRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public DiscountServiceImpl(
            DiscountApplicationRepository discountRepository,
            BundleRuleRepository bundleRuleRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository) {

        this.discountRepository = discountRepository;
        this.bundleRuleRepository = bundleRuleRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<DiscountApplication> evaluateDiscounts(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cart not found"));

        if (!cart.getActive()) {
            return Collections.emptyList();
        }

        List<CartItem> items = cartItemRepository.findByCartId(cartId);
        List<BundleRule> rules = bundleRuleRepository.findByActiveTrue();

        discountRepository.deleteByCartId(cartId);

        Set<Long> cartProductIds = items.stream()
                .map(i -> i.getProduct().getId())
                .collect(Collectors.toSet());

        List<DiscountApplication> appliedDiscounts = new ArrayList<>();

        for (BundleRule rule : rules) {
            Set<Long> requiredIds = Arrays.stream(
                            rule.getRequiredProductIds().split(","))
                    .map(String::trim)
                    .map(Long::valueOf)
                    .collect(Collectors.toSet());

            if (cartProductIds.containsAll(requiredIds)) {
                BigDecimal total = items.stream()
                        .filter(i -> requiredIds.contains(
                                i.getProduct().getId()))
                        .map(i -> i.getProduct()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(i.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal discount = total
                        .multiply(BigDecimal.valueOf(
                                rule.getDiscountPercentage()))
                        .divide(BigDecimal.valueOf(100));

                if (discount.compareTo(BigDecimal.ZERO) > 0) {
                    DiscountApplication app =
                            new DiscountApplication();
                    app.setCart(cart);
                    app.setBundleRule(rule);
                    app.setDiscountAmount(discount);
                    app.setAppliedAt(LocalDateTime.now());
                    appliedDiscounts.add(
                            discountRepository.save(app));
                }
            }
        }

        return appliedDiscounts;
    }
}
