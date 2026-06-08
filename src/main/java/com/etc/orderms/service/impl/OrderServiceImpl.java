package com.etc.orderms.service.impl;

import com.etc.orderms.dto.CreateOrderRequest;
import com.etc.orderms.dto.OrderPlacedEvent;
import com.etc.orderms.dto.OrderResponse;
import com.etc.orderms.entity.Order;
import com.etc.orderms.entity.OrderStatus;
import com.etc.orderms.exception.OrderNotFoundException;
import com.etc.orderms.kafka.OrderEventPublisher;
import com.etc.orderms.repository.OrderRepository;
import com.etc.orderms.service.OrderService;
import com.etc.orderms.service.RsaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implementation of order business operations.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RsaService rsaService;
    private final OrderEventPublisher orderEventPublisher;
    /**
     * Creates an order and encrypts the card number before persistence.
     *
     * @param request order data
     * @return
     */
    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {

        System.out.println(request.getProductName());
        String encrytedCardNumber =
                rsaService.encrypt(request.getCardNumber());

        Order order = Order.builder()
                .productName(request.getProductName())
                .amount(request.getAmount())
                .encryptedCardNumber(encrytedCardNumber)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        Order saved = orderRepository.save(order);

        OrderPlacedEvent event =
                OrderPlacedEvent.builder()
                        .orderId(saved.getId())
                        .encryptedCardNumber(
                                saved.getEncryptedCardNumber())
                        .amount(saved.getAmount())
                        .build();

        orderEventPublisher.publishOrderPlaced(event);

        return map(saved);
    }

    /**
     * Retrieves an order by id.
     *
     * @param id order identifier.
     * @return
     */
    @Override
    public OrderResponse getOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                    new OrderNotFoundException(id));
        return map(order);
    }

    /**
     * Maps an Order entity to an OrderResponse DTO.
     *
     * @param order source entity
     * @return mapped response object
     */
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
