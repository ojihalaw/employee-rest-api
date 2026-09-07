package com.example.demo.controller;

import com.example.demo.constants.ApiPath;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PageResponse;
import com.example.demo.dto.permission.PermissionCreateUpdateRequest;
import com.example.demo.dto.permission.PermissionResponse;
import com.example.demo.dto.role.RoleCreateUpdateRequest;
import com.example.demo.dto.role.RoleResponse;
import com.example.demo.service.PermissionService;
import com.example.demo.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiPath.V1 + "/roles")
public class RoleController {
    private final RoleService service;

    public RoleController(RoleService service){
        this.service = service;
    }

    @GetMapping
    public ApiResponse<PageResponse<RoleResponse>> getAll(
            @RequestParam(required = false) String name,
            Pageable pageable
    ){

        PageResponse<RoleResponse> roles = service.findAll(name, pageable);

        return ApiResponse.success(
                "Roles retrieved successfully",
                roles
        );
    }

    @PostMapping
    public ApiResponse<Void> create(
            @Valid
            @RequestBody RoleCreateUpdateRequest request
    ){

        service.create(request);

        return ApiResponse.success(
                "role created successfully"
        );
    }

    @GetMapping("{id}")
    public ApiResponse<RoleResponse > getById(@PathVariable UUID id){

        RoleResponse  role = service.findById(id);

        return ApiResponse.success(
                "Get role by ID successfully",
                role
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @Valid
            @RequestBody RoleCreateUpdateRequest request, @PathVariable UUID id
    ){
        service.update(id, request);

        return ApiResponse.success(
                "Update role by ID successfully"
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable UUID id
    ){
        service.delete(id);

        return ApiResponse.success(
                "Delete role by ID successfully"
        );
    }
}
