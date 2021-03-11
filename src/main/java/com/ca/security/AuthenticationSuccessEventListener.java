package com.ca.security;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.ca.service.LoginAttemptService;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Component
public class AuthenticationSuccessEventListener implements 
					ApplicationListener<AuthenticationSuccessEvent> {
	
	@Autowired
    private LoginAttemptService loginAttemptService;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
			Principal principal =(Principal) event.getSource();
			if(principal!=null) {
				String userName=principal.getName();
				if(userName!=null) {
					loginAttemptService.loginSucceeded(userName);
				}
			}
	}

}
