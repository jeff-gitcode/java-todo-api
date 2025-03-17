package com.example.application.usecase;

import com.example.domain.command.CreateTodoCommand;
import com.example.domain.model.Todo;
import com.example.application.interfaces.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateTodoCommandHandler {

    private final TodoRepository todoRepository;

    @Autowired
    public CreateTodoCommandHandler(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo handle(CreateTodoCommand command) {
        Todo todo = new Todo();
        todo.setTitle(command.getTitle());
        return todoRepository.save(todo);
    }
}