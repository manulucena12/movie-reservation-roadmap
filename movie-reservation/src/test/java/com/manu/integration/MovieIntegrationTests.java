package com.manu.integration;

import com.manu.dtos.requests.NewMovieRequest;
import com.manu.dtos.requests.UpdateGeneralMovieInfoRequest;
import com.manu.dtos.requests.UpdateSeatsPricesRequest;
import com.manu.dtos.requests.UpdateSpecificInfoRequest;
import com.manu.entities.RoomEntity;
import com.manu.entities.UserEntity;
import com.manu.repositories.RoomRepository;
import com.manu.repositories.UserRepository;
import com.manu.services.MovieService;
import java.util.List;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieIntegrationTests {

  @Autowired private WebTestClient webTestClient;
  @Autowired private UserRepository userRepository;
  @Autowired private RoomRepository roomRepository;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private MovieService movieService;
  private String adminHeader;
  private String userHeader;
  private Long movieId;
  private Long roomId;

  @BeforeAll
  void beforeAll() {
    var adminUser =
        userRepository.save(
            new UserEntity(0, "admin", passwordEncoder.encode("test-password-1"), "5@gmail.com"));
    var simpleUser =
        userRepository.save(
            new UserEntity(0, "user", passwordEncoder.encode("test-password-1"), "6@gmail.com"));

    var adminCredentials = adminUser.getEmail() + ":" + "test-password-1";
    adminHeader =
        "Basic "
            + java.util.Base64.getEncoder()
                .encodeToString(adminCredentials.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    var userCredentials = simpleUser.getEmail() + ":" + "test-password-1";
    userHeader =
        "Basic "
            + java.util.Base64.getEncoder()
                .encodeToString(userCredentials.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    var room = roomRepository.save(new RoomEntity("Room-Test-1", 5, 6, 0));
    roomId = room.getId();
    movieService.createMovie(
        new NewMovieRequest(room.getId(), "MT1", 7, 1, List.of(1, 2, 3), List.of("18:00"), 120));
    movieId = 1L;
  }

  @Test
  @DisplayName("Creating a list of movies works properly")
  void createMoviesTest() {
    var request = new NewMovieRequest(roomId, "MT2", 7, 1, List.of(4, 5, 6), List.of("18:00"), 120);
    webTestClient
        .post()
        .uri("/movies")
        .header("Authorization", adminHeader)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isCreated();
  }

  @Test
  @DisplayName("Creating a list of movies being a user is forbidden")
  void createMoviesForbiddenTest() {
    var request = new NewMovieRequest(roomId, "MT2", 7, 1, List.of(4, 5, 6), List.of("18:00"), 120);
    webTestClient
        .post()
        .uri("/movies")
        .header("Authorization", userHeader)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isForbidden();
    movieId = 1L;
  }

  @Test
  @DisplayName("Test Get Movie by ID")
  void testGetMovieById() {
    webTestClient
        .get()
        .uri("/movies/" + movieId)
        .header("Authorization", adminHeader)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  @DisplayName("Getting a non-existing movie gives error")
  void testGetNonMovieById() {
    webTestClient
        .get()
        .uri("/movies/" + 99L)
        .header("Authorization", adminHeader)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  @Test
  @DisplayName("Test Get Movie by Date or Name")
  void testGetMoviesByDateOrName() {
    webTestClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/movies/search")
                    .queryParam("date", "none")
                    .queryParam("name", "MT1")
                    .build())
        .header("Authorization", adminHeader)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody();

    webTestClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/movies/search")
                    .queryParam("date", "2025-01-01")
                    .queryParam("name", "none")
                    .build())
        .header("Authorization", adminHeader)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  @DisplayName("Test Update Movie General Info")
  void testUpdateMovieGeneralInfo() {
    var request = new UpdateGeneralMovieInfoRequest("MT1", "MT1", 150);
    webTestClient
        .put()
        .uri("/movies/all")
        .header("Authorization", adminHeader)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  @DisplayName("Test Update Movie Specific Info")
  void testUpdateMovieSpecificInfo() {
    var request = new UpdateSpecificInfoRequest(movieId, "Today", "19:00");
    webTestClient
        .put()
        .uri("/movies/single")
        .header("Authorization", adminHeader)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  @DisplayName("Test Update Day Prices")
  void testUpdateDayPrices() {
    var request = new UpdateSeatsPricesRequest(20.0);
    webTestClient
        .put()
        .uri(
            uriBuilder ->
                uriBuilder.path("/movies/prices").queryParam("date", "2025-01-01").build())
        .header("Authorization", adminHeader)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  @DisplayName("Test delete movies")
  void testDeleteMovies() {
    webTestClient
        .delete()
        .uri(uriBuilder -> uriBuilder.path("/movies").queryParam("date", "2025-01-05").build())
        .header("Authorization", adminHeader)
        .exchange()
        .expectStatus()
        .isOk();
  }
}
