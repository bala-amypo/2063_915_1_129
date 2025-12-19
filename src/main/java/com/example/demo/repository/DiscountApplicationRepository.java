package com.example.demo.repository;


import com.example.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface DiscountApplicationRepository extends JpaRepository<DiscountApplication, Long> {
void deleteByCartId(Long cartId);
List<DiscountApplication> findByCartId(Long cartId);
}