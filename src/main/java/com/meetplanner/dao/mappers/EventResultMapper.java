package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.ResultDTO;

public class EventResultMapper implements RowMapper<ResultDTO>{

	@Override
	public ResultDTO mapRow(ResultSet rs, int arg1) throws SQLException {
		ResultDTO result = new ResultDTO();
		result.setAthleteId(rs.getInt("athlete_id"));
		result.setEventId(rs.getInt("event_id"));
		result.setBibNo(rs.getString("bib"));
		result.setName(rs.getString("athlete_name"));
		result.setGroup(rs.getString("group_name"));
		result.setPerformance(String.valueOf(rs.getDouble("performance")));
		return result;
	}

}
