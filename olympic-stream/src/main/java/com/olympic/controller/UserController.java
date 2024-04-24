package com.olympic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.olympic.model.dto.SportDto;
import com.olympic.model.dto.UserDto;
import com.olympic.model.entity.Sport;
import com.olympic.model.form.CommentForm;
import com.olympic.model.form.PreferredSportForm;
import com.olympic.model.service.ChannelService;
import com.olympic.model.service.CommentService;
import com.olympic.model.service.SportService;
import com.olympic.model.service.UserService;
import com.olympic.model.service.ViewService;
import com.olympic.model.service.ViewTimeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private SportService sportService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private ViewService viewService;

	@Autowired
	private ViewTimeService viewTimeService;

	@GetMapping({ "/user", "/admin", "/home" })
	String home(Authentication auth, HttpSession session, ModelMap map) {
		var isUser = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
		var noSession = session.getAttribute("user") == null;
		if (isUser) {
			var user = userService.findUserByEmail(auth.getName()).get();
			if(noSession) {				
				session.setAttribute("user", user);
			}
			map.put("prefChannels", channelService.getChannelsByPreferredSports(userService.getPreferredSportsByUser(user.getId())));
			
		}
		map.put("newChannels", channelService.getLatestChannels());
		map.put("trendingChannels", channelService.getTrendingChannels());
		System.out.println(channelService.getTrendingChannels().size());
		
		return "home";
	}

	@GetMapping("/sports")
	String sportsList(ModelMap map) {

		map.put("groupedSports", groupedSports());
		return "sports";
	}
	
	@GetMapping("/channels")
	String channels(ModelMap map) {
		map.put("channels", channelService.getAllChannels());
		return "channels";
	}

	@GetMapping("/sports/{id}")
	String sportDetails(@PathVariable Integer id, ModelMap map) {
		var sport = sportService.findOneById(id);

		if (sport.isPresent()) {
			map.put("sport", sport.get());
			map.put("channels", channelService.getChannelBySport(id));
			return "sport";
		}

		return "sport-not-found";
	}

	@GetMapping("/channel/{id}")
	String watchStream(@PathVariable("id") Integer channelId, ModelMap map, HttpSession session) {
		var channel = channelService.findChannelById(channelId);
		if (channel.isPresent()) {
			map.put("channel", channel.get());

			if (session.getAttribute("user") != null) {
				UserDto user = (UserDto) session.getAttribute("user");
				var view = viewService.getViewByIds(channelId, user.getId());
				viewTimeService.addViewTime(view);
			}

			return "watch-stream";
		}
		return "channel-not-found";
	}

	@GetMapping("/channel/{id}/comments")
	String getComments(@PathVariable("id") Integer channelId, ModelMap map) {

		map.put("comments", commentService.getCommentByChannelId(channelId));
		map.put("form", new CommentForm());
		map.put("channelId", channelId);

		return "comments";
	}
	
	@GetMapping("/search")
	String search(@RequestParam("keyword") String keyword, ModelMap map) {
		
		map.put("sports", sportService.findByKeyword(keyword));
		map.put("channels", channelService.findChannelByKeyword(keyword));
		
		return "search-results";
	}

	@PostMapping("/channel/{id}/comments")
	String saveComment(@PathVariable("id") Integer channelId, @ModelAttribute CommentForm form, HttpSession session) {

		form.setChannelId(channelId);
		UserDto user = (UserDto) session.getAttribute("user");
		commentService.addComment(form, user);

		return "redirect:/channel/" + channelId + "/comments";
	}

	@GetMapping("/user/{id}")
	String userDetails(@PathVariable("id") String userId, ModelMap map) {
		var user = userService.findUserById(userId).get();
		map.put("user", user);
		map.put("sports", userService.getPreferredSportsByUser(userId));
		return "user-details";
	}

	@PostMapping("/user/{id}/username")
	String changeUsername(@RequestParam("username") String username, @PathVariable("id") String userId, HttpSession session) {
		UserDto user = (UserDto)session.getAttribute("user");
		if(!user.getName().equals(username)) {			
			userService.updateUsername(username, userId);
			user = userService.findUserById(userId).get();
			session.setAttribute("user", user);
		}
		return "redirect:/user/" + userId;
	}
	
	@PostMapping("/user/{id}/password")
	String updatePassword(@RequestParam("currentPassword") String currentPass, @RequestParam("newPassword") String newPass, @RequestParam("confirmPassword") String conPass, @PathVariable("id") String userId, RedirectAttributes redirectAttributes) {
		
		var map = new HashMap<String, String>();
		
		if(!validatePassword(currentPass, newPass, conPass, map, userId)) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
	            redirectAttributes.addFlashAttribute(entry.getKey(), entry.getValue());
	        }
			return "redirect:/user/" + userId + "?error=true";
		}
		
		userService.updatePassword(userId, newPass);
		return "redirect:/logout";
	}
	
	@PostMapping("/user/{id}/phone")
	String updatePhoneNumber(@RequestParam("phone") String phone, @PathVariable("id") String userId, HttpSession session) {
		UserDto user = (UserDto)session.getAttribute("user");
		if(!user.getPhone().equals(phone)) {			
			userService.updatePhoneNumber(userId, phone);
			user = userService.findUserById(userId).get();
			session.setAttribute("user", user);
		}
		return "redirect:/user/" + userId;
	}
	
	@PostMapping("/user/{id}/image")
	String changeProfile(@PathVariable("id") String userId, @RequestParam("image") MultipartFile image, HttpSession session) {
		
		userService.updateImage(userId, image, session.getServletContext());
		var user = userService.findUserById(userId).get();
		session.setAttribute("user", user);
		return "redirect:/user/" + userId;
	}
	
	@GetMapping("/user/{id}/preferred-sports")
	String editPreferredSportsPage(@PathVariable("id") String userId, ModelMap map) {
		map.put("sportsList", sportService.findAll());
		List<Integer> preferredSports = userService.getPreferredSportsByUser(userId).stream()
																			.map(Sport::getId).collect(Collectors.toList());
		var form = new PreferredSportForm();
		form.setSports(preferredSports);
		map.put("sportsForm", form);
		map.put("url", "/user/%s/preferred-sports".formatted(userId));
		
		return "preferred-sports";
	}
	
	@PostMapping("/user/{id}/preferred-sports")
	String editPreferredSports(@ModelAttribute("sportsForm") PreferredSportForm form ,@PathVariable("id") String userId) {
		
		userService.setPreferredSports(form, userId);
		
		return "redirect:/user/%s".formatted(userId);
	}

	private Map<String, List<SportDto>> groupedSports() {
		var sports = sportService.findAll();

		Map<String, List<SportDto>> groupedSports = new HashMap<>();

		for (SportDto sport : sports) {
			String firstChar = String.valueOf(sport.getName().charAt(0));
			if (!Character.isLetter(sport.getName().charAt(0))) {
				firstChar = "#";
			}
			groupedSports.computeIfAbsent(firstChar, fun -> new ArrayList<>()).add(sport);
		}
		return groupedSports;
	}

	private boolean validatePassword(String currentPass, String newPass, String conPass, Map<String, String> map, String userId) {
		if(!userService.passwordMatch(userId, currentPass)) {
			map.put("currentPasswordError", "Current Password Does not match.");
			return false;
		}
		
		if(currentPass.equals(newPass)) {
			map.put("newPasswordError", "Current Password and New Password cannot be the same.");
			return false;
		}
		
		if(!newPass.equals(conPass)) {
			map.put("confirmPasswordError", "Passwords Does not matches.");
			return false;
		}
		
		return true;
	}
	
}
