package com.manu.dtos.requests;

import java.util.List;

public class NewMovieRequest {

  private Long room;
  private String name;
  private double price;
  private int month;
  private List<Integer> days;
  private List<String> hours;
  private int minutes;

  public NewMovieRequest(
      Long room,
      String name,
      double price,
      int month,
      List<Integer> days,
      List<String> hours,
      int minutes) {
    this.room = room;
    this.name = name;
    this.price = price;
    this.month = month;
    this.days = days;
    this.hours = hours;
    this.minutes = minutes;
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

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public List<Integer> getDays() {
    return days;
  }

  public void setDays(List<Integer> days) {
    this.days = days;
  }

  public List<String> getHours() {
    return hours;
  }

  public void setHours(List<String> hours) {
    this.hours = hours;
  }

  public int getMinutes() {
    return minutes;
  }

  public void setMinutes(int minutes) {
    this.minutes = minutes;
  }
}
