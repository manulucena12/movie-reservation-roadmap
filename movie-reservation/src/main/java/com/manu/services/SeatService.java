package com.manu.services;

import com.manu.dtos.requests.BookSeatsRequest;
import com.manu.dtos.requests.CancelSeatsRequest;
import com.manu.dtos.responses.HttpCustomResponse;
import com.manu.entities.TicketEntity;
import com.manu.exceptions.ResourceNotFoundException;
import com.manu.repositories.SeatRepository;
import com.manu.repositories.TicketRepository;
import com.manu.repositories.UserRepository;
import com.manu.security.resources.UserChecker;
import com.manu.utils.TicketUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

  @Autowired private UserRepository userRepository;
  @Autowired private SeatRepository seatRepository;
  @Autowired private TicketRepository ticketRepository;

  public HttpCustomResponse<Object> bookSeats(String userEmail, BookSeatsRequest body) {
    try {
      if (!UserChecker.check(userEmail)) {
        return new HttpCustomResponse<>(
            403, null, "You cannot book seats in the name of other user");
      }
      var user =
          userRepository
              .findByEmail(userEmail)
              .orElseThrow(() -> new ResourceNotFoundException("user"));
      var tickets = new ArrayList<TicketEntity>(List.of());
      for (int i = 0; i < body.getSeats().size(); i++) {
        var seat =
            seatRepository
                .findById(body.getSeats().get(i))
                .orElseThrow(() -> new ResourceNotFoundException("seat"));
        if (!seat.isAvailable()) {
          return new HttpCustomResponse<>(
              400,
              null,
              "The seat: " + seat.getName() + " has been taken, please select another one");
        }
        var price = seat.getPrice() * body.getSeats().size();
        if (price > user.getBalance()) {
          return new HttpCustomResponse<>(
              400, null, "You have not enough money to buy the tickets");
        }
        var newTicket = TicketUtils.createTicket(user, seat, ticketRepository, seatRepository);
        userRepository.updateUserBalance(user.getBalance() - price, user.getId());
        tickets.add(newTicket);
      }
      return new HttpCustomResponse<>(
          201, tickets.size() == 1 ? tickets.getFirst() : tickets, null);
    } catch (Exception e) {
      if (e instanceof ResourceNotFoundException) {
        return new HttpCustomResponse<>(
            400, null, e.getMessage().equals("seat") ? "Seat not found" : "User not found");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }

  public HttpCustomResponse<Object> cancelSeats(String userEmail, CancelSeatsRequest body) {
    try {
      if (!UserChecker.check(userEmail)) {
        return new HttpCustomResponse<>(
            403, null, "You cannot cancel seats in the name of other user");
      }
      var user =
          userRepository
              .findByEmail(userEmail)
              .orElseThrow(() -> new ResourceNotFoundException("user"));
      var tickets = TicketUtils.checkSeatsOwner(body.getTickets(), userEmail, ticketRepository);
      if (tickets == null) {
        return new HttpCustomResponse<>(403, null, "You cannot cancel tickets you have not bought");
      }
      TicketUtils.cancelTickets(tickets, user, ticketRepository, seatRepository, userRepository);
      return new HttpCustomResponse<>(200, null, "Your seats has been cancelled successfully");
    } catch (Exception e) {
      if (e instanceof ResourceNotFoundException) {
        return new HttpCustomResponse<>(
            400, null, e.getMessage().equals("ticket") ? "Ticket not found" : "User not found");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }
}
