package com.meetplanner.dto;

public class ResultDTO {

	private int athleteId;
	private int eventId;
	private String bibNo;
	private String performance;
	private String name;
	private String group;
	private int place;

	public int getAthleteId() {
		return athleteId;
	}

	public void setAthleteId(int athleteId) {
		this.athleteId = athleteId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getBibNo() {
		return bibNo;
	}

	public void setBibNo(String bibNo) {
		this.bibNo = bibNo;
	}

	public String getPerformance() {
		return performance;
	}

	public void setPerformance(String performance) {
		this.performance = performance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

}
