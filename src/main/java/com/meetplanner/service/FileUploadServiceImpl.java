package com.meetplanner.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meetplanner.dao.CommonDao;
import com.meetplanner.dao.FileUploadDao;
import com.meetplanner.dto.AgeGroupDTO;
import com.meetplanner.dto.AllEvents;
import com.meetplanner.dto.Athlete;
//import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.GroupDTO;
import com.meetplanner.exception.GenricSqlException;

@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService,Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private FileUploadDao fileUploadDao;
	@Qualifier("commondDao")
	@Autowired
	private CommonDao commonDao;

	@Override
	public void saveFileDataToDb(AllEvents events) {
		/*List<List<Athlete>> athletes = events.getAthletes();
		for (List<Athlete> each : athletes) {
			for (Athlete a : each) {
				String name = a.getName();
				List<String> eventList = a.getEvents();
				System.out.println(name + " has " + eventList.size()
						+ " events...");			
			}
		}*/
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

	@Override
	@Transactional
	public void saveAthlete(List<Athlete> athletes,String ageGroup) throws GenricSqlException {
		for(Athlete athlete : athletes){
			String groupStringVal = athlete.getGroup();
			int groupId = commonDao.addGroup(groupStringVal);
			athlete.setGroup(String.valueOf(groupId));
			String gender = athlete.getGender();
			AgeGroupDTO age = new AgeGroupDTO();
			age.setAgeGroup(ageGroup);
			age.setFromAge(new Date());
			age.setToAge(new Date());
			age.setFromBibNumber(1);
			age.setToBibNumber(100);
			int ageGroupId = commonDao.addAgeGroupForUpload(age);
			athlete.setAgeGroup(String.valueOf(ageGroupId));
			
			age.setId(ageGroupId);
			
			List<EventDTO> events = athlete.getEvents();
			List<EventDTO> toAdd = new ArrayList<>();
			for(EventDTO event :events){
				event.getAgeGroups().add(age);
				int eventId = commonDao.addEventForUpload(event,gender);
				EventDTO athEvent = commonDao.getEvent(eventId);
				toAdd.add(athEvent);
			}
			athlete.setEvents(toAdd);
			commonDao.saveAthlete(athlete);
		}
	}

}
