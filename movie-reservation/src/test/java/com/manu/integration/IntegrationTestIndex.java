package com.manu.integration;

import com.manu.dtos.requests.NewRoomRequest;
import com.manu.dtos.requests.UpdateRoomRequest;
import com.manu.entities.RoomEntity;
import com.manu.entities.UserEntity;
import com.manu.repositories.RoomRepository;
import com.manu.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTestIndex {

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
            new UserEntity(0, "admin", passwordEncoder.encode("test-password-1"), "1@gmail.com"));
    var simpleUser =
        userRepository.save(
            new UserEntity(0, "user", passwordEncoder.encode("test-password-1"), "2@gmail.com"));

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

  @DisplayName("Getting a list of users without being the admin works properly")
  @Test
  void forbiddenGetUserTest() {
    UserIntegrationTests.forbiddenGetUserTest(userHeader, webTestClient);
  }

  @DisplayName("Getting a list of users by being the admin works properly")
  @Test
  void getUserTest() {
    UserIntegrationTests.getUserTest(adminHeader, webTestClient);
  }

  @DisplayName("Getting all rooms as a admin works properly")
  @Test
  void getAllRoomsGranted() {
    RoomIntegrationTests.getAllRoomsGranted(adminHeader, webTestClient);
  }

  @DisplayName("Getting all rooms as a user is not allowed")
  @Test
  void getAllRoomsForbidden() {
    RoomIntegrationTests.getAllRoomsForbidden(userHeader, webTestClient);
  }

  @DisplayName("Getting rooms without token is not possible")
  @Test
  void getAllRoomsUnauthenticated() {
    RoomIntegrationTests.getAllRoomsForbidden(userHeader, webTestClient);
  }

  @DisplayName("Getting a single room works properly")
  @Test
  void getRoomByIdGranted() {
    RoomIntegrationTests.getRoomByIdGranted(adminHeader, webTestClient, roomId);
  }

  @DisplayName("Getting a non-existing room is not possible")
  @Test
  void getRoomByIdWrong() {
    RoomIntegrationTests.getRoomByIdWrong(adminHeader, webTestClient, 99L);
  }

  @DisplayName("Creating a room works properly")
  @Test
  void createRoomGranted() {
    RoomIntegrationTests.createRoomGranted(
        adminHeader, webTestClient, new NewRoomRequest("RT2", 5, 6));
  }

  @DisplayName("Updating a room works properly")
  @Test
  void updateRoomGranted() {
    RoomIntegrationTests.updateRoomGranted(
        adminHeader, webTestClient, roomId, new UpdateRoomRequest(0));
  }

  @DisplayName("Deleting a room works properly")
  @Test
  void deleteRoomGranted() {
    RoomIntegrationTests.deleteRoomGranted(adminHeader, webTestClient, roomId);
  }
}
