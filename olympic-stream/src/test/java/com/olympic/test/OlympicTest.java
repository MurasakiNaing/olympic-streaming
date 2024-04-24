package com.olympic.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import com.olympic.config.DatabaseConfig;
import com.olympic.config.SecurityConfig;
import com.olympic.config.WebConfig;
import com.olympic.model.service.SportService;

@SpringJUnitWebConfig(classes = {DatabaseConfig.class, WebConfig.class, SecurityConfig.class})
public class OlympicTest {

	@Autowired
	private SportService sportService;
	
	@Test
	void findSportByKeyword() {
		var list = sportService.findByKeyword("A");
		assertEquals(4, list.size());
	}
	
}
