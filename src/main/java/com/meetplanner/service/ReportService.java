package com.meetplanner.service;

import java.util.List;

import com.meetplanner.dto.GroupAthleteCountDTO;
import com.meetplanner.dto.GroupAthleteDTO;
import com.meetplanner.dto.PlayerEventDTO;
import com.meetplanner.dto.PlayerListDTO;
import com.meetplanner.dto.ReportDTO;
import com.meetplanner.exception.GenricSqlException;

public interface ReportService {

	public List<ReportDTO> getAthleteData(String ageGroup, String event,String gender) throws GenricSqlException;
	
	public List<GroupAthleteCountDTO> getGroupWiseAthleteCount() throws GenricSqlException;
	
	public List<GroupAthleteDTO> getGroupAthletes() throws GenricSqlException;
	
	public List<PlayerListDTO> getGroupWiseAthlete(int groupId, int ageGroupId,String gender) throws GenricSqlException;
	
	public List<PlayerEventDTO> getEventWiseAthletes(int eventId,int ageGroupId, String gender);

	public List<PlayerEventDTO> getEventWiseAthletesWithPlace(int selectedEvent, int selectedAgeGroup, String gender);
}
