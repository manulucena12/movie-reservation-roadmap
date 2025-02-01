package com.manu.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class TicketEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "seat")
  @JsonIgnore
  private SeatEntity seat;

  @ManyToOne
  @JoinColumn(name = "owner")
  @JsonIgnore
  private UserEntity owner;

  @Column(nullable = false)
  private String movie;

  @Column(nullable = false)
  private String date;

  @Column(nullable = false)
  private String spot;

  public TicketEntity() {}

  public TicketEntity(SeatEntity seat, UserEntity owner, String movie, String date, String spot) {
    this.seat = seat;
    this.owner = owner;
    this.movie = movie;
    this.date = date;
    this.spot = spot;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SeatEntity getSeat() {
    return seat;
  }

  public void setSeat(SeatEntity seat) {
    this.seat = seat;
  }

  public UserEntity getOwner() {
    return owner;
  }

  public void setOwner(UserEntity owner) {
    this.owner = owner;
  }

  public String getMovie() {
    return movie;
  }

  public void setMovie(String movie) {
    this.movie = movie;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getSpot() {
    return spot;
  }

  public void setSpot(String spot) {
    this.spot = spot;
  }
}
