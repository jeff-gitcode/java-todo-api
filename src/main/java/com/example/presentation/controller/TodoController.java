package com.example.presentation.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Todo> getAllTodos() {
        GetAllTodosQuery query = new GetAllTodosQuery();
        return getAllTodosQueryHandler.handle(query);
    }

    @GetMapping("/{id}")
    public Optional<Todo> getTodoById(@PathVariable Integer id) {
        GetTodoByIdQuery query = new GetTodoByIdQuery(id);
        return getTodoByIdQueryHandler.handle(query);
    }

    @DeleteMapping("/{id}")
    public void deleteTodoById(@PathVariable Integer id) {
        DeleteTodoCommand command = new DeleteTodoCommand(id);
        deleteTodoCommandHandler.handle(command);
    }

    @PostMapping
    public Todo createTodo(@Valid @RequestBody TodoDTO todo) {
        CreateTodoCommand command = new CreateTodoCommand(todo.getTitle());

        return createTodoCommandHandler.handle(command);
    }
    
    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Integer id, @Valid @RequestBody TodoDTO todo) {
        UpdateTodoCommand command = new UpdateTodoCommand(id, todo.getTitle());
        return updateTodoCommandHandler.handle(command);
    }    
}
