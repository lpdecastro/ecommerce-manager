package com.lpdecastro.ecommerce.repository;

import com.lpdecastro.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
