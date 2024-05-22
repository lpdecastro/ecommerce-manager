package com.lpdecastro.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsResponseDto {

    private int total;
    private List<CartItemViewDto> cartItems;

    public CartItemsResponseDto(List<CartItemViewDto> cartItems) {
        this(cartItems.size(), cartItems);
    }
}
