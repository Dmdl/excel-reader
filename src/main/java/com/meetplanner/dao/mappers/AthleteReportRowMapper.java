package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.PlayerListDTO;

public class AthleteReportRowMapper implements RowMapper<PlayerListDTO>{

	@Override
	public PlayerListDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PlayerListDTO ply = new PlayerListDTO();
		ply.setId(rs.getInt("athlete_id"));
		ply.setAthleteName(rs.getString("athlete_name"));
		ply.setDateOfBirth(rs.getDate("date_of_birth"));
		ply.setBibNumber(rs.getString("bib"));
		ply.setGender(rs.getString("gender"));
		ply.setGroupName(rs.getString("group_name"));
		ply.setAgeGroup(rs.getString("age_group"));
		return ply;
	}

}
