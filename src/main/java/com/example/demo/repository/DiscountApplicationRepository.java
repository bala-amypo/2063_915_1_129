package com.example.demo.repository;

import com.example.demo.model.DiscountApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DiscountApplicationRepository extends JpaRepository<DiscountApplication, Long> {
    // Clears old applications before recalculating[cite: 152].
    void deleteByCartId(Long cartId);

    // Loads applications for a cart in HQL tests[cite: 154].
    List<DiscountApplication> findByCartId(Long cartId);
}