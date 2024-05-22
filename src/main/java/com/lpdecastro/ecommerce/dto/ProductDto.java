package com.lpdecastro.ecommerce.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {

    private long productId;
    private String productName;
    private double price;
    private String description;
    private String manufacturer;
    private int quantity;
    private String category;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private SellerDto seller;
}
