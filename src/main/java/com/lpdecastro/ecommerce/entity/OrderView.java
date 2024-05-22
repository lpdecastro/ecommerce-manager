package com.lpdecastro.ecommerce.entity;

import java.time.LocalDate;

public interface OrderView {

    long getOrderId();
    LocalDate getOrderDate();
    String getOrderStatus();
    double getTotal();
}
