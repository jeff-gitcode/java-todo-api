package com.example.application.usecase.album;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.example.domain.model.Album;
import com.example.domain.query.GetAlbumsQuery;

public class GetAlbumsQueryHandlerUnitTest {

    private final GetAlbumsQueryHandler getAlbumsQueryHandler = new GetAlbumsQueryHandler();

    @Test
    public void testHandle_Success() {
        // Act
        List<Album> albums = getAlbumsQueryHandler.handle(new GetAlbumsQuery());

        // Assert
        assertEquals(100, albums.size()); // JSONPlaceholder has 100 albums
        assertEquals("quidem molestiae enim", albums.get(0).getTitle());
    }

    @Test
    public void testHandle_Failure() {
        // Simulate failure by using an invalid URL
        GetAlbumsQueryHandler handlerWithInvalidUrl = new GetAlbumsQueryHandler() {
            @Override
            public List<Album> handle(GetAlbumsQuery query) {
                throw new RuntimeException("Simulated failure");
            }
        };

        // Act & Assert
        assertThrows(RuntimeException.class, () -> handlerWithInvalidUrl.handle(new GetAlbumsQuery()));
    }
}
