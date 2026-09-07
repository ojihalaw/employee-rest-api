package com.example.demo.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserUpdateRequest {

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

    @NotNull(message = "Role is required")
    private UUID roleId;
}