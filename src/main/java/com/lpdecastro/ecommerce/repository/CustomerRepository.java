package com.lpdecastro.ecommerce.repository;

import com.lpdecastro.ecommerce.entity.Customer;
import com.lpdecastro.ecommerce.entity.CustomerView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByMobile(String mobile);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Customer> findByUsername(String username);

    @Query(value = "SELECT customerId AS customerId, username AS username, mobile AS mobile, email AS email " +
            "FROM Customer")
    List<CustomerView> getAll();

    boolean existsByMobileAndCustomerIdNot(String mobile, Long customerId);
    boolean existsByEmailAndCustomerIdNot(String email, Long customerId);
    boolean existsByUsernameAndCustomerIdNot(String username, Long customerId);
}
