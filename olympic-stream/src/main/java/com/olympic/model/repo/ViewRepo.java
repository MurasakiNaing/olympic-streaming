package com.olympic.model.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.olympic.model.dto.ViewDto;
import com.olympic.model.entity.View;
import com.olympic.model.entity.id.ViewId;

public interface ViewRepo extends JpaRepository<View, ViewId> {

	@Query("select v from View v where v.user.id = :userId and v.channel.id = :channelId")
	Optional<View> findViewByChannelIdAndUserId(@Param("userId") String userId, @Param("channelId") Integer channelId);
	
	@Query("select v from View v where v.user.id = :userId")
	List<ViewDto> findViewByUserId(@Param("userId") String userId);
	
	@Query("select COUNT(v) from View v where v.channel.id = :channelId")
	Integer countViewForChannel(@Param("channelId") Integer channelId);

	@Query("select COUNT(v) from View v where v.channel.sport.id = :sportId")
	Integer countViewForSport(@Param("sportId") Integer sportId);
}
