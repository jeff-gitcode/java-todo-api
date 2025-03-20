package com.example.integration;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.application.auth.JwtUtil;
import com.example.application.interfaces.UserRepository;
import com.example.domain.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll(); // Clear the database before each test
    }

    @Test
    @WithMockUser(username = "test@example.com", password = "password123", roles = "USER")
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
    @WithMockUser(username = "test@example.com", password = "password123", roles = "USER")
    public void testSignup_Failure_UserAlreadyExists() throws Exception {
        // Arrange
        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        existingUser.setPassword(new BCryptPasswordEncoder().encode("password123")); // bcrypt-encode the password
        userRepository.save(existingUser); // Save the existing user to the database

        String signupRequest = objectMapper.writeValueAsString(new SignupRequest("test@example.com", "password123"));

        // Act & Assert
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signupRequest))
                .andExpect(status().isBadRequest()) // Expect 400 Bad Request
                .andExpect(jsonPath("$").value("User already exists")); // Expect error message
    }

    @Test
    @WithMockUser(username = "test@example.com", password = "password123", roles = "USER")
    public void testSignin_Success() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(new BCryptPasswordEncoder().encode("password123")); // bcrypt-encode the password
        userRepository.save(user); // Save the user to the database

        String signinRequest = objectMapper.writeValueAsString(new SigninRequest("test@example.com", "password123"));

        // Act & Assert
        MvcResult result = mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signinRequest))
                .andExpect(status().isOk())
                .andReturn();

        String jwtToken = result.getResponse().getContentAsString();
        System.out.println("JWT Token: " + jwtToken); // Debug the token

        assertThat(jwtToken).isNotEmpty(); // Verify the response contains a JWT token
    }

    @Test
    @WithMockUser(username = "test@example.com", password = "password123", roles = "USER")
    public void testSignin_Failure_InvalidCredentials() throws Exception {
        // Arrange
        String signinRequest = objectMapper.writeValueAsString(new SigninRequest("test@example.com", "wrongpassword"));

        // Act & Assert
        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signinRequest))
                .andExpect(status().isUnauthorized()) // Expect 401 Unauthorized
                .andExpect(jsonPath("$").value("Invalid credentials")); // Expect error message
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
