package com.neon.customs.handlers;

import com.neon.customs.CustomError;
import com.neon.customs.CustomServerResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrorHandler {
 @ExceptionHandler(value = CustomError.class)
 public ResponseEntity<CustomServerResponse<String>> exception(CustomError error) {
  return new ResponseEntity<>(
   new CustomServerResponse<String>(
    error.getCode(),
    error.getMessage()
   ),
   HttpStatus.valueOf(error.getCode())
  );
 }
}
