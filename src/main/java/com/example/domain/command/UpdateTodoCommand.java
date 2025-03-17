package com.example.domain.command;

public class UpdateTodoCommand {
    private final Long id;
    private final String title;

    public UpdateTodoCommand(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
