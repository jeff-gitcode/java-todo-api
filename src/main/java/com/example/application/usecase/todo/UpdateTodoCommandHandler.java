package com.example.application.usecase.todo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.interfaces.TodoRepository;
import com.example.domain.command.UpdateTodoCommand;
import com.example.domain.model.Todo;

@Component
public class UpdateTodoCommandHandler {

    @Autowired
    private TodoRepository todoRepository;

    public Todo handle(UpdateTodoCommand command) {
        Optional<Todo> optionalTodo = todoRepository.findById(command.getId());
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setTitle(command.getTitle());
            return todoRepository.save(todo);
        } else {
            throw new RuntimeException("Todo not found");
        }
    }
}
