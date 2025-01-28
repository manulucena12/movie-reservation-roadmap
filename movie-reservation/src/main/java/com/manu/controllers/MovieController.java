package com.manu.controllers;

import com.manu.dtos.requests.NewMovieRequest;
import com.manu.services.MovieService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
@Tag(
    name = "Movie Billboard Controller",
    description = "Allows admins to list movies and users to buy tickets for one")
public class MovieController {

  @Autowired private MovieService movieService;

  @PostMapping
  public ResponseEntity<Object> createMovie(@RequestBody NewMovieRequest request) {
    var response = movieService.createMovie(request);
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 201 ? response.getContent() : response.getMessage());
  }
}
