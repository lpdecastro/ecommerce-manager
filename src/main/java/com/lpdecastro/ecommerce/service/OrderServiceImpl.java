package com.lpdecastro.ecommerce.service;

import com.lpdecastro.ecommerce.dto.*;
import com.lpdecastro.ecommerce.entity.*;
import com.lpdecastro.ecommerce.exception.AppException;
import com.lpdecastro.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.lpdecastro.ecommerce.config.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;
    private final CreditCardRepository creditCardRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public OrdersResponseDto getOrders(LocalDate startDate) {
        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        return convertToDto(orderRepository.getAllOrders(startDateTime));
    }

    @Override
    public OrdersResponseDto getOrdersOfCurrentCustomer(LocalDate startDate) {
        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        return convertToDto(orderRepository.getAllOrdersByCustomerId(getCurrentCustomer().getCustomerId(),
                startDateTime));
    }

    @Override
    public OrderDto getOrderById(long orderId) {
        return convertToDto(getOrderEntityById(orderId));
    }

    @Transactional
    @Override
    public OrderDto addOrder(AddOrderRequestDto addOrderRequestDto) {
        Customer customer = getCurrentCustomer();
        Cart cart = customer.getCart();
        Address address = addressRepository.findById(addOrderRequestDto.getAddressId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, ADDRESS_NOT_FOUND));
        CreditCard creditCard = creditCardRepository.findById(addOrderRequestDto.getCreditCardId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, CREDIT_CARD_NOT_FOUND));
        List<CartItem> cartItems = cartRepository.findById(cart.getCartId()).map(Cart::getCartItems)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, CART_NOT_FOUND));
        List<CartItem> orderedCartItems = cartItems.stream().map(this::createNewCartItem).toList();

        Order newOrder = createNewOrder(customer, address, creditCard, orderedCartItems);

        updateProductQuantity(cartItems);
        clearCart(cart);

        return convertToDto(orderRepository.save(newOrder));
    }

    @Override
    public OrderDto updateOrder(long orderId, OrderRequestDto orderRequestDto) {
        Order order = getOrderEntityById(orderId);
        order.setOrderStatus(orderRequestDto.getOrderStatus());
        return convertToDto(orderRepository.save(order));
    }

    @Override
    public ResponseDto deleteOrderById(long orderId) {
        Order order = getOrderEntityById(orderId);
        orderRepository.delete(order);
        return new ResponseDto(true);
    }

    private CartItem createNewCartItem(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItem.class);
    }

    private Order createNewOrder(Customer customer, Address address, CreditCard creditCard, List<CartItem> cartItems) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.NEW);
        order.setOrderDate(LocalDate.now());
        order.setCustomer(customer);
        order.setAddress(address);
        order.setCreditCard(creditCard);
        order.setCartItems(cartItems);
        order.setTotal(computeTotal(cartItems));

        return order;
    }

    private void updateProductQuantity(List<CartItem> cartItems) {
        List<Product> products = cartItems.stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setQuantity(product.getQuantity() - cartItem.getQuantity());

                    if (product.getQuantity() <= 0) {
                        product.setStatus(ProductStatus.OUT_OF_STOCK);
                    }

                    return product;
                }).toList();

        productRepository.saveAll(products);
    }

    private void clearCart(Cart cart) {
        cart.setCartItems(new ArrayList<>());
        cartRepository.save(cart);
    }

    private Order getOrderEntityById(long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, ORDER_NOT_FOUND));
    }

    private Customer getCurrentCustomer() {
        return (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private double computeTotal(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> {
                    int quantity = cartItem.getQuantity();
                    double price = cartItem.getProduct().getPrice();
                    return quantity * price;
                })
                .reduce(0.0, Double::sum);

    }

    private OrdersResponseDto convertToDto(List<OrderView> orderViews) {
        List<OrderViewDto> orders = orderViews.stream().map(this::convertToDto).toList();
        return new OrdersResponseDto(orders);
    }

    private OrderViewDto convertToDto(OrderView orderView) {
        return modelMapper.map(orderView, OrderViewDto.class);
    }

    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
