package com.lpdecastro.ecommerce.security;

import com.lpdecastro.ecommerce.entity.Admin;
import com.lpdecastro.ecommerce.entity.Customer;
import com.lpdecastro.ecommerce.entity.Seller;
import com.lpdecastro.ecommerce.repository.AdminRepository;
import com.lpdecastro.ecommerce.repository.CustomerRepository;
import com.lpdecastro.ecommerce.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (customer.isPresent()) {
            return customer.get();
        }

        Optional<Seller> seller = sellerRepository.findByUsername(username);
        if (seller.isPresent()) {
            return seller.get();
        }

        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            return admin.get();
        }

        throw new UsernameNotFoundException("User not found");
    }
}
