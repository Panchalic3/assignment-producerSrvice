package com.assignment.producer.repo;

import com.assignment.producer.model.PendingEvent;
import com.assignment.producer.model.RetryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PendingEventRepository extends JpaRepository<PendingEvent, Long> {

    List<PendingEvent> findByStatus(RetryStatus status);
}