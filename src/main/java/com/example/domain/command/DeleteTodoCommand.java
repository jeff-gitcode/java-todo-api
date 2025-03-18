package com.example.domain.command;

public class DeleteTodoCommand {
    private final Integer id;

    public DeleteTodoCommand(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
