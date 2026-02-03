
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
				Optional.of(MethodArgumentNotValidException.class.getName()),
				ex.getMessage(),
				null);
		return response;
	}

}
