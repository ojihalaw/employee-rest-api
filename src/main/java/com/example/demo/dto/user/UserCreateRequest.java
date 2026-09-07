package com.example.demo.dto.user;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserCreateRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name maximum 50 characters")
    private String name;

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username maximum 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 150, message = "Email maximum 150 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;

    @NotNull(message = "Role is required")
    private UUID roleId;
}