package com.example.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.interfaces.TodoRepository;
import com.example.domain.model.Todo;

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
