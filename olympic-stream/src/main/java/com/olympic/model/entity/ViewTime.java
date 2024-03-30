package com.olympic.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ViewTime {

	@Id
	@UuidGenerator
	private String id;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "user_id", referencedColumnName = "user_id"),
		@JoinColumn(name = "channel_id", referencedColumnName = "channel_id")
	})
	private View view;
	
	private LocalDateTime viewedTime;
	
}
