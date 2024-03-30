package com.olympic.model.dto;

import java.util.List;

public interface ChannelDto {

	Integer getId();
	
	String getName();
	
	SportDto getSport();
	
	String getDescription();
	
	String getVideoName();
	
	List<ViewDto> getViews();
}
