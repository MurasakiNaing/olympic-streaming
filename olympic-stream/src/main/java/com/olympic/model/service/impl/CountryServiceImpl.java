package com.olympic.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olympic.model.entity.Country;
import com.olympic.model.repo.CountryRepo;
import com.olympic.model.service.CountryService;

@Service
@Transactional(readOnly = true)
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepo countryRepo;
	
	@Override
	public List<Country> getAllCountries() {
		return countryRepo.findAll();
	}

}
