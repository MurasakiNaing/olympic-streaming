package com.olympic.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olympic.model.dto.CommentDto;
import com.olympic.model.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {

	List<CommentDto> getCommentByChannelId(Integer channelId);
	
}
