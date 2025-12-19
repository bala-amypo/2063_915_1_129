package com.example.demo.repository;


import com.example.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface CartRepository extends JpaRepository<Cart, Long> {
Optional<Cart> findByUserIdAndActiveTrue(Long userId);
}