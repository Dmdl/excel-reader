package com.meetplanner.service;

import java.util.List;

import com.meetplanner.dto.GroupAthleteCountDTO;
import com.meetplanner.dto.GroupAthleteDTO;
import com.meetplanner.dto.ReportDTO;
import com.meetplanner.exception.GenricSqlException;

public interface ReportService {

	public List<ReportDTO> getAthleteData(String ageGroup, String event,String gender) throws GenricSqlException;
	
	public List<GroupAthleteCountDTO> getGroupWiseAthleteCount() throws GenricSqlException;
	
	public List<GroupAthleteDTO> getGroupAthletes() throws GenricSqlException;
}
