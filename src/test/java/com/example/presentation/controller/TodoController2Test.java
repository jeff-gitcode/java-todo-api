package com.example.presentation.controller;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.application.dto.TodoDTO;
import com.example.application.usecase.CreateTodoCommandHandler;
import com.example.application.usecase.DeleteTodoCommandHandler;
import com.example.application.usecase.GetAllTodosQueryHandler;
import com.example.application.usecase.GetTodoByIdQueryHandler;
import com.example.application.usecase.UpdateTodoCommandHandler;
import com.example.domain.command.CreateTodoCommand;
import com.example.domain.command.DeleteTodoCommand;
import com.example.domain.command.UpdateTodoCommand;
import com.example.domain.model.Todo;
import com.example.domain.query.GetAllTodosQuery;
import com.example.domain.query.GetTodoByIdQuery;

@ExtendWith(MockitoExtension.class)
public class TodoController2Test {

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
    public void testGetAllTodos() throws Exception {
        // Arrange
        Todo todo1 = new Todo();
        todo1.setTitle("Todo 1");

        Todo todo2 = new Todo();
        todo2.setTitle("Todo 2");

        given(getAllTodosQueryHandler.handle(any(GetAllTodosQuery.class))).willReturn(Arrays.asList(todo1, todo2));

        // Act
        var result = todoController.getAllTodos();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Todo 1");
        assertThat(result.get(1).getTitle()).isEqualTo("Todo 2");
    }

    @Test
    public void testGetTodoById() throws Exception {
        // Arrange
        Todo todo = new Todo();
        todo.setTitle("Todo 1");

        given(getTodoByIdQueryHandler.handle(any(GetTodoByIdQuery.class))).willReturn(Optional.of(todo));

        // Act
        var result = todoController.getTodoById(1);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Todo 1");
    }

    @Test
    public void testCreateTodo() throws Exception {
        // Arrange
        var todoDTO = new TodoDTO();
        todoDTO.setTitle("New Todo");

        Todo todo = new Todo();
        todo.setTitle("New Todo");

        given(createTodoCommandHandler.handle(any(CreateTodoCommand.class))).willReturn(todo);

        // Act
        var result = todoController.createTodo(todoDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("New Todo");
    }

    @Test
    public void testUpdateTodo() throws Exception {
        // Arrange
        var todoDTO = new TodoDTO();
        todoDTO.setTitle("Updated Todo");

        Todo todo = new Todo();
        todo.setTitle("Updated Todo");

        given(updateTodoCommandHandler.handle(any(UpdateTodoCommand.class))).willReturn(todo);

        // Act
        var result = todoController.updateTodo(1, todoDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Updated Todo");
    }

    @Test
    public void testDeleteTodo() throws Exception {
        // Act
        todoController.deleteTodoById(0x1);

        // Assert
        // Verify that the deleteTodoCommandHandler's handle method was called with the correct argument
        verify(deleteTodoCommandHandler).handle(any(DeleteTodoCommand.class));
    }
}
