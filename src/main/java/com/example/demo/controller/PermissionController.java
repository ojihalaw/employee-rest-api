package com.example.demo.controller;

import com.example.demo.constants.ApiPath;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PageResponse;
import com.example.demo.dto.permission.PermissionCreateUpdateRequest;
import com.example.demo.dto.permission.PermissionResponse;
import com.example.demo.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiPath.V1 + "/permissions")
public class PermissionController {
    private final PermissionService service;

    public PermissionController(PermissionService service){
        this.service = service;
    }

    @GetMapping
    public ApiResponse<PageResponse<PermissionResponse>> getAll(
            @RequestParam(required = false) String name,
            Pageable pageable
    ){

        PageResponse<PermissionResponse> permissions = service.findAll(name, pageable);

        return ApiResponse.success(
                "Permissions retrieved successfully",
                permissions
        );
    }

    @PostMapping
    public ApiResponse<Void> create(
            @Valid
            @RequestBody PermissionCreateUpdateRequest request
    ){

        service.create(request);

        return ApiResponse.success(
                "Permission created successfully"
        );
    }

    @GetMapping("{id}")
    public ApiResponse<PermissionResponse > getById(@PathVariable UUID id){

        PermissionResponse  permission = service.findById(id);

        return ApiResponse.success(
                "Get permission by ID successfully",
                permission
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @Valid
            @RequestBody PermissionCreateUpdateRequest request, @PathVariable UUID id
    ){
        service.update(id, request);

        return ApiResponse.success(
                "Update permission by ID successfully"
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable UUID id
    ){
        service.delete(id);

        return ApiResponse.success(
                "Delete permission by ID successfully"
        );
    }
}
