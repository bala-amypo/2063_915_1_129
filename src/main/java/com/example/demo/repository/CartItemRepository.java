package com.example.demo.repository;

import com.example.demo.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Detects existing items to aggregate quantity[cite: 147].
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    // Loads all items for a specific cart[cite: 148].
    List<CartItem> findByCartId(Long cartId);

    // Advanced HQL/HCQL query used in repository tests[cite: 149].
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.quantity >= :minQuantity")
    List<CartItem> findByCartIdAndMinQuantity(@Param("cartId") Long cartId, @Param("minQuantity") Integer minQuantity);
}