package com.example.domain.query;

public class GetAlbumByIdQuery {
    private final Integer id;

    public GetAlbumByIdQuery(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}