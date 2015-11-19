package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.meetplanner.dto.Athlete;
import com.meetplanner.service.CommonService;
import com.meetplanner.util.SpringApplicationContex;

public class BibNumbersBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String selectedGroup = null;
	private String selectedAgeGroup = null;
	private CommonService commonService;
	private List<Athlete> athleteList = null;

	public BibNumbersBean() {
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
	}

	public void search(){
		System.out.println("in search....selectedGroup "+selectedGroup+" selectedAgeGroup "+selectedAgeGroup);
		athleteList = commonService.searchAthleteByGroupAndAge(Integer.parseInt(selectedGroup), Integer.parseInt(selectedAgeGroup));
		if(null!=athleteList && athleteList.size()>0){
			System.out.println("result list size "+athleteList.size());
		}else if(null!=athleteList && athleteList.size()==0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No Matching records Found."));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured."));
		}
		
	}
	
	public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", ((Athlete) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Athlete) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
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

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public List<Athlete> getAthleteList() {
		return athleteList;
	}

	public void setAthleteList(List<Athlete> athleteList) {
		this.athleteList = athleteList;
	}

}
