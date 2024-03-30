package com.olympic.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.olympic.model.dto.ChannelDto;
import com.olympic.model.entity.Sport;
import com.olympic.model.form.ChannelForm;

import jakarta.servlet.ServletContext;

@Service
public interface ChannelService {

	List<ChannelDto> getChannelBySport(Integer id);
	
	void addChannel(ChannelForm form, MultipartFile video, ServletContext context) throws IOException;
	
	Optional<ChannelDto> findChannelById(Integer id);
	
	List<ChannelDto> getAllChannels();
	
	List<ChannelDto> getLatestChannels();
	
	List<ChannelDto> getChannelsByPreferredSports(List<Sport> sports);
	
}
