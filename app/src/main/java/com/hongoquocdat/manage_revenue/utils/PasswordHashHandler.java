package com.hongoquocdat.manage_revenue.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * General handler for password hashing with SHA-256 and salt
 */
public class PasswordHashHandler {

    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;
    private final SecureRandom secureRandom;
    private final MessageDigest messageDigest;

    /**
     * Constructor initializes the handler
     */
    public PasswordHashHandler() {
        this.secureRandom = new SecureRandom();
        try {
            this.messageDigest = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to initialize MessageDigest", e);
        }
    }

    /**
     * Generate a random salt
     * @return Base64 encoded salt string
     */
    public String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hash a password with the provided salt
     * @param password The plain text password
     * @param salt The Base64 encoded salt
     * @return Base64 encoded hash
     */
    public String hashPassword(String password, String salt) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (salt == null || salt.isEmpty()) {
            throw new IllegalArgumentException("Salt cannot be null or empty");
        }

        try {
            synchronized (messageDigest) {
                messageDigest.reset();
                messageDigest.update(Base64.getDecoder().decode(salt));
                byte[] hashedPassword = messageDigest.digest(password.getBytes());
                return Base64.getEncoder().encodeToString(hashedPassword);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash password", e);
        }
    }

    /**
     * Hash a password and generate a new salt
     * @param password The plain text password
     * @return HashedPassword object containing both hash and salt
     */
    public HashedPassword hashPasswordWithSalt(String password) {
        String salt = generateSalt();
        String hash = hashPassword(password, salt);
        return new HashedPassword(hash, salt);
    }

    /**
     * Verify if a password matches the stored hash
     * @param password The plain text password to verify
     * @param salt The salt used in the original hash
     * @param storedHash The stored hash to compare against
     * @return true if password is correct, false otherwise
     */
    public boolean verifyPassword(String password, String salt, String storedHash) {
        if (password == null || salt == null || storedHash == null) {
            return false;
        }

        try {
            String newHash = hashPassword(password, salt);
            return MessageDigest.isEqual(
                    newHash.getBytes(),
                    storedHash.getBytes()
            );
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Data class to hold hash and salt together
     */
    public static class HashedPassword {
        private final String hash;
        private final String salt;

        public HashedPassword(String hash, String salt) {
            this.hash = hash;
            this.salt = salt;
        }

        public String getHash() {
            return hash;
        }

        public String getSalt() {
            return salt;
        }

        @Override
        public String toString() {
            return "HashedPassword{hash='" + hash + "', salt='" + salt + "'}";
        }
    }
}

// Usage Example:
/*
// Initialize handler (can be singleton or dependency injection)
PasswordHashHandler handler = new PasswordHashHandler();

// 1. Register new user - hash password with new salt
HashedPassword result = handler.hashPasswordWithSalt("myPassword123");
String hashToStore = result.getHash();
String saltToStore = result.getSalt();
// Store both in database

// 2. Login - verify password
String inputPassword = "myPassword123";
String storedHash = "..."; // from database
String storedSalt = "..."; // from database
boolean isValid = handler.verifyPassword(inputPassword, storedSalt, storedHash);

// 3. Manual salt management
String customSalt = handler.generateSalt();
String hash = handler.hashPassword("myPassword123", customSalt);
*/