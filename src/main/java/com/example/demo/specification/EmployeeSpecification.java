package com.example.demo.specification;

import com.example.demo.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    public static Specification<Employee> notDeleted(){
        return (root, query, cb) ->
                cb.isNull(root.get("deletedAt"));
    }

    public static Specification<Employee> hasName(String name){
        return (root, query, cb) -> {
            if(name == null || name.isBlank()) return cb.conjunction();

            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%" );
        };
    }
}
