package com.ca.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

//@ControllerAdvice
//@RestController
public class GlobalExceptionHandeller extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> universalExceptionHandeller(Exception ex,
			 WebRequest request){
		List<String> errors=new ArrayList<String>(1);
		errors.add(ex.getMessage());
		ExceptionResponse exceptionResponse=new ExceptionResponse(new Date(),errors,request.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	
}
