package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.meetplanner.dto.AgeGroupDTO;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.GroupDTO;
import com.meetplanner.dto.PlayerEventDTO;
import com.meetplanner.dto.PlayerListDTO;
import com.meetplanner.service.CommonService;
import com.meetplanner.service.FileUploadService;
import com.meetplanner.service.ReportService;
import com.meetplanner.util.SpringApplicationContex;

public class GeneralReportsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Integer, String> allGroups = new HashMap<Integer, String>();
	private HashMap<Integer, String> ageList = new HashMap<Integer, String>();
	private HashMap<Integer, String> eventList = new HashMap<Integer, String>();
	private FileUploadService fileUploadService;
	private int selectedGroup;
	private int selectedAgeGroup;
	private String gender;
	private ReportService reportService;
	private List<PlayerListDTO> groupathleteList;
	private int selectedEvent;
	private List<PlayerEventDTO> eventAthletes;
	private List<PlayerEventDTO> eventResult;
	private CommonService commonService;

	public GeneralReportsBean() {
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
		fileUploadService = (FileUploadService) SpringApplicationContex.getBean("fileUploadService");
		reportService = (ReportService) SpringApplicationContex.getBean("reportService");
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
				System.out.println("id " + e.getId() + " val " + e.getEventName());
				eventList.put(e.getId(), e.getEventName());
			}
		}
	}

	public void searchAthletes(){
		try{
			groupathleteList = reportService.getGroupWiseAthlete(selectedGroup, selectedAgeGroup, gender);
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public void resetAthleteGroup(){
		selectedGroup = 0;
		selectedAgeGroup = 0;
		gender = null;
		groupathleteList.clear();
	}
	
	public void searchEventAthletes(){
		System.out.println("selectedEvent "+selectedEvent+" selectedAgeGroup "+selectedAgeGroup+" gender "+gender);
		eventAthletes = reportService.getEventWiseAthletes(selectedEvent, selectedAgeGroup, gender);
		if(eventAthletes ==null || eventAthletes.size()==0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "No matching records for search criteria"));
		}
	}
	
	public void searchEventResults(){
		System.out.println("selectedEvent "+selectedEvent+" selectedAgeGroup "+selectedAgeGroup+" gender "+gender);
		//eventResult = reportService.getEventWiseAthletes(selectedEvent, selectedAgeGroup, gender);
		eventResult = reportService.getEventWiseAthletesWithPlace(selectedEvent, selectedAgeGroup, gender);
		if(eventResult ==null || eventResult.size()==0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "No matching records for search criteria"));
		}
	}
	
	public void onAgeGroupChange(){
		System.out.println("gender "+gender+" age group "+selectedAgeGroup);
		eventList.clear();
		List<EventDTO> eventForAgeGroup = commonService.getEventsForAgeGroupAndGender(selectedAgeGroup,gender);
		if (eventForAgeGroup.size() > 0) {
			System.out.println("number of events "+eventForAgeGroup.size());
			for (EventDTO e : eventForAgeGroup) {
				eventList.put(e.getId(), e.getEventName());
			}
		}
	}
	
	public void getEventResultExcel(){
		System.out.println("downloading excel report");
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

	public int getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(int selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public int getSelectedAgeGroup() {
		return selectedAgeGroup;
	}

	public void setSelectedAgeGroup(int selectedAgeGroup) {
		this.selectedAgeGroup = selectedAgeGroup;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<PlayerListDTO> getGroupathleteList() {
		return groupathleteList;
	}

	public void setGroupathleteList(List<PlayerListDTO> groupathleteList) {
		this.groupathleteList = groupathleteList;
	}

	public HashMap<Integer, String> getEventList() {
		return eventList;
	}

	public void setEventList(HashMap<Integer, String> eventList) {
		this.eventList = eventList;
	}

	public int getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(int selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

	public List<PlayerEventDTO> getEventAthletes() {
		return eventAthletes;
	}

	public void setEventAthletes(List<PlayerEventDTO> eventAthletes) {
		this.eventAthletes = eventAthletes;
	}

	public List<PlayerEventDTO> getEventResult() {
		return eventResult;
	}

	public void setEventResult(List<PlayerEventDTO> eventResult) {
		this.eventResult = eventResult;
	}

}
