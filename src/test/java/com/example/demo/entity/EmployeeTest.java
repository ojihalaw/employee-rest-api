package com.example.demo.entity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void prePersist_shouldSetCreatedAtAndUpdatedAt() {

        // Arrange
        Employee employee = new Employee();

        // Act
        employee.prePersist();

        // Assert
        assertNotNull(employee.getCreatedAt());
        assertNotNull(employee.getUpdatedAt());

        assertEquals(
                employee.getCreatedAt(),
                employee.getUpdatedAt()
        );
    }

    @Test
    void preUpdate_shouldUpdateUpdatedAt() {

        // Arrange
        Employee employee = new Employee();

        LocalDateTime oldUpdatedAt = LocalDateTime.now().minusDays(1);

        employee.setUpdatedAt(oldUpdatedAt);

        // Act
        employee.preUpdate();

        // Assert
        assertNotNull(employee.getUpdatedAt());

        assertTrue(
                employee.getUpdatedAt().isAfter(oldUpdatedAt)
        );
    }
}