package com.lpdecastro.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreditCardRequestDto {

    @NotBlank
    private String cardNumber;

    @NotBlank
    private String cardHolderName;

    @NotNull
    private LocalDate expirationDate;

    @NotBlank
    private String cvc;
}
