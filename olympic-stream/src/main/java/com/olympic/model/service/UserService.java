package com.olympic.model.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.olympic.exception.UserAlreadyExistException;
import com.olympic.model.dto.UserDto;
import com.olympic.model.entity.User;
import com.olympic.model.form.PreferredSportForm;
import com.olympic.model.form.RegisterForm;

@Service
public interface UserService {

	Optional<User> findByEmail(String email);
	
	Optional<User> findById(String id);
	
	Optional<UserDto> findUserByEmail(String email);
	
	User register(RegisterForm from) throws UserAlreadyExistException;
	
	void addPreferredSports(PreferredSportForm form, String id);
}
