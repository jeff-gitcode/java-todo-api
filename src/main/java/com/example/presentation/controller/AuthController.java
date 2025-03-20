package com.example.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.usecase.auth.SigninUserQueryHandler;
import com.example.application.usecase.auth.SignupUserCommandHandler;
import com.example.domain.command.SignupUserCommand;
import com.example.domain.query.SigninUserQuery;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SignupUserCommandHandler signupUserCommandHandler;

    @Autowired
    private SigninUserQueryHandler signinUserQueryHandler;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupUserCommand command) {
        // if exception return bad request
        var result = signupUserCommandHandler.handle(command);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody SigninUserQuery query) {
            return ResponseEntity.ok(signinUserQueryHandler.handle(query));
    }
}
