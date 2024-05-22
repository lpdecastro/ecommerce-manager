package com.lpdecastro.ecommerce.repository;

import com.lpdecastro.ecommerce.entity.Order;
import com.lpdecastro.ecommerce.entity.OrderView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT o.orderId AS orderId, o.orderDate AS orderDate, o.orderStatus AS orderStatus, " +
            "o.total AS total FROM Order o " +
            "WHERE (:startDateTime IS NULL OR o.createdAt >= :startDateTime)")
    List<OrderView> getAllOrders(@Param("startDateTime") LocalDateTime startDateTime);

    @Query(value = "SELECT o.orderId AS orderId, o.orderDate AS orderDate, o.orderStatus AS orderStatus, " +
            "o.total AS total FROM Order o " +
            "INNER JOIN Customer cus ON cus.customerId = o.customer.customerId " +
            "WHERE cus.customerId = :customerId " +
            "AND (:startDateTime IS NULL OR o.createdAt >= :startDateTime)")
    List<OrderView> getAllOrdersByCustomerId(@Param("customerId") long customerId,
                                             @Param("startDateTime") LocalDateTime startDateTime);
}
