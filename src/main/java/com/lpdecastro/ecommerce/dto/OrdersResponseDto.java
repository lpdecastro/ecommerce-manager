package com.lpdecastro.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersResponseDto {

    private int total;
    private List<OrderViewDto> orders;

    public OrdersResponseDto(List<OrderViewDto> orders) {
        this(orders.size(), orders);
    }
}
