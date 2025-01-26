package com.manu.controllers;

import com.manu.dtos.requests.NewRoomRequest;
import com.manu.dtos.requests.UpdateRoomRequest;
import com.manu.entities.UserEntity;
import com.manu.services.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@Tag(
    name = "Rooms Controller",
    description = "These endpoints allow the admin to control the rooms of its cinema")
public class RoomController {

  @Autowired private RoomService roomService;

  @Operation(
      summary = "Gets all rooms",
      description = "It provides all rooms information to the admin",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Rooms info provided successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserEntity.class, type = "array"))),
        @ApiResponse(responseCode = "401", description = "Authentication not provided/failed"),
        @ApiResponse(
            responseCode = "403",
            description = "Authentication succeeded but not authorized"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "Internal Server Error")))
      })
  @GetMapping
  public ResponseEntity<Object> getAllRooms() {
    var response = roomService.findAllRooms();
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 200 ? response.getContent() : response.getMessage());
  }

  @Operation(
      summary = "Gets a single room",
      description = "It provides a single room information to the admin",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Room info provided successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserEntity.class))),
        @ApiResponse(responseCode = "400", description = "Room not found"),
        @ApiResponse(responseCode = "401", description = "Authentication not provided/failed"),
        @ApiResponse(
            responseCode = "403",
            description = "Authentication succeeded but not authorized"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "Internal Server Error")))
      })
  @GetMapping("/{id}")
  public ResponseEntity<Object> findById(@PathVariable Long id) {
    var response = roomService.findById(id);
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 200 ? response.getContent() : response.getMessage());
  }

  @Operation(
      summary = "Creates a room",
      description = "It allows the admin to create a new room for the cinema",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Room created successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserEntity.class, type = "array"))),
        @ApiResponse(responseCode = "401", description = "Authentication not provided/failed"),
        @ApiResponse(
            responseCode = "403",
            description = "Authentication succeeded but not authorized"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "Internal Server Error")))
      })
  @PostMapping
  public ResponseEntity<Object> createRoom(@RequestBody NewRoomRequest request) {
    var response = roomService.createRoom(request);
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 201 ? response.getContent() : response.getMessage());
  }

  @Operation(
      summary = "Deletes a room",
      description = "It allows the admin to delete a specific room of cinema",
      responses = {
        @ApiResponse(
            responseCode = "204",
            description = "Room deleted successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserEntity.class, type = "array"))),
        @ApiResponse(responseCode = "400", description = "Room not found"),
        @ApiResponse(responseCode = "401", description = "Authentication not provided/failed"),
        @ApiResponse(
            responseCode = "403",
            description = "Authentication succeeded but not authorized"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "Internal Server Error")))
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteRoom(@PathVariable Long id) {
    var response = roomService.deleteRoom(id);
    return ResponseEntity.status(response.getStatusCode()).body(response.getMessage());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateRoom(
      @PathVariable Long id, @RequestBody UpdateRoomRequest request) {
    var response = roomService.updateRoom(request.getUnavailable(), id);
    return ResponseEntity.status(response.getStatusCode()).body(response.getMessage());
  }
}
