
package com.example.jwt.exception;

import java.util.Optional;

import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.jwt.response.DefaultResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public DefaultResponse handleValidation(MethodArgumentNotValidException ex) {
		DefaultResponse response = new DefaultResponse(
				MethodArgumentNotValidException.class.getName(),
				ex.getMessage(),
				null);
		return response;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ UserNotFoundException.class, AuthException.class, UserAlreadyCreatedException.class,
			ServerErrorException.class })
	public DefaultResponse handleMissingUser(UserNotFoundException ex) {
		return new DefaultResponse(
				UserNotFoundException.class.getName(),
				ex.getMessage(),
				null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public DefaultResponse handleUserAlreadyCreated(HttpMessageNotReadableException ex) {
		return new DefaultResponse(
				MethodArgumentNotValidException.class.getName(),
				"Required body is missing",
				null);
	}
}
