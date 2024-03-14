package com.olympic.model.dto;

public interface SportDto {

	Integer getId();
	String getName();
	String getImageName();
	String getDescription();
	
	default String getImagePath() {
		return "/resources/images/sports/" + getImageName() + ".svg";
	}
	
}
