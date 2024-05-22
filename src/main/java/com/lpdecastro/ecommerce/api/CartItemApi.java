package com.lpdecastro.ecommerce.api;

import com.lpdecastro.ecommerce.dto.CartItemDto;
import com.lpdecastro.ecommerce.dto.AddCartItemRequestDto;
import com.lpdecastro.ecommerce.dto.CartItemsResponseDto;
import com.lpdecastro.ecommerce.dto.ResponseDto;
import com.lpdecastro.ecommerce.service.CartItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/.rest/cart/items")
@RequiredArgsConstructor
public class CartItemApi {

    private final CartItemService cartItemService;

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartItemsResponseDto getCartItems() {
        return cartItemService.getCartItems();
    }

    @GetMapping("/{cartItemId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartItemDto getCartItemById(@PathVariable Long cartItemId) {
        return cartItemService.getCartItemById(cartItemId);
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartItemsResponseDto addCartItem(@Valid @RequestBody AddCartItemRequestDto addCartItemRequestDto) {
        return cartItemService.addCartItem(addCartItemRequestDto);
    }

    @PutMapping("/{cartItemId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartItemDto updateCartItem(@PathVariable Long cartItemId,
                                      @Valid @RequestBody AddCartItemRequestDto addCartItemRequestDto) {
        return cartItemService.updateCartItem(cartItemId, addCartItemRequestDto);
    }

    @DeleteMapping("/{cartItemId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseDto removeCartItemById(@PathVariable long cartItemId) {
        return cartItemService.removeCartItemById(cartItemId);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseDto clearCartItems() {
        return cartItemService.clearCartItems();
    }
}
