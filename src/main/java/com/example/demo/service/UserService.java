package com.example.demo.service;

import com.example.demo.dto.PageResponse;
import com.example.demo.dto.SortResponse;
import com.example.demo.dto.user.UserCreateRequest;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.dto.user.UserUpdateRequest;
import com.example.demo.dto.user.UserWithRolePermissionResponse;
import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository repository,
            RoleRepository roleRepository,
            UserMapper mapper,
            PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public PageResponse<UserResponse> findAll(
            String name,
            Pageable pageable
    ){

        Specification<User> spec = Specification
                .where(UserSpecification.hasName(name));

        Page<User> users = repository.findAll(spec, pageable);

        Page<UserResponse> response = users.map(mapper::toResponse);

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
    public User create(UserCreateRequest request){

        if(repository.existsByEmail(request.getEmail())){
            throw new DuplicateResourceException(
                    String.format("User with email %s already exists", request.getEmail())
            );
        }

        if (repository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException(
                    String.format("User with username %s already exists", request.getUsername())
            );
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Role with id %s not found", request.getRoleId())
                ));

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setUsername(request.getUsername());
        user.setRole(role);

        return repository.save(user);
    }

    public UserWithRolePermissionResponse findById(UUID id){
        User user = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );

        return mapper.userWithRolePermissionResponse(user);
    }

    @Transactional
    public User update(UUID id, UserUpdateRequest request){
        User user = repository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("User not found")
                );

        if (repository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new DuplicateResourceException(
                    String.format(
                            "User with email %s already exists",
                            request.getEmail()
                    )
            );
        }

        if (repository.existsByUsernameAndIdNot(request.getUsername(), id)) {
            throw new DuplicateResourceException(
                    String.format(
                            "User with username %s already exists",
                            request.getUsername()
                    )
            );
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Role with id %s not found", request.getRoleId())
                ));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setRole(role);

        return repository.save(user);
    }

    @Transactional
    public void delete(UUID id){
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        repository.delete(user);

    }
}
