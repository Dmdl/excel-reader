package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.meetplanner.dto.Athlete;
import com.meetplanner.exception.DuplicateValueException;
import com.meetplanner.exception.GenricSqlException;
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
		Athlete athlete = (Athlete) event.getObject();
		String id = athlete.getId();
		String bibNum = athlete.getBibNumber();
		boolean ok = false;
		try{
			ok = commonService.updateBibNumber(Integer.parseInt(bibNum), Integer.parseInt(id));
			if(ok){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Updated."));
			}
		}catch(DuplicateValueException inVex){
			System.out.println("error "+inVex.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Duplicate BIB Number."));
		}catch (GenricSqlException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured."));
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
			List<Athlete> temp = new ArrayList<Athlete>(0);
			if(null!=startNumer && isInt(startNumer)){
				int start = Integer.parseInt(startNumer);
				System.out.println("size "+athleteList.size());
				if(null!=athleteList && athleteList.size()>0){
					Iterator<Athlete> ite = athleteList.iterator();
					while(ite.hasNext()){
						Athlete each = ite.next();
						each.setBibNumber(String.valueOf(start));
						temp.add(each);
						start = start+1;
					}
					athleteList.clear();
					athleteList.addAll(temp);
					int count = commonService.addBibNumbers(athleteList);
					System.out.println("count "+count);
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

}
