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
import com.olympic.model.service.MailService;
import com.olympic.model.service.SportService;
import com.olympic.model.service.UserService;
import com.olympic.model.service.ViewService;
import com.olympic.model.service.ViewTimeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private SportService sportService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ViewService viewService;
	
	@Autowired
	private ViewTimeService viewTimeService;
	
	@Autowired
	private MailService mailService;
	
	private static final String DEFAULT_PASSWORD = "Xyz123!@#";
	

	@GetMapping("/sport-channels/{id}")
	String channelEdit(@PathVariable("id") Integer sportId, ModelMap map) {
		var sport = sportService.findOneById(sportId);
		if(sport.isPresent()) {
			map.put("sport", sport.get());
			map.put("channels", channelService.getChannelBySport(sportId));
			map.put("views", viewService.countSportView(sportId));
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
	
	@GetMapping("/users")
	public String getUsers(ModelMap map) {
		map.put("users", userService.findAllUser());
		map.put("resetUsers", userService.getPasswordResetUsers());
		return "users";
	}
	
	@GetMapping("/user/{id}")
	public String userDetails(@PathVariable("id") String userId, ModelMap map) {
		var user = userService.findUserById(userId).get();
		map.put("user", user);
		map.put("sports", userService.getPreferredSportsByUser(userId));
		map.put("views", viewService.getUserViews(userId));
		return "user-details";
	}
	
	@GetMapping("/user-log/{userId}/{channelId}")
	public String userChannelLog( @PathVariable("userId") String userId ,@PathVariable("channelId") Integer channelId, ModelMap map) {
		map.put("channel", channelService.findChannelById(channelId).get());
		map.put("viewedTimes", viewTimeService.getViewLog(userId, channelId));
		return "user-log";
	}
	
	@PostMapping("/sports/channel-add/{id}")
	public String addChannelPost(@ModelAttribute ChannelForm form, @PathVariable Integer id, @RequestParam("video") MultipartFile video, ModelMap map, HttpSession session) throws IOException {
		form.setSportId(id);
		channelService.addChannel(form, video, session.getServletContext());
		return "redirect:/";
	}
	
	@PostMapping("/user/reset/{id}")
	public String resetPassword(@PathVariable String id, @RequestParam("email") String email) {
		userService.updatePassword(id, DEFAULT_PASSWORD);
		userService.removePasswordReset(email);
		mailService.sendPasswordResetMail(email, DEFAULT_PASSWORD);
		return "redirect:/admin/users";
	}
}
