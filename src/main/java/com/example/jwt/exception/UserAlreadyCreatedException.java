package com.example.jwt.exception;

public class UserAlreadyCreatedException extends RuntimeException {

	public UserAlreadyCreatedException(String msg) {
		super(msg);
	}

}
