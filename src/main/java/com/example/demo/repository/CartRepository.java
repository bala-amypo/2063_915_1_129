package com.example.demo.repository;

import com.example.demo.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // Required for Tests 13 and 14
    Optional<Cart> findByUserIdAndActiveTrue(Long userId);
}