package com.example.demo.service;

import com.example.demo.dto.employee.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @Mock
    private EmployeeMapper mapper;

    @InjectMocks
    private EmployeeService service;

    @Test
    void findById() {
        UUID id = UUID.randomUUID();

        Employee employee = new Employee();
        employee.setId(id);
        employee.setName("test");
        employee.setEmail("test@mail.com");
        employee.setUsername("test");

        EmployeeResponse response = new EmployeeResponse(
                id,
                "test",
                "test",
                "test@mail.com",
                null,
                employee.getCreatedAt(),
                employee.getUpdatedAt()

        );

        when(repository.findByIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(employee));

        when(mapper.toResponse(employee))
                .thenReturn(response);

        EmployeeResponse result = service.findById(id);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals("test", result.name());
        assertEquals("test", result.username());
        assertEquals("test@mail.com", result.email());
    }
}
