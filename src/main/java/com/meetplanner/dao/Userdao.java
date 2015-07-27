package com.meetplanner.dao;

import com.meetplanner.dto.UserDTO;

public interface Userdao {

	public UserDTO findUser(String name);
}
