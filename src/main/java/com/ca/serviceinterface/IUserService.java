package com.ca.serviceinterface;

import javax.servlet.http.HttpServletRequest;

import com.ca.entitis.UserDtlEntity;
import com.ca.entitis.VerificationToken;
import com.ca.exception.UserAlreadyExistException;
import com.ca.model.UserDto;


public interface IUserService {

	UserDtlEntity registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException;

	void createVerificationToken(UserDtlEntity user, String token);

	VerificationToken getVerificationToken(String token);

	void saveRegisteredUser(UserDtlEntity user);

	UserDtlEntity sendPasswordResetLink(String username, HttpServletRequest request);

	VerificationToken generateNewVerificationToken(String existingToken, HttpServletRequest request);

	UserDtlEntity findUserByEmail(String name);

	boolean checkIfValidOldPassword(UserDtlEntity user, String oldPassword);

	void changeUserPassword(UserDtlEntity user, String newPassword);;

}
