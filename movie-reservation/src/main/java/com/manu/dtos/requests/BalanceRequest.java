package com.manu.dtos.requests;

public class BalanceRequest {

  private double balance;

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public BalanceRequest(double balance) {
    this.balance = balance;
  }
}
