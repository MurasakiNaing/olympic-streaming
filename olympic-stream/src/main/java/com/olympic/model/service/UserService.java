package com.olympic.model.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.olympic.model.entity.User;

@Service
public interface UserService {

	Optional<User> findByEmail(String email);
	
}
