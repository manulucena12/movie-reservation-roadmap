package com.manu.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "rooms")
public class RoomEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, updatable = false)
  private String name;

  @Column(nullable = false, updatable = false)
  private int rows;

  @Column(nullable = false, updatable = false)
  private int colums;

  @Column(nullable = false)
  private int unavailable;

  @OneToMany(mappedBy = "room")
  private List<MovieEntity> movies;

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

  public int getRows() {
    return rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public int getColums() {
    return colums;
  }

  public void setColums(int colums) {
    this.colums = colums;
  }

  public int getUnavailable() {
    return unavailable;
  }

  public void setUnavailable(int unavailable) {
    this.unavailable = unavailable;
  }

  public RoomEntity(String name, int rows, int colums, int unavailable) {
    this.name = name;
    this.rows = rows;
    this.colums = colums;
    this.unavailable = unavailable;
  }

  public RoomEntity() {}
}
