package com.example.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.application.config.SecurityConfig;
import com.example.application.interfaces.UserRepository;
import com.example.domain.model.User;
import com.example.presentation.controller.AuthController;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AuthController.class, SecurityConfig.class})
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll(); // Clear the database before each test
    }

    @Test
    public void testSignup_Success() throws Exception {
        // Arrange
        String signupRequest = objectMapper.writeValueAsString(new SignupRequest("test@example.com", "password123"));

        // Act & Assert
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signupRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User registered successfully"));
    }

    @Test
    public void testSignup_Failure_UserAlreadyExists() throws Exception {
        // Arrange
        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        existingUser.setPassword("password123");
        userRepository.save(existingUser);

        String signupRequest = objectMapper.writeValueAsString(new SignupRequest("test@example.com", "password123"));

        // Act & Assert
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signupRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("User already exists"));
    }

    @Test
    public void testSignin_Success() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("$2a$10$D9Q9Q9Q9Q9Q9Q9Q9Q9Q9QO"); // bcrypt-encoded password for "password123"
        userRepository.save(user);

        String signinRequest = objectMapper.writeValueAsString(new SigninRequest("test@example.com", "password123"));

        // Act & Assert
        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signinRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty()); // Expect a JWT token in the response
    }

    @Test
    public void testSignin_Failure_InvalidCredentials() throws Exception {
        // Arrange
        String signinRequest = objectMapper.writeValueAsString(new SigninRequest("test@example.com", "wrongpassword"));

        // Act & Assert
        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signinRequest))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("Invalid credentials"));
    }

    // Helper classes for request payloads
    private static class SignupRequest {
        private String email;
        private String password;

        public SignupRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    private static class SigninRequest {
        private String email;
        private String password;

        public SigninRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}
