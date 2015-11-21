package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.tabview.TabView;

import com.meetplanner.dto.ResultDTO;
import com.meetplanner.service.CommonService;
import com.meetplanner.util.SpringApplicationContex;

public class EventResultsBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String selectedAgeGroup;
	private String selectedEvent;
	private TabView trackResultTabView;
	private CommonService commonService;
	private List<ResultDTO> results;
	
	public EventResultsBean(){
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
	}

	public void handleEventChange(String gender){
		System.out.println("age "+selectedAgeGroup+" event "+selectedEvent+"tab "+trackResultTabView.getActiveIndex()+" gender "+gender);			
			results = commonService.getAthletesForEvents(Integer.parseInt(selectedEvent), Integer.parseInt(selectedAgeGroup), gender);			
	}
	
	public void handleAgegroupChange(){
		selectedEvent = null;
		if(null!=results){
			results.clear();
		}		
	}
	
	public void saveResults(){
		boolean ok = true;
		if(null!=results){
			for(ResultDTO each:results){
				if(each.getPerformance().equals("") || null==each.getPerformance()){
					ok = false;
					break;
				}
			}
			if(!ok){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Enter Performance for All Athletes."));
			}else{
				boolean isOk = commonService.saveAthletesPerformances(results);
				if(isOk){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Successfully Updated."));
				}else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Something went wrong!!!"));
				}
			}
		}		
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

	public TabView getTrackResultTabView() {
		return trackResultTabView;
	}

	public void setTrackResultTabView(TabView trackResultTabView) {
		this.trackResultTabView = trackResultTabView;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public List<ResultDTO> getResults() {
		return results;
	}

	public void setResults(List<ResultDTO> results) {
		this.results = results;
	}	
	
}
