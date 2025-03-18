package com.example.application.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class TodoDTOUnitTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTodoDTOSettersAndGetters() {
        // Arrange
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setId(2);
        todoDTO.setTitle("Another Test Todo");

        // Act & Assert
        assertEquals(2, todoDTO.getId());
        assertEquals("Another Test Todo", todoDTO.getTitle());
    }

    @Test
    public void testTodoDTOValidation() {
        // Arrange
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setId(2);
        todoDTO.setTitle("");

        // Act & Assert
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            // Simulate validation
            validator.validate(todoDTO).forEach(violation -> {
                throw new ConstraintViolationException(violation.getMessage(), null);
            });
        });
        assertEquals("Title is mandatory", exception.getMessage());
    }
}
