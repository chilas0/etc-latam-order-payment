package com.etc.orderms.service;

import com.etc.orderms.dto.CreateOrderRequest;
import com.etc.orderms.dto.OrderResponse;
import com.etc.orderms.entity.OrderStatus;

/**
 * Business operations related to orders.
 */
public interface OrderService {

    /**
     * Creates a new order.
     *
     * @param request order data
     * @return created order
     */
    OrderResponse createOrder(CreateOrderRequest request);

    /**
     * Retrieves an order by id.
     *
     * @param id order identifier.
     * @return order information
     */
    OrderResponse getOrder(Long id);

    /**
     * Updates the status of an existing order.
     *
     * @param orderId order identifier
     * @param status new order status
     */
    void updateOrderStatus(Long orderId, OrderStatus status);

}
