package com.lpdecastro.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomersResponseDto {

    private int total;
    private List<CustomerViewDto> customers;

    public CustomersResponseDto(List<CustomerViewDto> customers) {
        this(customers.size(), customers);
    }
}
