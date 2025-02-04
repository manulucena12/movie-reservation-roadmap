package com.manu.controllers;

import com.manu.dtos.requests.BookSeatsRequest;
import com.manu.dtos.requests.CancelSeatsRequest;
import com.manu.entities.TicketEntity;
import com.manu.services.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seats")
@Tag(name = "Seats controller", description = "Seats endpoints for users")
public class SeatController {

  @Autowired private SeatService seatService;

  @Operation(
      summary = "Books seats",
      description = "It allows an user to book some seats if it has enough money",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Payload containing the chosen seats.",
              required = true,
              content = @Content(schema = @Schema(implementation = BookSeatsRequest.class))),
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Seats booked successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TicketEntity.class))),
        @ApiResponse(responseCode = "400", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Seats not found"),
        @ApiResponse(responseCode = "400", description = "Seats already taken"),
        @ApiResponse(responseCode = "401", description = "Authentication not provided/failed"),
        @ApiResponse(
            responseCode = "403",
            description = "Authentication succeeded but not authorized"),
        @ApiResponse(
            responseCode = "403",
            description = "The user tried to book seats in the name of other user"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "Internal Server Error")))
      })
  @PostMapping
  public ResponseEntity<Object> bookSeats(
      @RequestBody BookSeatsRequest body, HttpServletRequest request) {
    var userEmail = request.getHeader("User-Info");
    var response = seatService.bookSeats(userEmail, body);
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 201 ? response.getContent() : response.getMessage());
  }

  @Operation(
      summary = "Cancel seats",
      description =
          "It allows an user to book cancel the seats it bough and get its money returned",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Payload containing the chosen seats.",
              required = true,
              content = @Content(schema = @Schema(implementation = CancelSeatsRequest.class))),
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Seats cancelled successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "Your seats has been cancelled successfully"))),
        @ApiResponse(responseCode = "400", description = "Seats not found"),
        @ApiResponse(responseCode = "401", description = "Authentication not provided/failed"),
        @ApiResponse(
            responseCode = "403",
            description = "Authentication succeeded but not authorized"),
        @ApiResponse(
            responseCode = "403",
            description = "The user tried to cancel seats in the name of other user"),
        @ApiResponse(
            responseCode = "403",
            description = "The user tried to cancel seats that are not its"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "Internal Server Error")))
      })
  @PostMapping("/cancel")
  public ResponseEntity<Object> cancelSeats(
      @RequestBody CancelSeatsRequest body, HttpServletRequest request) {
    var userEmail = request.getHeader("User-Info");
    var response = seatService.cancelSeats(userEmail, body);
    return ResponseEntity.status(response.getStatusCode()).body(response.getMessage());
  }
}
