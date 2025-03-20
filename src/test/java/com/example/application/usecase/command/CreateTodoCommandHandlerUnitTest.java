package com.example.application.usecase.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.application.interfaces.TodoRepository;
import com.example.application.usecase.todo.CreateTodoCommandHandler;
import com.example.domain.command.CreateTodoCommand;
import com.example.domain.model.Todo;

public class CreateTodoCommandHandlerUnitTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private CreateTodoCommandHandler createTodoCommandHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandle() {
        // Arrange
        CreateTodoCommand command = new CreateTodoCommand("New Todo");

        Todo todo = new Todo();
        todo.setTitle("New Todo");

        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        // Act
        Todo result = createTodoCommandHandler.handle(command);

        // Assert
        assertEquals("New Todo", result.getTitle());
    }
}