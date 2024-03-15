package com.olympic.model.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.olympic.model.service.StreamingService;

import jakarta.servlet.ServletContext;

@Service
public class StreamingServiceImpl implements StreamingService {
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Override
	public Resource getVideo(String title,  ServletContext context) {
		var directory = "/resources/videos/";
		var filePath = context.getContextPath() + directory + title + ".mp4";
		return resourceLoader.getResource(filePath);
	}

	@Override
	public String saveVideo(MultipartFile video, ServletContext context) throws IOException {
		var directory = "/resources/videos/";
		var filePath = context.getRealPath(directory+video.getOriginalFilename());
		try {
			Files.copy(video.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return FilenameUtils.removeExtension(video.getOriginalFilename());
	}

}
