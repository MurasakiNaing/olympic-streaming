package com.olympic.model.dto;

import java.util.List;

import com.olympic.model.entity.Sport;

public interface UserDto {

	String getId();
	CountryDto getCountry();
	List<Sport> getPreferredSports();
	String getEmail();
	String getImageName();
	String getName();
	String getPhone();
	String getPasswordReset();
	
	default String getImagePath() {
		return "/resources/images/profile/" + getImageName();
	}
}
