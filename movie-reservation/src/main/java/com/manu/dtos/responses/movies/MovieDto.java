package com.manu.dtos.responses.movies;

public class MovieDto {

  private Long id;
  private String name;
  private String schedule;
  private String date;
  private int minutes;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSchedule() {
    return schedule;
  }

  public void setSchedule(String schedule) {
    this.schedule = schedule;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public int getMinutes() {
    return minutes;
  }

  public void setMinutes(int minutes) {
    this.minutes = minutes;
  }

  public MovieDto() {}

  public MovieDto(Long id, String name, String schedule, String date, int minutes) {
    this.id = id;
    this.name = name;
    this.schedule = schedule;
    this.date = date;
    this.minutes = minutes;
  }
}
