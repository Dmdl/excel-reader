package com.meetplanner.dto;

import java.io.Serializable;
import java.util.List;

public class GroupAthleteDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String groupName;
	private List<Athlete> athletes;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Athlete> getAthletes() {
		return athletes;
	}

	public void setAthletes(List<Athlete> athletes) {
		this.athletes = athletes;
	}

}
