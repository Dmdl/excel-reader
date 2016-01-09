package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.AgeGroupDTO;

public class AgeGroupRowMapper implements RowMapper<AgeGroupDTO>{

	@Override
	public AgeGroupDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		AgeGroupDTO age = new AgeGroupDTO();
		age.setId(rs.getInt("id"));
		age.setAgeGroup(rs.getString("age_group"));
		age.setFromAge(rs.getDate("from_date"));
		age.setToAge(rs.getDate("to_date"));
		age.setFromBibNumber(rs.getInt("bib_from"));
		age.setToBibNumber(rs.getInt("bib_to"));
		return age;
	}

}
