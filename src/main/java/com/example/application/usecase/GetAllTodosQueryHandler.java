package com.example.application.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.interfaces.TodoRepository;
import com.example.domain.query.GetAllTodosQuery;
import com.example.domain.model.Todo;

@Component
public class GetAllTodosQueryHandler {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> handle(GetAllTodosQuery query) {
        return todoRepository.findAll();
    }
}
