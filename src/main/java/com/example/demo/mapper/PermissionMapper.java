package com.example.demo.mapper;

import com.example.demo.dto.permission.PermissionResponse;
import com.example.demo.entity.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {
    public PermissionResponse toResponse(Permission permission) {
        return new PermissionResponse(
                permission.getId(),
                permission.getName()
        );
    }
}
