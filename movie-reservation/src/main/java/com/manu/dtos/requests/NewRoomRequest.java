package com.manu.dtos.requests;

import java.util.Optional;

public class NewRoomRequest {

  private String name;
  private int seats;
  private Optional<Integer> unavailable;

  public Optional<Integer> getUnavailable() {
    return unavailable;
  }

  public void setUnavailable(Optional<Integer> unavailable) {
    this.unavailable = unavailable;
  }

  public int getSeats() {
    return seats;
  }

  public void setSeats(int seats) {
    this.seats = seats;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
