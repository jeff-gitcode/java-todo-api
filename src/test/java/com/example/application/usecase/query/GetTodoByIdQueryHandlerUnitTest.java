package com.example.application.usecase.query;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.application.usecase.todo.GetTodoByIdQueryHandler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.application.interfaces.TodoRepository;
import com.example.domain.model.Todo;
import com.example.domain.query.GetTodoByIdQuery;

public class GetTodoByIdQueryHandlerUnitTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private GetTodoByIdQueryHandler getTodoByIdQueryHandler;

    public GetTodoByIdQueryHandlerUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandle() {
        Todo todo = new Todo();
        todo.setTitle("Todo 1");

        when(todoRepository.findById(1)).thenReturn(Optional.of(todo));

        GetTodoByIdQuery query = new GetTodoByIdQuery(1);
        Optional<Todo> result = getTodoByIdQueryHandler.handle(query);
        assertEquals("Todo 1", result.get().getTitle());
    }
}
