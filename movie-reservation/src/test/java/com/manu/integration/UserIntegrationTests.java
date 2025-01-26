package com.manu.integration;

import com.manu.entities.UserEntity;
import com.manu.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserIntegrationTests {

  @Autowired private WebTestClient webTestClient;
  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;

  private String adminHeader;
  private String userHeader;

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
  }

  @DisplayName("Getting a list of users without being the admin works properly")
  @Test
  void forbiddenGetUserTest() {
    webTestClient
        .get()
        .uri("/users")
        .header("Authorization", userHeader)
        .exchange()
        .expectStatus()
        .isForbidden();
  }

  @DisplayName("Getting a list of users by being the admin works properly")
  @Test
  void getUserTest() {
    webTestClient
        .get()
        .uri("/users")
        .header("Authorization", adminHeader)
        .exchange()
        .expectStatus()
        .isOk();
  }
}
