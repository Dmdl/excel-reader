package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.GroupAthleteCountDTO;

public class GroupWiseAthleteCountMapper implements RowMapper<GroupAthleteCountDTO>{

	@Override
	public GroupAthleteCountDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		GroupAthleteCountDTO gr = new GroupAthleteCountDTO();
		gr.setGroupName(rs.getString("name"));
		gr.setAthleteCount(rs.getInt("total"));
		return gr;
	}

}
