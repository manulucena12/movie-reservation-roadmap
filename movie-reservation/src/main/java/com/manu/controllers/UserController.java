package com.manu.controllers;

import com.manu.entities.UserEntity;
import com.manu.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management Controller")
public class UserController {

  @Autowired private UserService userService;

  @Operation(
      summary = "Get all users",
      description = "It provides all users information to the admin",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Users info provided successfully",
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
  public ResponseEntity<Object> getAllUsers() {
    var response = userService.getAllUsers();
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 200 ? response.getContent() : response.getMessage());
  }
}
