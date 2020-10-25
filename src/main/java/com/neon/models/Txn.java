package com.neon.models;

import java.util.List;

import com.neon.models.enums.TxnStatus;

@SuppressWarnings("serial")
public class Txn implements java.io.Serializable {
 private List<TxnInput> txnInputs;
 private List<TxnOutput> txnOutputs;
 private String txnHash;
 private String txnSignature;
 private TxnStatus txnStatus;

 public Txn() {}

 public Txn(
  List<TxnInput> txnInputs, 
  List<TxnOutput> txnOutputs, 
  String txnHash, 
  String txnSignature, 
  TxnStatus txnStatus
 ) {
  this.txnInputs = txnInputs;
  this.txnOutputs = txnOutputs;
  this.txnHash = txnHash;
  this.txnStatus = txnStatus;
  this.txnSignature = txnSignature;
 }

 public List<TxnInput> getTxnInputs() {
  return txnInputs;
 }

 public List<TxnOutput> getTxnOutputs() {
  return txnOutputs;
 }

 public String getTxnHash() {
  return txnHash;
 }

 public void setTxnSignature(String txnSignature) {
  this.txnSignature = txnSignature;
 }

 public String getTxnSignature() {
  return txnSignature;
 }

 public void setTxnStatus(TxnStatus txnStatus) {
  this.txnStatus = txnStatus;
 }

 public TxnStatus getTxnStatus() {
  return txnStatus;
 }

 @Override
 public String toString() {
  return String.format(
   "{ txnInputs: %s, txnOutputs: %s, txnHash: %s, txnSignature: %s, txnStatus: %s }",
   txnInputs,
   txnOutputs,
   txnHash,
   txnSignature,
   txnStatus
  );
 }
}
