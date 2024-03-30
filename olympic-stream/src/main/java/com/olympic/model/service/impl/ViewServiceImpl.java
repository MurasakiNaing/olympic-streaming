package com.olympic.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olympic.model.dto.ViewDto;
import com.olympic.model.entity.View;
import com.olympic.model.repo.ChannelRepo;
import com.olympic.model.repo.UserRepo;
import com.olympic.model.repo.ViewRepo;
import com.olympic.model.service.ViewService;

@Service
@Transactional(readOnly = true)
public class ViewServiceImpl implements ViewService {

	@Autowired
	private ViewRepo viewRepo;

	@Autowired
	private ChannelRepo channelRepo;

	@Autowired
	private UserRepo userRepo;

	@Override
	@Transactional
	public View getViewByIds(Integer channelId, String userId) {

		var channel = channelRepo.findById(channelId);
		var user = userRepo.findById(userId);
		var viewOptional = viewRepo.findViewByChannelIdAndUserId(userId, channelId);

		if (viewOptional.isEmpty()) {
			var view = new View();
			view.setUser(user.get());
			view.setChannel(channel.get());
			return viewRepo.save(view);
		}

		return viewOptional.get();
	}

	@Override
	public List<ViewDto> getUserViews(String userId) {
		return viewRepo.findViewByUserId(userId);
	}

	@Override
	public Integer countChannelView(Integer channelId) {
		return viewRepo.countViewForChannel(channelId);
	}

	@Override
	public Integer countSportView(Integer sportId) {
		return viewRepo.countViewForSport(sportId);
	}

}
