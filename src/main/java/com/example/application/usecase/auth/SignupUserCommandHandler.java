package com.example.application.usecase.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.application.interfaces.UserRepository;
import com.example.domain.command.SignupUserCommand;
import com.example.domain.model.User;

@Component
public class SignupUserCommandHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String handle(SignupUserCommand command) {
        User user = new User();
        user.setEmail(command.getEmail());
        user.setPassword(passwordEncoder.encode(command.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }
}
