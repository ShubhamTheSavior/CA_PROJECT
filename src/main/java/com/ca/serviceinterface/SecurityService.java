package com.ca.serviceinterface;

import com.ca.model.PasswordDto;

public interface SecurityService {

	String validatePasswordResetToken(String token);

	String updatePassword(PasswordDto passwordDto);

}
