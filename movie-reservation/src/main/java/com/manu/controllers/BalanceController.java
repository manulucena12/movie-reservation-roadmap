package com.manu.controllers;

import com.manu.dtos.requests.BalanceRequest;
import com.manu.services.BalanceService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
@Tag(name = "Balance Controller", description = "Allows users to add cash to their accounts")
public class BalanceController {

  @Autowired private BalanceService balanceService;

  @Operation(
      summary = "Add cash",
      description = "It allows a user add cash",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Payload containing the amount of money the user want to add.",
              required = true,
              content = @Content(schema = @Schema(implementation = BalanceRequest.class))),
      responses = {
        @ApiResponse(responseCode = "200", description = "Money added successfully"),
        @ApiResponse(responseCode = "400", description = "User not found"),
        @ApiResponse(responseCode = "401", description = "Authentication failed"),
        @ApiResponse(
            responseCode = "403",
            description = "The user tried to update a balance that is not its"),
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
  @PutMapping
  public ResponseEntity<Object> addCash(
      @RequestBody BalanceRequest body, HttpServletRequest request) {
    var userEmail = request.getHeader("User-Info");
    var response = balanceService.updateCash(body, userEmail);
    return ResponseEntity.status(response.getStatusCode()).body(response.getMessage());
  }
}
