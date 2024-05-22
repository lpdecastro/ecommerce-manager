package com.lpdecastro.ecommerce.service;

import com.lpdecastro.ecommerce.dto.*;
import com.lpdecastro.ecommerce.entity.*;
import com.lpdecastro.ecommerce.exception.AppException;
import com.lpdecastro.ecommerce.repository.CartItemRepository;
import com.lpdecastro.ecommerce.repository.CartRepository;
import com.lpdecastro.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lpdecastro.ecommerce.config.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Override
    public CartItemsResponseDto getCartItems() {
        return convertToDto(getCurrentCart());
    }

    @Override
    public CartItemDto getCartItemById(long cartItemId) {
        return convertToDto(getCartItemEntityById(cartItemId));
    }

    @Override
    public CartItemsResponseDto addCartItem(AddCartItemRequestDto addCartItemRequestDto) {
        Cart cart = getCurrentCart();
        Product product = getProductById(addCartItemRequestDto.getProductId());

        validateProduct(product, addCartItemRequestDto.getQuantity(), cart);

        CartItem newCartItem = new CartItem();
        newCartItem.setQuantity(addCartItemRequestDto.getQuantity());
        newCartItem.setProduct(product);

        cart.getCartItems().add(newCartItem);

        return convertToDto(cartRepository.save(cart));
    }

    @Override
    public CartItemDto updateCartItem(long cartItemId, CartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = getCartItemEntityById(cartItemId);
        Product product = getProductById(cartItem.getProduct().getProductId());

        validateProductQuantity(product, cartItemRequestDto.getQuantity());

        cartItem.setQuantity(cartItemRequestDto.getQuantity());

        return convertToDto(cartItemRepository.save(cartItem));
    }

    @Override
    public ResponseDto removeCartItemById(long cartItemId) {
        Cart cart = getCurrentCart();
        cart.getCartItems().removeIf(cartItem -> cartItemId == cartItem.getCartItemId());
        cartRepository.save(cart);
        return new ResponseDto(true);
    }

    @Transactional
    @Override
    public ResponseDto clearCartItems() {
        Cart cart = getCurrentCart();
        cart.getCartItems().clear();
        cartRepository.save(cart);
        return new ResponseDto(true);
    }

    private Cart getCurrentCart() {
        Customer currentCustomer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return cartRepository.findById(currentCustomer.getCart().getCartId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, CART_ITEM_NOT_FOUND));
    }

    private CartItem getCartItemEntityById(long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, CART_ITEM_NOT_FOUND));
    }

    private Product getProductById(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND));
    }

    private void validateProduct(Product product, int quantity, Cart cart) {
        validateProductQuantity(product, quantity);

        boolean isProductExistsInCart = cart.getCartItems().stream()
                .anyMatch(cartItem -> cartItem.getProduct().getProductId() == product.getProductId());

        if (isProductExistsInCart) {
            throw new AppException(HttpStatus.CONFLICT, PRODUCT_ALREADY_EXISTS_IN_CART);
        }
    }

    private void validateProductQuantity(Product product, int requestedQuantity) {
        int productQuantity = product.getQuantity();

        if (product.getStatus() == ProductStatus.OUT_OF_STOCK || productQuantity == 0) {
            throw new AppException(HttpStatus.NOT_FOUND, PRODUCT_OUT_OF_STOCK);
        }
        if (requestedQuantity > productQuantity) {
            throw new AppException(HttpStatus.CONFLICT, PRODUCT_LIMITED_STOCK, new Object[]{ productQuantity });
        }
    }

    private CartItemsResponseDto convertToDto(Cart cart) {
        List<CartItemViewDto> cartItems = cart.getCartItems().stream().map(this::convertToViewDto).toList();
        return new CartItemsResponseDto(cartItems);
    }

    private CartItemViewDto convertToViewDto(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemViewDto.class);
    }

    private CartItemDto convertToDto(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDto.class);
    }
}
