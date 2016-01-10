package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;

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
	private Integer lastBibNumber;
	private String selectedGender;

	public BibNumbersBean() {
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
		try{
			String bib = commonService.getLastAssignBibNumber();
			if(null==bib){
				lastBibNumber= 1;
			}else{
				lastBibNumber= Integer.parseInt(bib)+1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	public void search(){
		System.out.println("in search....selectedGroup "+selectedGroup+" selectedAgeGroup "+selectedAgeGroup+" gender "+selectedGender);
		//athleteList = commonService.searchAthleteByGroupAndAge(Integer.parseInt(selectedGroup), Integer.parseInt(selectedAgeGroup));
		athleteList = commonService.searchAthleteByGenderAndAge(selectedGender,Integer.parseInt(selectedAgeGroup));
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
	
	public void assignBibNumbers(){
		System.out.println("last bib "+lastBibNumber);
		List<Athlete> temp = new ArrayList<Athlete>(0);
		if(null!=athleteList && athleteList.size()>0){
			Iterator<Athlete> ite = athleteList.iterator();
			while(ite.hasNext()){
				Athlete each = ite.next();
				if(null==each.getBibNumber() || "".equals(each.getBibNumber())){
					each.setBibNumber(String.valueOf(lastBibNumber));
					lastBibNumber = lastBibNumber+1;
				}				
				temp.add(each);				
			}
			athleteList.clear();
			athleteList.addAll(temp);
			try{
				int count = commonService.addBibNumbers(athleteList);
				System.out.println("count "+count);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Updated."));
			}catch(GenricSqlException e){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured."));
			}catch(DuplicateValueException d){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Duplicate BIB Numbers."));
			}
		}
	}
	
	public void onTabChange(TabChangeEvent event){
		try{
			/*String bib = commonService.getLastAssignBibNumber();
			if(null==bib){
				lastBibNumber= 1;
			}else{
				lastBibNumber= Integer.parseInt(bib)+1;
			}*/
			String lastBib = commonService.getLastAssignBibNumberForAgeGroup(Integer.parseInt(selectedAgeGroup));
			if(null!=lastBib){
				lastBibNumber = Integer.parseInt(lastBib)+1;
			}else{
				lastBibNumber = commonService.getStartBibForAgeGroup(Integer.parseInt(selectedAgeGroup));
			}
			if(null!=selectedAgeGroup && null!=selectedGroup){
				athleteList = commonService.searchAthleteByGroupAndAge(Integer.parseInt(selectedGroup), Integer.parseInt(selectedAgeGroup));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public void onAgeGroupChange(){
		System.out.println("age group select "+selectedAgeGroup);
		try{
			String lastBib = commonService.getLastAssignBibNumberForAgeGroup(Integer.parseInt(selectedAgeGroup));
			System.out.println("lastBib::::::::::: "+lastBib);
			if(null!=lastBib){
				lastBibNumber = Integer.parseInt(lastBib)+1;
			}else{
				lastBibNumber = commonService.getStartBibForAgeGroup(Integer.parseInt(selectedAgeGroup));
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
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

	public int getLastBibNumber() {
		return lastBibNumber;
	}

	public void setLastBibNumber(int lastBibNumber) {
		this.lastBibNumber = lastBibNumber;
	}

	public String getSelectedGender() {
		return selectedGender;
	}

	public void setSelectedGender(String selectedGender) {
		this.selectedGender = selectedGender;
	}

}
