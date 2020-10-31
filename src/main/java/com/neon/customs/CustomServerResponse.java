package com.neon.customs;

@SuppressWarnings("serial")
public class CustomServerResponse<T> implements java.io.Serializable {
 private Integer code;
 private T response;

 public CustomServerResponse() {}
 
 public CustomServerResponse(final Integer code, final T response) {
  this.code = code;
  this.response = response;
 }

 public Integer getCode() {
  return code;
 }

 public T getResponse() {
  return response;
 }
}
