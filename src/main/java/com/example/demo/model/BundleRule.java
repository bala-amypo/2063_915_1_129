package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class BundleRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    [cite_start]private Long id; [cite: 62]
    [cite_start]private String ruleName; [cite: 64]
    private String requiredProductIds; [cite_start]// CSV string [cite: 66, 76]
    [cite_start]private Double discountPercentage; [cite: 67]
    [cite_start]private Boolean active = true; [cite: 68]

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    public String getRequiredProductIds() { return requiredProductIds; }
    public void setRequiredProductIds(String requiredProductIds) { this.requiredProductIds = requiredProductIds; }
    public Double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(Double discountPercentage) { this.discountPercentage = discountPercentage; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}