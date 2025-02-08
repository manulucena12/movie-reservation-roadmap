package com.manu.integration;

import com.manu.dtos.requests.AuthRequest;
import com.manu.dtos.requests.BalanceRequest;
import com.manu.entities.UserEntity;
import com.manu.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthAndBalanceTests {

  @Autowired private WebTestClient webTestClient;
  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;
  private String userHeader;
  private String secondUserHeader;
  private final String userEmail = "8@gmail.com";
  private final String userPassword = "test-password-1";

  @BeforeAll
  void beforeAll() {
    userRepository.save(
        new UserEntity(0, "user", passwordEncoder.encode(userPassword), "9@gmail.com"));
    var userCredentials = userEmail + ":" + userPassword;
    var secondUserCredentials = "9@gmail.com" + ":" + userPassword;
    userHeader =
        "Basic "
            + java.util.Base64.getEncoder()
                .encodeToString(userCredentials.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    secondUserHeader =
        "Basic "
            + java.util.Base64.getEncoder()
                .encodeToString(
                    secondUserCredentials.getBytes(java.nio.charset.StandardCharsets.UTF_8));
  }

  @Test
  @DisplayName("Successful Registration")
  void shouldRegisterSuccessfully() {
    AuthRequest newUserRequest = new AuthRequest(userEmail, userPassword);

    webTestClient
        .post()
        .uri("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(newUserRequest)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(String.class)
        .consumeWith(
            response -> {
              String token = response.getResponseBody();
              Assertions.assertNotNull(token, "Token should not be null");
              Assertions.assertTrue(
                  token.startsWith("Basic "), "Token should be a Basic Auth token");
            });
    Assertions.assertTrue(
        userRepository.findByEmail(userEmail).isPresent(), "User should be saved in the database");
  }

  @Test
  @DisplayName("Successful Login After Registration")
  void shouldLoginSuccessfully() {
    AuthRequest loginRequest = new AuthRequest("9@gmail.com", userPassword);

    webTestClient
        .post()
        .uri("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(loginRequest)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(String.class)
        .consumeWith(
            response -> {
              String token = response.getResponseBody();
              Assertions.assertNotNull(token, "Token should not be null");
              Assertions.assertTrue(
                  token.startsWith("Basic "), "Token should be a Basic Auth token");
            });
  }

  @Test
  @DisplayName("Login Fails with Wrong Password")
  void shouldFailLoginWithWrongPassword() {
    AuthRequest wrongLoginRequest = new AuthRequest(userEmail, "wrong-password");

    webTestClient
        .post()
        .uri("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(wrongLoginRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(String.class)
        .isEqualTo("Bad credentials");
  }

  @Test
  @DisplayName("Login Fails with Non-Existing User")
  void shouldFailLoginWithNonExistingUser() {
    AuthRequest nonExistingUserRequest = new AuthRequest("nonexistent@gmail.com", "password");

    webTestClient
        .post()
        .uri("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(nonExistingUserRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(String.class)
        .isEqualTo("Bad credentials");
  }

  @Test
  @DisplayName("Registration Fails for Duplicate Email")
  void shouldFailRegisteringWithDuplicateEmail() {
    AuthRequest duplicateUserRequest = new AuthRequest(userEmail, "newpassword123");

    webTestClient
        .post()
        .uri("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(duplicateUserRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(String.class)
        .isEqualTo("Email already taken");
  }

  @Test
  @DisplayName("Successful Balance Update (Add Cash)")
  void shouldAddCashSuccessfully() {
    BalanceRequest balanceRequest = new BalanceRequest(100);

    webTestClient
        .put()
        .uri("/balance")
        .header("Authorization", secondUserHeader)
        .header("User-Info", "9@gmail.com")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(balanceRequest)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  @DisplayName("Fail to Add Cash for Another User")
  void shouldFailAddingCashForDifferentUser() {
    BalanceRequest balanceRequest = new BalanceRequest(100);

    webTestClient
        .put()
        .uri("/balance")
        .header("Authorization", secondUserHeader)
        .header("User-Info", userEmail)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(balanceRequest)
        .exchange()
        .expectStatus()
        .isForbidden()
        .expectBody(String.class)
        .isEqualTo("You cannot update a cash of a user that is not you");
  }
}
