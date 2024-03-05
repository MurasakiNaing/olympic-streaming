package com.olympic.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olympic.model.entity.Channel;

public interface ChannelRepo extends JpaRepository<Channel, Integer>{

}
