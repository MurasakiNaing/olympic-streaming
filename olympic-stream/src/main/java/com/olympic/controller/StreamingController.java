package com.olympic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.olympic.model.service.StreamingService;

import jakarta.servlet.http.HttpSession;

@RestController
public class StreamingController {

	@Autowired
	private StreamingService streamService;
	
	@GetMapping(value = "/videos/{videoName}", produces = "video/mp4")
	public Resource getVideo(@PathVariable String videoName,  HttpSession session) {
		return streamService.getVideo(videoName, session.getServletContext());
	}
	
}
