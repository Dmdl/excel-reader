package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.ResultDTO;
import com.meetplanner.exception.GenricSqlException;
import com.meetplanner.exception.NoDataException;
import com.meetplanner.service.CommonService;
import com.meetplanner.service.SerchService;
import com.meetplanner.util.SpringApplicationContex;

public class EventResultsBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String selectedAgeGroup;
	private String selectedEvent;
	private TabView trackResultTabView;
	private CommonService commonService;
	private List<ResultDTO> results;
	private List<Athlete> resultToFill;
	private boolean disableSubmit = true;
	private HashMap<Integer, String> eventList = new HashMap<Integer, String>();
	private SerchService searchService;
	private int activeIndexTrack;
	private int activeIndexFeild;
	
	public EventResultsBean(){
		resultToFill = new ArrayList<Athlete>(0);
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
		searchService = (SerchService) SpringApplicationContex.getBean("searchService");
		List<EventDTO> events = searchService.getFilteredEventList("M", null);
		if (events.size() > 0) {
			for (EventDTO e : events) {
				System.out.println("id " + e.getId() + " val " + e.getEventName());
				eventList.put(e.getId(), e.getEventName());
			}
		}
	}

	public void handleEventChange(String gender){
		System.out.println("age "+selectedAgeGroup+" event "+selectedEvent+"tab "+trackResultTabView.getActiveIndex()+" gender "+gender);			
			results = commonService.getAthletesForEvents(Integer.parseInt(selectedEvent), Integer.parseInt(selectedAgeGroup), gender);
			if(results.size()==0){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "No Records Found."));
			}
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
	
	public void submit(){
		if(null!=resultToFill && resultToFill.size()>0){
			for(Athlete a:resultToFill){
				System.out.println(a.getName()+" per "+a.getEventResult().getPerformance()+" id "+a.getId());				
			}
			try{
				commonService.updatePerformanceForEvent(Integer.parseInt(selectedEvent), resultToFill);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Successfully Updated."));
			}catch(GenricSqlException e){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Something went wrong!!!"));
			}		
		}
	}
	
	public void verify(String gender){
		disableSubmit = false;	
		if((null!=selectedAgeGroup && !"".equals(selectedAgeGroup)) && (null!=selectedEvent && !"".equals(selectedEvent)) && (null!=gender && !"".equals(gender))){
			if(null!=resultToFill){
				for(Athlete each:resultToFill){
					try{
						Athlete ath = commonService.getAthleteFromBibNumber(each.getBibNumber(),Integer.parseInt(selectedAgeGroup),Integer.parseInt(selectedEvent),gender);
						each.setId(ath.getId());
						each.setName(ath.getName());
						each.setNic(ath.getNic());
						each.setBibNumber(ath.getBibNumber());
						each.setGroup(ath.getGroup());
						each.getEventResult().setPerformance(ath.getEventResult().getPerformance());
					}catch(GenricSqlException e){
						disableSubmit = true;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured"));
					}catch(NoDataException e){
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Invalid BIB number "+each.getBibNumber()+" for selected criteria "));
						each.setName(null);
						each.setGroup(null);
						disableSubmit = true;
						break;
					}					
				}
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error!", "Select Event and Age group"));
		}
	}
	
	public void addRow(){
		if((null!=selectedAgeGroup && !"".equals(selectedAgeGroup)) && (null!=selectedEvent && !"".equals(selectedEvent))){
			resultToFill.add(new Athlete());
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error!", "Select Event and Age group"));
		}
	}
	
	public void RemoveRow(){
		if(null!= resultToFill && resultToFill.size()>0){
			resultToFill.remove(resultToFill.size()-1);
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "No Rows to Remove"));
		}
	}
	
	public void onTabChange(TabChangeEvent event){
		if(null!=resultToFill){
			resultToFill.clear();
		}
		if(null!=eventList){
			eventList.clear();
		}
		AccordionPanel accPanel = (AccordionPanel) event.getComponent();
		System.out.println("track "+activeIndexTrack+" feild "+activeIndexFeild+" main active in "+accPanel.getActiveIndex());
		int accPanelActiveIndex = Integer.parseInt(accPanel.getActiveIndex());
		List<EventDTO> events = new ArrayList<EventDTO>(0);
		if(accPanelActiveIndex == 0){
			if(activeIndexTrack == 0){
				events = searchService.getFilteredEventList("M", null);
			}else if(activeIndexTrack == 1){
				events = searchService.getFilteredEventList("F", null);
			}
		}else if(accPanelActiveIndex == 1){
			if(activeIndexFeild == 0){
				events = searchService.getFilteredEventList("M", null);
			}else if(activeIndexFeild == 1){
				events = searchService.getFilteredEventList("F", null);
			}
		}
		if (events.size() > 0) {
			for (EventDTO e : events) {
				System.out.println("id " + e.getId() + " val " + e.getEventName());
				eventList.put(e.getId(), e.getEventName());
			}
		}
	}
	
	public void onTrackTabViewChange(TabChangeEvent event){
		TabView tabView = (TabView) event.getComponent();
		activeIndexTrack = tabView.getActiveIndex();
		List<EventDTO> events = new ArrayList<EventDTO>(0);
		if(activeIndexTrack == 0){
			events = searchService.getFilteredEventList("M", null);			
		}else if(activeIndexTrack == 1){
			events = searchService.getFilteredEventList("F", null);
		}
		if(null!=eventList){
			eventList.clear();
		}
		if (events.size() > 0) {
			for (EventDTO e : events) {
				System.out.println("id " + e.getId() + " val " + e.getEventName());
				eventList.put(e.getId(), e.getEventName());
			}
		}
	}
	
	public void onFeildTabViewChange(TabChangeEvent event){
		TabView tabView = (TabView) event.getComponent();
		activeIndexFeild = tabView.getActiveIndex();
		List<EventDTO> events = new ArrayList<EventDTO>(0);
		if(activeIndexFeild == 0){
			events = searchService.getFilteredEventList("M", null);			
		}else if(activeIndexFeild == 1){
			events = searchService.getFilteredEventList("F", null);
		}
		if(null!=eventList){
			eventList.clear();
		}
		if (events.size() > 0) {
			for (EventDTO e : events) {
				System.out.println("id " + e.getId() + " val " + e.getEventName());
				eventList.put(e.getId(), e.getEventName());
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

	public List<Athlete> getResultToFill() {
		return resultToFill;
	}

	public void setResultToFill(List<Athlete> resultToFill) {
		this.resultToFill = resultToFill;
	}

	public boolean isDisableSubmit() {
		return disableSubmit;
	}

	public void setDisableSubmit(boolean disableSubmit) {
		this.disableSubmit = disableSubmit;
	}

	public HashMap<Integer, String> getEventList() {
		return eventList;
	}

	public void setEventList(HashMap<Integer, String> eventList) {
		this.eventList = eventList;
	}
	
}
