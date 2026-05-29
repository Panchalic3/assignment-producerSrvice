package com.assignment.producer.util;

import lombok.Generated;

@Generated
public class Constants {
    public static final String LOCALHOST_9092 = "localhost:9092";
    public static final String TOPIC = "event-topic";

    // Logs
    public static final String KAFKA_SEND_ERROR_LOG = "Error while sending message to Kafka with event id {}, Saving this in retry";
    public static final String FOUND_EVENT_LOG = "Found {} pending events";
    public static final String INPUT_DATA_INGESTED_LOG = "Ingested input data with eventId {}";
    public static final String MSG_SUCCESSFULLY_SENT_TO_KAFKA_LOG = "Message sent to Kafka with event id {}";

    // Messages
    public static final String KAFKA_SEND_ERROR = "Error while sending message to Kafka";
    public static final String MSG_SENT_TO_KAFKA = "Message sent to Kafka";

}
