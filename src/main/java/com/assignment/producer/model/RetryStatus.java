package com.assignment.producer.model;

/**
 * Entity representing events that failed to publish to Kafka and are stored for retry.
 * Used by scheduler to ensure reliable delivery with retry tracking.
 */
public enum RetryStatus {
    SENT,
    FAILED
}