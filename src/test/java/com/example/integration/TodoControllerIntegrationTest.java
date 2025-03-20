package com.example.integration;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.application.auth.JwtUtil;
import com.example.application.dto.TodoDTO;
import com.example.application.interfaces.TodoRepository;
import com.example.domain.model.Todo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureMockMvc
public class TodoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private String jwtToken;

    @BeforeEach
    public void setUp() {
        todoRepository.deleteAll();

        // Generate a valid JWT token
        jwtToken = jwtUtil.generateToken("test@example.com");
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        return headers;
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    // @WithMockUser(username = "test@example.com", roles = {"USER"})
    public void testGetAllTodos() {
        // Arrange
        Todo todo1 = new Todo();
        todo1.setTitle("Todo 1");
        todoRepository.save(todo1);

        Todo todo2 = new Todo();
        todo2.setTitle("Todo 2");
        todoRepository.save(todo2);

        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Act
        ResponseEntity<Todo[]> response = restTemplate.exchange(
                createURLWithPort("/todos"),
                HttpMethod.GET,
                entity,
                Todo[].class
        );

        // Assert
        List<Todo> todos = Arrays.asList(response.getBody());
        assertThat(todos).hasSize(2);
        assertThat(todos.get(0).getTitle()).isEqualTo("Todo 1");
        assertThat(todos.get(1).getTitle()).isEqualTo("Todo 2");
    }

    @Test
    public void testGetTodoById() {
        Todo todo = new Todo();
        todo.setTitle("Todo 1");
        todo = todoRepository.save(todo);

        ResponseEntity<Todo> response = restTemplate.getForEntity(createURLWithPort("/todos/" + todo.getId()), Todo.class);
        Todo result = response.getBody();

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Todo 1");
    }

    @Test
    public void testCreateTodo() {
        // Arrange
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("New Todo");

        HttpHeaders headers = createHeaders();
        HttpEntity<TodoDTO> entity = new HttpEntity<>(todoDTO, headers);

        // Act
        ResponseEntity<Todo> response = restTemplate.exchange(
                createURLWithPort("/todos"),
                HttpMethod.POST,
                entity,
                Todo.class
        );

        // Assert
        Todo result = response.getBody();
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("New Todo");
    }

    @Test
    public void testUpdateTodo() {
        Todo todo = new Todo();
        todo.setTitle("Old Todo");
        todo = todoRepository.save(todo);

        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("Updated Todo");

        HttpEntity<TodoDTO> requestEntity = new HttpEntity<>(todoDTO);
        ResponseEntity<Todo> response = restTemplate.exchange(createURLWithPort("/todos/" + todo.getId()), HttpMethod.PUT, requestEntity, Todo.class);
        Todo result = response.getBody();

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Updated Todo");
    }

    @Test
    public void testDeleteTodo() {
        Todo todo = new Todo();
        todo.setTitle("Todo to be deleted");
        todo = todoRepository.save(todo);

        restTemplate.delete(createURLWithPort("/todos/" + todo.getId()));

        ResponseEntity<Todo> response = restTemplate.getForEntity(createURLWithPort("/todos/" + todo.getId()), Todo.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        assertThat(response.getBody()).isNull();
    }
}
