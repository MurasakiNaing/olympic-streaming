package com.olympic.model.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Service
public interface StreamingService {

	Resource getVideo(String title,  ServletContext context);
	
	String saveVideo(MultipartFile video, ServletContext context) throws IOException;
}
