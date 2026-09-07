package com.example.demo.specification;

import com.example.demo.entity.Department;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<Department> notDeleted(){
        return (root, query, cb) ->
                cb.isNull(root.get("deletedAt"));
    }

    public static Specification<User> hasName(String name){
        return (root, query, cb) -> {
            if(name == null || name.isBlank()) return cb.conjunction();

            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%" );
        };
    }
}
