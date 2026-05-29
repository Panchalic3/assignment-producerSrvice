package com.assignment.producer.service;

import com.assignment.producer.model.EventPayload;
import com.assignment.producer.util.EventMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.assignment.producer.util.Constants.*;

/**
 * Service responsible for sending event messages to Kafka.
 */
@Slf4j
@Service
@AllArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final EventMapper eventMapper;

    /**
     * Converts payload to JSON and publishes it to Kafka.
     *
     * @param payloadParam event data to send
     * @throws RuntimeException if message publishing fails
     */
    public void sendMessage(EventPayload payloadParam) {
        EventPayload event = eventMapper.mapToEvent(payloadParam);

        log.info(INPUT_DATA_INGESTED_LOG, event.getEventId());

        try {
            String message = this.objectMapper.writeValueAsString(event);
            this.kafkaTemplate.send(TOPIC, message);
            log.info(MSG_SUCCESSFULLY_SENT_TO_KAFKA_LOG, event.getEventId());

        }
        catch (Exception exception) {
            log.error(KAFKA_SEND_ERROR_LOG, event.getEventId(), exception.getClass());
            throw new RuntimeException(KAFKA_SEND_ERROR, exception);
        }
    }
}