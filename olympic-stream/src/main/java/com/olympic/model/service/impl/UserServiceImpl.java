package com.olympic.model.service.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.olympic.exception.UserAlreadyExistException;
import com.olympic.model.dto.UserDto;
import com.olympic.model.entity.Sport;
import com.olympic.model.entity.User;
import com.olympic.model.form.PreferredSportForm;
import com.olympic.model.form.RegisterForm;
import com.olympic.model.repo.CountryRepo;
import com.olympic.model.repo.SportRepo;
import com.olympic.model.repo.UserRepo;
import com.olympic.model.service.UserService;

import jakarta.servlet.ServletContext;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CountryRepo countryRepo;
	
	@Autowired
	private SportRepo sportRepo;
	
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

	@Override
	public void addPreferredSports(PreferredSportForm form, String id) {
		var userOptional = userRepo.findById(id);
		if(userOptional.isPresent()) {
			var user = userOptional.get();
			for (Integer i : form.getSports()) {
				var sport = sportRepo.findById(i);
				if(sport.isPresent()) {
					user.getPreferredSports().add(sport.get());
				}
			}
		}
	}

	@Override
	public Optional<User> findById(String id) {
		return userRepo.findById(id);
	}

	@Override
	public Optional<UserDto> findUserByEmail(String email) {
		return userRepo.findUserByemail(email);
	}

	@Override
	public List<UserDto> findAllUser() {
		return userRepo.findAllUser();
	}

	@Override
	public List<Sport> getPreferredSportsByUser(String userId) {
		return userRepo.findPreferredSportsById(userId);
	}

	@Override
	@Transactional
	public void updateUsername(String username, String id) {
		userRepo.updateUsernameById(id, username);
	}

	@Override
	@Transactional
	public void updatePassword(String id, String password) {
		userRepo.updatePasswordById(id, encoder.encode(password));
	}

	@Override
	@Transactional
	public void updateImage(String id, MultipartFile image, ServletContext context) {
		var directory = "/resources/images/profile/";
		var filePath = context.getRealPath(directory + image.getOriginalFilename());
		try {
			Files.copy(image.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		} catch(Exception e) {
			e.printStackTrace();
		}
		userRepo.changeProfile(id, image.getOriginalFilename());
	}

	@Override
	@Transactional
	public void updatePhoneNumber(String id, String phoneNumber) {
		userRepo.updatePhoneNumberById(id, phoneNumber);
	}

	@Override
	public Optional<UserDto> findUserById(String id) {
		return userRepo.findUserById(id);
	}

	@Override
	public boolean passwordMatch(String id, String currentPassword) {
		var user = userRepo.findById(id).get();
		return encoder.matches(currentPassword, user.getPassword());
	}
	
}
