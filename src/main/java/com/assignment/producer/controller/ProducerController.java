package com.assignment.producer.controller;

import com.assignment.producer.model.EventPayload;
import com.assignment.producer.service.KafkaProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.assignment.producer.util.Constants.MSG_SENT_TO_KAFKA;

/**
 * REST controller for producing events to Kafka.

 * Exposes APIs to accept event payloads from clients
 * and publish them to Kafka topics via KafkaProducerService.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ProducerController {

    private final KafkaProducerService kafkaProducerService;

    /**
     * Ingests event data and sends it to Kafka.
     *
     * @param payloadParam event data from client
     * @return success response
     */
    @PostMapping("/ingest")
    public ResponseEntity<String> ingest(@RequestBody @Valid EventPayload payloadParam) throws JsonProcessingException {
        this.kafkaProducerService.sendMessage(payloadParam);
        return ResponseEntity.ok(MSG_SENT_TO_KAFKA);
    }
}