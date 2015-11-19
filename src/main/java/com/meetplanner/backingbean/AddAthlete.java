package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.meetplanner.dto.Athlete;
import com.meetplanner.service.CommonService;
import com.meetplanner.service.FileUploadService;
import com.meetplanner.util.SpringApplicationContex;

public class AddAthlete implements Serializable {

	private static final long serialVersionUID = 1L;
	private String athleteName;
	private String nic = null;
	private FileUploadService fileUploadService;
	private String gender = null;
	private String selectedGroup = null;
	private String selectedAgeGroup = null;
	private String selectedEvent = null;
	private Date dateOfBirth;
	private CommonService commonService;

	public AddAthlete() {
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
	}

	public void saveAthlete() {
		System.out.println("gender " + gender + " age group " + selectedAgeGroup + " event " + selectedEvent + " group " + selectedGroup+" dateOfBirth "+dateOfBirth+" name "+athleteName+" nic "+nic);
		Athlete athlete = new Athlete();
		athlete.setDateOfBirth(dateOfBirth);
		athlete.setGroup(selectedGroup.toString());
		athlete.setName(athleteName);
		athlete.setNic(nic == null ? "" : nic);
		athlete.setEmpNo("");
		if(null!=gender && gender.equals("male")){
			athlete.setGender("M");
		}else if(null!=gender && gender.equals("female")){
			athlete.setGender("F");
		}
		athlete.setAgeGroup(selectedAgeGroup);
		boolean ok = commonService.saveAthlete(athlete);
		if(ok){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Saved."));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured."));
		}
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

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

}
