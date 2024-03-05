package com.olympic.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olympic.model.entity.Sport;

public interface SportRepo extends JpaRepository<Sport, Integer>{

}
