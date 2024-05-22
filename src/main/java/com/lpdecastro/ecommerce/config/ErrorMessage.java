package com.lpdecastro.ecommerce.config;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    MOBILE_ALREADY_EXISTS("error.mobile.exists"),
    EMAIL_ALREADY_EXISTS("error.email.exists"),
    USERNAME_ALREADY_EXISTS("error.username.exists"),
    USER_NOT_FOUND("error.user.not.found"),
    CURRENT_PASSWORD_IS_INCORRECT("error.password.incorrect"),
    ADDRESS_NOT_FOUND("error.address.not.found"),
    CREDIT_CARD_NOT_FOUND("error.credit.card.not.found"),
    PRODUCT_NOT_FOUND("error.product.not.found"),
    PRODUCT_OUT_OF_STOCK("error.product.out.of.stock"),
    PRODUCT_LIMITED_STOCK("error.product.limited.stock"),
    CART_NOT_FOUND("error.cart.not.found"),
    CART_ITEM_NOT_FOUND("error.cart.item.not.found"),
    PRODUCT_ALREADY_EXISTS_IN_CART("error.product.already.in.cart"),
    ORDER_NOT_FOUND("error.order.not.found"),
    ROLE_NOT_EXISTS("error.role.not.exists");

    private final String code;

    ErrorMessage(String code) {
        this.code = code;
    }
}
