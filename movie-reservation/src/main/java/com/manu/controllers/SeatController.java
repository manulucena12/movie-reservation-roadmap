package com.manu.controllers;

import com.manu.dtos.requests.BookSeatsRequest;
import com.manu.services.SeatService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seats")
public class SeatController {

  @Autowired private SeatService seatService;

  @PostMapping
  public ResponseEntity<Object> bookSeats(
      @RequestBody BookSeatsRequest body, HttpServletRequest request) {
    var userEmail = request.getHeader("User-Info");
    var response = seatService.bookSeats(userEmail, body);
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 201 ? response.getContent() : response.getMessage());
  }
}
