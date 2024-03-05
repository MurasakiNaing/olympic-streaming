package com.olympic.model.dto;

import lombok.Data;

@Data
public class RegisterForm {

	private String accountName;
	
	private String email;
	
	private String password;
	
	private String phone;
	
	private Integer country;
	
}
