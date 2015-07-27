package com.meetplanner.service;

import com.meetplanner.dto.UserDTO;

public interface UserBo {

	public String getMessage();
	
	public void insert();
	
	public UserDTO findUser(String userName);
}
