package com.etc.orderms.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Event published when a new order is created.
 */
@Data
@Builder
public class OrderPlacedEvent {

    private Long orderId;

    private BigDecimal amount;

    private String encryptedCardNumber;
}
