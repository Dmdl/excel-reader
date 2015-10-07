package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.HashMap;

public class AddAthlete implements Serializable{

	private static final long serialVersionUID = 1L;
	private HashMap<Integer, String> groupList;
	private HashMap<Integer, String> ageList;
	private HashMap<Integer, String> eventList;
	private String athleteName;
	private String nic;
	
	public AddAthlete(){
		groupList = new HashMap<Integer, String>();
		groupList.put(1, "Group 1");
		groupList.put(2, "Group 2");
		groupList.put(3, "Group 3");
		
		ageList = new HashMap<Integer, String>();
		ageList.put(1, "Age 1");
		ageList.put(2, "Age 2");
		ageList.put(3, "Age 3");
		
		eventList = new HashMap<Integer, String>();
		eventList.put(1, "Event 1");
		eventList.put(2, "Event 2");
		eventList.put(3, "Event 3");
	}

	public HashMap<Integer, String> getGroupList() {
		return groupList;
	}

	public void setGroupList(HashMap<Integer, String> groupList) {
		this.groupList = groupList;
	}

	public HashMap<Integer, String> getAgeList() {
		return ageList;
	}

	public void setAgeList(HashMap<Integer, String> ageList) {
		this.ageList = ageList;
	}

	public String getAthleteName() {
		return athleteName;
	}

	public void setAthleteName(String athleteName) {
		this.athleteName = athleteName;
	}

	public HashMap<Integer, String> getEventList() {
		return eventList;
	}

	public void setEventList(HashMap<Integer, String> eventList) {
		this.eventList = eventList;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}
	
}
