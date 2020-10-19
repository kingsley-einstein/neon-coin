package com.neon.customs;

@SuppressWarnings("serial")
public class CustomError extends RuntimeException {
 private Integer code;
 
 public CustomError(Integer code, String message) {
  super(message);
  this.code = code;
 }

 public Integer getCode() {
  return code;
 }
}
