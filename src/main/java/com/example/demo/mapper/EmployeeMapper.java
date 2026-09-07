package com.example.demo.mapper;

import com.example.demo.dto.department.DepartmentResponse;
import com.example.demo.dto.employee.EmployeeResponse;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public EmployeeResponse toResponse(Employee employee) {
        Department department = employee.getDepartment();

        DepartmentResponse departmentResponse = new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getCreatedAt(),
                department.getUpdatedAt()
        );

        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getUsername(),
                employee.getEmail(),
                departmentResponse,
                employee.getCreatedAt(),
                employee.getUpdatedAt()
        );
    }
}
