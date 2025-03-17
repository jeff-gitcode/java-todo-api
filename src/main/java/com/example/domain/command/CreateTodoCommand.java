package com.example.domain.command;

public class CreateTodoCommand {
    private String title;

    public CreateTodoCommand(String title) {
        this.title = title;
    }

    // Getters and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}