package com.etc.orderms.kafka;

import com.etc.orderms.dto.OrderPlacedEvent;

/**
 * Publishes order related events.
 */
public interface OrderEventPublisher {

    void publishOrderPlaced(
            OrderPlacedEvent event);
}
