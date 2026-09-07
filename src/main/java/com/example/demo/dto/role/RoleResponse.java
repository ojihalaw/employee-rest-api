package com.example.demo.dto.role;

import com.example.demo.dto.permission.PermissionResponse;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name,
        Set<PermissionResponse>  permissions,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
