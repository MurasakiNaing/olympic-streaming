package com.olympic.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olympic.model.entity.User;

public interface UserRepo extends JpaRepository<User, String>{

	Optional<User> findByemail(String email);
	
}
