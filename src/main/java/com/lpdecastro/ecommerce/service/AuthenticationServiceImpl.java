package com.lpdecastro.ecommerce.service;

import com.lpdecastro.ecommerce.dto.*;
import com.lpdecastro.ecommerce.entity.*;
import com.lpdecastro.ecommerce.exception.AppException;
import com.lpdecastro.ecommerce.repository.CartRepository;
import com.lpdecastro.ecommerce.repository.CustomerRepository;
import com.lpdecastro.ecommerce.repository.RoleRepository;
import com.lpdecastro.ecommerce.repository.SellerRepository;
import com.lpdecastro.ecommerce.security.JwtTokenBlacklist;
import com.lpdecastro.ecommerce.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static com.lpdecastro.ecommerce.config.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final SellerRepository sellerRepository;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;
    private final JwtTokenBlacklist jwtTokenBlacklist;

    @Transactional
    @Override
    public CustomerDto registerCustomer(RegisterCustomerRequestDto registerCustomerRequestDto) {
        validateCustomerAttributes(registerCustomerRequestDto.getMobile(), registerCustomerRequestDto.getEmail(),
                registerCustomerRequestDto.getUsername());

        Role role = roleRepository.findByName(RoleName.CUSTOMER)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, ROLE_NOT_EXISTS));

        Customer customer = convertToEntity(registerCustomerRequestDto);
        customer.setPassword(passwordEncoder.encode(registerCustomerRequestDto.getPassword()));
        customer.setRole(role);
        customer.setCart(cartRepository.save(new Cart()));

        return convertToDto(customerRepository.save(customer));
    }

    @Override
    public SellerDto registerSeller(RegisterSellerRequestDto registerSellerRequestDto) {
        validateSellerAttributes(registerSellerRequestDto.getMobile(), registerSellerRequestDto.getEmail(),
                registerSellerRequestDto.getUsername());

        Role role = roleRepository.findByName(RoleName.SELLER)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, ROLE_NOT_EXISTS));

        Seller seller = convertToEntity(registerSellerRequestDto);
        seller.setPassword(passwordEncoder.encode(registerSellerRequestDto.getPassword()));
        seller.setRole(role);

        return convertToDto(sellerRepository.save(seller));
    }

    @Override
    public AccessTokenDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(), loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenProvider.generateToken(authentication);
        long expirationInMs = jwtTokenProvider.getExpirationInMs();

        return new AccessTokenDto(accessToken, "Bearer", expirationInMs / 1000);
    }

    @Override
    public ResponseDto logout(String token) {
        jwtTokenBlacklist.addToBlacklist(token);
        return new ResponseDto(true);
    }

    private void validateCustomerAttributes(String mobile, String email, String username) {
        boolean isMobileExists = StringUtils.hasText(mobile) && customerRepository.existsByMobile(mobile);
        boolean isEmailExists = StringUtils.hasText(email) && customerRepository.existsByEmail(email);
        boolean isUsernameExists = StringUtils.hasText(username) && customerRepository.existsByUsername(username);

        validateAttributes(isMobileExists, isEmailExists, isUsernameExists);
    }

    private void validateSellerAttributes(String mobile, String email, String username) {
        boolean isMobileExists = StringUtils.hasText(mobile) && sellerRepository.existsByMobile(mobile);
        boolean isEmailExists = StringUtils.hasText(email) && sellerRepository.existsByEmail(email);
        boolean isUsernameExists = StringUtils.hasText(username) && sellerRepository.existsByUsername(username);

        validateAttributes(isMobileExists, isEmailExists, isUsernameExists);
    }

    private void validateAttributes(boolean isMobileExists, boolean isEmailExists, boolean isUsernameExists) {
        if (isMobileExists) {
            throw new AppException(HttpStatus.CONFLICT, MOBILE_ALREADY_EXISTS);
        }
        if (isEmailExists) {
            throw new AppException(HttpStatus.CONFLICT, EMAIL_ALREADY_EXISTS);
        }
        if (isUsernameExists) {
            throw new AppException(HttpStatus.CONFLICT, USERNAME_ALREADY_EXISTS);
        }
    }

    private Customer convertToEntity(RegisterCustomerRequestDto registerCustomerRequestDto) {
        return modelMapper.map(registerCustomerRequestDto, Customer.class);
    }

    private Seller convertToEntity(RegisterSellerRequestDto sellerDto) {
        return modelMapper.map(sellerDto, Seller.class);
    }

    private CustomerDto convertToDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    private SellerDto convertToDto(Seller seller) {
        return modelMapper.map(seller, SellerDto.class);
    }
}
