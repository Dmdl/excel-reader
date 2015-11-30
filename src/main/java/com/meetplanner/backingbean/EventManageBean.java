package com.meetplanner.backingbean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.meetplanner.dto.EventsDTO;
import com.meetplanner.dto.GroupDTO;
import com.meetplanner.service.CommonService;
import com.meetplanner.util.SpringApplicationContex;

public class EventManageBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String eventName;
	private String participants;
	private String eventType;
	private CommonService commonService;
	private String groupName;
	
	public EventManageBean(){
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
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
	
}
