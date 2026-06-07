package com.etc.orderms.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Request payload used to create a new order.
 */
@Data
public class CreateOrderRequest {

    /**
     * Product name.
     */
    @NotBlank(message = "Product name is required")
    private String productName;

    /**
     * Order amount.
     */
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    /**
     * Credit card number.
     */
    @NotBlank(message = "Card number is required")
    private String cardNumber;
}
