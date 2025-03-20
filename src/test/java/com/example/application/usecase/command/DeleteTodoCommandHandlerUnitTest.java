package com.example.application.usecase.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

import com.example.application.interfaces.TodoRepository;
import com.example.application.usecase.todo.DeleteTodoCommandHandler;
import com.example.domain.command.DeleteTodoCommand;

public class DeleteTodoCommandHandlerUnitTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private DeleteTodoCommandHandler deleteTodoCommandHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandle() {
        // Arrange
        DeleteTodoCommand command = new DeleteTodoCommand(1);

        // Act
        deleteTodoCommandHandler.handle(command);

        // Assert
        verify(todoRepository).deleteById(1);
    }
}
