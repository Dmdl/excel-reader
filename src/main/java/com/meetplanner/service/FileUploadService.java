package com.meetplanner.service;

import java.util.List;

import com.meetplanner.dto.AgeGroupDTO;
import com.meetplanner.dto.AllEvents;
import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.GroupDTO;
import com.meetplanner.exception.GenricSqlException;

public interface FileUploadService {

	public void saveFileDataToDb(AllEvents events);
	
	public List<GroupDTO> getAllGroups();
	
	public List<AgeGroupDTO> getAllAgeGroups();
	
	public List<EventDTO> getAllEvents();
	
	public void saveAthlete(List<Athlete> athletes,String ageGroup) throws GenricSqlException;
}
