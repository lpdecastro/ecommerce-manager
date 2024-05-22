package com.lpdecastro.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenDto {

    private String accessToken;
    private String tokenType;
    private long expiresIn;
}
