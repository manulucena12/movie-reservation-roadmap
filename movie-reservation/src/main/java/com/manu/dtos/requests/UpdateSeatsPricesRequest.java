package com.manu.dtos.requests;

public class UpdateSeatsPricesRequest {

  private double price;

  public UpdateSeatsPricesRequest(double price) {
    this.price = price;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}
