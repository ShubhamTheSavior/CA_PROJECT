package com.ca.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ca.entitis.PasswordResetToken;
import com.ca.entitis.UserDtlEntity;
import com.ca.model.PasswordDto;
import com.ca.repositories.PasswordResetTokenRepo;
import com.ca.repositories.UserDtlRepository;
import com.ca.serviceinterface.SecurityService;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Component
public class SecurityServiceImpl implements SecurityService{
	
	@Autowired
	private PasswordResetTokenRepo passwordTokenRepository;
	
	@Autowired
	private UserDtlRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public String validatePasswordResetToken(String token) {
		final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
		return !isTokenFound(passToken) ? "invalidToken"
	            : isTokenExpired(passToken) ? "expired"
	            : null;
	}

	private boolean isTokenFound(PasswordResetToken passToken) {
	    return passToken != null;
	}
	 
	private boolean isTokenExpired(PasswordResetToken passToken) {
	    final Calendar cal = Calendar.getInstance();
	    return passToken.getExpiryDate().before(cal.getTime());
	}

	@Override
	public String updatePassword(PasswordDto passwordDto) {
	    
		String result = validatePasswordResetToken(passwordDto.getToken());
		if(result != null) {
	        return "error";	
	    }
		
		final PasswordResetToken passToken = passwordTokenRepository.findByToken(passwordDto.getToken());
		
		UserDtlEntity user=passToken.getUser();
		
		 if(user!=null) {
			 user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
			 userRepository.save(user);
			 return "updated";	
		 }else {
			 return "error";	
		 }
		
	}
}
