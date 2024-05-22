package com.lpdecastro.ecommerce.service;

import com.lpdecastro.ecommerce.dto.*;

public interface AuthenticationService {

    CustomerDto registerCustomer(RegisterCustomerRequestDto registerCustomerRequestDto);
    SellerDto registerSeller(RegisterSellerRequestDto registerSellerRequestDto);
    AccessTokenDto login(LoginRequestDto loginRequestDto);
    ResponseDto logout(String token);
}
