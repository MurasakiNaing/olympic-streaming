package com.olympic.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olympic.model.dto.SportDto;
import com.olympic.model.repo.SportRepo;
import com.olympic.model.service.SportService;

@Service
@Transactional(readOnly = true)
public class SportServiceImpl implements SportService{

	@Autowired
	private SportRepo repo;

	@Override
	public List<SportDto> findAll() {
		return repo.findAllSport();
	}

	@Override
	public Optional<SportDto> findOneById(Integer id) {
		return repo.findOneById(id);
	}
	
	
	
}
