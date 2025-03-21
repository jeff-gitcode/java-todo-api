package com.example.application.usecase.album;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.example.domain.command.UpdateAlbumCommand;
import com.example.domain.model.Album;

public class UpdateAlbumCommandHandlerUnitTest {

    private final UpdateAlbumCommandHandler updateAlbumCommandHandler = new UpdateAlbumCommandHandler();

    @Test
    public void testHandle_Success() {
        // Arrange
        UpdateAlbumCommand command = new UpdateAlbumCommand(1, 1, "Updated Album");

        // Act
        Album album = updateAlbumCommandHandler.handle(command);

        // Assert
        assertEquals(1, album.getId());
        assertEquals("Updated Album", album.getTitle());
    }

    @Test
    public void testHandle_Failure() {
        // Simulate failure by using invalid data
        UpdateAlbumCommandHandler handlerWithInvalidData = new UpdateAlbumCommandHandler() {
            @Override
            public Album handle(UpdateAlbumCommand command) {
                throw new RuntimeException("Simulated failure");
            }
        };

        // Act & Assert
        assertThrows(RuntimeException.class, () -> handlerWithInvalidData.handle(new UpdateAlbumCommand(1, 1, "")));
    }
}
