package com.example.demo.repository;

import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID>,
        JpaSpecificationExecutor<Role>
{
    @EntityGraph(attributePaths = "permissions")
    @Override
    List<Role> findAll();

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}