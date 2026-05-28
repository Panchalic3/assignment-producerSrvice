package com.assignment.producer.util;
import com.assignment.producer.model.EventPayload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EventMapper {

    /**
     * Mapper method for converting request payload into enriched event.
     *
     * Adds system-generated fields like eventId and timestamp
     * to the incoming payload.
     */
    public EventPayload mapToEvent(EventPayload payloadParam) {
        EventPayload event = new EventPayload();

        event.setEventId(UUID.randomUUID().toString());
        event.setTimestamp(LocalDateTime.now());
        event.setUserName(payloadParam.getUserName());
        event.setData(payloadParam.getData());

        return event;
    }
}