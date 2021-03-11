package com.ca.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.model.AuthRequest;
import com.ca.util.JwtUtil;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@RestController
public class LoginRestController {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/authentication")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
					);
		} catch (Exception e) {
			throw new Exception("invalid username/password");
		}
		
		return jwtUtil.generateToken(authRequest.getUsername());
	}
	
	@GetMapping("/shubham")
	public String afterLogin() {
		return "validated";
	}
	
}
