package com.manu.dtos.requests;

import java.util.List;

public class CancelSeatsRequest {

  private List<Long> tickets;

  public List<Long> getTickets() {
    return tickets;
  }

  public void setTickets(List<Long> tickets) {
    this.tickets = tickets;
  }

  public CancelSeatsRequest(List<Long> tickets) {
    this.tickets = tickets;
  }
}
