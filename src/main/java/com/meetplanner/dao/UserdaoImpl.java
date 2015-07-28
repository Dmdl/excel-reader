package com.meetplanner.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.meetplanner.dao.mappers.UserRowMapper;
import com.meetplanner.dto.UserDTO;

public class UserdaoImpl extends JdbcDaoSupport implements Userdao {

	@Override
	public UserDTO findUser(String name) {
		UserDTO user = null;
		try {
			String sql = "SELECT * FROM users WHERE user_name=?";
			user = (UserDTO) getJdbcTemplate().queryForObject(sql,
					new Object[] { name }, new UserRowMapper());
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return user;
		}
	}

}
