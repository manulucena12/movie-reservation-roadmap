package com.manu.controllers;

import com.manu.services.TicketService;
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

  @GetMapping
  public ResponseEntity<Object> getUserTickets(HttpServletRequest request) {
    var userEmail = request.getHeader("User-Info");
    var response = ticketService.getUserTickets(userEmail);
    return ResponseEntity.status(response.getStatusCode())
        .body(response.getStatusCode() == 200 ? response.getContent() : response.getMessage());
  }
}
