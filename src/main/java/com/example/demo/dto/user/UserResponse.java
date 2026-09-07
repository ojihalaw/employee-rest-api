package com.example.demo.dto.user;

import com.example.demo.dto.role.UserRoleResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
    UUID id,
    String name,
    String username,
    String email,
    UserRoleResponse role,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}
