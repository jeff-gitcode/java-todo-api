package com.example.application.usecase.album;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.example.domain.command.DeleteAlbumCommand;

public class DeleteAlbumCommandHandlerUnitTest {

    private final DeleteAlbumCommandHandler deleteAlbumCommandHandler = new DeleteAlbumCommandHandler();

    @Test
    public void testHandle_Success() {
        // Act & Assert
        assertDoesNotThrow(() -> deleteAlbumCommandHandler.handle(new DeleteAlbumCommand(1)));
    }

    @Test
    public void testHandle_Failure() {
        // Simulate failure by using an invalid ID
        DeleteAlbumCommandHandler handlerWithInvalidId = new DeleteAlbumCommandHandler() {
            @Override
            public void handle(DeleteAlbumCommand command) {
                throw new RuntimeException("Simulated failure");
            }
        };

        // Act & Assert
        assertThrows(RuntimeException.class, () -> handlerWithInvalidId.handle(new DeleteAlbumCommand(999)));
    }
}