package com.lpdecastro.ecommerce.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SellerDto {

    private long sellerId;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private RoleDto role;
}
