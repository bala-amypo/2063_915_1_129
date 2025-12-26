package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CartItemServiceImpl {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public CartItemServiceImpl(CartItemRepository cir, CartRepository cr) {
        this.cartItemRepository = cir;
        this.cartRepository = cr;
    }

    public CartItem addItemToCart(CartItem item) {
        Cart cart = cartRepository.findById(item.getCart().getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Test 33: Block inactive carts
        if (!cart.getActive()) {
            throw new IllegalArgumentException("active carts"); 
        }

        // Test 32: Positive quantity check
        if (item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        // Test 16: Aggregation logic (merge instead of duplicate)
        Optional<CartItem> existing = cartItemRepository.findByCartIdAndProductId(
                item.getCart().getId(), item.getProduct().getId());

        if (existing.isPresent()) {
            CartItem ci = existing.get();
            ci.setQuantity(ci.getQuantity() + item.getQuantity());
            return cartItemRepository.save(ci);
        }
        return cartItemRepository.save(item);
    }
}