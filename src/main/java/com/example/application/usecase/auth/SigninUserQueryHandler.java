package com.example.application.usecase.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.application.auth.JwtUtil;
import com.example.application.interfaces.UserRepository;
import com.example.domain.model.User;
import com.example.domain.query.SigninUserQuery;

@Component
public class SigninUserQueryHandler {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String handle(SigninUserQuery query) {
        Optional<User> existingUser = userRepository.findByEmail(query.getEmail());
        if (existingUser.isPresent() && passwordEncoder.matches(query.getPassword(), existingUser.get().getPassword())) {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    query.getEmail(),
                    query.getPassword()
                )
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtUtil.generateToken(userDetails.getUsername());

            // return jwtUtil.generateToken(query.getEmail());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
