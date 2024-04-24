package com.olympic.model.dto;

public interface TrendingChannelDto {

	Integer getId();

	String getName();

	TrendingSportDto getSport();
	
	String getDescription();
}
