package com.ca.controller;

import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ca.entitis.UserDtlEntity;
import com.ca.entitis.VerificationToken;
import com.ca.events.OnRegistrationCompleteEvent;
import com.ca.exception.InvalidOldPasswordException;
import com.ca.exception.UserAlreadyExistException;
import com.ca.exception.UserNotFoundException;
import com.ca.model.GenericResponse;
import com.ca.model.PasswordDto;
import com.ca.model.UserDto;
import com.ca.serviceinterface.IUserService;
import com.ca.serviceinterface.SecurityService;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Controller
public class RegistrationController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
    private MessageSource messages;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private SecurityService securityService;
	
	@GetMapping("/user/registration")
	public String getRegistrationPage(WebRequest request, Model model) {
		UserDto  userDto =new UserDto ();
		model.addAttribute("user", userDto);
		return "registration";
	}
	
	@PostMapping("/user/registration")
	public String registerUserAccount(
			  @ModelAttribute("user") @Valid UserDto userDto,
			  BindingResult result,Model model,HttpServletRequest request) {
			 
			if(result.hasErrors()) {
				return "registration";
			}
				
			try {
				UserDtlEntity registered = userService.registerNewUserAccount(userDto);
				String appUrl = request.getContextPath();
				 eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, 
				          request.getLocale(), appUrl));
				 
			} catch (UserAlreadyExistException uaeEx) {
				model.addAttribute("message", "An account for that username/email already exists.");
				return "registration";
			}catch (RuntimeException ex) {
				model.addAttribute("emailError", userDto);
		    }
				
			  return "redirect:/home";
	}
	

	@GetMapping("/user/regitrationConfirm")
	public String confirmRegistration
	  (WebRequest request, Model model, @RequestParam("token") String token,
			  RedirectAttributes redirectAttributes) {
		Locale locale = request.getLocale();
		VerificationToken verificationToken = userService.getVerificationToken(token);
		 if (verificationToken == null) {
		        String message = messages.getMessage("auth.message.invalidToken", null, locale);
		        model.addAttribute("message", message);
		        return "redirect:/badUser.html?lang=" + locale.getLanguage();
		 }
		 UserDtlEntity user = verificationToken.getUser();
		 Calendar cal = Calendar.getInstance();
		 if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			 redirectAttributes.addFlashAttribute("message", messages.getMessage("auth.message.expired", null, locale));
			 redirectAttributes.addFlashAttribute("expired", true);
			 redirectAttributes.addFlashAttribute("token", token);
		        return "redirect:/home";
		  }
		
		 user.setEnabled(true); 
		 userService.saveRegisteredUser(user); 
		 return "redirect:/login.html?lang=" + request.getLocale().getLanguage(); 
	}
	
	@GetMapping("/user/resendRegistrationToken")
	public GenericResponse resendRegistrationToken(
			HttpServletRequest request, @RequestParam("token") String existingToken) {
		
		userService.generateNewVerificationToken(existingToken,request);
		return new GenericResponse(
			      messages.getMessage("message.resendToken", null, request.getLocale()));
		
	}
	@GetMapping("/user/resetPassword")
	public String forgotPassword(WebRequest request, Model model) {
		UserDto userDto =new UserDto ();
		model.addAttribute("user", userDto);
		return "redirect:/home";
	}
	
	@PostMapping("/user/resetPassword")
	public String resetPassword(@ModelAttribute("user") UserDto userDto,
			HttpServletRequest request) {
		
		try {
			userService.sendPasswordResetLink(userDto.getUsername(),request);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
		return "redirect:/home";
	}
	
	@GetMapping("/user/changePassword")
	public String showChangePasswordPage(Locale locale, Model model, 
			  @RequestParam("token") String token) {
		String result = securityService.validatePasswordResetToken(token);
		if(result != null) {
	        String message = messages.getMessage("auth.message." + result, null, locale);
	        return "redirect:/login.html?lang=" 
	            + locale.getLanguage() + "&message=" + message;
	    } else {
	    	PasswordDto passwordDto=new PasswordDto();
	    	model.addAttribute("passwordDto", passwordDto);
	        model.addAttribute("token", token);
	        return "updatePassword";
	    }
	}
	
	@PostMapping("/user/savePassword")
	public String savePassword(@Valid @ModelAttribute("passwordDto") PasswordDto passwordDto,
			Model model,BindingResult error) {
		if(error.hasErrors()) {
			return "updatePassword";
		}
		String result=securityService.updatePassword(passwordDto);
		if(result.equals("updated"))
			return "redirect:/home";
		return "updatePassword";
	}
	
	@GetMapping("/user/updatePassword")
	public String showUpdatePasswordPage(WebRequest request, Model model) {
		PasswordDto passwordDto=new PasswordDto();
    	model.addAttribute("passwordDto", passwordDto);
    	return "changePassword";
	}
	
	@PostMapping("/user/updatePassword")
	public String changeUserPassword(Locale locale, 
			  @ModelAttribute("passwordDto") PasswordDto passwordDto) {
		
		UserDtlEntity user = userService.findUserByEmail(
			      SecurityContextHolder.getContext().getAuthentication().getName());
		
		if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
	        throw new InvalidOldPasswordException("Invalid Old Password");
	    }
		
		userService.changeUserPassword(user, passwordDto.getNewPassword());
		return "changePassword";
	}
	
	@GetMapping("/admin/home")
	public void test() {
		System.out.println("ADMIN");
	}
	
}
