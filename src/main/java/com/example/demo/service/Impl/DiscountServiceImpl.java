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
        if (cart == null || !cart.getActive()) return Collections.emptyList();
        discountRepo.deleteByCartId(cartId);
        List<CartItem> items = itemRepo.findByCartId(cartId);
        List<BundleRule> rules = ruleRepo.findByActiveTrue();
        List<DiscountApplication> applications = new ArrayList<>();
        Map<Long, CartItem> itemMap = items.stream()
            .collect(Collectors.toMap(i -> i.getProduct().getId(), i -> i));
        for (BundleRule rule : rules) {
            String[] requiredIds = rule.getRequiredProductIds().split(",");
            boolean match = true;
            BigDecimal totalQualifyingPrice = BigDecimal.ZERO;
            for (String idStr : requiredIds) {
                Long prodId = Long.parseLong(idStr.trim());
                if (!itemMap.containsKey(prodId)) {
                    match = false;
                    break;
                }
                Product p = itemMap.get(prodId).getProduct();
                int qty = itemMap.get(prodId).getQuantity();
                totalQualifyingPrice = totalQualifyingPrice.add(p.getPrice().multiply(new BigDecimal(qty)));
            }
            if (match) {
                BigDecimal discount = totalQualifyingPrice.multiply(BigDecimal.valueOf(rule.getDiscountPercentage() / 100.0));
                DiscountApplication app = new DiscountApplication();
                app.setCart(cart);
                app.setBundleRule(rule);
                app.setDiscountAmount(discount);
                applications.add(discountRepo.save(app));
            }
        }
        return applications;
    }
}