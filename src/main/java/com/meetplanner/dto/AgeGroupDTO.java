package com.meetplanner.dto;

import java.util.Date;

public class AgeGroupDTO {

	private int id;
	private String ageGroup;
	private Date fromAge;
	private Date toAge;
	private int fromBibNumber;
	private int toBibNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}

	public Date getFromAge() {
		return fromAge;
	}

	public void setFromAge(Date fromAge) {
		this.fromAge = fromAge;
	}

	public Date getToAge() {
		return toAge;
	}

	public void setToAge(Date toAge) {
		this.toAge = toAge;
	}

	public int getFromBibNumber() {
		return fromBibNumber;
	}

	public void setFromBibNumber(int fromBibNumber) {
		this.fromBibNumber = fromBibNumber;
	}

	public int getToBibNumber() {
		return toBibNumber;
	}

	public void setToBibNumber(int toBibNumber) {
		this.toBibNumber = toBibNumber;
	}

}
