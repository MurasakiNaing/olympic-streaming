package com.olympic.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.olympic.model.entity.Country;

@Service
public interface CountryService {

	List<Country> getAllCountries();
	
}
