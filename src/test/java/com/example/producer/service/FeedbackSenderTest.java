package com.example.producer.service;

import com.example.producer.model.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.slf4j.MDC;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FeedbackSenderTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private FeedbackSender feedbackSender;

    private Feedback sampleFeedback;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        MDC.put("requestId", "test-request-id");

        sampleFeedback = new Feedback();
        sampleFeedback.setName("Alice");
        sampleFeedback.setEmail("alice@example.com");
        sampleFeedback.setMessage("Great service!");
        sampleFeedback.setRating(5);
        sampleFeedback.setTimestamp(Instant.now());
    }

    @Test
    void sendFeedback_shouldSendMessageToRabbitMQ() {
        feedbackSender.sendFeedback(sampleFeedback);

        verify(rabbitTemplate, times(1))
                .convertAndSend(eq("feedback.queue"), any(Feedback.class));
    }

    @Test
    void sendFeedback_shouldThrowExceptionIfSendingFails() {
        doThrow(new RuntimeException("RabbitMQ failure"))
                .when(rabbitTemplate).convertAndSend(eq("feedback.queue"), any(Feedback.class));

        assertThrows(RuntimeException.class, () -> {
            feedbackSender.sendFeedback(sampleFeedback);
        });

        verify(rabbitTemplate, times(1))
                .convertAndSend(eq("feedback.queue"), any(Feedback.class));
    }
}
