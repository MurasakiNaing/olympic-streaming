package com.olympic.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.olympic.model.dto.ViewTimeDto;
import com.olympic.model.entity.ViewTime;

public interface ViewTimeRepo extends JpaRepository<ViewTime, String> {

	@Query("select vt from ViewTime vt where vt.view.user.id = :userId and vt.view.channel.id = :channelId")
	List<ViewTimeDto> findViewTimeByUserAndChannel(@Param("userId") String userId, @Param("channelId") Integer channelId);
	
}
