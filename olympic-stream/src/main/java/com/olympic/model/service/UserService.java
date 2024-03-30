package com.olympic.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.olympic.exception.UserAlreadyExistException;
import com.olympic.model.dto.UserDto;
import com.olympic.model.entity.Sport;
import com.olympic.model.entity.User;
import com.olympic.model.form.PreferredSportForm;
import com.olympic.model.form.RegisterForm;

import jakarta.servlet.ServletContext;

@Service
public interface UserService {

	Optional<User> findByEmail(String email);
	
	Optional<User> findById(String id);
	
	Optional<UserDto> findUserById(String id);
	
	Optional<UserDto> findUserByEmail(String email);
	
	List<UserDto> findAllUser();
	
	User register(RegisterForm from) throws UserAlreadyExistException;
	
	void addPreferredSports(PreferredSportForm form, String id);
	
	List<Sport> getPreferredSportsByUser(String userId);
	
	void updateUsername(String username, String id);
	
	void updatePassword(String id, String password);
	
	void updateImage(String id, MultipartFile image, ServletContext context);
	
	void updatePhoneNumber(String id, String phoneNumber);
	
	boolean passwordMatch(String id, String currentPassword);
}
