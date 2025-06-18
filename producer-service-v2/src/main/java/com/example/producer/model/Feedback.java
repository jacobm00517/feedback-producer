package com.example.producer.model;

import java.io.Serializable;
import jakarta.validation.constraints.*;
import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonFormat;


public class Feedback implements Serializable {
    private String name;
    private String email;
    private String message;
    private int rating;
    private Instant timestamp;
    private String requestId;

    public Feedback() {
        this.timestamp = Instant.now();
    }
    

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    
    public Instant getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }


	public String getRequestId() {
		return requestId;
	}


	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}

