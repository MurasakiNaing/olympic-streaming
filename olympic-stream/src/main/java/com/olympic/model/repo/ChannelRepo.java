package com.olympic.model.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.olympic.model.dto.ChannelDto;
import com.olympic.model.entity.Channel;

public interface ChannelRepo extends JpaRepository<Channel, Integer>{

//	@Query("select c from Channel c where c.sport.id = :id")
	List<ChannelDto> getChannelBySportId(@Param("id") Integer id);
	
	Optional<ChannelDto> findChannelById(Integer id);
}
