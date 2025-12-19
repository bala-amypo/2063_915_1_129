package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DiscountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountApplicationRepository discountRepo;
    private final BundleRuleRepository ruleRepo;
    private final CartRepository cartRepo;
    private final CartItemRepository itemRepo;

    public DiscountServiceImpl(DiscountApplicationRepository discountRepo, BundleRuleRepository ruleRepo, 
                               CartRepository cartRepo, CartItemRepository itemRepo) {
        this.discountRepo = discountRepo;
        this.ruleRepo = ruleRepo;
        this.cartRepo = cartRepo;
        [cite_start]this.itemRepo = itemRepo; [cite: 224, 225]
    }

    @Override
    @Transactional
    public List<DiscountApplication> evaluateDiscounts(Long cartId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        [cite_start]if (!cart.getActive()) return Collections.emptyList(); [cite: 229, 230]

        [cite_start]discountRepo.deleteByCartId(cartId); [cite: 234]
        [cite_start]List<CartItem> items = itemRepo.findByCartId(cartId); [cite: 231]
        [cite_start]List<BundleRule> rules = ruleRepo.findByActiveTrue(); [cite: 232]
        List<DiscountApplication> applications = new ArrayList<>();

        Set<Long> productIdsInCart = items.stream()
                .map(i -> i.getProduct().getId())
                .collect(Collectors.toSet());

        for (BundleRule rule : rules) {
            List<Long> requiredIds = Arrays.stream(rule.getRequiredProductIds().split(","))
                    [cite_start].map(String::trim).map(Long::parseLong).collect(Collectors.toList()); [cite: 76]

            [cite_start]if (productIdsInCart.containsAll(requiredIds)) { [cite: 235]
                BigDecimal qualifyingTotal = items.stream()
                        .filter(i -> requiredIds.contains(i.getProduct().getId()))
                        .map(i -> i.getProduct().getPrice().multiply(new BigDecimal(i.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal discount = qualifyingTotal.multiply(BigDecimal.valueOf(rule.getDiscountPercentage() / 100.0));
                
                DiscountApplication app = new DiscountApplication();
                app.setCart(cart);
                app.setBundleRule(rule);
                app.setDiscountAmount(discount);
                [cite_start]applications.add(discountRepo.save(app)); [cite: 236]
            }
        }
        return applications;
    }
}