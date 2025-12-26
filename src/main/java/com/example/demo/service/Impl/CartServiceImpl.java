package com.example.demo.service.impl;

import com.example.demo.model.Cart;
import com.example.demo.repository.CartRepository;
import com.example.demo.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service // FIXES: "Consider defining a bean of type CartService"
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart createCart(Long userId) {
        // Business Logic: Check if an active cart already exists to avoid duplicates
        Optional<Cart> existingCart = cartRepository.findByUserIdAndActiveTrue(userId);
        if (existingCart.isPresent()) {
            return existingCart.get();
        }

        // Test 13: Create a new cart if none is active
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setActive(true); // Test 31: Default should be true
        return cartRepository.save(cart);
    }

    @Override
    public Cart getActiveCartForUser(Long userId) {
        // Test 14: Throw exception if no active cart is found
        return cartRepository.findByUserIdAndActiveTrue(userId)
                .orElseThrow(() -> new EntityNotFoundException("Active cart not found for user: " + userId));
    }
}