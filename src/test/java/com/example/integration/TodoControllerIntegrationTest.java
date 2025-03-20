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
        // Arrange
        Todo todo = new Todo();
        todo.setTitle("Sample Todo");
        todo = todoRepository.save(todo); // Save the Todo and get the generated ID

        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Act
        ResponseEntity<Todo> response = restTemplate.exchange(
                createURLWithPort("/todos/" + todo.getId()),
                HttpMethod.GET,
                entity,
                Todo.class
        );

        // Assert
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue(); // Verify the response status is 200 OK
        Todo result = response.getBody();
        assertThat(result).isNotNull(); // Verify the response body is not null
        assertThat(result.getId()).isEqualTo(todo.getId()); // Verify the ID matches
        assertThat(result.getTitle()).isEqualTo("Sample Todo"); // Verify the title matches
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
        // Arrange
        Todo todo = new Todo();
        todo.setTitle("Original Todo");
        todo = todoRepository.save(todo); // Save the Todo and get the generated ID

        Todo updatedTodo = new Todo();
        updatedTodo.setTitle("Updated Todo");

        HttpHeaders headers = createHeaders();
        HttpEntity<Todo> entity = new HttpEntity<>(updatedTodo, headers);

        // Act
        ResponseEntity<Todo> response = restTemplate.exchange(
                createURLWithPort("/todos/" + todo.getId()),
                HttpMethod.PUT,
                entity,
                Todo.class
        );

        // Assert
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue(); // Verify the response status is 200 OK
        Todo result = response.getBody();
        assertThat(result).isNotNull(); // Verify the response body is not null
        assertThat(result.getId()).isEqualTo(todo.getId()); // Verify the ID matches
        assertThat(result.getTitle()).isEqualTo("Updated Todo"); // Verify the title is updated
    }

    @Test
    public void testDeleteTodo() {
        // Arrange
        Todo todo = new Todo();
        todo.setTitle("Todo to be deleted");
        todo = todoRepository.save(todo); // Save the Todo and get the generated ID

        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Act
        ResponseEntity<Void> response = restTemplate.exchange(
                createURLWithPort("/todos/" + todo.getId()),
                HttpMethod.DELETE,
                entity,
                Void.class
        );

        // Assert
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue(); // Verify the response status is 200 OK

        // Verify the Todo is deleted
        boolean exists = todoRepository.existsById(todo.getId());
        assertThat(exists).isFalse(); // Verify the Todo no longer exists in the database
    }
}
