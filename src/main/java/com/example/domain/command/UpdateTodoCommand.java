package com.example.domain.command;

public class UpdateTodoCommand {
    private final Integer id;
    private final String title;

    public UpdateTodoCommand(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
