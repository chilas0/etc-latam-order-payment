package com.etc.orderms.entity;

/**
 * Represents the current state of an order.
 */
public enum OrderStatus {

    /**
     * Order created and awaiting payment.
     */
    PENDING,
    /**
     * Payment completed successfully.
     */
    PAID,
    /**
     * Payment processing failed.
     */
    FAILED_PAYMENT
}
