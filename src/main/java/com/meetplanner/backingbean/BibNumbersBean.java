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
	List<Athlete> updatedList;

	public BibNumbersBean() {
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
	}

	public void search(){
		System.out.println("in search....selectedGroup "+selectedGroup+" selectedAgeGroup "+selectedAgeGroup);
		athleteList = commonService.searchAthleteByGroupAndAge(Integer.parseInt(selectedGroup), Integer.parseInt(selectedAgeGroup));
		updatedList = athleteList;
		if(null!=athleteList && athleteList.size()>0){
			System.out.println("result list size "+athleteList.size());
		}else if(null!=athleteList && athleteList.size()==0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No Matching records Found."));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured."));
		}
		
	}
	
	public void onRowEdit(RowEditEvent event) {
		Athlete athlete = (Athlete) event.getObject();
		String id = athlete.getId();
		String bibNum = athlete.getBibNumber();
		boolean ok = commonService.updateBibNumber(Integer.parseInt(bibNum), Integer.parseInt(id));
		if(ok){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Updated."));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error?", "Error Occured."));
		}
    }
	
	public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Athlete) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void addBulkBbiNumbers(RowEditEvent event){
		try{
			Athlete athlete = (Athlete) event.getObject();
			String startNumer = athlete.getBibNumber();
			if(null!=startNumer && isInt(startNumer)){
				int start = Integer.parseInt(startNumer);
//				updatedList.clear();
				for(Athlete each:athleteList){
					each.setBibNumber(String.valueOf(start));
					start = start+1;
					updatedList.add(each);
				}
				int count = commonService.addBibNumbers(updatedList);
				if(count>0){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Aded."));
				}else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error?", "Error Occured."));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	private boolean isInt(String number){
		boolean res = false;
		try{
			Integer.parseInt(number);
			res = true;
		}catch(NumberFormatException e){
			
		}
		return res;
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

	public List<Athlete> getUpdatedList() {
		return updatedList;
	}

	public void setUpdatedList(List<Athlete> updatedList) {
		this.updatedList = updatedList;
	}

}
