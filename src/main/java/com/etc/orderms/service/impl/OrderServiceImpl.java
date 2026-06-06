package com.etc.orderms.service.impl;

import com.etc.orderms.dto.CreateOrderRequest;
import com.etc.orderms.dto.OrderResponse;
import com.etc.orderms.entity.Order;
import com.etc.orderms.entity.OrderStatus;
import com.etc.orderms.repository.OrderRepository;
import com.etc.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {

        Order order = Order.builder()
                .productName(request.getProductName())
                .amount(request.getAmount())
                .encryptedCardNumber(request.getCardNumber())
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        Order saved = orderRepository.save(order);

        return map(saved);
    }

    @Override
    public OrderResponse getOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Order not found"));
        return map(order);
    }

    private OrderResponse map(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .productName(order.getProductName())
                .amount(order.getAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
