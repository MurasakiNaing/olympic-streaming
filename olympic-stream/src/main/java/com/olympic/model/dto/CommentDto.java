package com.olympic.model.dto;

public interface CommentDto {

	Long getId();
	String getText();
	UserDto getUser();
	ChannelDto getChannel();
}
