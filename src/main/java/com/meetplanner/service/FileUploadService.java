package com.meetplanner.service;

import java.util.List;

import com.meetplanner.dto.AllEvents;
import com.meetplanner.dto.GroupDTO;

public interface FileUploadService {

	public void saveFileDataToDb(AllEvents events);
	
	public List<GroupDTO> getAllGroups();
}
