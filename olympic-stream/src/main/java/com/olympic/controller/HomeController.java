package com.olympic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("auth/authenticate")
	String userlogin() {
		return "login";
	}

}
