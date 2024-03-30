package com.olympic.model.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.olympic.model.dto.ChannelDto;
import com.olympic.model.entity.Channel;
import com.olympic.model.entity.Sport;

public interface ChannelRepo extends JpaRepository<Channel, Integer>{

	@Query("select c from Channel c where c.sport.id = :id")
	List<ChannelDto> getChannelBySportId(@Param("id") Integer id);
	
	@Query("select c from Channel c order by c.id desc limit 3")
	List<ChannelDto> getTop3OrderByIdDesc();
	
	@Query("select c from Channel c")
	List<ChannelDto> getAllChannel();
	
	@Query("select c from Channel c where c.sport in :sports order by c.id desc limit 3")
	List<ChannelDto> findChannelByPreferredSport(List<Sport> sports);
	
	Optional<ChannelDto> findChannelById(Integer id);
}
