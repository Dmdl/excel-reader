package com.meetplanner.service;

import com.meetplanner.dto.AllEvents;

public interface FileUploadService {

	public void saveFileDataToDb(AllEvents events);
}
