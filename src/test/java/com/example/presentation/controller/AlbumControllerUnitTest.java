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
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.application.usecase.album.GetAlbumsQueryHandler;
import com.example.domain.model.Album;
import com.example.domain.query.GetAlbumsQuery;

@ExtendWith(MockitoExtension.class)
public class AlbumControllerUnitTest {

    @InjectMocks
    private AlbumController albumController;

    @Mock
    private GetAlbumsQueryHandler getAlbumsQueryHandler;

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