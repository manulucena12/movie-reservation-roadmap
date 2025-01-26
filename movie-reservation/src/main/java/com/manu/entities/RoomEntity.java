package com.manu.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class RoomEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, updatable = false)
  private String name;

  @Column(nullable = false, updatable = false)
  private int seats;

  @Column(nullable = false)
  private int unavailable;

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

  public int getSeats() {
    return seats;
  }

  public void setSeats(int seats) {
    this.seats = seats;
  }

  public int getUnavailable() {
    return unavailable;
  }

  public void setUnavailable(int unavailable) {
    this.unavailable = unavailable;
  }

  public RoomEntity(String name, int seats, int unavailable) {
    this.name = name;
    this.seats = seats;
    this.unavailable = unavailable;
  }

  public RoomEntity() {}
}
