package com.ca.listeners;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ca.entitis.UserDtlEntity;
import com.ca.events.OnRegistrationCompleteEvent;
import com.ca.serviceinterface.EmailService;
import com.ca.serviceinterface.IUserService;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Component
public class RegistrationListener implements 
		ApplicationListener<OnRegistrationCompleteEvent>{

	@Value("${app.server.url}")
	private String appUrl;
	
	@Autowired
    private IUserService service;
 
    @Autowired
    private MessageSource messages;

    @Autowired
    private EmailService emailService;
    
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		UserDtlEntity user=event.getUser();
		String token = UUID.randomUUID().toString();
		service.createVerificationToken(user, token);
		String recipientAddress = user.getUsername();
		String subject = "Registration Confirmation";
		String confirmationUrl 
         = event.getAppUrl() + "/user/regitrationConfirm?token=" + token;
		String message = messages.getMessage("message.regSucc", null, event.getLocale());
        String text=message + "\r\n" + appUrl + confirmationUrl;
        emailService.sendSimpleMessage(recipientAddress,subject,text);
	}
	
	
	
}
