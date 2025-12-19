package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DiscountService;
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

    public DiscountServiceImpl(DiscountApplicationRepository dr, BundleRuleRepository br, CartRepository cr, CartItemRepository ir) {
        this.discountRepo = dr;
        this.ruleRepo = br;
        this.cartRepo = cr;
        this.itemRepo = ir;
    }

    @Override
    @Transactional
    public List<DiscountApplication> evaluateDiscounts(Long cartId) {
        Cart cart = cartRepo.findById(cartId).orElse(null);
        if (cart == null || !cart.getActive()) return Collections.emptyList(); [cite: 229, 230]

        discountRepo.deleteByCartId(cartId); [cite: 152, 233, 234]

        List<CartItem> items = itemRepo.findByCartId(cartId); [cite: 231]
        List<BundleRule> rules = ruleRepo.findByActiveTrue(); [cite: 138, 232]

        List<DiscountApplication> applications = new ArrayList<>();
        Map<Long, CartItem> itemMap = items.stream()
            .collect(Collectors.toMap(i -> i.getProduct().getId(), i -> i));

        for (BundleRule rule : rules) {
            String[] requiredIds = rule.getRequiredProductIds().split(","); [cite: 66, 76]
            boolean match = true;
            BigDecimal totalQualifyingPrice = BigDecimal.ZERO;

            for (String idStr : requiredIds) {
                Long prodId = Long.parseLong(idStr.trim());
                if (!itemMap.containsKey(prodId)) {
                    match = false; [cite: 235]
                    break;
                }
                Product p = itemMap.get(prodId).getProduct();
                int qty = itemMap.get(prodId).getQuantity();
                totalQualifyingPrice = totalQualifyingPrice.add(p.getPrice().multiply(new BigDecimal(qty)));
            }

            if (match) {
                BigDecimal discount = totalQualifyingPrice.multiply(BigDecimal.valueOf(rule.getDiscountPercentage() / 100.0));

                DiscountApplication app = new DiscountApplication();
                app.setCart(cart); [cite: 111, 112]
                app.setBundleRule(rule); [cite: 111, 112]
                app.setDiscountAmount(discount); [cite: 115, 236]
                applications.add(discountRepo.save(app)); [cite: 153]
            }
        }
        return applications;
    }
}