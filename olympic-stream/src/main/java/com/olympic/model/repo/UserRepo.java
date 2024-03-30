package com.olympic.model.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.olympic.model.dto.UserDto;
import com.olympic.model.entity.Sport;
import com.olympic.model.entity.User;

public interface UserRepo extends JpaRepository<User, String>{

	Optional<User> findByemail(String email);
	
	Optional<UserDto> findUserByemail(String email);
	
	Optional<UserDto> findUserById(String id);
	
	@Query("select u.preferredSports from User u where u.id = :id")
	List<Sport> findPreferredSportsById(@Param("id") String userId);
	
	@Query("select u from User u")
	List<UserDto> findAllUser();
	
	@Modifying
	@Query("update User u set u.activated = true where u.id = :id")
	void verifyUser(@Param("id") String id);
	
	@Modifying
	@Query("update User u set u.name = :name where u.id = :id")
	void updateUsernameById(@Param("id") String id, @Param("name") String name);
	
	@Modifying
	@Query("update User u set u.password = :password where u.id = :id")
	void updatePasswordById(@Param("id") String id, @Param("password") String password);
	
	@Modifying
	@Query("update User u set u.imageName = :imageName where u.id = :id")
	void changeProfile(@Param("id") String id, @Param("imageName") String imageName);
	
	@Modifying
	@Query("update User u set u.phone = :phone where u.id = :id")
	void updatePhoneNumberById(@Param("id") String id, @Param("phone") String phoneNumber);
}
