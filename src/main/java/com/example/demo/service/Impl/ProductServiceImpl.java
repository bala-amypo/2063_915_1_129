package com.example.demo.service.impl;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        [cite_start]this.productRepository = productRepository; [cite: 183]
    }

    @Override
    public Product createProduct(Product product) {
        if (productRepository.findBySku(product.getSku()).isPresent()) {
            [cite_start]throw new IllegalArgumentException("SKU already exists"); [cite: 185, 186]
        }
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            [cite_start]throw new IllegalArgumentException("Price must be positive"); [cite: 187]
        }
        [cite_start]return productRepository.save(product); [cite: 134]
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                [cite_start].orElseThrow(() -> new EntityNotFoundException("Product not found")); [cite: 160, 189]
    }

    @Override
    public void deactivateProduct(Long id) {
        Product p = getProductById(id);
        [cite_start]p.setActive(false); [cite: 191]
        productRepository.save(p);
    }
    
    @Override
    public Product updateProduct(Long id, Product product) {
        Product existing = getProductById(id);
        existing.setName(product.getName());
        [cite_start]existing.setPrice(product.getPrice()); [cite: 188]
        return productRepository.save(existing);
    }
}