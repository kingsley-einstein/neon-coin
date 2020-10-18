package com.neon.models;

import java.util.Date;

@SuppressWarnings("serial")
public class Block implements java.io.Serializable {
 private Integer index;
 private Date timestamp;
 private String hash;
 private String previousHash;
 private Txn txn;
 private Integer nonce;

 public Block() {}

 public Block(
  Integer index,
  Date timestamp,
  String hash,
  String previousHash,
  Txn txn,
  Integer nonce
 ) {
  this.index = index;
  this.timestamp = timestamp;
  this.hash = hash;
  this.previousHash = previousHash;
  this.txn = txn;
  this.nonce = nonce;
 }

 public Integer getIndex() {
  return index;
 }

 public void setTimestamp(Date timestamp) {
  this.timestamp = timestamp;
 }

 public Date getTimestamp() {
  return timestamp;
 }

 public void setHash(String hash) {
  this.hash = hash;
 }

 public String getHash() {
  return hash;
 }

 public String getPreviousHash() {
  return previousHash;
 }

 public Txn getTxn() {
  return txn;
 }

 public void setNonce(Integer nonce) {
  this.nonce = nonce;
 }

 public Integer getNonce() {
  return nonce;
 }
}
