package com.neon.services;

import com.neon.models.Wallet;
import com.neon.utils.Crypto;
import com.neon.utils.classes.KeyConfluence;

import org.springframework.stereotype.Component;

@Component
public class WalletService {
 public Wallet generateWallet(String[] recoveryPhrase) throws Exception {
  try {
   String phrase = "";

   for (String p: recoveryPhrase)
    phrase += p + " ";

   Crypto c = new Crypto();
   KeyConfluence keyConfluence = c.generateConfluence(phrase);
   return new Wallet(
    keyConfluence.getAddress(), 
    keyConfluence.getPublicKey(), 
    keyConfluence.getPrivateKey(), 
    0.0
   );
  } catch (Exception ex) {
   throw new Exception(ex.getMessage());
  }
 }
}
