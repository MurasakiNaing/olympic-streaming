package com.olympic.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.olympic.model.dto.CommentDto;
import com.olympic.model.dto.UserDto;
import com.olympic.model.form.CommentForm;

@Service
public interface CommentService {

	List<CommentDto> getCommentByChannelId(Integer channelId);
	
	void addComment(CommentForm form, UserDto user);
	
}
