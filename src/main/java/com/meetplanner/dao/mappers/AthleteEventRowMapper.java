package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.PlayerEventDTO;

public class AthleteEventRowMapper implements RowMapper<PlayerEventDTO>{

	@Override
	public PlayerEventDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PlayerEventDTO pla = new PlayerEventDTO();
		pla.setId(rs.getInt("athlete_id"));
		pla.setBibNumber(rs.getString("bib_number"));
		pla.setAthleteName(rs.getString("athlete_name"));
		pla.setDateOfBirth(rs.getDate("date_of_birth"));
		pla.setGroupName(rs.getString("group_name"));
		pla.setPerformance(rs.getDouble("performance"));
		pla.setPlace(rs.getInt("place"));
		return pla;
	}

}
