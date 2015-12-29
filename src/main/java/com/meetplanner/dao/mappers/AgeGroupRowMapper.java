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
		return age;
	}

}
