package com.example.presentation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.application.dto.TodoDTO;
import com.example.application.usecase.CreateTodoCommandHandler;
import com.example.application.usecase.DeleteTodoCommandHandler;
import com.example.application.usecase.GetAllTodosQueryHandler;
import com.example.application.usecase.GetTodoByIdQueryHandler;
import com.example.application.usecase.UpdateTodoCommandHandler;
import com.example.domain.command.CreateTodoCommand;
import com.example.domain.command.UpdateTodoCommand;
import com.example.domain.model.Todo;
import com.example.domain.query.GetAllTodosQuery;
import com.example.domain.query.GetTodoByIdQuery;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetAllTodosQueryHandler getAllTodosQueryHandler;

    @MockBean
    private GetTodoByIdQueryHandler getTodoByIdQueryHandler;

    @MockBean
    private DeleteTodoCommandHandler deleteTodoCommandHandler;

    @MockBean
    private CreateTodoCommandHandler createTodoCommandHandler;

    @MockBean
    private UpdateTodoCommandHandler updateTodoCommandHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllTodos() throws Exception {
        Todo todo1 = new Todo();
        todo1.setTitle("Todo 1");

        Todo todo2 = new Todo();
        todo2.setTitle("Todo 2");

        when(getAllTodosQueryHandler.handle(any(GetAllTodosQuery.class))).thenReturn(Arrays.asList(todo1, todo2));

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Todo 1"))
                .andExpect(jsonPath("$[1].title").value("Todo 2"));
    }

    @Test
    public void testGetTodoById() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("Todo 1");

        when(getTodoByIdQueryHandler.handle(any(GetTodoByIdQuery.class))).thenReturn(Optional.of(todo));

        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Todo 1"));
    }

    @Test
    public void testCreateTodo() throws Exception {
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("New Todo");

        Todo todo = new Todo();
        todo.setTitle("New Todo");

        when(createTodoCommandHandler.handle(any(CreateTodoCommand.class))).thenReturn(todo);

        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Todo"));
    }

    @Test
    public void testUpdateTodo() throws Exception {
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("Updated Todo");

        Todo todo = new Todo();
        todo.setTitle("Updated Todo");

        when(updateTodoCommandHandler.handle(any(UpdateTodoCommand.class))).thenReturn(todo);

        mockMvc.perform(put("/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Todo"));
    }

    @Test
    public void testDeleteTodo() throws Exception {
        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isOk());
    }
}
