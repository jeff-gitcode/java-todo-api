package com.example.application.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.interfaces.TodoRepository;
import com.example.domain.command.DeleteTodoCommand;

@Component
public class DeleteTodoCommandHandler {

    private final TodoRepository todoRepository;

    @Autowired
    public DeleteTodoCommandHandler(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void handle(DeleteTodoCommand command) {
        todoRepository.deleteById(command.getId());
    }
}
