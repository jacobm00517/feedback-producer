package com.example.secureportal.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RateLimiterTest {

    @Test
    public void testBlockAfterMaxAttempts() {
        String ip = "127.0.0.1";

        for (int i = 0; i < 5; i++) {
            RateLimiter.recordFailure(ip);
        }

        assertTrue(RateLimiter.isBlocked(ip));
    }

    @Test
    public void testResetClearsBlock() {
        String ip = "192.168.1.100";

        for (int i = 0; i < 5; i++) {
            RateLimiter.recordFailure(ip);
        }
        assertTrue(RateLimiter.isBlocked(ip));

        RateLimiter.reset(ip);
        assertFalse(RateLimiter.isBlocked(ip));
    }
}