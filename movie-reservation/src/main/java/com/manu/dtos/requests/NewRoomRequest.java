package com.manu.dtos.requests;

public class NewRoomRequest {

  private String name;
  private int rows;
  private int colums;

  public NewRoomRequest(String name, int rows, int colums) {
    this.name = name;
    this.rows = rows;
    this.colums = colums;
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
}
