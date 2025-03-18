package com.example.application.usecase;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.application.interfaces.TodoRepository;
import com.example.domain.model.Todo;
import com.example.domain.query.GetAllTodosQuery;

public class GetAllTodosQueryHandlerUnitTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private GetAllTodosQueryHandler getAllTodosQueryHandler;

    public GetAllTodosQueryHandlerUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandle() {
        Todo todo1 = new Todo();
        todo1.setTitle("Todo 1");

        Todo todo2 = new Todo();
        todo2.setTitle("Todo 2");

        List<Todo> todos = Arrays.asList(todo1, todo2);

        when(todoRepository.findAll()).thenReturn(todos);

        GetAllTodosQuery query = new GetAllTodosQuery();
        List<Todo> result = getAllTodosQueryHandler.handle(query);
        assertEquals(2, result.size());
        assertEquals("Todo 1", result.get(0).getTitle());
        assertEquals("Todo 2", result.get(1).getTitle());
    }
}
