package com.olympic.model.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.olympic.model.dto.ChannelDto;
import com.olympic.model.entity.Channel;
import com.olympic.model.form.ChannelForm;
import com.olympic.model.repo.ChannelRepo;
import com.olympic.model.repo.SportRepo;
import com.olympic.model.service.ChannelService;
import com.olympic.model.service.StreamingService;

import jakarta.servlet.ServletContext;

@Service
@Transactional(readOnly = true)
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelRepo channelRepo;
	
	@Autowired
	private SportRepo sportRepo;
	
	@Autowired
	private StreamingService streamService;
	
	@Override
	public List<ChannelDto> getChannelBySport(Integer id) {
		return channelRepo.getChannelBySportId(id);
	}

	@Override
	@Transactional
	public void addChannel(ChannelForm form, MultipartFile video, ServletContext context) throws IOException {
		var channel = new Channel();
		channel.setName(form.getName());
		channel.setDescription(form.getDescription());
		channel.setSport(sportRepo.findById(form.getSportId()).get());
		channel.setVideoName(streamService.saveVideo(video, context));
		channelRepo.save(channel);
	}

	@Override
	public Optional<ChannelDto> findChannelById(Integer id) {
		return channelRepo.findChannelById(id);
	}

}
