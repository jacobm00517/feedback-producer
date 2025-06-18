package com.example.producer.controller;

import com.example.producer.dto.FeedbackDTO;
import com.example.producer.dto.ResponseDTO;
import com.example.producer.model.Feedback;
import com.example.producer.service.FeedbackSender;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import com.example.producer.mapper.FeedbackMapper;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);
    private final FeedbackSender feedbackSender;

    public FeedbackController(FeedbackSender feedbackSender) {
        this.feedbackSender = feedbackSender;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> submitFeedback(@Valid @RequestBody FeedbackDTO dto) {
        logger.info("Received feedback from: {}", dto.getEmail());

        String requestId = MDC.get("requestId");

        Feedback feedback = FeedbackMapper.toModel(dto, requestId);
        feedbackSender.sendFeedback(feedback);

        ResponseDTO response = new ResponseDTO("accepted", "Feedback queued successfully.", requestId);
        logger.info("Responding with requestId: {}", requestId);
        return ResponseEntity.accepted().body(response);
    }
}
