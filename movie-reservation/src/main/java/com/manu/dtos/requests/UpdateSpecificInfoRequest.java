package com.manu.dtos.requests;

public class UpdateSpecificInfoRequest {

  private Long id;
  private String date;
  private String schedule;

  public UpdateSpecificInfoRequest(Long id, String date, String schedule) {
    this.id = id;
    this.date = date;
    this.schedule = schedule;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getSchedule() {
    return schedule;
  }

  public void setSchedule(String schedule) {
    this.schedule = schedule;
  }
}
