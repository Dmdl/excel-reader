package com.meetplanner.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.meetplanner.dto.UserDTO;

public class UserdaoImpl extends JdbcDaoSupport implements Userdao{

	@Override
	public UserDTO findUser(String name) {
		return  null;
	}

}
