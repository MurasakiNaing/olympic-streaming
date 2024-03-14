package com.olympic.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.olympic.model.dto.SportDto;

@Service
public interface SportService {
	
	List<SportDto> findAll();
	
	Optional<SportDto> findOneById(Integer id);
	
}
