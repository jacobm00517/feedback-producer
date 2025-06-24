package com.example.producer.controller;

import com.example.producer.dto.FeedbackDTO;
import com.example.producer.service.FeedbackSender;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedbackController.class)
class FeedbackControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackSender feedbackSender;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenValidInput_thenReturnsAccepted() throws Exception {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setName("Alice");
        dto.setEmail("alice@example.com");
        dto.setMessage("This service rocks!");
        dto.setRating(5);

        mockMvc.perform(post("/api/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("accepted"))
                .andExpect(jsonPath("$.message").value("Feedback queued successfully."));
    }

    @Test
    void whenInvalidInput_thenReturnsBadRequest() throws Exception {
        FeedbackDTO dto = new FeedbackDTO(); // All fields blank by default

        mockMvc.perform(post("/api/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
