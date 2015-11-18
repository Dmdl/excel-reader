package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.Date;

import com.meetplanner.service.FileUploadService;

public class AddAthlete implements Serializable {

	private static final long serialVersionUID = 1L;
	private String athleteName;
	private String nic;
	private FileUploadService fileUploadService;
	private String gender = null;
	private String selectedGroup = null;
	private String selectedAgeGroup = null;
	private String selectedEvent = null;
	private Date dateOfBirth;

	public AddAthlete() {

	}

	public void saveAthlete() {
		System.out.println("gender " + gender + " age group " + selectedAgeGroup + " event " + selectedEvent + " group " + selectedGroup+" dateOfBirth "+dateOfBirth);
	}

	public String getAthleteName() {
		return athleteName;
	}

	public void setAthleteName(String athleteName) {
		this.athleteName = athleteName;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(String selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public String getSelectedAgeGroup() {
		return selectedAgeGroup;
	}

	public void setSelectedAgeGroup(String selectedAgeGroup) {
		this.selectedAgeGroup = selectedAgeGroup;
	}

	public String getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(String selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
