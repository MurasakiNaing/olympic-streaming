package com.olympic.model.service;

import org.springframework.stereotype.Service;

import com.olympic.exception.VerificationException;
import com.olympic.model.entity.User;

@Service
public interface VerificationService {

	void addVerification(User u, String code);
	
	void verify(String code) throws VerificationException;
}
