package com.ca.exception;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

public class UserAlreadyExistException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException(String message) {
		super(message);
	}

	
}
