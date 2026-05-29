package com.assignment.producer.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.assignment.producer.model.PendingEvent;
import com.assignment.producer.model.RetryStatus;
import com.assignment.producer.repo.PendingEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

import static com.assignment.producer.util.Constants.FOUND_EVENT_LOG;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaRetryScheduler {

    private final PendingEventRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Retry sending failed Kafka messages every 60 seconds
     */
    @Scheduled(fixedDelay = 60000)
    public void retryPendingEvents() {

        List<PendingEvent> pendingEvents = repository.findByStatus(RetryStatus.FAILED);
        log.info(FOUND_EVENT_LOG, pendingEvents.size());
        for (PendingEvent event : pendingEvents) {
            try {
                kafkaTemplate.send("event-topic", event.getMessage());
                event.setStatus(RetryStatus.SENT);
            } catch (Exception ex) {
                event.setRetryCount(event.getRetryCount() + 1);
            }
            repository.save(event);
        }
    }
}
