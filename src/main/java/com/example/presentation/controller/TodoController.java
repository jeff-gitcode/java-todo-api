package com.example.presentation.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private GetAllTodosQueryHandler getAllTodosQueryHandler;

    @Autowired
    private GetTodoByIdQueryHandler getTodoByIdQueryHandler;

    @Autowired
    private DeleteTodoCommandHandler deleteTodoCommandHandler;

    @Autowired
    private CreateTodoCommandHandler createTodoCommandHandler;

    @Autowired
    private UpdateTodoCommandHandler updateTodoCommandHandler;

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = getAllTodosQueryHandler.handle(new GetAllTodosQuery());
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Integer id) {
        Optional<Todo> todo = getTodoByIdQueryHandler.handle(new GetTodoByIdQuery(id));
        return todo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody TodoDTO todo) {
        CreateTodoCommand command = new CreateTodoCommand(todo.getTitle());
        Todo createdTodo = createTodoCommandHandler.handle(command);
        return ResponseEntity.status(201).body(createdTodo); // HTTP 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Integer id, @Valid @RequestBody TodoDTO todo) {
        UpdateTodoCommand command = new UpdateTodoCommand(id, todo.getTitle());
        Todo updatedTodo = updateTodoCommandHandler.handle(command);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Integer id) {
        deleteTodoCommandHandler.handle(new DeleteTodoCommand(id));
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}
