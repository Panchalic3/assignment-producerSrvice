package com.assignment.producer.util;

import com.assignment.producer.model.EventPayload;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventMapperTest {

    private final EventMapper eventMapper = new EventMapper();

    @Test
    void shouldMapPayloadToEventSuccessfully() {

        // Arrange
        EventPayload inputPayload = new EventPayload();
        inputPayload.setUserName("Panchali");
        inputPayload.setData("Test data");

        // Act
        EventPayload result = eventMapper.mapToEvent(inputPayload);

        // Assert
        assertNotNull(result);

        // business fields copied
        assertEquals("Panchali", result.getUserName());
        assertEquals("Test data", result.getData());

        // system generated fields
        assertNotNull(result.getEventId());
        assertNotNull(result.getTimestamp());
    }
}
