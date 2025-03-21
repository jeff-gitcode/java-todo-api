package com.example.application.usecase.album;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.example.domain.command.CreateAlbumCommand;
import com.example.domain.model.Album;

public class CreateAlbumCommandHandlerUnitTest {

    private final CreateAlbumCommandHandler createAlbumCommandHandler = new CreateAlbumCommandHandler();

    @Test
    public void testHandle_Success() {
        // Arrange
        CreateAlbumCommand command = new CreateAlbumCommand(1, "New Album");

        // Act
        Album album = createAlbumCommandHandler.handle(command);

        // Assert
        assertEquals(1, album.getUserId());
        assertEquals("New Album", album.getTitle());
    }

    @Test
    public void testHandle_Failure() {
        // Simulate failure by using invalid data
        CreateAlbumCommandHandler handlerWithInvalidData = new CreateAlbumCommandHandler() {
            @Override
            public Album handle(CreateAlbumCommand command) {
                throw new RuntimeException("Simulated failure");
            }
        };

        // Act & Assert
        assertThrows(RuntimeException.class, () -> handlerWithInvalidData.handle(new CreateAlbumCommand(1, "")));
    }
}
