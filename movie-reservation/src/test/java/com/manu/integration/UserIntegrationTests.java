package com.manu.integration;

import org.springframework.test.web.reactive.server.WebTestClient;

public class UserIntegrationTests {

  public static void forbiddenGetUserTest(String userHeader, WebTestClient webTestClient) {
    webTestClient
        .get()
        .uri("/users")
        .header("Authorization", userHeader)
        .exchange()
        .expectStatus()
        .isForbidden();
  }

  public static void getUserTest(String adminHeader, WebTestClient webTestClient) {
    webTestClient
        .get()
        .uri("/users")
        .header("Authorization", adminHeader)
        .exchange()
        .expectStatus()
        .isOk();
  }
}
