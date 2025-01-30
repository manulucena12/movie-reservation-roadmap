package com.manu.dtos.requests;

public class UpdateGeneralMovieInfoRequest {

  private String old;
  private String name;
  private int minutes;

  public UpdateGeneralMovieInfoRequest(String old, String name, int minutes) {
    this.old = old;
    this.name = name;
    this.minutes = minutes;
  }

  public String getOld() {
    return old;
  }

  public void setOld(String old) {
    this.old = old;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getMinutes() {
    return minutes;
  }

  public void setMinutes(int minutes) {
    this.minutes = minutes;
  }
}
