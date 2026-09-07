package com.example.demo.mapper;

import com.example.demo.dto.permission.PermissionResponse;
import com.example.demo.dto.role.RoleResponse;
import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RoleMapper {
    private final PermissionMapper permissionMapper;

    public RoleMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    public RoleResponse toResponse(Role role) {

        Set<PermissionResponse> permissions  = role.getPermissions()
                .stream()
                .map(permissionMapper::toResponse)
                .collect(Collectors.toSet());

        return new RoleResponse(
                role.getId(),
                role.getName(),
                permissions ,
                role.getCreatedAt(),
                role.getUpdatedAt()
        );
    }
}
