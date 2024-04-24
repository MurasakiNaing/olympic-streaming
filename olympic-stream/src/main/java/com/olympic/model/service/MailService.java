package com.olympic.model.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {

	void sendVerificationMail(String email, String code);
	
	void sendPasswordResetMail(String email, String password);
	
}
