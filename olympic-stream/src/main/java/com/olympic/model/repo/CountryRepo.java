package com.olympic.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olympic.model.entity.Country;

public interface CountryRepo extends JpaRepository<Country, Integer> {

}
