package com.example.demo.service;

import com.example.demo.dto.PageResponse;
import com.example.demo.dto.SortResponse;
import com.example.demo.dto.employee.EmployeeCreateRequest;
import com.example.demo.dto.employee.EmployeeResponse;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.specification.EmployeeSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository repository, DepartmentRepository departmentRepository, EmployeeMapper employeeMapper) {
        this.repository = repository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = employeeMapper;
    }

    public PageResponse<EmployeeResponse> findAll(
            String name,
            Pageable pageable
    ){

        Specification<Employee> spec = Specification
                .where(EmployeeSpecification.notDeleted())
                .and(EmployeeSpecification.hasName(name));

        Page<Employee> employees = repository.findAll(spec, pageable);

        Page<EmployeeResponse> response = employees.map(employeeMapper::toResponse);

        List<SortResponse> sort = pageable.getSort()
                .stream()
                .map(order -> new SortResponse(
                        order.getProperty(),
                        order.getDirection().name().toLowerCase()
                ))
                .toList();

        return new PageResponse<>(
                response.getContent(),
                response.getNumber(),
                response.getSize(),
                response.getTotalElements(),
                response.getTotalPages(),
                name,
                sort
        );
    }

    @Transactional
    public Employee create(EmployeeCreateRequest request){
        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        Employee employee = new Employee();

        employee.setName(request.getName());
        employee.setDepartment(department);
        employee.setEmail(request.getEmail());
        employee.setUsername(request.getUsername());

        return repository.save(employee);
    }

    public EmployeeResponse  findById(UUID id){
        Employee employee = repository.findByIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found")
        );

        return employeeMapper.toResponse(employee);
    }

    @Transactional
    public Employee update(UUID id, EmployeeCreateRequest request){
        Employee employee = repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Employee not found")
                );

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setUsername(request.getUsername());

        return repository.save(employee);
    }

    @Transactional
    public void delete(UUID id){
        Employee employee = repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        employee.setDeletedAt(LocalDateTime.now());

    }
}
