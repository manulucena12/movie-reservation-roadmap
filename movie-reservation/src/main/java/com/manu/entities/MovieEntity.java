package com.manu.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "movies")
public class MovieEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "room")
  @JsonIgnore
  private RoomEntity room;

  @Column(nullable = false)
  private String date;

  @Column(nullable = false)
  @OneToMany(mappedBy = "movie")
  private List<SeatEntity> seats;

  public MovieEntity() {}

  public MovieEntity(String name, RoomEntity room, String date) {
    this.name = name;
    this.room = room;
    this.date = date;
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

  public RoomEntity getRoom() {
    return room;
  }

  public void setRoom(RoomEntity room) {
    this.room = room;
  }

  public List<SeatEntity> getSeats() {
    return seats;
  }

  public void setSeats(List<SeatEntity> seats) {
    this.seats = seats;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
