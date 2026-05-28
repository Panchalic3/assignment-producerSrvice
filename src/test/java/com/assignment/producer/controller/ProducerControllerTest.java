package com.assignment.producer.controller;

import com.assignment.producer.exception.GlobalExceptionHandler;
import com.assignment.producer.model.EventPayload;
import com.assignment.producer.service.KafkaProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProducerController.class)
@Import(GlobalExceptionHandler.class)
public class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldIngestEventSuccessfully() throws Exception {

        // Arrange
        EventPayload payloadParam = new EventPayload();
        payloadParam.setUserName("Panchali");
        payloadParam.setData("Test data");

        doNothing().when(kafkaProducerService).sendMessage(payloadParam);

        // Act + Assert
        mockMvc.perform(post("/api/ingest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payloadParam)))
                .andExpect(status().isOk())
                .andExpect(content().string("Message sent to Kafka")); // use your constant value
    }

    @Test
    void shouldReturnMultipleValidationErrors() throws Exception {

        EventPayload payloadParam = new EventPayload();
        payloadParam.setUserName("");

        mockMvc.perform(post("/api/ingest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payloadParam)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.userName").value("userName is a mandatory field"));
    }
}