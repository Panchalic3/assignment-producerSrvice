package com.assignment.producer.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import java.util.HashMap;
import java.util.Map;
import static com.assignment.producer.util.Constants.LOCALHOST_9092;

/**
 * Configuration class for Kafka Producer setup.
 *
 * Defines beans required to publish messages to Kafka:
 * ProducerFactory - Configures Kafka producer properties
 * KafkaTemplate - Used to send messages to Kafka topics
 *
 * Uses String serialization for both key and value.
 */

@Configuration
public class KafkaProducerConfig {

    /**
     * Creates Kafka producer instances used to send messages for Kafka producers.
     *
     * BOOTSTRAP_SERVERS_CONFIG - Kafka broker host
     * KEY_SERIALIZER_CLASS_CONFIG - Serializes key as String
     * VALUE_SERIALIZER_CLASS_CONFIG - Serializes value as JSON String
     *
     * @return ProducerFactory<String, String>
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {

        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, LOCALHOST_9092);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);          // fail fast (5 sec)
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);    // request timeout
        configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 10000);  // total retry window

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Creates KafkaTemplate to publish messages to Kafka topics.
     * Key = String, Value = JSON String.
     *
     * @return KafkaTemplate instance
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
