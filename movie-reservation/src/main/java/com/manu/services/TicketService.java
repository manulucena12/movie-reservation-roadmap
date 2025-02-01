package com.manu.services;

import com.manu.dtos.responses.HttpCustomResponse;
import com.manu.exceptions.ResourceNotFoundException;
import com.manu.repositories.TicketRepository;
import com.manu.repositories.UserRepository;
import com.manu.security.resources.UserChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

  @Autowired private TicketRepository ticketRepository;
  @Autowired private UserRepository userRepository;

  public HttpCustomResponse<Object> getUserTickets(String userEmail) {
    try {
      if (!UserChecker.check(userEmail)) {
        return new HttpCustomResponse<>(
            403, null, "You cannot access to ticket that are not yours");
      }
      var user =
          userRepository
              .findByEmail(userEmail)
              .orElseThrow(() -> new ResourceNotFoundException("Nothing"));
      return new HttpCustomResponse<>(200, ticketRepository.findByOwner(user.getId()), null);
    } catch (Exception e) {
      if (e instanceof ResourceNotFoundException) {
        return new HttpCustomResponse<>(400, null, "User not found");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }
}
