package com.example.jwt.exception;

public class AccessTokenExpiredException extends RuntimeException {

  public AccessTokenExpiredException(String msg) {
    super(msg);
  }
}
