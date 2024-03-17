package com.olympic.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olympic.model.dto.CommentDto;
import com.olympic.model.dto.UserDto;
import com.olympic.model.entity.Comment;
import com.olympic.model.form.CommentForm;
import com.olympic.model.repo.ChannelRepo;
import com.olympic.model.repo.CommentRepo;
import com.olympic.model.repo.UserRepo;
import com.olympic.model.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ChannelRepo channelRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public List<CommentDto> getCommentByChannelId(Integer channelId) {
		return commentRepo.getCommentByChannelId(channelId);
	}

	@Override
	public void addComment(CommentForm form, UserDto user) {
		var comment = new Comment();
		comment.setUser(userRepo.findById(user.getId()).get());
		comment.setChannel(channelRepo.findById(form.getChannelId()).get());
		comment.setText(form.getText());
		commentRepo.save(comment);
	}

}
