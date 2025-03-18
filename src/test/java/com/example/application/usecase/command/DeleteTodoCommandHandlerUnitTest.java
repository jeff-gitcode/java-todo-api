package com.example.application.usecase.command;

import com.example.application.usecase.todo.DeleteTodoCommandHandler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

import com.example.application.interfaces.TodoRepository;
import com.example.domain.command.DeleteTodoCommand;

public class DeleteTodoCommandHandlerUnitTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private DeleteTodoCommandHandler deleteTodoCommandHandler;

    public DeleteTodoCommandHandlerUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandle() {
        DeleteTodoCommand command = new DeleteTodoCommand(1);

        deleteTodoCommandHandler.handle(command);

        verify(todoRepository).deleteById(1);
    }
}
