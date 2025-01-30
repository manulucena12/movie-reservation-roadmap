package com.manu.integration;

import com.manu.dtos.requests.NewRoomRequest;
import com.manu.dtos.requests.UpdateRoomRequest;
import com.manu.entities.RoomEntity;
import com.manu.entities.UserEntity;
import com.manu.repositories.RoomRepository;
import com.manu.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoomIntegrationTests {

  @Autowired private WebTestClient webTestClient;
  @Autowired private UserRepository userRepository;
  @Autowired private RoomRepository roomRepository;
  @Autowired private PasswordEncoder passwordEncoder;

  private String adminHeader;
  private String userHeader;
  private Long roomId;

  @BeforeAll
  void beforeAll() {
    var adminUser =
        userRepository.save(
            new UserEntity(0, "admin", passwordEncoder.encode("test-password-1"), "3@gmail.com"));
    var simpleUser =
        userRepository.save(
            new UserEntity(0, "user", passwordEncoder.encode("test-password-1"), "4@gmail.com"));

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
  }

  @DisplayName("Getting all rooms as a admin works properly")
  @Test
  void getAllRoomsGranted() {
    webTestClient
        .get()
        .uri("/rooms")
        .header("Authorization", adminHeader)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @DisplayName("Getting all rooms as a user is not allowed")
  @Test
  void getAllRoomsForbidden() {
    webTestClient
        .get()
        .uri("/rooms")
        .header("Authorization", userHeader)
        .exchange()
        .expectStatus()
        .isForbidden();
  }

  @DisplayName("Getting rooms without token is not possible")
  @Test
  void getAllRoomsUnauthenticated() {
    webTestClient.get().uri("/rooms").exchange().expectStatus().isUnauthorized();
  }

  @DisplayName("Getting a single room works properly")
  @Test
  void getRoomByIdGranted() {
    webTestClient
        .get()
        .uri("/rooms/" + roomId)
        .header("Authorization", adminHeader)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @DisplayName("Getting a non-existing room is not possible")
  @Test
  void getRoomByIdWrong() {
    webTestClient
        .get()
        .uri("/rooms/" + 99L)
        .header("Authorization", adminHeader)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  @DisplayName("Creating a room works properly")
  @Test
  void createRoomGranted() {
    webTestClient
        .post()
        .uri("/rooms")
        .header("Authorization", adminHeader)
        .bodyValue(new NewRoomRequest("RT2", 5, 6))
        .exchange()
        .expectStatus()
        .isCreated();
  }

  @DisplayName("Updating a room works properly")
  @Test
  void updateRoomGranted() {
    webTestClient
        .put()
        .uri("/rooms/" + roomId)
        .header("Authorization", adminHeader)
        .bodyValue(new UpdateRoomRequest(0))
        .exchange()
        .expectStatus()
        .isOk();
  }

  @DisplayName("Deleting a room works properly")
  @Test
  void deleteRoomGranted() {
    webTestClient
        .delete()
        .uri("/rooms/" + roomId)
        .header("Authorization", adminHeader)
        .exchange()
        .expectStatus()
        .isNoContent();
  }
}
