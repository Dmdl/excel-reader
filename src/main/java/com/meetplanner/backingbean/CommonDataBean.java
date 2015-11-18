package com.meetplanner.backingbean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.meetplanner.dto.AgeGroupDTO;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.GroupDTO;
import com.meetplanner.service.FileUploadService;
import com.meetplanner.util.SpringApplicationContex;

public class CommonDataBean {

	private FileUploadService fileUploadService;
	private Map<Integer, String> allGroups = new HashMap<Integer, String>();
	private HashMap<Integer, String> ageList = new HashMap<Integer, String>();
	private HashMap<Integer, String> eventList = new HashMap<Integer, String>();

	public CommonDataBean() {
		fileUploadService = (FileUploadService) SpringApplicationContex.getBean("fileUploadService");
		List<GroupDTO> groups = fileUploadService.getAllGroups();
		if (groups.size() > 0) {
			for (GroupDTO e : groups) {
				allGroups.put(e.getId(), e.getName());
			}
		}
		List<AgeGroupDTO> ageGroups = fileUploadService.getAllAgeGroups();
		if (ageGroups.size() > 0) {
			for (AgeGroupDTO e : ageGroups) {
				System.out.println("id " + e.getId() + " val " + e.getAgeGroup());
				ageList.put(e.getId(), e.getAgeGroup());
			}
		}
		List<EventDTO> events = fileUploadService.getAllEvents();
		if (events.size() > 0) {
			for (EventDTO e : events) {
				System.out.println("id " + e.getId() + " val " + e.getEventName());
				eventList.put(e.getId(), e.getEventName());
			}
		}
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

	public HashMap<Integer, String> getAgeList() {
		return ageList;
	}

	public void setAgeList(HashMap<Integer, String> ageList) {
		this.ageList = ageList;
	}

	public HashMap<Integer, String> getEventList() {
		return eventList;
	}

	public void setEventList(HashMap<Integer, String> eventList) {
		this.eventList = eventList;
	}

}
