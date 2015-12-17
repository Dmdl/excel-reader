package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

import com.meetplanner.dto.AgeGroupDTO;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.EventsDTO;
import com.meetplanner.dto.GroupDTO;
import com.meetplanner.service.CommonService;
import com.meetplanner.service.FileUploadService;
import com.meetplanner.util.SpringApplicationContex;

public class EventManageBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String eventName;
	private String participants;
	private String eventType;
	private CommonService commonService;
	private String groupName;
	private FileUploadService fileUploadService;
	private List<EventDTO> events;
	private List<GroupDTO> groups;
	private int eventId;
	private int groupId;
	private List<AgeGroupDTO> ageGroups;
	private String ageGroup;
	private int ageGroupId;
	
	public EventManageBean(){
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
		fileUploadService = (FileUploadService) SpringApplicationContex.getBean("fileUploadService");
		events = fileUploadService.getAllEvents();
		groups = fileUploadService.getAllGroups();
		ageGroups = fileUploadService.getAllAgeGroups();
	}

	public void addEvent(){
		System.out.println("eventName "+eventName+" participants "+participants+" eventType "+eventType);
		try{
			EventsDTO event = new EventsDTO();
			event.setEvent(eventName);
			event.setParticipants(participants);
			event.setType(eventType);
			boolean ok = commonService.addEvent(event);
			if(ok){
				resetFields();
				events = fileUploadService.getAllEvents();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Saved."));
			}
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Occured."));
		}
	}
	
	public void addGroup(){
		if(null!=groupName){
			GroupDTO group = new GroupDTO();
			group.setName(groupName);
			try{
				boolean ok = commonService.addGroup(group);
				if(ok){
					resetFields();
					groups = fileUploadService.getAllGroups();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Saved."));
				}
			}catch(Exception e){
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Occured."));
			}			
		}
	}
	
	public void resetFields(){
		eventName = null;
		participants = null;
		eventType = null;
	}
	
	public void editEvent(EventDTO event){
		if(event !=null){
			this.eventName = event.getEventName();
			this.eventType = event.getType();
			this.participants = event.getParticipants();
			this.eventId = event.getId();
		}
	}
	
	public void updateEvent(){
		if(eventId>0){
			System.out.println("eventId "+eventId+" eventName "+eventName+" eventType "+eventType+" participants "+participants);
			try{
				EventDTO event = new EventDTO();
				event.setId(eventId);
				event.setEventName(eventName);
				event.setType(eventType);
				event.setParticipants(participants);
				commonService.updateEvent(event);
				events = fileUploadService.getAllEvents();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Updated."));
			}catch(Exception e){
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Occured."));
			}
		}
	}
	
	public void showEventDeleteDialog(EventDTO event){
		this.eventId = event.getId();
		RequestContext.getCurrentInstance().execute("PF('confirmDialog').show();");
	}
	
	public void deleteEvent(){
		if(eventId!=0){
			boolean ok = commonService.deleteEvent(eventId);
			if(ok){
				events = fileUploadService.getAllEvents();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Deleted."));
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Occured."));
			}
		}
	}
	
	public void editGroup(GroupDTO group){
		if(null!=group){
			this.groupName = group.getName();
			this.groupId = group.getId();
		}
	}
	
	public void updateGroup(){
		try{
			GroupDTO group = new GroupDTO();
			group.setId(groupId);
			group.setName(groupName);
			commonService.updateGroup(group);
			groups = fileUploadService.getAllGroups();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Updated."));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Occured."));
		}
	}
	
	public void onTabChange(TabChangeEvent event){
		
	}
	
	public void showGroupDeleteDialog(GroupDTO group){
		this.groupId = group.getId();
		RequestContext.getCurrentInstance().execute("PF('confirmGrpDialog').show();");
	}
	
	public void deleteGroup(){
		boolean ok = commonService.deleteGroup(groupId);
		if(ok){
			groups = fileUploadService.getAllGroups();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Deleted."));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Occured."));
		}
	}
	
	public void addAgeGroup(){
		if(null!=ageGroup){
			AgeGroupDTO age = new AgeGroupDTO();
			age.setAgeGroup(this.ageGroup);
			boolean ok = commonService.addAgeGroup(age);
			if(ok){
				ageGroups = fileUploadService.getAllAgeGroups();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Added."));
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Occured."));
			}
		}		
	}
	
	public void editAgeGroup(AgeGroupDTO age){
		this.ageGroupId = age.getId();
		this.ageGroup = age.getAgeGroup();
	}
	
	public void updateAgeGroup(){
		AgeGroupDTO age = new AgeGroupDTO();
		age.setId(ageGroupId);
		age.setAgeGroup(ageGroup);
		boolean ok = commonService.updateAgeGroup(age);
		if(ok){
			ageGroups = fileUploadService.getAllAgeGroups();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Updated."));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Occured."));
		}
	}
	
	public void showDeleteDialog(AgeGroupDTO age){
		this.ageGroupId = age.getId();
		RequestContext.getCurrentInstance().execute("PF('confirmDialog').show();");
	}
	
	public void deleteAgeGroup(){
		try{			
			commonService.deleteAgeGroup(this.ageGroupId);
			ageGroups = fileUploadService.getAllAgeGroups();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Deleted."));			
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Occured."));
		}
	}
	
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public List<EventDTO> getEvents() {
		return events;
	}

	public void setEvents(List<EventDTO> events) {
		this.events = events;
	}

	public List<GroupDTO> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupDTO> groups) {
		this.groups = groups;
	}

	public List<AgeGroupDTO> getAgeGroups() {
		return ageGroups;
	}

	public void setAgeGroups(List<AgeGroupDTO> ageGroups) {
		this.ageGroups = ageGroups;
	}

	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
	
}
