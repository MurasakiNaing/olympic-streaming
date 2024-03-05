package com.olympic.event.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.olympic.event.RegisterEvent;
import com.olympic.model.entity.User;
import com.olympic.model.service.MailService;
import com.olympic.model.service.VerificationService;

@Component
public class RegisterEventListener implements ApplicationListener<RegisterEvent> {

	@Autowired
	private VerificationService verificationService;
	
	@Autowired
	private MailService mailService;
	
	@Override
	public void onApplicationEvent(RegisterEvent event) {
		sendVerification(event.getUser());
	}

	
	private void sendVerification(User user) {
		String code = UUID.randomUUID().toString();
		verificationService.addVerification(user, code);
		mailService.sendMail(user.getEmail(), code);
	}
	
	
}
