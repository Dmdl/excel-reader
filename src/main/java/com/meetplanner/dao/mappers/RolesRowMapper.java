package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.RoleDTO;

public class RolesRowMapper implements RowMapper<RoleDTO>{

	@Override
	public RoleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		RoleDTO role = new RoleDTO();
		role.setId(rs.getInt("id"));
		role.setName(rs.getString("name"));
		return role;
	}

}
