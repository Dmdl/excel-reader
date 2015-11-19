package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.Athlete;

public class AthleteRowMapper implements RowMapper<Athlete>{

	@Override
	public Athlete mapRow(ResultSet rs, int rowNum) throws SQLException {
		Athlete athlete = new Athlete();
		athlete.setId(String.valueOf(rs.getInt("id")));
		athlete.setName(rs.getString("athlete_name"));
		athlete.setBibNumber(rs.getString("bib"));
		athlete.setGroup(rs.getString("group_name"));
		athlete.setGroupId(rs.getInt("group_id"));
		return athlete;
	}

}
