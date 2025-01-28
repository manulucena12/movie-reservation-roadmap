package com.manu.dtos.requests;

import java.util.List;

public class NewMovieRequest {

  private Long room;
  private String name;
  private double price;
  private String month;
  private List<Integer> days;

  public NewMovieRequest(Long room, String name, double price, String month, List<Integer> days) {
    this.room = room;
    this.name = name;
    this.price = price;
    this.month = month;
    this.days = days;
  }

  public Long getRoom() {
    return room;
  }

  public void setRoom(Long room) {
    this.room = room;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public List<Integer> getDays() {
    return days;
  }

  public void setDays(List<Integer> days) {
    this.days = days;
  }
}
