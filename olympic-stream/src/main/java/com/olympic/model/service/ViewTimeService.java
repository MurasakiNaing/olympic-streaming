package com.olympic.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.olympic.model.dto.ViewTimeDto;
import com.olympic.model.entity.View;

@Service
public interface ViewTimeService {

	void addViewTime(View view);

	List<ViewTimeDto> getViewLog(String userId, Integer channelId);

}
