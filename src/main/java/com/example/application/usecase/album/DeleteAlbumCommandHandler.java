package com.example.application.usecase.album;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

import com.example.domain.command.DeleteAlbumCommand;

@Component
public class DeleteAlbumCommandHandler {

    private final HttpClient httpClient;

    public DeleteAlbumCommandHandler() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public void handle(DeleteAlbumCommand command) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/albums/" + command.getId()))
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to delete album: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting album", e);
        }
    }
}