package com.olympic.event;

import org.springframework.context.ApplicationEvent;

import com.olympic.model.entity.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RegisterEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private User user;

	public RegisterEvent(User user) {
		super(user);
		this.user = user;
	}

}
