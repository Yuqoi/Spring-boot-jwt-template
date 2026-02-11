package com.example.jwt.exception;

import com.example.jwt.response.DefaultResponse;
import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public DefaultResponse handleValidation(MethodArgumentNotValidException ex) {
    DefaultResponse response =
        new DefaultResponse(MethodArgumentNotValidException.class.getName(), ex.getMessage(), null);
    return response;
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({
    UserNotFoundException.class,
    AuthException.class,
    UserAlreadyCreatedException.class,
    ServerErrorException.class,
    AccessTokenExpiredException.class
  })
  public DefaultResponse handleMissingUser(Exception ex) {
    return new DefaultResponse(ex.getClass().getName(), ex.getMessage(), null);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public DefaultResponse handleUserAlreadyCreated(HttpMessageNotReadableException ex) {
    return new DefaultResponse(
        MethodArgumentNotValidException.class.getName(), "Required body is missing", null);
  }
}
