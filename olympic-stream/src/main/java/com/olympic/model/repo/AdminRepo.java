package com.olympic.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olympic.model.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long> {
	
	Optional<Admin> findByName(String name);
	
}
