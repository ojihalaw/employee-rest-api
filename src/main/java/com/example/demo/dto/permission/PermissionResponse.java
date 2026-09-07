package com.example.demo.dto.permission;

import java.time.LocalDateTime;
import java.util.UUID;

public record PermissionResponse(
    UUID id,
    String name
) {
}
