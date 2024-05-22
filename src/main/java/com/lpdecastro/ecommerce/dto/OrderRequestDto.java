package com.lpdecastro.ecommerce.dto;

import com.lpdecastro.ecommerce.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDto {

    @NotNull
    private OrderStatus orderStatus;
}
