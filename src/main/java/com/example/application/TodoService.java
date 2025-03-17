package com.example.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.infrastructure.TodoRepository;
import com.example.model.Todo;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    
    // get all todos
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    // create a todo
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }
    
}
