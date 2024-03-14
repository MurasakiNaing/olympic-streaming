package com.olympic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.olympic.model.dto.SportDto;
import com.olympic.model.service.SportService;
import com.olympic.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SportService sportService;
	
	@GetMapping({"/user", "/admin", "/home"})
	String home(Authentication auth, HttpSession session) {
		var isUser = auth.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_USER"));
		var noSession = session.getAttribute("user") == null;
		if(isUser && noSession) {
			var user = userService.findUserByEmail(auth.getName()).get();
			session.setAttribute("user", user);			
		}
		return "home";
	}
	
	@GetMapping("/sports")
	String sportsList(ModelMap map) {
		
		map.put("groupedSports", groupedSports());
		return "sports";
	}
	
	@GetMapping("/sports/{id}")
	String sportDetails(@PathVariable Integer id, ModelMap map) {
		var sport = sportService.findOneById(id);
		
		if(sport.isPresent()) {
			map.put("sport", sport.get());
			return "sport";
		}
		
		return "sport-not-found";
	}
	
	private Map<String, List<SportDto>> groupedSports() {
		var sports = sportService.findAll();
		
		Map<String, List<SportDto>> groupedSports = new HashMap<>();
		
		for (SportDto sport : sports) {
			String firstChar = String.valueOf(sport.getName().charAt(0));
			if(!Character.isLetter(sport.getName().charAt(0))) {
				firstChar = "#";				
			}
			groupedSports.computeIfAbsent(firstChar, fun -> new ArrayList<>()).add(sport);
		}
		return groupedSports;
	}
	
}
