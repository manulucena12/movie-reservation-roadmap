package com.manu.controllers;

import com.manu.dtos.requests.*;
import com.manu.dtos.responses.movies.MovieDto;
import com.manu.entities.MovieEntity;
import com.manu.entities.UserEntity;
import com.manu.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@Tag(
    name = "Movie Billboard Controller",
    description = "Allows admins to list movies and users to buy tickets for one")
public class MovieController {

  @Autowired private MovieService movieService;

  @Operation(
      summary = "Creates a list of movies with their seats",
      description = "It allows the admin to create a list of movies giving the schedule",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Payload containing movie details.",
              required = true,
              content = @Content(schema = @Schema(implementation = NewMovieRequest.class))),
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Room created successfully",
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
  @PostMapping
  public ResponseEntity<Object> createMovie(@RequestBody NewMovieRequest request) {
    var response = movieService.createMovie(request);
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 201 ? response.getContent() : response.getMessage());
  }

  @Operation(
      summary = "Gets a single movie",
      description = "It provides a single movie information to the all the users",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Movie info provided successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MovieEntity.class))),
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
  public ResponseEntity<Object> getMovieById(@PathVariable Long id) {
    var response = movieService.getMovieById(id);
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 200 ? response.getContent() : response.getMessage());
  }

  @Operation(
      parameters = {
        @Parameter(
            name = "date",
            description = "Date of the movie (use 'none' to search by name instead)",
            required = true,
            schema = @Schema(type = "string", example = "2023-10-15")),
        @Parameter(
            name = "name",
            description = "Name of the movie (ignored if date is provided and not 'none')",
            required = true,
            schema = @Schema(type = "string", example = "Inception"))
      },
      summary = "Gets a single movie by date/name",
      description =
          "It provides a single movie information to the all the users basing on its name or date",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Movie info provided successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MovieEntity.class))),
        @ApiResponse(responseCode = "400", description = "Movies not found"),
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
  @GetMapping("/search")
  public ResponseEntity<Object> getMoviesByDateOrName(
      @RequestParam String date, @RequestParam String name) {
    var response =
        Objects.equals(date, "none")
            ? movieService.getMoviesByName(name)
            : movieService.getMoviesByDate(date);
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 200 ? response.getContent() : response.getMessage());
  }

  @Operation(
      summary = "Updates a specific list of movies",
      description =
          "It allows the admin to update the name and minutes of the movies using the old name",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Payload containing the old and new name and its minutes.",
              required = true,
              content =
                  @Content(schema = @Schema(implementation = UpdateGeneralMovieInfoRequest.class))),
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Movies updated successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MovieDto.class, type = "array"))),
        @ApiResponse(responseCode = "400", description = "Movies not found"),
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
  @PutMapping("/all")
  public ResponseEntity<Object> updateGeneralInfo(
      @RequestBody UpdateGeneralMovieInfoRequest request) {
    var response = movieService.updateGeneralInfo(request);
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 200 ? response.getContent() : response.getMessage());
  }

  @Operation(
      summary = "Updates a specific movie",
      description = "It allows the admin to update the date and schedule of a single movie",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Payload containing the old and new name and its minutes.",
              required = true,
              content =
                  @Content(schema = @Schema(implementation = UpdateSpecificInfoRequest.class))),
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Movie updated successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MovieDto.class))),
        @ApiResponse(responseCode = "400", description = "Movies not found"),
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
  @PutMapping("/single")
  public ResponseEntity<Object> updateSpecificInfo(@RequestBody UpdateSpecificInfoRequest request) {
    var response = movieService.updateSpecificInfo(request);
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 200 ? response.getContent() : response.getMessage());
  }

  @Operation(
      summary = "Updates the prices of all movies of a day",
      description = "It allows the admin to set a single price for a movies for a day",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Payload containing the old and new name and its minutes.",
              required = true,
              content =
                  @Content(schema = @Schema(implementation = UpdateSeatsPricesRequest.class))),
      responses = {
        @ApiResponse(responseCode = "200", description = "Movies' prices updated successfully"),
        @ApiResponse(responseCode = "400", description = "Movies not found"),
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
  @PutMapping("/prices")
  public ResponseEntity<Object> updateDayPrices(
      @RequestParam String date, @RequestBody UpdateSeatsPricesRequest request) {
    var response = movieService.updateDayPrices(date, request);
    return ResponseEntity.status(response.getStatusCode()).body(response.getMessage());
  }

  @Operation(
      parameters = {
        @Parameter(
            name = "date",
            description = "Date of the movie",
            required = true,
            schema = @Schema(type = "string", example = "2023-10-15")),
      },
      summary = "Delete movies of a day using their date",
      description = "It allows the admin to delete movies if they have been streamed",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Movies deleted successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MovieEntity.class))),
        @ApiResponse(responseCode = "400", description = "Movies not found"),
        @ApiResponse(
            responseCode = "400",
            description = "It's not possible to delete movies before their stream"),
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
  @DeleteMapping
  public ResponseEntity<Object> deleteMoviesByDate(@RequestParam String date) {
    var response = movieService.deleteOldMovies(date);
    return ResponseEntity.status(response.getStatusCode()).body(response.getMessage());
  }
}
