package com.olympic.exception;

public class VerificationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public VerificationException() {
		super("Verification link expired or does not exist.");
	}

}
