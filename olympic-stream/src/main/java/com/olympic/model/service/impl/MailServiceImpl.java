package com.olympic.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.olympic.model.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendVerificationMail(String email, String code) {

		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("funolympicpayris@gmail.com");
			helper.setTo(email);
			helper.setSubject("Account Verification");
			message.setText("""
					Please Verify your account by clicking on this link.
					http://localhost:8080/auth/verification/""" + code);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendPasswordResetMail(String email, String password) {

		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("funolympicpayris@gmail.com");
			helper.setTo(email);
			helper.setSubject("Password Reset");
			message.setText("""
					Your password has been reseted.
					Please log into your account using password of %s.
					Please reset your password after logging into your account.""".formatted(password));
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
