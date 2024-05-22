package com.lpdecastro.ecommerce.dto;

import lombok.Data;

@Data
public class ProductViewDto {

    private long productId;
    private String productName;
    private int quantity;
    private String status;
}
