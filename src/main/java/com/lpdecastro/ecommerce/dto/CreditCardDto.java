package com.lpdecastro.ecommerce.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreditCardDto {

    private long creditCardId;
    private String cardNumber;
    private String cardHolderName;
    private LocalDate expirationDate;
    private String cvc;
}
