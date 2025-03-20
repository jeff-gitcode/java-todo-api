package com.example.application.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

class JwtUtilUnitTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    private SecretKey jwtSecretKey; // Use a secure key
    private final int jwtExpirationMs = 3600000; // 1 hour
    private final String testEmail = "test@example.com";
    private String token;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        jwtUtil = new JwtUtil();

        // Generate a secure key for HS512
        jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        // Use reflection to set the private fields
        var secretField = JwtUtil.class.getDeclaredField("key");
        secretField.setAccessible(true);
        secretField.set(jwtUtil, jwtSecretKey);

        var expirationField = JwtUtil.class.getDeclaredField("jwtExpirationMs");
        expirationField.setAccessible(true);
        expirationField.set(jwtUtil, jwtExpirationMs);

        token = jwtUtil.generateToken(testEmail);
    }

    @Test
    void testGenerateToken() {
        // Act
        String generatedToken = token;

        // Assert
        assertNotNull(generatedToken);
        assertTrue(generatedToken.length() > 0);
    }

    @Test
    void testGetUsernameFromToken() {
        // Arrange
        String expectedEmail = testEmail;

        // Act
        String username = jwtUtil.getUsernameFromToken(token);

        // Assert
        assertEquals(expectedEmail, username);
    }

    @Test
    void testValidateJwtToken_ValidToken() {
        // Act
        boolean isValid = jwtUtil.validateJwtToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void testValidateJwtToken_InvalidToken() {
        // Arrange
        String invalidToken = token + "invalid";

        // Act
        boolean isValid = jwtUtil.validateJwtToken(invalidToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void testValidateJwtToken_ExpiredToken() {
        // Arrange
        // Generate an expired token
        String expiredToken = Jwts.builder()
                .setSubject(testEmail)
                .setIssuedAt(new Date(System.currentTimeMillis() - 10000)) // Set issued time in the past
                .setExpiration(new Date(System.currentTimeMillis() - 5000)) // Set expiration time in the past
                .signWith(jwtSecretKey) // Use the secure key
                .compact();

        // Act
        boolean isValid = jwtUtil.validateJwtToken(expiredToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void testValidateJwtToken_EmptyToken() {
        boolean isValid = jwtUtil.validateJwtToken("");
        assertFalse(isValid);
    }

    @Test
    void testValidateJwtToken_NullToken() {
        boolean isValid = jwtUtil.validateJwtToken(null);
        assertFalse(isValid);
    }
}