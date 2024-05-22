package com.lpdecastro.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterSellerRequestDto extends SellerRequestDto {

    @NotBlank
    private String password;
}
