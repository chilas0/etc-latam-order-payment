package com.etc.orderms.controller;

import com.etc.orderms.dto.CreateOrderRequest;
import com.etc.orderms.dto.OrderResponse;
import com.etc.orderms.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(
            @Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(
            @PathVariable Long id) {
        return orderService.getOrder(id);
    }
}
