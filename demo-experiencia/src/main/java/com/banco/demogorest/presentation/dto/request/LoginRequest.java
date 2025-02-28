package com.banco.demogorest.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(

        @Email(message = "Email should be valid")
        @NotBlank(message = "Email should not be blank")
        @NotNull(message = "Email should not be null")
        String email,

        @NotBlank(message = "Password should not be blank")
        String password
){}
