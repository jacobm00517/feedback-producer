package com.example.producer.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidatorInstance() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validFeedbackDTO_shouldHaveNoViolations() {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setName("Alice");
        dto.setEmail("alice@example.com");
        dto.setMessage("Great!");
        dto.setRating(5);

        Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void emptyName_shouldFailValidation() {
        FeedbackDTO dto = createValidDTO();
        dto.setName("");

        Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void invalidEmail_shouldFailValidation() {
        FeedbackDTO dto = createValidDTO();
        dto.setEmail("not-an-email");

        Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void ratingTooLow_shouldFailValidation() {
        FeedbackDTO dto = createValidDTO();
        dto.setRating(0);

        Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rating")));
    }

    @Test
    void ratingTooHigh_shouldFailValidation() {
        FeedbackDTO dto = createValidDTO();
        dto.setRating(6);

        Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rating")));
    }

    private FeedbackDTO createValidDTO() {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setName("Alice");
        dto.setEmail("alice@example.com");
        dto.setMessage("Excellent.");
        dto.setRating(4);
        return dto;
    }
}
