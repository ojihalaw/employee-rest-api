package com.example.demo.specification;

import com.example.demo.entity.Permission;
import org.springframework.data.jpa.domain.Specification;

public class PermissionSpecification {
    
    public static Specification<Permission> hasName(String name){
        return (root, query, cb) -> {
            if(name == null || name.isBlank()) return cb.conjunction();

            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%" );
        };
    }
}
