package com.olympic.exception;

public class UserAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException() {
		super("User Already Exist.");
	}
	
}
