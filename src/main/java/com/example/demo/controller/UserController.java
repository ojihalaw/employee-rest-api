package com.example.demo.controller;

import com.example.demo.constants.ApiPath;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PageResponse;
import com.example.demo.dto.role.RoleCreateUpdateRequest;
import com.example.demo.dto.role.RoleResponse;
import com.example.demo.dto.user.UserCreateRequest;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.dto.user.UserUpdateRequest;
import com.example.demo.dto.user.UserWithRolePermissionResponse;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiPath.V1 + "/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping
    public ApiResponse<PageResponse<UserResponse>> getAll(
            @RequestParam(required = false) String name,
            Pageable pageable
    ){

        PageResponse<UserResponse> users = service.findAll(name, pageable);

        return ApiResponse.success(
                "Users retrieved successfully",
                users
        );
    }

    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping
    public ApiResponse<Void> create(
            @Valid
            @RequestBody UserCreateRequest request
    ){

        service.create(request);

        return ApiResponse.success(
                "User created successfully"
        );
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("{id}")
    public ApiResponse<UserWithRolePermissionResponse> getById(@PathVariable UUID id){

        UserWithRolePermissionResponse  user = service.findById(id);

        return ApiResponse.success(
                "Get user by ID successfully",
                user
        );
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @Valid
            @RequestBody UserUpdateRequest request, @PathVariable UUID id
    ){
        service.update(id, request);

        return ApiResponse.success(
                "Update user by ID successfully"
        );
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable UUID id
    ){
        service.delete(id);

        return ApiResponse.success(
                "Delete user by ID successfully"
        );
    }
}
