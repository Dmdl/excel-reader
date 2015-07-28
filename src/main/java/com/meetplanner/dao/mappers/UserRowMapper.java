package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.UserDTO;

/**
 * @author lakmal.d
 */

public class UserRowMapper implements RowMapper<UserDTO> {

	@Override
	public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserDTO user = new UserDTO();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("user_name"));
		user.setPassword(rs.getString("password"));
		return user;
	}

}
