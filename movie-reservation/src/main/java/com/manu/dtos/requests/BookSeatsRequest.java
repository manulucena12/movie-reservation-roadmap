package com.manu.dtos.requests;

import java.util.List;

public class BookSeatsRequest {

  private List<Long> seats;

  public List<Long> getSeats() {
    return seats;
  }

  public void setSeats(List<Long> seats) {
    this.seats = seats;
  }

  public BookSeatsRequest(List<Long> seats) {
    this.seats = seats;
  }
}
