package com.ca.filter;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ca.exception.ExceptionResponse;
import com.ca.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Component
public class JwtFilter

	//extends OncePerRequestFilter
{

	/*
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsService service;

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		final String authorizationHeader=request.getHeader("Authorization");
		String token=null;
		String username=null;
		
		try {
			if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")) {
				token=authorizationHeader.substring(7);
				username=jwtUtil.extractUsername(token);
			}
			
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails= service.loadUserByUsername(username);
				if (jwtUtil.validateToken(token, userDetails)) {

	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
	                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                usernamePasswordAuthenticationToken
	                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            }
			}
			
			filterChain.doFilter(request, response);
			
		} catch (Exception ex) {
			List<String> errors=new ArrayList<String>(1);
			errors.add(ex.getMessage());
			ExceptionResponse exceptionResponse=new ExceptionResponse(new Date(),errors,"");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(exceptionResponse);
            response.getWriter().write(json);
		}
		
		
		
		}
	*/

}
