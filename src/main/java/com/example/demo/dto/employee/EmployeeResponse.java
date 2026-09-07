package com.example.demo.dto.employee;

import com.example.demo.dto.department.DepartmentResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record EmployeeResponse(
        UUID id,
        String name,
        String username,
        String email,
        DepartmentResponse department,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
