package com.example.demo.service;

import com.example.demo.dto.PageResponse;
import com.example.demo.dto.SortResponse;
import com.example.demo.dto.permission.PermissionCreateUpdateRequest;
import com.example.demo.dto.permission.PermissionResponse;
import com.example.demo.dto.role.RoleCreateUpdateRequest;
import com.example.demo.dto.role.RoleResponse;
import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.specification.PermissionSpecification;
import com.example.demo.specification.RoleSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class RoleService {
    private final RoleRepository repository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper mapper;

    public RoleService(RoleRepository repository, PermissionRepository permissionRepository, RoleMapper mapper) {
        this.repository = repository;
        this.permissionRepository = permissionRepository;
        this.mapper = mapper;
    }

    public PageResponse<RoleResponse> findAll(
            String name,
            Pageable pageable
    ){

        Specification<Role> spec = Specification
                .where(RoleSpecification.hasName(name));

        Page<Role> roles = repository.findAll(spec, pageable);

        Page<RoleResponse> response = roles.map(mapper::toResponse);

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
    public Role create(RoleCreateUpdateRequest request){

        if(repository.existsByName(request.getName())){
            throw new DuplicateResourceException(
                    String.format("Role with name %s already exists", request.getName())
            );
        }

        Set<Permission> permissions = new HashSet<>(
                permissionRepository.findAllById(request.getPermissionIds())
        );

        if (permissions.size() != request.getPermissionIds().size()) {
            throw new ResourceNotFoundException("Permissions not found");
        }

        Role role = new Role();

        role.setName(request.getName());
        role.setPermissions(permissions);

        return repository.save(role);
    }

    public RoleResponse  findById(UUID id){
        Role role = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role not found")
        );

        return mapper.toResponse(role);
    }

    @Transactional
    public Role update(UUID id, RoleCreateUpdateRequest request){
        Role role = repository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Role not found")
                );

        if (repository.existsByNameAndIdNot(request.getName(), id)) {
            throw new DuplicateResourceException(
                    String.format(
                            "Role with name %s already exists",
                            request.getName()
                    )
            );
        }

        Set<Permission> permissions = new HashSet<>(
                permissionRepository.findAllById(request.getPermissionIds())
        );

        if (permissions.size() != request.getPermissionIds().size()) {
            throw new ResourceNotFoundException("Permissions not found");
        }

        role.setName(request.getName());
        role.setPermissions(permissions);

        return repository.save(role);
    }

    @Transactional
    public void delete(UUID id){
        Role role = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        repository.delete(role);

    }
}
