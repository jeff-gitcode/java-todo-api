package com.example.domain.command;

public class UpdateAlbumCommand {
    private final Integer id;
    private final Integer userId;
    private final String title;

    public UpdateAlbumCommand(Integer id, Integer userId, String title) {
        this.id = id;
        this.userId = userId;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }
}