package com.example.demo.service;

import com.example.demo.dto.PageResponse;
import com.example.demo.dto.SortResponse;
import com.example.demo.dto.permission.PermissionCreateUpdateRequest;
import com.example.demo.dto.permission.PermissionResponse;
import com.example.demo.entity.Permission;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.specification.PermissionSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PermissionService {
    private final PermissionRepository repository;
    private final PermissionMapper mapper;

    public PermissionService(PermissionRepository repository, PermissionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PageResponse<PermissionResponse> findAll(
            String name,
            Pageable pageable
    ){

        Specification<Permission> spec = Specification
                .where(PermissionSpecification.hasName(name));

        Page<Permission> permissions = repository.findAll(spec, pageable);

        Page<PermissionResponse> response = permissions.map(mapper::toResponse);

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
    public Permission create(PermissionCreateUpdateRequest request){
        Permission permission = new Permission();

        permission.setName(request.getName());

        return repository.save(permission);
    }

    public PermissionResponse  findById(UUID id){
        Permission permission = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Permission not found")
        );

        return mapper.toResponse(permission);
    }

    @Transactional
    public Permission update(UUID id, PermissionCreateUpdateRequest request){
        Permission permission = repository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Permission not found")
                );

        permission.setName(request.getName());

        return repository.save(permission);
    }

    @Transactional
    public void delete(UUID id){
        Permission permission = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("permission not found"));

        repository.delete(permission);

    }
}
