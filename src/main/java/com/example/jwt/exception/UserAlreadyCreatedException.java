package com.example.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyCreatedException extends AuthenticationException {
  public UserAlreadyCreatedException(String msg) {
    super(msg);
  }
}
