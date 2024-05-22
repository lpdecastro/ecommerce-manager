package com.lpdecastro.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsResponseDto {

    private int total;
    private List<ProductViewDto> products;

    public ProductsResponseDto(List<ProductViewDto> products) {
        this(products.size(), products);
    }
}
