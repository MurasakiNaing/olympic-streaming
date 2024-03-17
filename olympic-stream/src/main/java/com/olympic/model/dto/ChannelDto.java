package com.olympic.model.dto;

import com.olympic.model.entity.Sport;

public interface ChannelDto {

	Integer getId();
	
	String getName();
	
	Sport getSport();
	
	String getDescription();
	
	String getVideoName();
	
}
