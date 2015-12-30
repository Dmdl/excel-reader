package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.GroupDTO;

public class GroupRowMapper implements RowMapper<GroupDTO>{

	@Override
	public GroupDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		GroupDTO group = new GroupDTO();
		group.setId(rs.getInt("group_id"));
		group.setName(rs.getString("name"));
		return group;
	}

}
