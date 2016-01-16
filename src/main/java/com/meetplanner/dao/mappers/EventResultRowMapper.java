package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.Athlete;

public class EventResultRowMapper implements RowMapper<Athlete>{

	@Override
	public Athlete mapRow(ResultSet rs, int rowNum) throws SQLException {
		Athlete athlete = new Athlete();
		athlete.setId(rs.getString("athlete_id"));
		athlete.setGroup(rs.getString("group_name"));
		athlete.setName(rs.getString("thlete_name"));
		athlete.setNic(rs.getString("nic"));
		athlete.setBibNumber(rs.getString("bib"));
		athlete.getEventResult().setPerformance(rs.getString("performance"));
		athlete.getEventResult().setPlace(rs.getInt("athlete_place"));
		return athlete;
	}

}
