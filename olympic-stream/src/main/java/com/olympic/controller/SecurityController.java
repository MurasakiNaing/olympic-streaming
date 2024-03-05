package com.olympic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.olympic.event.RegisterEvent;
import com.olympic.exception.UserAlreadyExistException;
import com.olympic.exception.VerificationException;
import com.olympic.model.dto.RegisterForm;
import com.olympic.model.service.CountryService;
import com.olympic.model.service.UserService;
import com.olympic.model.service.VerificationService;

@Controller
@RequestMapping("/auth")
public class SecurityController {

	@Autowired
	private UserService userService;

	@Autowired
	private VerificationService verificationService;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private CountryService countryService;

	@GetMapping("/login")
	String login() {
		return "login";
	}

	@GetMapping("/register")
	String register(ModelMap model) {
		model.put("registerForm", new RegisterForm());
		model.put("countries", countryService.getAllCountries());
		return "register";
	}

	@PostMapping("/register")
	String registerUser(@ModelAttribute("registerForm") RegisterForm form) {
		try {
			userService.register(form);
			var user = userService.findByEmail(form.getEmail()).get();
			eventPublisher.publishEvent(new RegisterEvent(user));
		} catch (UserAlreadyExistException e) {
			return "redirect:/auth/register?error=true";
		}

		return "verification";
	}

	@GetMapping("/verification/{code}")
	String activate(@PathVariable String code) {
		
		try {
			verificationService.verify(code);
		} catch (VerificationException e) {
			return "verification-fail";
		}

		return "verification-success";
	}
}
