package com.example.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.usecase.album.GetAlbumsQueryHandler;
import com.example.domain.model.Album;
import com.example.domain.query.GetAlbumsQuery;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private GetAlbumsQueryHandler getAlbumsQueryHandler;

    @GetMapping
    public List<Album> getAllAlbums() {
        GetAlbumsQuery query = new GetAlbumsQuery();
        return getAlbumsQueryHandler.handle(query);
    }
}
