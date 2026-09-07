package com.example.demo.dto.user;

import com.example.demo.dto.permission.PermissionResponse;
import com.example.demo.dto.role.UserRoleResponse;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record UserWithRolePermissionResponse(
    UUID id,
    String name,
    String username,
    String email,
    UserRoleResponse role,
    Set<PermissionResponse> permissions,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}
