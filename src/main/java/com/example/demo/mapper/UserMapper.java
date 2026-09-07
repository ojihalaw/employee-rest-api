package com.example.demo.mapper;

import com.example.demo.dto.permission.PermissionResponse;
import com.example.demo.dto.role.RoleResponse;
import com.example.demo.dto.role.UserRoleResponse;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.dto.user.UserWithRolePermissionResponse;
import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private final PermissionMapper permissionMapper;

    public UserMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    public UserResponse toResponse(User user) {

        UserRoleResponse role  = new UserRoleResponse(
                user.getRole().getId(),
                user.getRole().getName()
        );

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                role,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public UserWithRolePermissionResponse userWithRolePermissionResponse(User user) {

        UserRoleResponse role  = new UserRoleResponse(
                user.getRole().getId(),
                user.getRole().getName()
        );

        Set<PermissionResponse> permissions  = user.getRole()
                .getPermissions()
                .stream()
                .map(permissionMapper::toResponse)
                .collect(Collectors.toSet());

        return new UserWithRolePermissionResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                role,
                permissions,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
