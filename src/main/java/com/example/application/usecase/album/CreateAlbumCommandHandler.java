package com.example.application.usecase.album;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

import com.example.domain.command.CreateAlbumCommand;
import com.example.domain.model.Album;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CreateAlbumCommandHandler {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CreateAlbumCommandHandler() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public Album handle(CreateAlbumCommand command) {
        try {
            String requestBody = objectMapper.writeValueAsString(command);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/albums"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                return objectMapper.readValue(response.body(), Album.class);
            } else {
                throw new RuntimeException("Failed to create album: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while creating album", e);
        }
    }
}