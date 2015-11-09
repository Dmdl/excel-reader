package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.meetplanner.dto.AgeGroupDTO;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.GroupDTO;
import com.meetplanner.service.FileUploadService;
import com.meetplanner.util.SpringApplicationContex;

public class AddAthlete implements Serializable {

	private static final long serialVersionUID = 1L;
	private HashMap<Integer, String> ageList = new HashMap<Integer, String>();
	private HashMap<Integer, String> eventList = new HashMap<Integer, String>();
	private String athleteName;
	private String nic;
	private FileUploadService fileUploadService;
	private Map<Integer, String> allGroups = new HashMap<Integer, String>();
	private String gender = null;
	private String selectedGroup = null;
	private String selectedAgeGroup = null;
	private String selectedEvent = null;

	public AddAthlete() {
		try {
			fileUploadService = (FileUploadService) SpringApplicationContex
					.getBean("fileUploadService");
			List<GroupDTO> groups = fileUploadService.getAllGroups();
			if (groups.size() > 0) {
				for (GroupDTO e : groups) {
					allGroups.put(e.getId(), e.getName());
				}
			}
			List<AgeGroupDTO> ageGroups = fileUploadService.getAllAgeGroups();
			if (ageGroups.size() > 0) {
				for (AgeGroupDTO e : ageGroups) {
					ageList.put(e.getId(), e.getAgeGroup());
				}
			}
			List<EventDTO> events = fileUploadService.getAllEvents();
			if (events.size() > 0) {
				for (EventDTO e : events) {
					eventList.put(e.getId(), e.getEventName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveAthlete(){
		System.out.println("gender "+gender+" age group "+selectedAgeGroup+" event "+selectedEvent+" group "+selectedGroup);
	}
	
	public HashMap<Integer, String> getAgeList() {
		return ageList;
	}

	public void setAgeList(HashMap<Integer, String> ageList) {
		this.ageList = ageList;
	}

	public String getAthleteName() {
		return athleteName;
	}

	public void setAthleteName(String athleteName) {
		this.athleteName = athleteName;
	}

	public HashMap<Integer, String> getEventList() {
		return eventList;
	}

	public void setEventList(HashMap<Integer, String> eventList) {
		this.eventList = eventList;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public Map<Integer, String> getAllGroups() {
		return allGroups;
	}

	public void setAllGroups(Map<Integer, String> allGroups) {
		this.allGroups = allGroups;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(String selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public String getSelectedAgeGroup() {
		return selectedAgeGroup;
	}

	public void setSelectedAgeGroup(String selectedAgeGroup) {
		this.selectedAgeGroup = selectedAgeGroup;
	}

	public String getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(String selectedEvent) {
		this.selectedEvent = selectedEvent;
	}
	
}
