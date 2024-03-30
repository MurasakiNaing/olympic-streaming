package com.olympic.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.olympic.model.dto.ViewDto;
import com.olympic.model.entity.View;

@Service
public interface ViewService {

	View getViewByIds(Integer channelId, String userId);
	
	List<ViewDto> getUserViews(String userId);
	
	Integer countChannelView(Integer channelId);
	
	Integer countSportView(Integer sportId);
}
