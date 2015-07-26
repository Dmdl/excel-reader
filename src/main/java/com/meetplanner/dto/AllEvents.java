package com.meetplanner.dto;

import java.util.ArrayList;
import java.util.List;

public class AllEvents {

	private String company;
	private List<List<Athlete>> athletes = new ArrayList<List<Athlete>>();

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<List<Athlete>> getAthletes() {
		return athletes;
	}

	public void setAthletes(List<List<Athlete>> athletes) {
		this.athletes = athletes;
	}
}
