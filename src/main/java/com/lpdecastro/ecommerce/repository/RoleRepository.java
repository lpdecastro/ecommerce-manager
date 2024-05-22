package com.lpdecastro.ecommerce.repository;

import com.lpdecastro.ecommerce.entity.Role;
import com.lpdecastro.ecommerce.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
