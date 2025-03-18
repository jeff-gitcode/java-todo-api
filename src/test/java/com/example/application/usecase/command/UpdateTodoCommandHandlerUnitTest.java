package com.example.application.usecase.command;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.application.usecase.todo.UpdateTodoCommandHandler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.application.interfaces.TodoRepository;
import com.example.domain.command.UpdateTodoCommand;
import com.example.domain.model.Todo;

public class UpdateTodoCommandHandlerUnitTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private UpdateTodoCommandHandler updateTodoCommandHandler;

    public UpdateTodoCommandHandlerUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandle() {
        Todo todo = new Todo();
        todo.setTitle("Old Todo");

        when(todoRepository.findById(1)).thenReturn(Optional.of(todo));

        UpdateTodoCommand command = new UpdateTodoCommand(1, "Updated Todo");

        when(todoRepository.save(todo)).thenReturn(todo);

        Todo result = updateTodoCommandHandler.handle(command);
        assertEquals("Updated Todo", result.getTitle());
    }
}
