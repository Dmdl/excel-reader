package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.ReportDTO;

public class ReportRowMapper implements RowMapper<ReportDTO>{

	@Override
	public ReportDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReportDTO report = new ReportDTO();
		report.setId(rs.getInt("id"));
		report.setAthleteName(rs.getString("name"));
		report.setAgeGroup(rs.getString("age_group"));
		report.setBibNumber(rs.getString("bib"));
		report.setDateOfBirth(rs.getDate("date_of_birth"));
		report.setGender(rs.getString("gender"));
		report.setNic(rs.getString("nic"));
		report.setPerformance(rs.getString("performance"));
		return report;
	}

}
