package com.example.domain.command;

public class DeleteAlbumCommand {
    private final Integer id;

    public DeleteAlbumCommand(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}