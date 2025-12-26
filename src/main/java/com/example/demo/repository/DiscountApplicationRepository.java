package com.example.demo.repository;

import com.example.demo.model.DiscountApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface DiscountApplicationRepository extends JpaRepository<DiscountApplication, Long> {
    
    // Test 60
    List<DiscountApplication> findByCartId(Long cartId);

    // Required for cleaning up before evaluation
    @Transactional
    void deleteByCartId(Long cartId);
}