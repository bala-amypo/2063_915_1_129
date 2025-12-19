package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    [cite_start]private Long id; [cite: 95]
    
    @ManyToOne
    @JoinColumn(name = "cart_id")
    [cite_start]private Cart cart; [cite: 96]
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    [cite_start]private Product product; [cite: 97]
    
    [cite_start]private Integer quantity; [cite: 98]

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}