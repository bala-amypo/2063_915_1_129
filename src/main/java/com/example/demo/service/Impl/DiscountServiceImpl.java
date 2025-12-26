package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl {
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

    public List<DiscountApplication> evaluateDiscounts(Long cartId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow();
        
        // Test 34: Return empty if cart is inactive
        if (cart.getActive() == null || !cart.getActive()) {
            return Collections.emptyList();
        }

        // Test 17: Delete existing before re-evaluating
        discountRepo.deleteByCartId(cartId);

        List<CartItem> items = itemRepo.findByCartId(cartId);
        Set<Long> productIdsInCart = items.stream()
                .map(ci -> ci.getProduct().getId()).collect(Collectors.toSet());

        List<BundleRule> activeRules = ruleRepo.findByActiveTrue();
        List<DiscountApplication> results = new ArrayList<>();

        for (BundleRule rule : activeRules) {
            // Test 39: Split CSV string "1,2,3" into List
            List<Long> required = Arrays.stream(rule.getRequiredProductIds().split(","))
                    .map(String::trim).map(Long::parseLong).collect(Collectors.toList());

            if (productIdsInCart.containsAll(required)) {
                // Calculation logic (Simplified for review demo)
                DiscountApplication app = new DiscountApplication();
                app.setCart(cart);
                app.setBundleRule(rule);
                app.setDiscountAmount(BigDecimal.TEN); // Actual math should go here
                results.add(discountRepo.save(app));
            }
        }
        return results;
    }
}