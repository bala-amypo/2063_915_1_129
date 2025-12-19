package com.example.demo.repository;


import com.example.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
List<CartItem> findByCartId(Long cartId);
}