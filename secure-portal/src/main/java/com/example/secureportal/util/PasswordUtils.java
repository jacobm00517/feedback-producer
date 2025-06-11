package com.example.secureportal.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    private static final int BCRYPT_COST = 12;
    private static final int MAX_LENGTH = 72; //password.length>72 is silently ignored by bcrypt

    public static String hashPassword(String plainTextPassword) {
        validate(plainTextPassword);
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(BCRYPT_COST));
    }

    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        validate(plainTextPassword);
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    private static void validate(String password) {
        if (password == null || password.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Password must be non-null and ≤ 72 characters.");
        }
    }
}