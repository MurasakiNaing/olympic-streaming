package com.olympic.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olympic.model.entity.User;
import com.olympic.model.repo.UserRepo;
import com.olympic.model.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo repo;
	
	@Transactional
	public void save(User user) {
		repo.save(user);
	}
	
}
