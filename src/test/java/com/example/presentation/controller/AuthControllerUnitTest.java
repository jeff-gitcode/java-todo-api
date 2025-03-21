package com.example.presentation.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.application.usecase.auth.SigninUserQueryHandler;
import com.example.application.usecase.auth.SignupUserCommandHandler;
import com.example.domain.command.SignupUserCommand;
import com.example.domain.query.SigninUserQuery;

class AuthControllerUnitTest {

    @Mock
    private SignupUserCommandHandler signupUserCommandHandler;

    @Mock
    private SigninUserQueryHandler signinUserQueryHandler;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignup_Success() {
        // Arrange
        SignupUserCommand command = new SignupUserCommand();
        command.setEmail("test@example.com");
        command.setPassword("password123");

        when(signupUserCommandHandler.handle(command)).thenReturn("User registered successfully");

        // Act
        ResponseEntity<String> response = authController.signup(command);

        // Assert
        assertEquals(200, response.getStatusCode().value()); // Verify HTTP status code
        assertEquals("User registered successfully", response.getBody()); // Verify response body
        verify(signupUserCommandHandler, times(1)).handle(command);
    }

    @Test
    void testSignup_Failure() {
        // Arrange
        SignupUserCommand command = new SignupUserCommand();
        command.setEmail("test@example.com");
        command.setPassword("password123");

        when(signupUserCommandHandler.handle(command)).thenThrow(new RuntimeException("Signup failed"));

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authController.signup(command));

        // Assert
        assertEquals("Signup failed", exception.getMessage());
        verify(signupUserCommandHandler, times(1)).handle(command);
    }

    @Test
    void testSignin_Success() {
        // Arrange
        SigninUserQuery query = new SigninUserQuery();
        query.setEmail("test@example.com");
        query.setPassword("password123");

        when(signinUserQueryHandler.handle(query)).thenReturn("JWT_TOKEN");

        // Act
        ResponseEntity<String> response = authController.signin(query);

        // Assert
        assertEquals(200, response.getStatusCode().value()); // Verify HTTP status code
        assertEquals("JWT_TOKEN", response.getBody()); // Verify response body
        verify(signinUserQueryHandler, times(1)).handle(query);
    }

    @Test
    void testSignin_Failure() {
        // Arrange
        SigninUserQuery query = new SigninUserQuery();
        query.setEmail("test@example.com");
        query.setPassword("wrongpassword");

        when(signinUserQueryHandler.handle(query)).thenThrow(new RuntimeException("Invalid credentials"));

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authController.signin(query));

        // Assert
        assertEquals("Invalid credentials", exception.getMessage());
        verify(signinUserQueryHandler, times(1)).handle(query);
    }
}