package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DiscountService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service // This is the line that fixes your error
public class DiscountServiceImpl implements DiscountService {

    private final DiscountApplicationRepository discountRepo;
    private final BundleRuleRepository ruleRepo;
    private final CartItemRepository itemRepo;
    private final CartRepository cartRepo;

    public DiscountServiceImpl(DiscountApplicationRepository dr, BundleRuleRepository rr, 
                               CartItemRepository ir, CartRepository cr) {
        this.discountRepo = dr;
        this.ruleRepo = rr;
        this.itemRepo = ir;
        this.cartRepo = cr;
    }

    @Override
    public List<DiscountApplication> evaluateDiscounts(Long cartId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow();
        
        // Test 34: Inactive cart logic
        if (cart.getActive() == null || !cart.getActive()) {
            return Collections.emptyList();
        }

        // Test 17: Delete old records before recalculating
        discountRepo.deleteByCartId(cartId);

        List<CartItem> items = itemRepo.findByCartId(cartId);
        Set<Long> productIdsInCart = items.stream()
                .map(ci -> ci.getProduct().getId())
                .collect(Collectors.toSet());

        List<BundleRule> activeRules = ruleRepo.findByActiveTrue();
        List<DiscountApplication> results = new ArrayList<>();

        for (BundleRule rule : activeRules) {
            // Test 39: CSV Parsing logic
            List<Long> required = Arrays.stream(rule.getRequiredProductIds().split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            if (productIdsInCart.containsAll(required)) {
                // Calculate discount: sum of price of required items * percentage
                BigDecimal qualifyingTotal = items.stream()
                    .filter(i -> required.contains(i.getProduct().getId()))
                    .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal amount = qualifyingTotal.multiply(BigDecimal.valueOf(rule.getDiscountPercentage() / 100.0));

                DiscountApplication app = new DiscountApplication();
                app.setCart(cart);
                app.setBundleRule(rule);
                app.setDiscountAmount(amount);
                results.add(discountRepo.save(app));
            }
        }
        return results;
    }
}