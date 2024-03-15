package com.olympic.model.dto;

import java.util.List;

public interface SportDto {

	Integer getId();
	String getName();
	String getImageName();
	String getDescription();
	List<ChannelDto> getChannels();
	
	default String getImagePath() {
		return "/resources/images/sports/" + getImageName() + ".svg";
	}
	
}
