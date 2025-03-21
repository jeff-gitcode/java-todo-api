package com.example.presentation.controller;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

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

@ExtendWith(MockitoExtension.class)
public class AlbumControllerUnitTest {

    @InjectMocks
    private AlbumController albumController;

    @Mock
    private GetAlbumsQueryHandler getAlbumsQueryHandler;

    @Mock
    private GetAlbumByIdQueryHandler getAlbumByIdQueryHandler;

    @Mock
    private CreateAlbumCommandHandler createAlbumCommandHandler;

    @Mock
    private UpdateAlbumCommandHandler updateAlbumCommandHandler;

    @Mock
    private DeleteAlbumCommandHandler deleteAlbumCommandHandler;

    @Test
    public void testGetAllAlbums_Success() {
        // Arrange
        Album album1 = new Album(1, 1, "Album 1");
        Album album2 = new Album(2, 1, "Album 2");
        List<Album> albums = Arrays.asList(album1, album2);

        given(getAlbumsQueryHandler.handle(any(GetAlbumsQuery.class))).willReturn(albums);

        // Act
        ResponseEntity<List<Album>> response = albumController.getAllAlbums();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // HTTP 200 OK
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        assertThat(response.getBody().get(0).getTitle()).isEqualTo("Album 1");
        assertThat(response.getBody().get(1).getTitle()).isEqualTo("Album 2");
    }

    @Test
    public void testGetAlbumById_Success() {
        // Arrange
        Album album = new Album(1, 1, "Album 1");
        given(getAlbumByIdQueryHandler.handle(any(GetAlbumByIdQuery.class))).willReturn(album);

        // Act
        ResponseEntity<Album> response = albumController.getAlbumById(1);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // HTTP 200 OK
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getTitle()).isEqualTo("Album 1");
    }

    @Test
    public void testCreateAlbum_Success() {
        // Arrange
        Album album = new Album(1, 1, "New Album");
        given(createAlbumCommandHandler.handle(any(CreateAlbumCommand.class))).willReturn(album);

        // Act
        ResponseEntity<Album> response = albumController.createAlbum(new CreateAlbumCommand(1, "New Album"));

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(201); // HTTP 201 Created
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getTitle()).isEqualTo("New Album");
    }

    @Test
    public void testUpdateAlbum_Success() {
        // Arrange
        Album album = new Album(1, 1, "Updated Album");
        given(updateAlbumCommandHandler.handle(any(UpdateAlbumCommand.class))).willReturn(album);

        // Act
        ResponseEntity<Album> response = albumController.updateAlbum(1, new UpdateAlbumCommand(1, 1, "Updated Album"));

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // HTTP 200 OK
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getTitle()).isEqualTo("Updated Album");
    }

    @Test
    public void testDeleteAlbum_Success() {
        // Arrange
        doNothing().when(deleteAlbumCommandHandler).handle(any(DeleteAlbumCommand.class));

        // Act
        ResponseEntity<Void> response = albumController.deleteAlbum(1);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(204); // HTTP 204 No Content
        assertThat(response.getBody()).isNull(); // No content in the response body
    }
}