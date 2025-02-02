package com.manu.utils;

import com.manu.entities.SeatEntity;
import com.manu.entities.TicketEntity;
import com.manu.entities.UserEntity;
import com.manu.exceptions.ResourceNotFoundException;
import com.manu.repositories.SeatRepository;
import com.manu.repositories.TicketRepository;
import com.manu.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;

public class TicketUtils {

  public static TicketEntity createTicket(
      UserEntity user,
      SeatEntity seat,
      TicketRepository ticketRepository,
      SeatRepository seatRepository) {
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
    return newTicket;
  }

  public static List<TicketEntity> checkSeatsOwner(
      List<Long> identifiers, String userEmail, TicketRepository ticketRepository) {
    var tickets = new ArrayList<TicketEntity>(java.util.List.of());
    for (int i = 0; i < identifiers.size(); i++) {
      var ticket =
          ticketRepository
              .findById(identifiers.get(i))
              .orElseThrow(() -> new ResourceNotFoundException("ticket"));
      if (!ticket.getOwner().getEmail().equals(userEmail)) {
        return null;
      }
      tickets.add(ticket);
    }
    return tickets;
  }

  public static void cancelTickets(
      List<TicketEntity> tickets,
      UserEntity user,
      TicketRepository ticketRepository,
      SeatRepository seatRepository,
      UserRepository userRepository) {
    double price = tickets.getFirst().getSeat().getPrice() * tickets.size();
    for (int i = 0; i < tickets.size(); i++) {
      SeatEntity seat = tickets.get(i).getSeat();
      seat.setAvailable(true);
      seat.setTicket(null);
      seatRepository.save(seat);
      ticketRepository.delete(tickets.get(i));
    }
    userRepository.updateUserBalance(user.getBalance() + price, user.getId());
  }
}
