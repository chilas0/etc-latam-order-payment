package com.etc.orderms.kafka;

import com.etc.orderms.dto.PaymentProcessedEvent;
import com.etc.orderms.entity.OrderStatus;
import com.etc.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumes payment processed events and updates order status.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentProcessedConsumer {

    private final OrderService orderService;

    /**
     * Consumes payment processed events from Kafka.
     *
     * @param event payment processed event
     */
    @KafkaListener(
            topics = "payment-processed",
            groupId = "order-ms-group",
            containerFactory = "paymentProcessedKafkaListenerContainerFactory"
    )
    public void consume(
            PaymentProcessedEvent event) {

        log.info(
                "Received payment processed event: {}",
                event
        );

        OrderStatus status =
                OrderStatus.valueOf(event.getStatus());

        orderService.updateOrderStatus(
                event.getOrderId(),
                status
        );

        log.info(
                "Order {} status updated to {}",
                event.getOrderId(),
                status
        );
    }
}