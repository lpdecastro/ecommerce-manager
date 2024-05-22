package com.lpdecastro.ecommerce.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderViewDto {

    private long orderId;
    private LocalDate orderDate;
    private String orderStatus;
    private double total;
}
