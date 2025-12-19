package com.example.demo.repository;


import com.example.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface BundleRuleRepository extends JpaRepository<BundleRule, Long> {
List<BundleRule> findByActiveTrue();
}