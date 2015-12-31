package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.exception.GenricSqlException;
import com.meetplanner.service.CommonService;
import com.meetplanner.service.FileUploadService;
import com.meetplanner.service.SerchService;
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
	private DualListModel<EventDTO> events;
	private SerchService searchService;
	private int activeIndex;

	public AddAthlete() {
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
		fileUploadService = (FileUploadService) SpringApplicationContex.getBean("fileUploadService");
		searchService = (SerchService) SpringApplicationContex.getBean("searchService");
		List<EventDTO> eventsSource = new ArrayList<EventDTO>();
        List<EventDTO> eventsTarget = new ArrayList<EventDTO>();
        eventsSource = searchService.getFilteredEventList("M", null);
        events = new DualListModel<EventDTO>(eventsSource, eventsTarget);
	}

	public void saveAthlete() {
		System.out.println("gender " + gender + " age group " + selectedAgeGroup + "num of event " + selectedEvent + " group " + selectedGroup+" dateOfBirth "+dateOfBirth+" name "+athleteName+" nic "+nic);
		List<EventDTO> athleteEvents = events.getTarget();
		if(null==athleteEvents || athleteEvents.size()==0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Select Events"));
			return;
		}
		if(athleteEvents.size()>3){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Maximum Number of Events is 3"));
			return;
		}
		for(EventDTO e:athleteEvents){
			System.out.println("each "+e.getEventName());
		}
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
		athlete.setEvents(athleteEvents);
		try{
			boolean ok = commonService.saveAthlete(athlete);
			if(ok){
				resetFeilds();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully Saved."));
			}
		}catch(GenricSqlException e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured."));
		}		
		
	}

	public void onTransferEvent(TransferEvent event) {
		/*@SuppressWarnings("unchecked")
		List<EventDTO> eventsTrans = (List<EventDTO>)event.getItems();*/
		if(events.getTarget().size()>3){			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error!", "Maximum Number of Events is 3."));
		}
	}
	
	public void resetFeilds(){
		gender = null;
		selectedGroup = null;
		selectedAgeGroup = null;
		dateOfBirth = null;
		athleteName = null;
		nic = null;
		List<EventDTO> eventsSource = new ArrayList<EventDTO>();
        List<EventDTO> eventsTarget = new ArrayList<EventDTO>();
        if(activeIndex == 0){
        	eventsSource = searchService.getFilteredEventList("M", null);
        }else if(activeIndex == 1){
        	searchService.getFilteredEventList("F", null);
        }
        events = new DualListModel<EventDTO>(eventsSource, eventsTarget);
	}
	
	public void onTabChange(TabChangeEvent event){
		TabView tabView = (TabView) event.getComponent();
		activeIndex = tabView.getActiveIndex();
		List<EventDTO> eventsSource = new ArrayList<EventDTO>();
		List<EventDTO> eventsTarget = new ArrayList<EventDTO>();
		if(activeIndex == 0){	        
	        eventsSource = searchService.getFilteredEventList("M", null);
	        events = new DualListModel<EventDTO>(eventsSource, eventsTarget);
		}else if(activeIndex == 1){
			eventsSource = searchService.getFilteredEventList("F", null);
	        events = new DualListModel<EventDTO>(eventsSource, eventsTarget);
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

	public DualListModel<EventDTO> getEvents() {
		return events;
	}

	public void setEvents(DualListModel<EventDTO> events) {
		this.events = events;
	}

	public SerchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SerchService searchService) {
		this.searchService = searchService;
	}

}
