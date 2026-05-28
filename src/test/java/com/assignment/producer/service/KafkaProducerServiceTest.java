package com.assignment.producer.service;

import com.assignment.producer.model.EventPayload;
import com.assignment.producer.util.EventMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static com.assignment.producer.util.Constants.KAFKA_SEND_ERROR;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private EventMapper eventMapper;
    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    private EventPayload inputPayload;
    private EventPayload mappedEvent;

    @BeforeEach
    void setUp() {
        inputPayload = new EventPayload();
        inputPayload.setUserName("Panchali");

        mappedEvent = new EventPayload();
        mappedEvent.setEventId("123");
    }

    @Test
    void shouldSendMessageSuccessfully() throws Exception {

        when(eventMapper.mapToEvent(inputPayload)).thenReturn(mappedEvent);
        when(objectMapper.writeValueAsString(mappedEvent)).thenReturn("json");

        kafkaProducerService.sendMessage(inputPayload);

        verify(kafkaTemplate).send(anyString(), eq("json"));
    }

    @Test
    void shouldThrowException_whenJsonConversionFails() throws Exception {

        when(eventMapper.mapToEvent(inputPayload)).thenReturn(mappedEvent);

        when(objectMapper.writeValueAsString(mappedEvent))
                .thenThrow(new RuntimeException("JSON error"));

        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> kafkaProducerService.sendMessage(inputPayload)
        );

        Assertions.assertEquals(KAFKA_SEND_ERROR, exception.getMessage());
    }
}