package com.assignment.producer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing incoming event payload from client.
 *
 * Contains user input (userName, data) and
 * system-generated fields (eventId, timestamp).
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventPayload {

    @NotBlank(message = "userName is a mandatory field")
    private String userName;

    @Size(max = 100, message = "data cannot exceed 100 characters")
    private String data;

    private String eventId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
}
