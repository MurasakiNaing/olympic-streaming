package com.olympic.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.olympic.model.form.ChannelForm;
import com.olympic.model.service.ChannelService;
import com.olympic.model.service.SportService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private SportService sportService;

	@GetMapping("/sport-channels/{id}")
	String channelEdit(@PathVariable("id") Integer sportId, ModelMap map) {
		var sport = sportService.findOneById(sportId);
		if(sport.isPresent()) {
			map.put("sport", sport.get());
			map.put("channels", channelService.getChannelBySport(sportId));
			return "sport-channels";
		}
		return "sport-not-found";
	}
	
	@GetMapping("/sports/add-channel/{id}")
	public String addChannel(@PathVariable Integer id, ModelMap map) {
		var sport = sportService.findOneById(id);
		if(sport.isPresent()) {
			map.put("sport", sport.get());
			map.put("channelForm", new ChannelForm());
			return "add-channel";
		}
		return "sport-not-found";
	}
	
	@PostMapping("/sports/channel-add/{id}")
	public String addChannelPost(@ModelAttribute ChannelForm form, @PathVariable Integer id, @RequestParam("video") MultipartFile video, ModelMap map, HttpSession session) throws IOException {
		System.out.println(id);
		form.setSportId(id);
		channelService.addChannel(form, video, session.getServletContext());
		
		return "redirect:/";
	}
	
	
}
