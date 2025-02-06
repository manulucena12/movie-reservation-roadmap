package com.manu.controllers;

import com.manu.dtos.requests.AuthRequest;
import com.manu.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(
    name = "Authentication Controller",
    description = "Allows users to authenticate themselves on the app")
public class AuthController {

  @Autowired private AuthService authService;

  @Operation(
      summary = "Login",
      description = "It allows a user to authenticate itself",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Payload containing the user credentials.",
              required = true,
              content = @Content(schema = @Schema(implementation = AuthRequest.class))),
      responses = {
        @ApiResponse(responseCode = "200", description = "Authentication successful"),
        @ApiResponse(responseCode = "400", description = "Bad credentials"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "Internal Server Error")))
      })
  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody AuthRequest request) {
    var response = authService.login(request);
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 200 ? response.getContent() : response.getMessage());
  }

  @Operation(
      summary = "Register",
      description = "It allows a person to create a user",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Payload containing the user credentials.",
              required = true,
              content = @Content(schema = @Schema(implementation = AuthRequest.class))),
      responses = {
        @ApiResponse(responseCode = "200", description = "Registration successful"),
        @ApiResponse(responseCode = "400", description = "Email already taken"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "Internal Server Error")))
      })
  @PostMapping("/register")
  public ResponseEntity<Object> register(@RequestBody AuthRequest request) {
    var response = authService.register(request);
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 200 ? response.getContent() : response.getMessage());
  }
}
