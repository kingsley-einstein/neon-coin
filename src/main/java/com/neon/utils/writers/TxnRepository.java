package com.neon.utils.writers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.neon.models.Txn;
import com.neon.models.TxnInput;
import com.neon.models.TxnOutput;
import com.neon.models.Wallet;
import com.neon.models.enums.TxnStatus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component
public class TxnRepository {
 @Value("${txns.file.location}")
 private String TXNS_LOCATION;

 public Txn createTxn(List<TxnInput> txnInputs, List<TxnOutput> txnOutputs) throws Exception {
  try {
   FileInputStream fileInputStream = new FileInputStream(TXNS_LOCATION);
   ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

   List<Txn> txns = (List<Txn>) objectInputStream.readObject();
   
   objectInputStream.close();

   MessageDigest d = MessageDigest.getInstance("SHA-256");
   
   List<Byte> txnInputBytes = new ArrayList<>();

   for (byte b: txnInputs.toString().getBytes())
    txnInputBytes.add(b);

   List<Byte> txnOutputBytes = new ArrayList<>();

   for (byte b: txnOutputs.toString().getBytes())
    txnOutputBytes.add(b);

   List<Byte> randomUUIDBytes = new ArrayList<>();

   for (byte b: UUID.randomUUID().toString().getBytes())
    randomUUIDBytes.add(b);

   List<Byte> allBytes = new ArrayList<>();
   allBytes.addAll(txnInputBytes);
   allBytes.addAll(txnOutputBytes);
   allBytes.addAll(randomUUIDBytes);

   byte[] bytes = new byte[]{};

   for (int i = 0; i < allBytes.size(); i++)
    bytes[i] = allBytes.get(i);

   d.update(bytes);

   byte[] digestAsBytes = d.digest();
   StringBuffer buffer = new StringBuffer();

   for (byte b: digestAsBytes)
    buffer.append(Integer.toHexString(0xFF & b));

   Txn txn = new Txn(txnInputs, txnOutputs, buffer.toString(), "", TxnStatus.PENDING);

   txns.add(txn);

   FileOutputStream fileOutputStream = new FileOutputStream(TXNS_LOCATION);
   ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

   objectOutputStream.writeObject(txns);
   objectOutputStream.close();

   return txn;
  } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException e) {
   throw new Exception(e.getMessage());
  }
 }

 public List<Txn> getTxns() throws Exception {
  try {
   FileInputStream fileInputStream = new FileInputStream(TXNS_LOCATION);
   ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

   List<Txn> txns = (List<Txn>) objectInputStream.readObject();

   objectInputStream.close();
   
   return txns;
  } catch (IOException | ClassNotFoundException e) {
   throw new Exception(e.getMessage());
  }
 }

 public Optional<Txn> getTxn(String hash) throws Exception {
  try {
   FileInputStream fileInputStream = new FileInputStream(TXNS_LOCATION);
   ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

   List<Txn> txns = (List<Txn>) objectInputStream.readObject();

   objectInputStream.close();

   return txns.stream()
    .filter(t -> t.getTxnHash().equals(hash))
    .findFirst();
  } catch (IOException | ClassNotFoundException e) {
   throw new Exception(e.getMessage());
  }
 }

 public Txn signTxn(Txn txn, Wallet signer) throws Exception {
  try {
   FileInputStream fileInputStream = new FileInputStream(TXNS_LOCATION);
   ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

   List<Txn> txns = (List<Txn>) objectInputStream.readObject();

   objectInputStream.close();

   Txn signableTxn = txns.stream()
    .filter(t -> t.getTxnHash().equals(txn.getTxnHash()))
    .findFirst()
    .get();

   boolean signerEligible = signableTxn.getTxnInputs()
    .stream()
    .anyMatch(txnInput -> txnInput.getAddress().equals(signer.getAddress()));

   if (!signerEligible)
    throw new Exception(
     "Wallet with address - " + signer.getAddress() + " - is not eligible to sign this transaction"
    );

   MessageDigest d = MessageDigest.getInstance("SHA-256");

   d.update(
    (
     signableTxn.getTxnInputs().toString() + 
     signableTxn.getTxnOutputs().toString() + 
     signableTxn.getTxnHash() +
     signer.getPrivateKey()
    )
    .getBytes()
   );

   byte[] bytes = d.digest();
   StringBuffer buffer = new StringBuffer();

   for (byte b: bytes)
    buffer.append(Integer.toHexString(0xFF & b));

   signableTxn.setTxnSignature(buffer.toString());
   signableTxn.setTxnStatus(TxnStatus.CONFIRMED);

   for (int i = 0; i < txns.size(); i++)
    if (txns.get(i).getTxnHash().equals(signableTxn.getTxnHash()))
     txns.set(i, signableTxn);

   FileOutputStream fileOutputStream = new FileOutputStream(TXNS_LOCATION);
   ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

   objectOutputStream.writeObject(txns);
   objectOutputStream.close();

   return signableTxn;
  } catch (IOException | ClassNotFoundException e) {
   throw new Exception(e.getMessage());
  }
 }
}
