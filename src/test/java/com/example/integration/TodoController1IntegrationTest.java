package com.example.integration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.asyncDispatch;

import com.example.application.dto.TodoDTO;
import com.example.application.interfaces.TodoRepository;
import com.example.domain.model.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoController1IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        todoRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testGetAllTodos() throws Exception {
        // Arrange
        Todo todo1 = new Todo();
        todo1.setTitle("Todo 1");
        todoRepository.save(todo1);

        Todo todo2 = new Todo();
        todo2.setTitle("Todo 2");
        todoRepository.save(todo2);

        // Act
        MvcResult result = mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        mockMvc.perform(asyncDispatch(result))
                .andExpect(jsonPath("$[0].title").value("Todo 1"))
                .andExpect(jsonPath("$[1].title").value("Todo 2"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testGetTodoById() throws Exception {
        // Arrange
        Todo todo = new Todo();
        todo.setTitle("Todo 1");
        todo = todoRepository.save(todo);

        // Act
        MvcResult result = mockMvc.perform(get("/todos/" + todo.getId()))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        mockMvc.perform(asyncDispatch(result))
                .andExpect(jsonPath("$.title").value("Todo 1"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testCreateTodo() throws Exception {
        // Arrange
        Todo todo = new Todo();
        todo.setTitle("New Todo");
        String todoJson = objectMapper.writeValueAsString(todo);

        // Act
        MvcResult result = mockMvc.perform(post("/todos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(todoJson))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        mockMvc.perform(asyncDispatch(result))
                .andExpect(jsonPath("$.title").value("New Todo"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testUpdateTodo() throws Exception {
        // Arrange
        Todo todo = new Todo();
        todo.setTitle("Old Todo");
        todo = todoRepository.save(todo);

        Todo updatedTodo = new Todo();
        updatedTodo.setTitle("Updated Todo");
        String updatedTodoJson = objectMapper.writeValueAsString(updatedTodo);

        // Act
        MvcResult result = mockMvc.perform(put("/todos/" + todo.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedTodoJson))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        mockMvc.perform(asyncDispatch(result))
                .andExpect(jsonPath("$.title").value("Updated Todo"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testDeleteTodo() throws Exception {
        // Arrange
        Todo todo = new Todo();
        todo.setTitle("Todo to be deleted");
        todo = todoRepository.save(todo);

        // Act
        MvcResult result = mockMvc.perform(delete("/todos/" + todo.getId()))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        boolean exists = todoRepository.existsById(todo.getId());
        assertFalse(exists, "Todo should be deleted");
    }
}
