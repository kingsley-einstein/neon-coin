package com.neon.utils.writers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Optional;

import com.neon.models.Block;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component
public class BlockRepository {
 @Value("${blocks.file.location}")
 private String BLOCKS_LOCATION;

 public Block addBlock(Block block) throws Exception {
  try {
   FileInputStream fileInputStream = new FileInputStream(BLOCKS_LOCATION);
   ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
   
   List<Block> blocks = (List<Block>) objectInputStream.readObject();
   blocks.add(block);

   objectInputStream.close();

   FileOutputStream fileOutputStream = new FileOutputStream(BLOCKS_LOCATION);
   ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

   objectOutputStream.writeObject(blocks);
   objectOutputStream.close();

   return blocks.stream()
    .filter(b -> b.getHash().equals(block.getHash()))
    .findFirst()
    .get();
  } catch (IOException | ClassNotFoundException exc) {
   throw new Exception(exc.getMessage());
  }
 }

 public List<Block> getBlocks() throws Exception {
  try {
   FileInputStream fileInputStream = new FileInputStream(BLOCKS_LOCATION);
   ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
   
   List<Block> blocks = (List<Block>) objectInputStream.readObject();

   objectInputStream.close();

   return blocks;
  } catch (IOException | ClassNotFoundException exc) {
   throw new Exception(exc.getMessage());
  }
 }

 public Optional<Block> getBlockByNumber(Integer number) throws Exception {
  try {
   FileInputStream fileInputStream = new FileInputStream(BLOCKS_LOCATION);
   ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

   List<Block> blocks = (List<Block>) objectInputStream.readObject();

   objectInputStream.close();

   return blocks.stream()
    .filter(block -> blocks.indexOf(block) == number)
    .findFirst();
  } catch (IOException | ClassNotFoundException exc) {
   throw new Exception(exc.getMessage());
  }
 }

 public Optional<Block> getBlockByHash(String hash) throws Exception {
  try {
   FileInputStream fileInputStream = new FileInputStream(BLOCKS_LOCATION);
   ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

   List<Block> blocks = (List<Block>) objectInputStream.readObject();

   objectInputStream.close();

   return blocks.stream()
    .filter(block -> block.getHash().equals(hash))
    .findFirst();
  } catch (IOException | ClassNotFoundException exc) {
   throw new Exception(exc.getMessage());
  }
 }
}
