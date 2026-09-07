package com.example.demo.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter

public class DepartmentUpdateRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name maximum 100 characters")
    private String name;
}
