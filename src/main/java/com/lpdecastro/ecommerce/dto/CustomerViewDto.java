package com.lpdecastro.ecommerce.dto;

import lombok.Data;

@Data
public class CustomerViewDto {

    private long customerId;
    private String username;
    private String mobile;
    private String email;
}
