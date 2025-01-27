package com.manu.integration;

import com.manu.dtos.requests.NewRoomRequest;
import com.manu.dtos.requests.UpdateRoomRequest;
import org.springframework.test.web.reactive.server.WebTestClient;

public class RoomIntegrationTests {

  public static void getAllRoomsGranted(String adminHeader, WebTestClient webTestClient) {
    webTestClient
        .get()
        .uri("/rooms")
        .header("Authorization", adminHeader)
        .exchange()
        .expectStatus()
        .isOk();
  }

  public static void getAllRoomsForbidden(String userHeader, WebTestClient webTestClient) {
    webTestClient
        .get()
        .uri("/rooms")
        .header("Authorization", userHeader)
        .exchange()
        .expectStatus()
        .isForbidden();
  }

  public static void getAllRoomsUnauthenticated(WebTestClient webTestClient) {
    webTestClient.get().uri("/rooms").exchange().expectStatus().isUnauthorized();
  }

  public static void getRoomByIdGranted(
      String adminHeader, WebTestClient webTestClient, Long roomId) {
    webTestClient
        .get()
        .uri("/rooms/" + roomId)
        .header("Authorization", adminHeader)
        .exchange()
        .expectStatus()
        .isOk();
  }

  public static void getRoomByIdWrong(String userHeader, WebTestClient webTestClient, Long roomId) {
    webTestClient
        .get()
        .uri("/rooms/" + roomId)
        .header("Authorization", userHeader)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  public static void createRoomGranted(
      String adminHeader, WebTestClient webTestClient, NewRoomRequest request) {
    webTestClient
        .post()
        .uri("/rooms")
        .header("Authorization", adminHeader)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isCreated();
  }

  public static void createRoomForbidden(
      String userHeader, WebTestClient webTestClient, NewRoomRequest request) {
    webTestClient
        .post()
        .uri("/rooms")
        .header("Authorization", userHeader)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isForbidden();
  }

  public static void deleteRoomGranted(
      String adminHeader, WebTestClient webTestClient, Long roomId) {
    webTestClient
        .delete()
        .uri("/rooms/" + roomId)
        .header("Authorization", adminHeader)
        .exchange()
        .expectStatus()
        .isNoContent();
  }

  public static void deleteRoomForbidden(
      String userHeader, WebTestClient webTestClient, Long roomId) {
    webTestClient
        .delete()
        .uri("/rooms/" + roomId)
        .header("Authorization", userHeader)
        .exchange()
        .expectStatus()
        .isForbidden();
  }

  public static void updateRoomGranted(
      String adminHeader, WebTestClient webTestClient, Long roomId, UpdateRoomRequest request) {
    webTestClient
        .put()
        .uri("/rooms/" + roomId)
        .header("Authorization", adminHeader)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isOk();
  }

  public static void updateRoomForbidden(
      String userHeader, WebTestClient webTestClient, Long roomId, UpdateRoomRequest request) {
    webTestClient
        .put()
        .uri("/rooms/" + roomId)
        .header("Authorization", userHeader)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isForbidden();
  }
}
