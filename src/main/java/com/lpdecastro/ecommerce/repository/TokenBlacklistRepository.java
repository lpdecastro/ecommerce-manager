package com.lpdecastro.ecommerce.repository;

import com.lpdecastro.ecommerce.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, String> {
}
