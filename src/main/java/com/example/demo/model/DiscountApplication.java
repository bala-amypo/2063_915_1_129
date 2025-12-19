package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "discount_applications")
public class DiscountApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // [cite: 105]

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart; // [cite: 106]

    @ManyToOne
    @JoinColumn(name = "bundle_rule_id")
    private BundleRule bundleRule; // [cite: 107]

    private BigDecimal discountAmount; // [cite: 108]

    private LocalDateTime appliedAt = LocalDateTime.now(); // [cite: 109]

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
    public BundleRule getBundleRule() { return bundleRule; }
    public void setBundleRule(BundleRule bundleRule) { this.bundleRule = bundleRule; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }
    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }
}