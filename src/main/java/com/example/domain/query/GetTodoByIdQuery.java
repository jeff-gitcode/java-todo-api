package com.example.domain.query;

public class GetTodoByIdQuery {
    private final Integer id;

    public GetTodoByIdQuery(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}