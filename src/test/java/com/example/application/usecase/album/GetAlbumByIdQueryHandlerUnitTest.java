package com.example.application.usecase.album;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.example.domain.model.Album;
import com.example.domain.query.GetAlbumByIdQuery;

public class GetAlbumByIdQueryHandlerUnitTest {

    private final GetAlbumByIdQueryHandler getAlbumByIdQueryHandler = new GetAlbumByIdQueryHandler();

    @Test
    public void testHandle_Success() {
        // Act
        Album album = getAlbumByIdQueryHandler.handle(new GetAlbumByIdQuery(1));

        // Assert
        assertEquals(1, album.getId());
        assertEquals("quidem molestiae enim", album.getTitle());
    }

    @Test
    public void testHandle_Failure() {
        // Simulate failure by using an invalid ID
        GetAlbumByIdQueryHandler handlerWithInvalidId = new GetAlbumByIdQueryHandler() {
            @Override
            public Album handle(GetAlbumByIdQuery query) {
                throw new RuntimeException("Simulated failure");
            }
        };

        // Act & Assert
        assertThrows(RuntimeException.class, () -> handlerWithInvalidId.handle(new GetAlbumByIdQuery(999)));
    }
}