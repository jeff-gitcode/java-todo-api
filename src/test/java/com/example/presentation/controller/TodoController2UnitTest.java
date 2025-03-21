package com.example.presentation.controller;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.application.dto.TodoDTO;
import com.example.application.usecase.todo.CreateTodoCommandHandler;
import com.example.application.usecase.todo.DeleteTodoCommandHandler;
import com.example.application.usecase.todo.GetAllTodosQueryHandler;
import com.example.application.usecase.todo.GetTodoByIdQueryHandler;
import com.example.application.usecase.todo.UpdateTodoCommandHandler;
import com.example.domain.command.CreateTodoCommand;
import com.example.domain.command.DeleteTodoCommand;
import com.example.domain.command.UpdateTodoCommand;
import com.example.domain.model.Todo;
import com.example.domain.query.GetAllTodosQuery;
import com.example.domain.query.GetTodoByIdQuery;

@ExtendWith(MockitoExtension.class)
public class TodoController2UnitTest {

    @InjectMocks
    private TodoController todoController;

    @Mock
    private CreateTodoCommandHandler createTodoCommandHandler;

    @Mock
    private GetAllTodosQueryHandler getAllTodosQueryHandler;

    @Mock
    private GetTodoByIdQueryHandler getTodoByIdQueryHandler;

    @Mock
    private UpdateTodoCommandHandler updateTodoCommandHandler;

    @Mock
    private DeleteTodoCommandHandler deleteTodoCommandHandler;

    @Test
    public void testGetAllTodos() {
        // Arrange
        Todo todo1 = new Todo();
        todo1.setTitle("Todo 1");

        Todo todo2 = new Todo();
        todo2.setTitle("Todo 2");

        given(getAllTodosQueryHandler.handle(any(GetAllTodosQuery.class))).willReturn(Arrays.asList(todo1, todo2));

        // Act
        ResponseEntity<java.util.List<Todo>> response = todoController.getAllTodos();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // HTTP 200 OK
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        assertThat(response.getBody().get(0).getTitle()).isEqualTo("Todo 1");
        assertThat(response.getBody().get(1).getTitle()).isEqualTo("Todo 2");
    }

    @Test
    public void testGetTodoById() {
        // Arrange
        Todo todo = new Todo();
        todo.setTitle("Todo 1");

        given(getTodoByIdQueryHandler.handle(any(GetTodoByIdQuery.class))).willReturn(Optional.of(todo));

        // Act
        ResponseEntity<Todo> response = todoController.getTodoById(1);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // HTTP 200 OK
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Todo 1");
    }

    @Test
    public void testCreateTodo() {
        // Arrange
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("New Todo");

        Todo todo = new Todo();
        todo.setTitle("New Todo");

        given(createTodoCommandHandler.handle(any(CreateTodoCommand.class))).willReturn(todo);

        // Act
        ResponseEntity<Todo> response = todoController.createTodo(todoDTO);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(201); // HTTP 201 Created
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("New Todo");
    }

    @Test
    public void testUpdateTodo() {
        // Arrange
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("Updated Todo");

        Todo todo = new Todo();
        todo.setTitle("Updated Todo");

        given(updateTodoCommandHandler.handle(any(UpdateTodoCommand.class))).willReturn(todo);

        // Act
        ResponseEntity<Todo> response = todoController.updateTodo(1, todoDTO);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // HTTP 200 OK
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Updated Todo");
    }

    @Test
    public void testDeleteTodo() {
        // Act
        ResponseEntity<Void> response = todoController.deleteTodoById(1);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(204); // HTTP 204 No Content
        verify(deleteTodoCommandHandler).handle(any(DeleteTodoCommand.class));
    }
}
