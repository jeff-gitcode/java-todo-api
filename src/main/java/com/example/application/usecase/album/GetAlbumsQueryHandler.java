package com.example.application.usecase.album;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.domain.model.Album;
import com.example.domain.query.GetAlbumsQuery;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GetAlbumsQueryHandler {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GetAlbumsQueryHandler() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<Album> handle(GetAlbumsQuery query) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/albums"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<Album>>() {});
            } else {
                throw new RuntimeException("Failed to fetch albums: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching albums", e);
        }
    }
}
