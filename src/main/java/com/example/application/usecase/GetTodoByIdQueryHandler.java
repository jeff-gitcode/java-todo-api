package com.example.application.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.interfaces.TodoRepository;
import com.example.domain.model.Todo;
import com.example.domain.query.GetTodoByIdQuery;

@Component
public class GetTodoByIdQueryHandler {

    @Autowired
    private TodoRepository todoRepository;

    public Optional<Todo> handle(GetTodoByIdQuery query) {
        return todoRepository.findById(query.getId());
    }
}