package com.ca.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ca.custom.annotation.PasswordMatches;
import com.ca.custom.annotation.ValidEmail;
import com.ca.custom.annotation.ValidPassword;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@PasswordMatches
public class UserDto  {

	@ValidEmail
	@NotNull
    @NotEmpty
	private String username;
	
	@NotNull
    @NotEmpty
    @ValidPassword
	private String password;
	
	private String matchingPassword;
	
	private boolean isEnabled;
	
	public UserDto () {
		super();
		this.isEnabled=false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return "UserDto [username=" + username + ", password=" + password + ", matchingPassword=" + matchingPassword
				+ ", isEnabled=" + isEnabled + "]";
	}
	
	

}
