package com.meetplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetplanner.dao.FileUploadDao;
import com.meetplanner.dto.AgeGroupDTO;
import com.meetplanner.dto.AllEvents;
import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.GroupDTO;

@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	private FileUploadDao fileUploadDao;

	@Override
	public void saveFileDataToDb(AllEvents events) {
		List<List<Athlete>> athletes = events.getAthletes();
		for (List<Athlete> each : athletes) {
			for (Athlete a : each) {
				String name = a.getName();
				List<String> eventList = a.getEvents();
				System.out.println(name + " has " + eventList.size()
						+ " events...");				
			}
		}
	}

	@Override
	public List<GroupDTO> getAllGroups() {
		return fileUploadDao.getAllGroups();
	}

	public FileUploadDao getFileUploadDao() {
		return fileUploadDao;
	}

	public void setFileUploadDao(FileUploadDao fileUploadDao) {
		this.fileUploadDao = fileUploadDao;
	}

	@Override
	public List<AgeGroupDTO> getAllAgeGroups() {
		return fileUploadDao.getAllAgeGroups();
	}

	@Override
	public List<EventDTO> getAllEvents() {
		return fileUploadDao.getAllEvents();
	}

}
