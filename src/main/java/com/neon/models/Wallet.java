package com.neon.models;

@SuppressWarnings("serial")
public class Wallet implements java.io.Serializable {
 private String address;
 private String publicKey;
 private String privateKey;
 private Double balance;

 public Wallet() {}

 public Wallet(String address, String publicKey, String privateKey, Double balance) {
  this.address = address;
  this.publicKey = publicKey;
  this.privateKey = privateKey;
  this.balance = balance;
 }

 public void setBalance(Double balance) {
  this.balance = balance;
 }

 public Double getBalance() {
  return balance;
 }

 public String getAddress() {
  return address;
 }

 public String getPublicKey() {
  return publicKey;
 }

 public String getPrivateKey() {
  return privateKey;
 }
}
