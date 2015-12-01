package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.meetplanner.dto.AgeGroupDTO;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.GroupDTO;
import com.meetplanner.dto.RoleDTO;
import com.meetplanner.service.FileUploadService;
import com.meetplanner.service.UserService;
import com.meetplanner.util.SpringApplicationContex;

public class CommonDataBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private FileUploadService fileUploadService;
	private Map<Integer, String> allGroups = new HashMap<Integer, String>();
	private HashMap<Integer, String> ageList = new HashMap<Integer, String>();
	private HashMap<Integer, String> eventList = new HashMap<Integer, String>();
	private HashMap<Integer, String> userRoles = new HashMap<Integer, String>();
	private UserService userService;

	public CommonDataBean() {
		fileUploadService = (FileUploadService) SpringApplicationContex.getBean("fileUploadService");		
		userService = (UserService)SpringApplicationContex.getBean("userService");
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
		List<RoleDTO> roles = userService.getUserRoles();
		if (roles.size() > 0) {
			for (RoleDTO e : roles) {
				System.out.println("id " + e.getId() + " val " + e.getName());
				eventList.put(e.getId(), e.getName());
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

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public HashMap<Integer, String> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(HashMap<Integer, String> userRoles) {
		this.userRoles = userRoles;
	}

}
