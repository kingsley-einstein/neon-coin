package com.neon.models;

@SuppressWarnings("serial")
public class TxnOutput implements java.io.Serializable {
 private String address;
 private Double amount;

 public TxnOutput() {}

 public TxnOutput(String address, Double amount) {
  this.address = address;
  this.amount = amount;
 }

 public String getAddress() {
  return address;
 }

 public Double getAmount() {
  return amount;
 }
}
