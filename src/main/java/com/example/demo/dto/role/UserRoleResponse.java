package com.example.demo.dto.role;

import java.util.UUID;

public record UserRoleResponse(
        UUID id,
        String name
) {
}
