package com.olympic.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.olympic.model.dto.UserDto;
import com.olympic.model.entity.User;

public interface UserRepo extends JpaRepository<User, String>{

	Optional<User> findByemail(String email);
	
	Optional<UserDto> findUserByemail(String email);
	
	Optional<UserDto> findUserById(String id);
	
	@Modifying
	@Query("update User u set u.activated = true where u.id = :id")
	void verifyUser(@Param("id") String id);
	
}
