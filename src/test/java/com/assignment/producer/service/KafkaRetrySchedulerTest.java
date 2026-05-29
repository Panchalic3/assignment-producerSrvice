package com.assignment.producer.service;

import com.assignment.producer.model.PendingEvent;
import com.assignment.producer.model.RetryStatus;
import com.assignment.producer.repo.PendingEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class KafkaRetrySchedulerTest {

    @Mock
    private PendingEventRepository repository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaRetryScheduler scheduler;

    @Test
    void shouldMarkEventAsSent_whenKafkaSendSucceeds() {

        PendingEvent event = new PendingEvent("msg", RetryStatus.FAILED, 0, null);

        when(repository.findByStatus(RetryStatus.FAILED))
                .thenReturn(List.of(event));
        scheduler.retryPendingEvents();

        assertEquals(RetryStatus.SENT, event.getStatus());
        verify(repository).save(event);
        verify(kafkaTemplate).send(anyString(), eq("msg"));
    }

    @Test
    void shouldIncrementRetryCount_whenKafkaFails() {

        PendingEvent event = new PendingEvent("msg", RetryStatus.FAILED, 0, null);

        when(repository.findByStatus(RetryStatus.FAILED))
                .thenReturn(List.of(event));

        doThrow(new RuntimeException("Kafka down"))
                .when(kafkaTemplate).send(anyString(), anyString());

        scheduler.retryPendingEvents();

        assertEquals(1, event.getRetryCount());
        assertEquals(RetryStatus.FAILED, event.getStatus()); // remains FAILED
        verify(repository).save(event);
    }

    @Test
    void shouldDoNothing_whenNoPendingEvents() {

        when(repository.findByStatus(RetryStatus.FAILED))
                .thenReturn(List.of());

        scheduler.retryPendingEvents();

        verify(kafkaTemplate, never()).send(anyString(), anyString());
        verify(repository, never()).save(any());
    }
}
