package com.ca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ca.model.LoginForm;
import com.ca.security.ActiveUserStore;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Controller
public class WelcomeController {


	 @Autowired
	 private ActiveUserStore activeUserStore;
	 
	
	@GetMapping("/home")
	public ModelAndView getDemo() {
		LoginForm loginForm=new LoginForm();
		ModelAndView mv=new ModelAndView();
		mv.addObject("name", "David");
		mv.addObject("loginForm", loginForm);
		mv.setViewName("homePage");
		return mv;
	}
	
	@GetMapping("/")
	public String loginSuccess(Model model) {
		System.out.println(activeUserStore.getUsers());
		model.addAttribute("users", activeUserStore.getUsers());
		return "loginSuccess";
	}
	
	@GetMapping("/logout-success")
	public void logoutSuccess() {
		System.out.println("logout success");
	}

	
}
