package com.olympic.model.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olympic.exception.UserAlreadyExistException;
import com.olympic.model.dto.RegisterForm;
import com.olympic.model.entity.User;
import com.olympic.model.repo.CountryRepo;
import com.olympic.model.repo.UserRepo;
import com.olympic.model.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CountryRepo countryRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Transactional
	public void save(User user) {
		userRepo.save(user);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepo.findByemail(email);
	}

	@Override
	@Transactional
	public User register(RegisterForm form) throws UserAlreadyExistException {
		
		if(userRepo.findByemail(form.getEmail()).isPresent()) {
			throw new UserAlreadyExistException();
		}
		
		var user = new User();
		user.setName(form.getAccountName());
		user.setEmail(form.getEmail());
		user.setPassword(encoder.encode(form.getPassword()));
		user.setPhone(form.getPhone());
		user.setCountry(countryRepo.findById(form.getCountry()).get());
		
		return userRepo.save(user);
	}
	
}
