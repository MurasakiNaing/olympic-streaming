package com.olympic.model.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olympic.model.dto.ViewTimeDto;
import com.olympic.model.entity.View;
import com.olympic.model.entity.ViewTime;
import com.olympic.model.repo.ViewTimeRepo;
import com.olympic.model.service.ViewTimeService;

@Service
public class ViewTimeServiceImpl implements ViewTimeService {

	@Autowired
	private ViewTimeRepo viewTimeRepo;
	
	@Override
	public void addViewTime(View view) {
		var viewTime = new ViewTime();
		viewTime.setView(view);
		viewTime.setViewedTime(LocalDateTime.now());
		viewTimeRepo.save(viewTime);
	}

	@Override
	public List<ViewTimeDto> getViewLog(String userId, Integer channelId) {
		return viewTimeRepo.findViewTimeByUserAndChannel(userId, channelId);
	}

}
