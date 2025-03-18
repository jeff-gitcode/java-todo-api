package com.example.application.usecase.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.interfaces.TodoRepository;
import com.example.domain.command.CreateTodoCommand;
import com.example.domain.model.Todo;

@Component
public class CreateTodoCommandHandler {

    @Autowired
    private TodoRepository todoRepository;

    public Todo handle(CreateTodoCommand command) {
        Todo todo = new Todo();
        todo.setTitle(command.getTitle());
        var result = todoRepository.save(todo);
        return result;
    }
}