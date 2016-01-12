package com.meetplanner.dto;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class EventDTO {
	private int id;
	private String eventName;
	private String type;
	private String participants;
	private int eventCategoryId;
	private List<AgeGroupDTO> ageGroups;
	private String eventCategory;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
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

	public String getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
	            append(id).
	            toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EventDTO))
            return false;
        if (obj == this)
            return true;

        EventDTO rhs = (EventDTO) obj;
        return new EqualsBuilder().
            append(id, rhs.id).
            isEquals();
	}
}
