package com.meetplanner.dto;

public class EventCategoryDTO {

	private int id;
	private String categoryName;
	private double pointFirst;
	private double pointSecond;
	private double pointThird;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public double getPointFirst() {
		return pointFirst;
	}

	public void setPointFirst(double pointFirst) {
		this.pointFirst = pointFirst;
	}

	public double getPointSecond() {
		return pointSecond;
	}

	public void setPointSecond(double pointSecond) {
		this.pointSecond = pointSecond;
	}

	public double getPointThird() {
		return pointThird;
	}

	public void setPointThird(double pointThird) {
		this.pointThird = pointThird;
	}

}
