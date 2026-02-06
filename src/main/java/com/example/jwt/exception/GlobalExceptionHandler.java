
package com.example.jwt.exception;

import java.util.Optional;

import org.springframework.http.HttpStatus;
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
	@ExceptionHandler(UserAlreadyCreatedException.class)
	public DefaultResponse handleUserAlreadyCreated(UserAlreadyCreatedException ex) {
		DefaultResponse response = new DefaultResponse(
				MethodArgumentNotValidException.class.getName(),
				"User is already created on those credentials",
				null);
		return response;
	}

}
