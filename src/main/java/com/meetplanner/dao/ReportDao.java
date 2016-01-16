package com.meetplanner.dao;

import java.util.List;

import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.GroupAthleteCountDTO;
import com.meetplanner.dto.GroupDTO;
import com.meetplanner.dto.PlayerEventDTO;
import com.meetplanner.dto.PlayerListDTO;
import com.meetplanner.dto.ReportDTO;
import com.meetplanner.exception.GenricSqlException;

public interface ReportDao {

	public List<ReportDTO> getAthleteData(String ageGroup,String event,String gender) throws GenricSqlException;
	
	public List<GroupAthleteCountDTO> getGroupWiseAthleteCount() throws GenricSqlException;
	
	public List<GroupDTO> getUniqeAthleteGroups() throws GenricSqlException;
	
	public List<Athlete> searchAthleteByGroup(int groupId);
	
	public List<PlayerListDTO> getGroupWiseAthlete(int groupId,int ageGroupId,String gender);
	
	public List<PlayerEventDTO> getEventWiseAthletes(int eventId,int ageGroupId,String gender);
}
