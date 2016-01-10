package com.meetplanner.dto;

import java.util.List;

public class EventsDTO {

	private int id;
	private String event;
	private String participants;
	private String type;
	private int eventCategoryId;
	private List<AgeGroupDTO> ageGroups;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getEventCategoryId() {
		return eventCategoryId;
	}

	public void setEventCategoryId(int eventCategoryId) {
		this.eventCategoryId = eventCategoryId;
	}

	public List<AgeGroupDTO> getAgeGroups() {
		return ageGroups;
	}

	public void setAgeGroups(List<AgeGroupDTO> ageGroups) {
		this.ageGroups = ageGroups;
	}

}
