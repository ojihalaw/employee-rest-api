package com.example.demo.controller;

import com.example.demo.constants.ApiPath;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PageResponse;
import com.example.demo.dto.employee.EmployeeCreateRequest;
import com.example.demo.dto.employee.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiPath.V1 + "/employees")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service){
        this.service = service;
    }

    @GetMapping
    public ApiResponse<PageResponse<EmployeeResponse>> getAll(
            @RequestParam(required = false) String name,
            Pageable pageable
    ){

        PageResponse<EmployeeResponse> employees = service.findAll(name, pageable);

        return ApiResponse.success(
                "Employees retrieved successfully",
                employees
        );
    }

    @PostMapping
    public ApiResponse<Void> createEmployee(
            @Valid
            @RequestBody EmployeeCreateRequest request
    ){

        service.create(request);

        return ApiResponse.success(
                "Employee created successfully"
        );
    }

    @GetMapping("{id}")
    public ApiResponse<EmployeeResponse > getById(@PathVariable UUID id){

        EmployeeResponse  employee = service.findById(id);

        return ApiResponse.success(
                "Get Employee by ID successfully",
                employee
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateEmployee(
            @Valid
            @RequestBody EmployeeCreateRequest request, @PathVariable UUID id
    ){
        service.update(id, request);

        return ApiResponse.success(
                "Update Employee by ID successfully"
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteEmployee(
            @PathVariable UUID id
    ){
        service.delete(id);

        return ApiResponse.success(
                "Delete Employee by ID successfully"
        );
    }
}
