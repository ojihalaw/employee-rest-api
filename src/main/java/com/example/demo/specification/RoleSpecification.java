package com.example.demo.specification;

import com.example.demo.entity.Role;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecification {

    public static Specification<Role> hasName(String name){
        return (root, query, cb) -> {
            if(name == null || name.isBlank()) return cb.conjunction();

            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%" );
        };
    }
}
