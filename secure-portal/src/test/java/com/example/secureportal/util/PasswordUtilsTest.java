package com.example.secureportal.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilsTest {

    @Test
    public void testHashAndVerifyPassword() {
        String plain = "admin123";
        String hash = PasswordUtils.hashPassword(plain);

        assertNotNull(hash, "Hash should not be null");
        assertNotEquals(plain, hash, "Hash should not be same as plain text");
        assertTrue(PasswordUtils.checkPassword(plain, hash), "Password should match hash");
    }

    @Test
    public void testWrongPasswordFails() {
        String plain = "correctPassword";
        String hash = PasswordUtils.hashPassword(plain);

        assertFalse(PasswordUtils.checkPassword("wrongPassword", hash), "Wrong password should not match");
    }

    @Test
    public void testNullOrLongPasswordThrows() {
        assertThrows(IllegalArgumentException.class, () -> PasswordUtils.hashPassword(null));
        
        assertThrows(IllegalArgumentException.class, () -> {
            StringBuilder longPwd = new StringBuilder();
            for (int i = 0; i < 100; i++) longPwd.append("1234567890");
            PasswordUtils.hashPassword(longPwd.toString());
        });
    }
}
