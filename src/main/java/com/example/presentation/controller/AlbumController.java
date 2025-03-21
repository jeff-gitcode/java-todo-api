package com.example.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> albums = getAlbumsQueryHandler.handle(new GetAlbumsQuery());
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Integer id) {
        Album album = getAlbumByIdQueryHandler.handle(new GetAlbumByIdQuery(id));
        return ResponseEntity.ok(album);
    }

    @PostMapping
    public ResponseEntity<Album> createAlbum(@RequestBody CreateAlbumCommand command) {
        Album album = createAlbumCommandHandler.handle(command);
        return ResponseEntity.status(201).body(album); // HTTP 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Integer id, @RequestBody UpdateAlbumCommand command) {
        Album album = updateAlbumCommandHandler.handle(new UpdateAlbumCommand(id, command.getUserId(), command.getTitle()));
        return ResponseEntity.ok(album);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Integer id) {
        deleteAlbumCommandHandler.handle(new DeleteAlbumCommand(id));
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}
