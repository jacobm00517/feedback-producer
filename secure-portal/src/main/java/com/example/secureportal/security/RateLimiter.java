package com.example.secureportal.security;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {

    private static final int MAX_ATTEMPTS = 5;
    private static final long BLOCK_TIME_MS = 1 * 60 * 1000;

    private static final Map<String, Attempt> attempts = new ConcurrentHashMap<>();

    public static boolean isBlocked(String ip) {
        Attempt attempt = attempts.get(ip);
        if (attempt == null) return false;

        if (attempt.count >= MAX_ATTEMPTS) {
            long timeSinceLastFail = Instant.now().toEpochMilli() - attempt.lastAttemptTime;
            if (timeSinceLastFail < BLOCK_TIME_MS) {
                return true;
            } else {
                attempts.remove(ip); // unblock after time passes
            }
        }
        return false;
    }

    public static void recordFailure(String ip) {
        Attempt attempt = attempts.getOrDefault(ip, new Attempt());
        attempt.count++;
        attempt.lastAttemptTime = Instant.now().toEpochMilli();
        attempts.put(ip, attempt);
    }

    public static void reset(String ip) {
        attempts.remove(ip);
    }

    private static class Attempt {
        int count = 0;
        long lastAttemptTime = 0;
    }
}
