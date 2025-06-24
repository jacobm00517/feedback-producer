package com.example.producer.mapper;

import com.example.producer.dto.FeedbackDTO;
import com.example.producer.model.Feedback;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackMapperTest {

    @Test
    void toModel_shouldMapAllFieldsCorrectly() {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setName("Alice");
        dto.setEmail("alice@example.com");
        dto.setMessage("Great app!");
        dto.setRating(5);
        dto.setRequestId("ignored-in-mapper");

        String requestId = "abc-123";

        Instant beforeMapping = Instant.now();
        Feedback feedback = FeedbackMapper.toModel(dto, requestId);
        Instant afterMapping = Instant.now();

        assertEquals(dto.getName(), feedback.getName());
        assertEquals(dto.getEmail(), feedback.getEmail());
        assertEquals(dto.getMessage(), feedback.getMessage());
        assertEquals(dto.getRating(), feedback.getRating());
        assertEquals(requestId, feedback.getRequestId());

        assertNotNull(feedback.getTimestamp());
        assertTrue(!feedback.getTimestamp().isBefore(beforeMapping) && !feedback.getTimestamp().isAfter(afterMapping));
    }
}
