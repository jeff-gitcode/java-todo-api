package com.example.domain.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupUserCommand {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
