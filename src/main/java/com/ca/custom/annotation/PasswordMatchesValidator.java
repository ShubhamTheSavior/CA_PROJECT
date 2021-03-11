package com.ca.custom.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ca.model.UserDto;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

public class PasswordMatchesValidator 
	implements ConstraintValidator<PasswordMatches, Object> {
	
	 	@Override
	    public void initialize(PasswordMatches constraintAnnotation) {       
	    }
	    @Override
	    public boolean isValid(Object obj, ConstraintValidatorContext context){   
	        UserDto user = (UserDto) obj;
	        return user.getPassword().equals(user.getMatchingPassword());    
	    }

}
