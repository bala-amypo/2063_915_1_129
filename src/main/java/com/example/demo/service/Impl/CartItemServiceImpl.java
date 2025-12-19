package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.CartItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepo, CartRepository cartRepo, ProductRepository prodRepo) {
        this.cartItemRepository = cartItemRepo;
        this.cartRepository = cartRepo;
        this.productRepository = prodRepo;
    }

    @Override
    public CartItem addItemToCart(CartItem item) {
        Cart cart = cartRepository.findById(item.getCart().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found")); [cite: 215]

        Product product = productRepository.findById(item.getProduct().getId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found")); [cite: 215]

        if (!cart.getActive()) {
            throw new IllegalArgumentException("Only active carts can accept items"); [cite: 87, 88, 217]
        }
        if (!product.getActive()) {
            throw new IllegalArgumentException("Product is inactive");
        }
        if (item.getQuantity() == null || item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        return cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + item.getQuantity());
                    return cartItemRepository.save(existing);
                })
                .orElseGet(() -> cartItemRepository.save(item));
    }

    @Override
    public List<CartItem> getItemsForCart(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }
}