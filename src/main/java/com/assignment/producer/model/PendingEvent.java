package com.assignment.producer.model;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pending_events")
@Getter
@Setter
@NoArgsConstructor
@Generated
public class PendingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Enumerated(EnumType.STRING)
    private RetryStatus status;

    private int retryCount;

    private LocalDateTime createdAt;

    public PendingEvent(String msgParam, RetryStatus statusParam,
                        int retryCountParam, LocalDateTime createdAtParam) {

        this.message = msgParam;
        this.status = statusParam;
        this.retryCount = retryCountParam;
        this.createdAt = createdAtParam;
    }

}