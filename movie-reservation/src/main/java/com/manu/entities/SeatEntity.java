package com.manu.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class SeatEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private double price;

  @Column(nullable = false)
  private boolean available;

  @ManyToOne
  @JoinColumn(name = "movie")
  @JsonIgnore
  private MovieEntity movie;

  @OneToOne(mappedBy = "seat")
  @JsonIgnore
  private TicketEntity ticket;

  public SeatEntity() {}

  public SeatEntity(String name, double price, boolean available, MovieEntity movie) {
    this.name = name;
    this.price = price;
    this.available = available;
    this.movie = movie;
  }

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

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public MovieEntity getMovie() {
    return movie;
  }

  public void setMovie(MovieEntity movie) {
    this.movie = movie;
  }

  public TicketEntity getTicket() {
    return ticket;
  }

  public void setTicket(TicketEntity ticket) {
    this.ticket = ticket;
  }
}
