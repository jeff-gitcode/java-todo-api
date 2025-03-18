package com.example.application.service;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.application.TodoService;
import com.example.application.interfaces.TodoRepository;
import com.example.domain.model.Todo;

public class TodoServiceUnitTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    // public TodoServiceTest() {
    //     MockitoAnnotations.openMocks(this);
    // }

    @BeforeEach
    public void Setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTodos() {
        Todo todo1 = new Todo();
        todo1.setTitle("Todo 1");

        Todo todo2 = new Todo();
        todo2.setTitle("Todo 2");

        List<Todo> todos = Arrays.asList(todo1, todo2);

        when(todoRepository.findAll()).thenReturn(todos);

        List<Todo> result = todoService.getAllTodos();
        assertEquals(2, result.size());
        assertEquals("Todo 1", result.get(0).getTitle());
        assertEquals("Todo 2", result.get(1).getTitle());
    }

    @Test
    public void testCreateTodo() {
        Todo todo = new Todo();
        todo.setTitle("New Todo");

        when(todoRepository.save(todo)).thenReturn(todo);

        Todo result = todoService.createTodo(todo);
        assertEquals("New Todo", result.getTitle());
    }
}
