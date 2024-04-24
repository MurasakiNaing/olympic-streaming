package com.olympic.model.dto;

public interface TrendingSportDto {

	Integer getId();
	String getImageName();
	
	default String getImagePath() {
		return "/resources/images/sports/" + getImageName() + ".svg";
	}
}
