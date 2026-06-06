package com.etc.orderms.dto;

import com.etc.orderms.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {

    private Long id;

    private String productName;

    private BigDecimal amount;

    private OrderStatus status;

    private LocalDateTime createdAt;
}
