package com.ca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ca.entitis.UserDtlEntity;
import com.ca.repositories.UserDtlRepository;
import com.ca.security.MyUserDetails;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserDtlRepository userRepository;
	
	@Autowired
	private LoginAttemptService loginAttemptService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
        if (loginAttemptService.isBlocked(username)) {
            throw new RuntimeException("blocked");
        }
		
		UserDtlEntity user=userRepository.findByUsername(username);
		if(user==null)
			throw new UsernameNotFoundException("Username/Password incorrect");
		return new MyUserDetails(user);
	}
	
	
}
