package com.example.presentation.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @org.springframework.web.bind.annotation.GetMapping("/")
    public String index() {
        return "Hello from Spring Boot!";
    }
}
