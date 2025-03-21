package com.example.application.usecase.album;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

import com.example.domain.command.UpdateAlbumCommand;
import com.example.domain.model.Album;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UpdateAlbumCommandHandler {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public UpdateAlbumCommandHandler() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public Album handle(UpdateAlbumCommand command) {
        try {
            String requestBody = objectMapper.writeValueAsString(command);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/albums/" + command.getId()))
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), Album.class);
            } else {
                throw new RuntimeException("Failed to update album: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while updating album", e);
        }
    }
}