package com.manu.integration;

import com.manu.dtos.requests.BookSeatsRequest;
import com.manu.dtos.requests.CancelSeatsRequest;
import com.manu.dtos.requests.NewMovieRequest;
import com.manu.entities.RoomEntity;
import com.manu.entities.UserEntity;
import com.manu.repositories.RoomRepository;
import com.manu.repositories.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeatAndTicketIntegrationTests {

  @Autowired private WebTestClient webTestClient;
  @Autowired private UserRepository userRepository;
  @Autowired private RoomRepository roomRepository;
  @Autowired private PasswordEncoder passwordEncoder;
  private String userHeader;
  private int seatId;

  @BeforeAll
  void beforeAll() {
    var simpleUser =
        userRepository.save(
            new UserEntity(500, "admin", passwordEncoder.encode("test-password-1"), "7@gmail.com"));

    var userCredentials = simpleUser.getEmail() + ":" + "test-password-1";
    userHeader =
        "Basic "
            + java.util.Base64.getEncoder()
                .encodeToString(userCredentials.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    var room = roomRepository.save(new RoomEntity("Room-Test-1", 5, 6, 0));
    var movieRequest =
        new NewMovieRequest(
            room.getId(), "Movie-Test", 8, 1, List.of(1, 2, 3), List.of("18:00"), 90);
    webTestClient
        .post()
        .uri("/movies")
        .header("Authorization", userHeader)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(movieRequest)
        .exchange()
        .expectBody()
        .jsonPath("$.[0].seats[0].id")
        .value(id -> seatId = (int) id);
  }

  @DisplayName("Book seats works properly")
  @Test
  void bookSeats_Success() {
    var bookSeatsRequest = new BookSeatsRequest(List.of((long) seatId));
    webTestClient
        .post()
        .uri("/seats")
        .header("Authorization", userHeader)
        .header("User-Info", "7@gmail.com")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(bookSeatsRequest)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .jsonPath("$.id")
        .isNotEmpty();
  }

  @DisplayName("Book seats that are already taken is not allowed")
  @Test
  void bookSeatsAlreadyTakenTest() {
    var bookSeatsRequest = new BookSeatsRequest(List.of((long) seatId));
    webTestClient
        .post()
        .uri("/seats")
        .header("Authorization", userHeader)
        .header("User-Info", "7@gmail.com")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(bookSeatsRequest)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  @DisplayName("Book seats without enough money is not allowed")
  @Test
  void bookSeatsNotEnoughMoneyTest() {
    var user = userRepository.findByEmail("7@gmail.com").orElseThrow();
    user.setBalance(1);
    userRepository.save(user);
    var bookSeatsRequest = new BookSeatsRequest(List.of((long) seatId));
    webTestClient
        .post()
        .uri("/seats")
        .header("Authorization", userHeader)
        .header("User-Info", "7@gmail.com")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(bookSeatsRequest)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  @DisplayName("Cancel seats without being the user is forbidden")
  @Test
  void cancelSeatsForbiddenTest() {
    var cancelSeatsRequest = new CancelSeatsRequest(List.of(1L));
    webTestClient
        .post()
        .uri("/seats/cancel")
        .header("Authorization", userHeader)
        .header("User-Info", "user2@gmail.com")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(cancelSeatsRequest)
        .exchange()
        .expectStatus()
        .isForbidden();
  }

  @DisplayName("Getting tickets works properly")
  @Test
  void getTicketTest() {
    webTestClient
        .get()
        .uri("/tickets")
        .header("Authorization", userHeader)
        .header("User-Info", "7@gmail.com")
        .exchange()
        .expectStatus()
        .isOk();
  }

  @DisplayName("Getting tickets without being the owner is forbidden")
  @Test
  void getTicketForbiddenTest() {
    webTestClient
        .get()
        .uri("/tickets")
        .header("Authorization", userHeader)
        .header("User-Info", "1@gmail.com")
        .exchange()
        .expectStatus()
        .isForbidden();
  }

  @DisplayName("Cancel seats works properly")
  @Test
  void cancelSeatsTest() {
    var cancelSeatsRequest = new CancelSeatsRequest(List.of(1L));
    webTestClient
        .post()
        .uri("/seats/cancel")
        .header("Authorization", userHeader)
        .header("User-Info", "7@gmail.com")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(cancelSeatsRequest)
        .exchange()
        .expectStatus()
        .isOk();
  }
}
