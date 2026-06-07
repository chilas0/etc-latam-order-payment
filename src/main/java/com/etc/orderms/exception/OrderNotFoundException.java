package com.etc.orderms.exception;

/**
 * Exception thrown when an order cannot be found.
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Order not found with id " + id);
    }
}
