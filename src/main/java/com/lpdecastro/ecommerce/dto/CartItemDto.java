package com.lpdecastro.ecommerce.dto;

import lombok.Data;

@Data
public class CartItemDto {

    private long cartItemId;
    private int quantity;
    private ProductDto product;
}
