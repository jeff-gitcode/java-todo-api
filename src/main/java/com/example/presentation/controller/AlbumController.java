package com.example.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.usecase.album.CreateAlbumCommandHandler;
import com.example.application.usecase.album.DeleteAlbumCommandHandler;
import com.example.application.usecase.album.GetAlbumByIdQueryHandler;
import com.example.application.usecase.album.GetAlbumsQueryHandler;
import com.example.application.usecase.album.UpdateAlbumCommandHandler;
import com.example.domain.command.CreateAlbumCommand;
import com.example.domain.command.DeleteAlbumCommand;
import com.example.domain.command.UpdateAlbumCommand;
import com.example.domain.model.Album;
import com.example.domain.query.GetAlbumByIdQuery;
import com.example.domain.query.GetAlbumsQuery;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private GetAlbumsQueryHandler getAlbumsQueryHandler;

    @Autowired
    private GetAlbumByIdQueryHandler getAlbumByIdQueryHandler;

    @Autowired
    private CreateAlbumCommandHandler createAlbumCommandHandler;

    @Autowired
    private UpdateAlbumCommandHandler updateAlbumCommandHandler;

    @Autowired
    private DeleteAlbumCommandHandler deleteAlbumCommandHandler;

    @GetMapping
    public List<Album> getAllAlbums() {
        return getAlbumsQueryHandler.handle(new GetAlbumsQuery());
    }

    @GetMapping("/{id}")
    public Album getAlbumById(@PathVariable Integer id) {
        return getAlbumByIdQueryHandler.handle(new GetAlbumByIdQuery(id));
    }

    @PostMapping
    public Album createAlbum(@RequestBody CreateAlbumCommand command) {
        return createAlbumCommandHandler.handle(command);
    }

    @PutMapping("/{id}")
    public Album updateAlbum(@PathVariable Integer id, @RequestBody UpdateAlbumCommand command) {
        return updateAlbumCommandHandler.handle(new UpdateAlbumCommand(id, command.getUserId(), command.getTitle()));
    }

    @DeleteMapping("/{id}")
    public void deleteAlbum(@PathVariable Integer id) {
        deleteAlbumCommandHandler.handle(new DeleteAlbumCommand(id));
    }
}
