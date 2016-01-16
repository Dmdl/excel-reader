package com.meetplanner.dto;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PlayerListDTO {

	private int id;
	private String athleteName;
	private Date dateOfBirth;
	private String bibNumber;
	private String gender;
	private String groupName;
	private String ageGroup;
	private List<EventDTO> events;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAthleteName() {
		return athleteName;
	}

	public void setAthleteName(String athleteName) {
		this.athleteName = athleteName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getBibNumber() {
		return bibNumber;
	}

	public void setBibNumber(String bibNumber) {
		this.bibNumber = bibNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}

	public List<EventDTO> getEvents() {
		return events;
	}

	public void setEvents(List<EventDTO> events) {
		this.events = events;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). append(id).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PlayerListDTO))
            return false;
        if (obj == this)
            return true;

        PlayerListDTO rhs = (PlayerListDTO) obj;
        return new EqualsBuilder().append(id, rhs.id).isEquals();
	}
}
