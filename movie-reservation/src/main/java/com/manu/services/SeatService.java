package com.manu.services;

import com.manu.dtos.requests.BookSeatsRequest;
import com.manu.dtos.responses.HttpCustomResponse;
import com.manu.entities.TicketEntity;
import com.manu.exceptions.ResourceNotFoundException;
import com.manu.repositories.SeatRepository;
import com.manu.repositories.TicketRepository;
import com.manu.repositories.UserRepository;
import com.manu.security.resources.UserChecker;
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
              .orElseThrow(() -> new ResourceNotFoundException("Nothing"));
      var tickets = new ArrayList<TicketEntity>(List.of());
      for (int i = 0; i < body.getSeats().size(); i++) {
        var seat = seatRepository.findById(body.getSeats().get(i)).orElseThrow();
        if (!seat.isAvailable()) {
          return new HttpCustomResponse<>(
              400,
              null,
              "The seat: " + seat.getName() + " has been taken, please select another one");
        }
        var newTicket =
            ticketRepository.save(
                new TicketEntity(
                    seat,
                    user,
                    seat.getMovie().getName(),
                    seat.getMovie().getDate() + ", " + seat.getMovie().getSchedule(),
                    seat.getMovie().getRoom().getName() + ", seat: " + seat.getName()));
        seat.setTicket(newTicket);
        seatRepository.updateDisponibilityById(seat.getId());
        seatRepository.save(seat);
        tickets.add(newTicket);
      }
      return new HttpCustomResponse<>(201, tickets, null);
    } catch (Exception e) {
      if (e instanceof ResourceNotFoundException) {
        return new HttpCustomResponse<>(400, null, "User not found");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }
}
