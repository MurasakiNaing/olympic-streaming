package com.olympic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.olympic.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/user")
	String userhome(Authentication auth, HttpSession session) {
		var user = service.findByEmail(auth.getName()).get();
		session.setAttribute("user", user);
		return "userhome";
	}
	
	@GetMapping({"/admin", "/home"})
	String adminHome() {
		return "userhome";
	}
	
}
