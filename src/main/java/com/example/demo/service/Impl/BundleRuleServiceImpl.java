package com.example.demo.service.impl;

import com.example.demo.model.BundleRule;
import com.example.demo.repository.BundleRuleRepository;
import com.example.demo.service.BundleRuleService;
import org.springframework.stereotype.Service;

@Service
public class BundleRuleServiceImpl implements BundleRuleService {
    private final BundleRuleRepository bundleRuleRepository;

    public BundleRuleServiceImpl(BundleRuleRepository bundleRuleRepository) {
        this.bundleRuleRepository = bundleRuleRepository;
    }

    @Override
    public BundleRule createRule(BundleRule rule) {
        if (rule.getDiscountPercentage() < 0 || rule.getDiscountPercentage() > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100"); [cite: 71, 72, 197]
        }
        if (rule.getRequiredProductIds() == null || rule.getRequiredProductIds().trim().isEmpty()) {
            throw new IllegalArgumentException("Required products cannot be empty"); [cite: 74, 197]
        }
        return bundleRuleRepository.save(rule);
    }
}