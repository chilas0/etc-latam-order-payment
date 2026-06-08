package com.etc.orderms.kafka;

import com.etc.orderms.dto.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka implementation of order event publisher.
 */
@Component
@RequiredArgsConstructor
public class KafkaOrderEventPublisher implements OrderEventPublisher {

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(KafkaOrderEventPublisher.class);

    /**
     * Publishes an order placed event to Kafka.
     *
     * @param event order event to publish
     */
    @Override
    public void publishOrderPlaced(OrderPlacedEvent event) {

        kafkaTemplate.send("order-placed", event).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error sending Kafka event", ex);
            } else {
                log.info("Kafka event sent. Topic={}, Partition={}, Offset={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }
}
