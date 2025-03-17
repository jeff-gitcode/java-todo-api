package com.example.domain.query;

public class GetTodoByIdQuery {
    private final Long id;

    public GetTodoByIdQuery(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}