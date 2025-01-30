package com.manu.integration;

import com.manu.dtos.requests.NewMovieRequest;
import com.manu.entities.RoomEntity;
import com.manu.entities.UserEntity;
import com.manu.repositories.RoomRepository;
import com.manu.repositories.UserRepository;
import com.manu.services.MovieService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    movieService.createMovie(
        new NewMovieRequest(
            room.getId(), "MT1", 7, "July", List.of(1, 2, 3), List.of("18:00"), 120));
    movieId = 1L;
    System.out.println(movieId);
  }
}
