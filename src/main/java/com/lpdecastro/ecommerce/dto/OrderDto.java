package com.lpdecastro.ecommerce.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto {

    private long orderId;
    private LocalDate orderDate;
    private String orderStatus;
    private double total;
    private CustomerViewDto customer;
    private AddressDto address;
    private CreditCardDto creditCard;
    private List<CartItemViewDto> cartItems;
}
