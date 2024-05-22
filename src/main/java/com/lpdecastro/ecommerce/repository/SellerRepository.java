package com.lpdecastro.ecommerce.repository;

import com.lpdecastro.ecommerce.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    boolean existsByMobile(String mobile);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Seller> findByUsername(String username);
    boolean existsByMobileAndSellerIdNot(String mobile, Long sellerId);
    boolean existsByEmailAndSellerIdNot(String email, Long sellerId);
    boolean existsByUsernameAndSellerIdNot(String username, Long sellerId);
}
