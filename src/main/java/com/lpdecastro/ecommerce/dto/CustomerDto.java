package com.lpdecastro.ecommerce.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomerDto {

    private long customerId;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AddressDto> addresses;
    private List<CreditCardDto> creditCards;
    private RoleDto role;
}
