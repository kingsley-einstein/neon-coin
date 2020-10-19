package com.neon.utils.classes;

public class KeyConfluence {
 private String publicKey;
 private String privateKey;
 private String address;

 public KeyConfluence(String publicKey, String privateKey, String address) {
  this.publicKey = publicKey;
  this.privateKey = privateKey;
  this.address = address;
 }

 public String getPublicKey() {
  return publicKey;
 }

 public String getPrivateKey() {
  return privateKey;
 }

 public String getAddress() {
  return address;
 }
}
