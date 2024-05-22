package com.lpdecastro.ecommerce.service;

import com.lpdecastro.ecommerce.dto.*;

public interface CartItemService {

    CartItemsResponseDto getCartItems();
    CartItemDto getCartItemById(long cartItemId);
    CartItemsResponseDto addCartItem(AddCartItemRequestDto addCartItemRequestDto);
    CartItemDto updateCartItem(long cartItemId, CartItemRequestDto cartItemRequestDto);
    ResponseDto removeCartItemById(long cartItemId);
    ResponseDto clearCartItems();
}
