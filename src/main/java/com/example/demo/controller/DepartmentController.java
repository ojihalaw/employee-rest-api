package com.example.demo.controller;

import com.example.demo.constants.ApiPath;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PageResponse;
import com.example.demo.dto.department.DepartmentCreateRequest;
import com.example.demo.dto.department.DepartmentResponse;
import com.example.demo.dto.department.DepartmentUpdateRequest;
import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiPath.V1 + "/departments")
public class DepartmentController {
    private final DepartmentService service;

    public DepartmentController(DepartmentService service){
        this.service = service;
    }

    @PreAuthorize("hasAuthority('DEPARTMENT_READ')")
    @GetMapping
    public ApiResponse<PageResponse<DepartmentResponse>> getAll(
            @RequestParam(required = false) String name,
            Pageable pageable
    ){

        PageResponse<DepartmentResponse> departments = service.findAll(name, pageable);

        return ApiResponse.success(
                "Departments retrieved successfully",
                departments
        );
    }

    @PreAuthorize("hasAuthority('DEPARTMENT_CREATE')")
    @PostMapping
    public ApiResponse<Void> create(
            @Valid
            @RequestBody DepartmentCreateRequest request
    ){

        service.create(request);

        return ApiResponse.success(
                "Department created successfully"
        );
    }

    @PreAuthorize("hasAuthority('DEPARTMENT_READ')")
    @GetMapping("{id}")
    public ApiResponse<DepartmentResponse> getById(@PathVariable UUID id){

        DepartmentResponse department = service.findById(id);

        return ApiResponse.success(
                "Get Department by ID successfully",
                department
        );
    }

    @PreAuthorize("hasAuthority('DEPARTMENT_UPDATE')")
    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @Valid
            @RequestBody DepartmentUpdateRequest request, @PathVariable UUID id
    ){
        service.update(id, request);

        return ApiResponse.success(
                "Update Department by ID successfully"
        );
    }

    @PreAuthorize("hasAuthority('DEPARTMENT_DELETE')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable UUID id
    ){
        service.delete(id);

        return ApiResponse.success(
                "Delete Department by ID successfully"
        );
    }
}
