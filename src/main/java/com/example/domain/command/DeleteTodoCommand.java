package com.example.domain.command;

public class DeleteTodoCommand {
    private final Long id;

    public DeleteTodoCommand(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
