package com.olympic.model.entity.id;

import java.io.Serializable;

import com.olympic.model.entity.Channel;
import com.olympic.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewId implements Serializable{

	private static final long serialVersionUID = 1L;

	private User user;
	
	private Channel channel;
	
}
