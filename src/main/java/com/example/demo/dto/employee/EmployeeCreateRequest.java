package com.example.demo.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EmployeeCreateRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name maximum 100 characters")
    private String name;

    @NotBlank(message = "Username is required")
    @Size(max = 20, message = "Name maximum 100 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    @NotNull(message = "Department is required")
    private UUID departmentId;
}
