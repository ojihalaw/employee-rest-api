package com.example.demo.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class RoleCreateUpdateRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name maximum 50 characters")
    private String name;

    @NotEmpty(message = "Choose at least 1 permission")
    private Set<UUID> permissionIds;
}
