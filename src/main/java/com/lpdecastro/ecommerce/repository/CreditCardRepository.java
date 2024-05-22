package com.lpdecastro.ecommerce.repository;

import com.lpdecastro.ecommerce.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
