package com.etc.orderms.config;

import com.etc.orderms.dto.PaymentProcessedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka consumer configuration for OrderMS.
 */
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Creates the consumer factory used to deserialize payment processed events.
     *
     * @return configured consumer factory
     */
    @Bean
    public ConsumerFactory<String, PaymentProcessedEvent>
    paymentProcessedConsumerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );

        config.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "order-ms-group"
        );

        config.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                ErrorHandlingDeserializer.class
        );

        config.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                ErrorHandlingDeserializer.class
        );

        config.put(
                ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS,
                StringDeserializer.class
        );

        config.put(
                ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS,
                JsonDeserializer.class
        );

        config.put(
                JsonDeserializer.TRUSTED_PACKAGES,
                "*"
        );

        config.put(
                JsonDeserializer.VALUE_DEFAULT_TYPE,
                PaymentProcessedEvent.class.getName()
        );

        config.put(
                JsonDeserializer.USE_TYPE_INFO_HEADERS,
                false
        );

        return new DefaultKafkaConsumerFactory<>(config);
    }

    /**
     * Creates the listener container factory used by payment processed listeners.
     *
     * @return configured Kafka listener container factory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentProcessedEvent>
    paymentProcessedKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, PaymentProcessedEvent>
                factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(paymentProcessedConsumerFactory());

        return factory;
    }
}