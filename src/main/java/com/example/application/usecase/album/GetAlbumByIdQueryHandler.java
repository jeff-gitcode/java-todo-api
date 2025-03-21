package com.example.application.usecase.album;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

import com.example.domain.model.Album;
import com.example.domain.query.GetAlbumByIdQuery;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GetAlbumByIdQueryHandler {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GetAlbumByIdQueryHandler() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public Album handle(GetAlbumByIdQuery query) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/albums/" + query.getId()))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), Album.class);
            } else {
                throw new RuntimeException("Failed to fetch album: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching album", e);
        }
    }
}