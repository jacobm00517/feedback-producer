package com.example.producer.service;

import com.example.producer.model.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.MDC;

@Service
public class FeedbackSender {

    private final RabbitTemplate rabbitTemplate;
    
    private static final Logger logger = LoggerFactory.getLogger(FeedbackSender.class);

    public FeedbackSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendFeedback(Feedback feedback) {
        try {
            String requestId = MDC.get("requestId");
            feedback.setRequestId(requestId);

            rabbitTemplate.convertAndSend("feedback.queue", feedback);
            logger.info("Sent feedback to RabbitMQ for: {}, requestId: {}", feedback.getEmail(), requestId);
        } catch (Exception e) {
            logger.error("Failed to send feedback to RabbitMQ, requestId: {}", MDC.get("requestId"), e);
            throw e; // or handle it gracefully
        }
    }

}
