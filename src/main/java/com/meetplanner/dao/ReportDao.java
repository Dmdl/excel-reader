package com.meetplanner.dao;

import java.util.List;

import com.meetplanner.dto.ReportDTO;
import com.meetplanner.exception.GenricSqlException;

public interface ReportDao {

	public List<ReportDTO> getAthleteData(String ageGroup,String event,String gender) throws GenricSqlException;
}
