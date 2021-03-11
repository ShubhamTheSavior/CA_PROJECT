package com.ca.exception;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

public class InvalidOldPasswordException 
	extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	public InvalidOldPasswordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
