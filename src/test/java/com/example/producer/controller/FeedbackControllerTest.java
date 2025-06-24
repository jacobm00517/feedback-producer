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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeedbackController.class)
public class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackSender feedbackSender;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSubmitFeedback_ValidInput_ReturnsAccepted() throws Exception {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setName("Alice");
        dto.setEmail("alice@example.com");
        dto.setMessage("Great service!");
        dto.setRating(5);

        mockMvc.perform(post("/api/feedback")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testSubmitFeedback_InvalidInput_ReturnsBadRequest() throws Exception {
        FeedbackDTO dto = new FeedbackDTO(); // Missing required fields

        mockMvc.perform(post("/api/feedback")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
