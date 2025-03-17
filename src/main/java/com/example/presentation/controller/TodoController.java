package com.example.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.usecase.GetAllTodosQueryHandler;
import com.example.domain.model.Todo;
import com.example.domain.query.GetAllTodosQuery;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private GetAllTodosQueryHandler getAllTodosQueryHandler;

    @GetMapping
    public List<Todo> getAllTodos() {
        GetAllTodosQuery query = new GetAllTodosQuery();
        return getAllTodosQueryHandler.handle(query);
    }
}
