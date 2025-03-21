package com.example.integration;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.application.auth.JwtUtil;
import com.example.domain.model.Album;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlbumControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    private String jwtToken;

    @BeforeEach
    public void setUp() {
        // Generate a valid JWT token for the test user
        jwtToken = jwtUtil.generateToken("test@example.com");
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        return headers;
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void testGetAllAlbums() {
        // Arrange
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Act
        ResponseEntity<Album[]> response = restTemplate.exchange(
                createURLWithPort("/albums"),
                HttpMethod.GET,
                entity,
                Album[].class
        );

        // Assert
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue(); // Verify the response status is 200 OK
        Album[] albums = response.getBody();
        assertThat(albums).isNotNull();
        assertThat(albums.length).isGreaterThan(1);// JSONPlaceholder has 100 albums
    }

    @Test
    public void testGetAlbumById() {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Album> response = restTemplate.exchange(
                createURLWithPort("/albums/1"),
                HttpMethod.GET,
                entity,
                Album.class
        );

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testCreateAlbum() {
        Album album = new Album(null, 1, "New Album");
        HttpHeaders headers = createHeaders();
        HttpEntity<Album> entity = new HttpEntity<>(album, headers);

        ResponseEntity<Album> response = restTemplate.exchange(
                createURLWithPort("/albums"),
                HttpMethod.POST,
                entity,
                Album.class
        );

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("New Album");
    }

    @Test
    public void testUpdateAlbum() {
        Album updatedAlbum = new Album(null, 1, "Updated Album");
        HttpHeaders headers = createHeaders();
        HttpEntity<Album> entity = new HttpEntity<>(updatedAlbum, headers);

        ResponseEntity<Album> response = restTemplate.exchange(
                createURLWithPort("/albums/1"),
                HttpMethod.PUT,
                entity,
                Album.class
        );

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Updated Album");
    }

    @Test
    public void testDeleteAlbum() {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                createURLWithPort("/albums/1"),
                HttpMethod.DELETE,
                entity,
                Void.class
        );

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
