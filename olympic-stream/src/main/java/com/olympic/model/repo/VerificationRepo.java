package com.olympic.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olympic.model.entity.Verification;

public interface VerificationRepo extends JpaRepository<Verification, Long> {
	
	Optional<Verification> findByCode(String code);

}
