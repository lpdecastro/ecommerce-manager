package com.lpdecastro.ecommerce.repository;

import com.lpdecastro.ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByProduct_ProductId(long productId);
}
