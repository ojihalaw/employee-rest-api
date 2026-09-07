package com.example.demo.mapper;

import com.example.demo.dto.department.DepartmentResponse;
import com.example.demo.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public DepartmentResponse toResponse(Department department) {

        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getCreatedAt(),
                department.getUpdatedAt()
        );
    }
}
