package com.olympic.model.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.olympic.model.dto.SportDto;
import com.olympic.model.entity.Sport;

public interface SportRepo extends JpaRepository<Sport, Integer>{
	
	@Query("select s from Sport s")
	List<SportDto> findAllSport();
	
	Optional<SportDto> findOneById(Integer id);
	
}
