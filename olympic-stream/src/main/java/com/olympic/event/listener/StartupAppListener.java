package com.olympic.event.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.olympic.model.entity.Admin;
import com.olympic.model.entity.User;
import com.olympic.model.repo.AdminRepo;
import com.olympic.model.repo.SportRepo;
import com.olympic.model.repo.UserRepo;

@Component
public class StartupAppListener {

	@Autowired
	private UserRepo repo;
	
	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private SportRepo sportRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@EventListener({ContextRefreshedEvent.class})
	void contextRefreshedEvent() {
		var user = new User();
		user.setEmail("aungaung@gmail.com");
		user.setPassword(encoder.encode("aungaung"));
		user.setName("aungaung");
		user.setActivated(true);
		user.setRole("USER");
		user.setImageName("default_pfp.png");
		user.setPreferredSports(List.of(sportRepo.getOne(1), sportRepo.getById(2)));
		user.setPhone("09-123455678");
		repo.save(user);
		
		var admin = new Admin();
		admin.setName("admin");
		admin.setPassword("admin");
		adminRepo.save(admin);
	}
}
