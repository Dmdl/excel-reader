package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.Athlete;

public class UpdateAthleteRowMapper implements RowMapper<Athlete> {

	@Override
	public Athlete mapRow(ResultSet rs, int arg1) throws SQLException {
		Athlete athlete = new Athlete();
		athlete.setId(rs.getString("athlete_id"));
		athlete.setName(rs.getString("athlete_name"));
		athlete.setDateOfBirth(rs.getDate("date_of_birth"));
		athlete.setGroupId(rs.getInt("group_id"));
		athlete.setNic(rs.getString("nic"));
		athlete.setGender(rs.getString("gender"));
		athlete.setAgeGroup(rs.getString("age_group_id"));
		athlete.setBibNumber(rs.getString("bib"));
		athlete.setGroup(rs.getString("group_name"));
		athlete.setAgeGroupString(rs.getString("age_group"));
		return athlete;
	}

}
