package com.olympic.model.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.olympic.exception.UserAlreadyExistException;
import com.olympic.model.dto.RegisterForm;
import com.olympic.model.entity.User;

@Service
public interface UserService {

	Optional<User> findByEmail(String email);
	
	User register(RegisterForm from) throws UserAlreadyExistException;
	
}
