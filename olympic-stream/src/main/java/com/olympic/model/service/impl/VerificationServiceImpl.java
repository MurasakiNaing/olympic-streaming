package com.olympic.model.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olympic.exception.VerificationException;
import com.olympic.model.entity.User;
import com.olympic.model.entity.Verification;
import com.olympic.model.repo.UserRepo;
import com.olympic.model.repo.VerificationRepo;
import com.olympic.model.service.VerificationService;

@Service
@Transactional
public class VerificationServiceImpl implements VerificationService {

	@Autowired
	private VerificationRepo verificationRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public void addVerification(User u, String code) {
		
		Verification verification = new Verification();
		verification.setCode(code);
		verification.setUser(u);
		verification.setExpireTime();
		
		verificationRepo.save(verification);
	}

	@Override
	public void verify(String code) {
		
		var verificationOptional = verificationRepo.findByCode(code);
		
		if(verificationOptional.isPresent() && verificationOptional.get().getExpireTime().isAfter(LocalDateTime.now())) {
			var verifcation = verificationOptional.get();
			var user = verifcation.getUser();
			userRepo.verifyUser(user.getId());
			verifcation.setExpireTime(LocalDateTime.now());
			verificationRepo.save(verifcation);
		} else {
			throw new VerificationException();
		}
	}
	
}
