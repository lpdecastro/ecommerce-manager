package com.lpdecastro.ecommerce.repository;

import com.lpdecastro.ecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
