package com.example.demo.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Username is required")
        @Size(max = 50, message = "Name maximum 50 characters")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password min 8 characters")
        String password

) {
}
