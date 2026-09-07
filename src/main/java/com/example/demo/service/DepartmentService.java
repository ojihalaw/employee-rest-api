package com.example.demo.service;

import com.example.demo.dto.PageResponse;
import com.example.demo.dto.SortResponse;
import com.example.demo.dto.department.DepartmentCreateRequest;
import com.example.demo.dto.department.DepartmentResponse;
import com.example.demo.dto.department.DepartmentUpdateRequest;
import com.example.demo.entity.Department;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.specification.DepartmentSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService {
    private final DepartmentRepository repository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository repository, DepartmentMapper departmentMapper) {
        this.repository = repository;
        this.departmentMapper = departmentMapper;
    }

    public PageResponse<DepartmentResponse> findAll(
            String name,
            Pageable pageable
    ){

        Specification<Department> spec = Specification
                .where(DepartmentSpecification.notDeleted())
                .and(DepartmentSpecification.hasName(name));

        Page<Department> departments = repository.findAll(spec, pageable);

        Page<DepartmentResponse> response = departments.map(departmentMapper::toResponse);

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

    public Department create(DepartmentCreateRequest request){
        if (repository.existsByName(request.getName())){
            throw new DuplicateResourceException(
                    String.format("Department with name %s already exists", request.getName())
            );
        }

        Department department = new Department();

        department.setName(request.getName());

        return repository.save(department);
    }

    public DepartmentResponse findById(UUID id){
        Department department = repository.findByIdAndDeletedAtIsNull(id).orElseThrow(() ->
                new ResourceNotFoundException("Department not found")
        );

        return departmentMapper.toResponse(department);
    }

    public Department update(UUID id, DepartmentUpdateRequest request){
        Department department = repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found")
                );

        if (repository.existsByNameAndIdNot(request.getName(), id)){
            throw new DuplicateResourceException(
                    String.format("Department with name %s already exists", request.getName())
            );
        }

        department.setName(request.getName());

        return repository.save(department);
    }

    public void delete(UUID id){
        Department department = repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        department.setDeletedAt(LocalDateTime.now());

    }
}
