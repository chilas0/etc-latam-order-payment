package com.etc.orderms.controller;

import com.etc.orderms.dto.CreateOrderRequest;
import com.etc.orderms.dto.OrderResponse;
import com.etc.orderms.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for order operations.
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Creates a new order.
     *
     * @param request order information
     * @return created order
     */
    @PostMapping
    public OrderResponse createOrder(
            @Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    /**
     * Retrieves an order by id.
     *
     * @param id order identifier
     * @return order details
     */
    @GetMapping("/{id}")
    public OrderResponse getOrder(
            @PathVariable Long id) {
        return orderService.getOrder(id);
    }
}
