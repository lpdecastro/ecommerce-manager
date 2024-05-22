package com.lpdecastro.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellersResponseDto {

    private int total;
    private List<SellerDto> sellers;

    public SellersResponseDto(List<SellerDto> sellers) {
        this(sellers.size(), sellers);
    }
}
