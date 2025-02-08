package com.manu.controllers;

import com.manu.entities.MovieEntity;
import com.manu.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@Tag(name = "Ticket controller", description = "Ticket endpoints for users")
public class TicketController {

  @Autowired private TicketService ticketService;

  @Operation(
      summary = "Gets a user tickets",
      description = "It provides the ticket/s of a user",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Ticket info provided successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MovieEntity.class))),
        @ApiResponse(responseCode = "401", description = "Authentication not provided/failed"),
        @ApiResponse(
            responseCode = "403",
            description = "Authentication succeeded but not authorized"),
        @ApiResponse(
            responseCode = "403",
            description = "The user is not the owner of the tickets"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "Internal Server Error")))
      })
  @Parameter(
      name = "User-Info",
      description = "User's email to identify the account",
      required = true,
      in = ParameterIn.HEADER,
      schema = @Schema(type = "string"))
  @GetMapping
  public ResponseEntity<Object> getUserTickets(HttpServletRequest request) {
    var userEmail = request.getHeader("User-Info");
    var response = ticketService.getUserTickets(userEmail);
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 200 ? response.getContent() : response.getMessage());
  }
}
