package com.neon.utils.writers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Optional;

import com.neon.models.Wallet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component
public class WalletRepository {
 @Value("${wallets.file.location}")
 private String WALLETS_LOCATION;

 public Wallet write(Wallet w) throws Exception {
  try {
   FileInputStream fileInputStream = new FileInputStream(WALLETS_LOCATION);
   ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

   List<Wallet> wallets = (List<Wallet>) objectInputStream.readObject();

   wallets.add(w);
   objectInputStream.close();

   FileOutputStream fileOutputStream = new FileOutputStream(WALLETS_LOCATION);
   ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

   objectOutputStream.writeObject(wallets);
   objectOutputStream.close();

   return w;

  } catch (IOException | ClassNotFoundException e) {
   throw new Exception(e.getMessage());
  }
 }

 public Optional<Wallet> findByAddress(String address) throws Exception {
  try {
   FileInputStream fileInputStream = new FileInputStream(WALLETS_LOCATION);
   ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

   List<Wallet> wallets = (List<Wallet>) objectInputStream.readObject();

   objectInputStream.close();
   
   return wallets
    .stream()
    .filter(w -> w.getAddress().equals(address))
    .findFirst();
  } catch (IOException | ClassNotFoundException e) {
   throw new Exception(e.getMessage());
  }
 }

 public Optional<Wallet> updateBalance(String address, Double amount) throws Exception {
  try {
   FileInputStream fileInputStream = new FileInputStream(WALLETS_LOCATION);
   ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

   List<Wallet> wallets = (List<Wallet>) objectInputStream.readObject();

   objectInputStream.close();

   for (int i = 0; i < wallets.size(); i++)
    if (wallets.get(i).getAddress().equals(address)) {
     Wallet w = wallets.get(i);
     w.setBalance(amount);
     wallets.set(i, w);
    }

    FileOutputStream fileOutputStream = new FileOutputStream(WALLETS_LOCATION);
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

    objectOutputStream.writeObject(wallets);
    objectOutputStream.close();

    return wallets.stream()
     .filter(w -> w.getAddress().equals(address))
     .findFirst();
  } catch (IOException | ClassNotFoundException e) {
   throw new Exception(e.getMessage());
  }
 }
}
