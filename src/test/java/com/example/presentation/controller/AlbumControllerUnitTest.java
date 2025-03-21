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
        List<Album> result = albumController.getAllAlbums();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Album 1");
        assertThat(result.get(1).getTitle()).isEqualTo("Album 2");
    }

    @Test
    public void testGetAlbumById_Success() {
        // Arrange
        Album album = new Album(1, 1, "Album 1");
        given(getAlbumByIdQueryHandler.handle(any(GetAlbumByIdQuery.class))).willReturn(album);

        // Act
        Album result = albumController.getAlbumById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getTitle()).isEqualTo("Album 1");
    }

    @Test
    public void testCreateAlbum_Success() {
        // Arrange
        Album album = new Album(1, 1, "New Album");
        given(createAlbumCommandHandler.handle(any(CreateAlbumCommand.class))).willReturn(album);

        // Act
        Album result = albumController.createAlbum(new CreateAlbumCommand(1, "New Album"));

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getTitle()).isEqualTo("New Album");
    }

    @Test
    public void testUpdateAlbum_Success() {
        // Arrange
        Album album = new Album(1, 1, "Updated Album");
        given(updateAlbumCommandHandler.handle(any(UpdateAlbumCommand.class))).willReturn(album);

        // Act
        Album result = albumController.updateAlbum(1, new UpdateAlbumCommand(1, 1, "Updated Album"));

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getTitle()).isEqualTo("Updated Album");
    }

    @Test
    public void testDeleteAlbum_Success() {
        // Arrange
        doNothing().when(deleteAlbumCommandHandler).handle(any(DeleteAlbumCommand.class));

        // Act
        albumController.deleteAlbum(1);

        // Assert
        // No exception should be thrown
    }

    @Test
    public void testGetAllAlbums_EmptyList() {
        // Arrange
        given(getAlbumsQueryHandler.handle(any(GetAlbumsQuery.class))).willReturn(Arrays.asList());

        // Act
        List<Album> result = albumController.getAllAlbums();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(0); // Expect an empty list
    }

    @Test
    public void testGetAllAlbums_Exception() {
        // Arrange
        given(getAlbumsQueryHandler.handle(any(GetAlbumsQuery.class))).willThrow(new RuntimeException("Internal Server Error"));

        // Act & Assert
        try {
            albumController.getAllAlbums();
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Internal Server Error");
        }
    }
}