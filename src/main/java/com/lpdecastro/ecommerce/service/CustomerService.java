package com.lpdecastro.ecommerce.service;

import com.lpdecastro.ecommerce.dto.*;
import com.lpdecastro.ecommerce.entity.AddressType;

public interface CustomerService {

    CustomerDto getCurrentCustomer();
    CustomerDto getCustomerById(long customerId);
    CustomersResponseDto getCustomers();
    CustomerDto updateCurrentCustomer(CustomerRequestDto customerRequestDto);
    ResponseDto updateCurrentCustomerPassword(ChangePasswordRequestDto changePasswordRequestDto);
    AddressDto updateCurrentCustomerAddress(AddressType type, AddressRequestDto addressRequestDto);
    CreditCardDto updateCurrentCustomerCreditCard(long creditCardId, CreditCardRequestDto creditCardRequestDto);
    ResponseDto deleteCurrentCustomerAddress(AddressType type);
    ResponseDto deleteCurrentCustomerCreditCard(long creditCardId);
    ResponseDto deleteCustomerById(long customerId);
}
