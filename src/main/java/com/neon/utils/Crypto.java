package com.neon.utils;

import java.security.MessageDigest;

import com.neon.utils.classes.KeyConfluence;

class NKeyPair {
 private String publicKey;
 private String privateKey;

 public NKeyPair(String publicKey, String privateKey) {
  this.publicKey = publicKey;
  this.privateKey = privateKey;
 }

 public String getPublicKey() {
  return publicKey;
 }

 public String getPrivateKey() {
  return privateKey;
 }
}

public class Crypto {
 private NKeyPair keyPair(String passphrase) throws Exception {
  try {
   MessageDigest md = MessageDigest.getInstance("SHA-256");

   md.update(passphrase.getBytes());
   byte[] digest = md.digest();

   StringBuffer hex1 = new StringBuffer();

   for (byte d: digest)
    hex1.append(Integer.toHexString(0xFF & d));

   md.update(hex1.toString().getBytes());
   byte[] digest2 = md.digest();

   StringBuffer hex2 = new StringBuffer();

   for (byte d: digest2)
    hex2.append(Integer.toHexString(0xFF & d));

   return new NKeyPair(
    hex2.toString(), 
    hex1.toString()
   );
  } catch (Exception e) {
   throw new Exception(e.getMessage());
  }
 }
 
 public KeyConfluence generateConfluence(String passphrase) throws Exception{
  try {
   MessageDigest md = MessageDigest.getInstance("SHA-256");
   NKeyPair keyPair = this.keyPair(passphrase);
   byte[] asBytes = (keyPair.getPublicKey() + keyPair.getPrivateKey())
    .getBytes();

   md.update(asBytes);
   byte[] asDigest = md.digest();

   StringBuffer hex = new StringBuffer();

   for (byte d: asDigest)
    hex.append(Integer.toHexString(0xFF & d));

   return new KeyConfluence(
    keyPair.getPublicKey(), 
    keyPair.getPrivateKey(), 
    "NE:" + hex.toString()
   );
  } catch (Exception e) {
   throw new Exception(e.getMessage());
  }
 }
}
