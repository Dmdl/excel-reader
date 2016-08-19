package com.meetplanner.dto;

import java.util.Date;

public class PlayerEventDTO {

	private int id;
	private String bibNumber;
	private String athleteName;
	private Date dateOfBirth;
	private String groupName;
	private String performance;
	private int place;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBibNumber() {
		return bibNumber;
	}

	public void setBibNumber(String bibNumber) {
		this.bibNumber = bibNumber;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getPerformance() {
		return performance;
	}

	public void setPerformance(String performance) {
		this.performance = performance;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

}
