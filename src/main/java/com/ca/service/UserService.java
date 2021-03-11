package com.ca.service;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ca.entitis.PasswordResetToken;
import com.ca.entitis.Roles;
import com.ca.entitis.UserDtlEntity;
import com.ca.entitis.VerificationToken;
import com.ca.exception.UserAlreadyExistException;
import com.ca.exception.UserNotFoundException;
import com.ca.model.UserDto;
import com.ca.repositories.PasswordResetTokenRepo;
import com.ca.repositories.RolesRepo;
import com.ca.repositories.UserDtlRepository;
import com.ca.repositories.VerificationTokenRepo;
import com.ca.serviceinterface.EmailService;
import com.ca.serviceinterface.IUserService;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Service
public class UserService implements IUserService {

	@Value("${app.server.url}")
	private String appUrl;
	
	@Autowired
	private UserDtlRepository userRepository;
	
	@Autowired
	private RolesRepo reolesRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private VerificationTokenRepo tokenRepository;
	
	@Autowired
	private PasswordResetTokenRepo passwordTokenRepository;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private EmailService emailService;
		
	@Transactional
	@Override
	public UserDtlEntity registerNewUserAccount(UserDto userDto) {
		
		if (emailExist(userDto.getUsername())) {  
            throw new UserAlreadyExistException(
              "There is an account with that email address: "
              +  userDto.getUsername());
        }
		
		UserDtlEntity user=new UserDtlEntity();
		user.setUsername(userDto.getUsername().trim().toLowerCase());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setEnabled(userDto.isEnabled());
		Roles role=reolesRepo.findByRoleName("USER");
		user.addRoles(role);
		userRepository.save(user);
		return user;
	}
	
	private boolean emailExist(String email) {
        return userRepository.findByUsername(email) != null;
    }

	@Override
	public void createVerificationToken(UserDtlEntity user, String token) {
		VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
	}

	@Override
	public VerificationToken getVerificationToken(String token) {
		VerificationToken verificationToken=tokenRepository.findByToken(token);
		return verificationToken;
	}

	@Override
	public void saveRegisteredUser(UserDtlEntity user) {
		userRepository.save(user);
	}

	public void createPasswordResetTokenForUser(UserDtlEntity user, String token) {
	    PasswordResetToken myToken = new PasswordResetToken(token, user);
	    passwordTokenRepository.save(myToken);
	}
	
	@Override
	public UserDtlEntity sendPasswordResetLink(String username,HttpServletRequest request) {
		UserDtlEntity user = userRepository.findByUsername(username);
		if (user == null) {
	        throw new UserNotFoundException("User does not exist");
	    }
		String token = UUID.randomUUID().toString();
		createPasswordResetTokenForUser(user, token);
		String url = appUrl+request.getContextPath() + "/user/changePassword?token=" + token;
		String message = messages.getMessage("message.resetPassword", 
			      null, request.getLocale());
		String subject="Reset Password";
		String text=message + " \r\n" + url;
		emailService.sendSimpleMessage(user.getUsername(), subject, text);
		return user;
	}

	@Override
	public VerificationToken generateNewVerificationToken(String existingToken,
			HttpServletRequest request) {
		VerificationToken verificationToken=tokenRepository.findByToken(existingToken);
		UserDtlEntity user=verificationToken.getUser();
		if(user!=null) {
			String token = UUID.randomUUID().toString();
			updateVerificationToken(token,verificationToken);
			String recipientAddress = user.getUsername();
			String subject = "Registration Confirmation";
			String confirmationUrl 
	         = appUrl + "/regitrationConfirm?token=" + token;
			String message = messages.getMessage("message.regSucc", null, request.getLocale());
	        String text=message + "\r\n" + appUrl + confirmationUrl;
	        emailService.sendSimpleMessage(recipientAddress,subject,text);
	        return verificationToken;
		}
		return verificationToken;
	}
	
	public void updateVerificationToken(String token,VerificationToken verificationToken) {
		verificationToken.setToken(token);
		verificationToken.setExpiryDate(verificationToken.getcalculatedExpiryDate());
		tokenRepository.save(verificationToken);
	}

	@Override
	public UserDtlEntity findUserByEmail(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public boolean checkIfValidOldPassword(UserDtlEntity user, String oldPassword) {
		return passwordEncoder.matches(oldPassword, user.getPassword());
	}

	@Override
	public void changeUserPassword(UserDtlEntity user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}
	
}
