package com.example.domain.command;

public class CreateAlbumCommand {
    private final Integer userId;
    private final String title;

    public CreateAlbumCommand(Integer userId, String title) {
        this.userId = userId;
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }
}