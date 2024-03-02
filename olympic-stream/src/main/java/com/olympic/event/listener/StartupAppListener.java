package com.olympic.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.olympic.model.entity.User;
import com.olympic.model.repo.UserRepo;

public class StartupAppListener {

	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@EventListener({ContextRefreshedEvent.class})
	void contextRefreshedEvent() {
		var user = new User();
		user.setEmail("aungaung@gmail.com");
		user.setPassword(encoder.encode("aungaung"));
		user.setActivated(true);
		user.setRole("USER");
		repo.save(user);
	}
}
