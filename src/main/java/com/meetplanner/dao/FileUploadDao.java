package com.meetplanner.dao;

import java.util.List;

import com.meetplanner.dto.AgeGroupDTO;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.GroupDTO;

public interface FileUploadDao {

	public List<GroupDTO> getAllGroups();
	
	public int getEventId(String eventName);

	public List<AgeGroupDTO> getAllAgeGroups();

	public List<EventDTO> getAllEvents();
}
