package com.lpdecastro.ecommerce.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddCartItemRequestDto extends CartItemRequestDto {

    private long productId;
}
