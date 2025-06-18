package com.example.producer.mapper;

import com.example.producer.dto.FeedbackDTO;
import com.example.producer.model.Feedback;

import java.time.Instant;

public class FeedbackMapper {

    public static Feedback toModel(FeedbackDTO dto, String requestId) {
        Feedback feedback = new Feedback();
        feedback.setName(dto.getName());
        feedback.setEmail(dto.getEmail());
        feedback.setMessage(dto.getMessage());
        feedback.setRating(dto.getRating());
        feedback.setTimestamp(Instant.now());
        feedback.setRequestId(requestId);
        return feedback;
    }
}
