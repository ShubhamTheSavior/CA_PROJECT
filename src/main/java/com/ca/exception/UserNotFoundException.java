package com.ca.exception;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

public class UserNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
