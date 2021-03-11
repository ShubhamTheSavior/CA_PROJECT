package com.ca.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.ca.entitis.UserDtlEntity;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

public class OnRegistrationCompleteEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String appUrl;
    private Locale locale;
    private UserDtlEntity user;
	
    public OnRegistrationCompleteEvent(
    		UserDtlEntity user, Locale locale, String appUrl) {
    	        super(user);
    	        
    	        this.user = user;
    	        this.locale = locale;
    	        this.appUrl = appUrl;
    	    }

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public UserDtlEntity getUser() {
		return user;
	}

	public void setUser(UserDtlEntity user) {
		this.user = user;
	}
}
