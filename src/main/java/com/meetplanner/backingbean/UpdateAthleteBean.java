package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import com.meetplanner.dto.AgeGroupDTO;
import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.exception.GenricSqlException;
import com.meetplanner.service.CommonService;
import com.meetplanner.service.FileUploadService;
import com.meetplanner.service.SerchService;
import com.meetplanner.util.CommonUtill;
import com.meetplanner.util.SpringApplicationContex;

public class UpdateAthleteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String serachBib;
	private String searchname;
	private CommonService commonService;
	private SerchService searchService;
	private List<Athlete> searchResult;
	private String athleteGroup;
	private String athleteAgeGroup;
	private String athleteNic;
	private Date athleteDoB;
	private String athleteGender;
	private String athleteName;
	private int athleteId;
	private DualListModel<EventDTO> events;
	private FileUploadService fileUploadService;

	public UpdateAthleteBean() {
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
		searchService = (SerchService) SpringApplicationContex.getBean("searchService");
		fileUploadService = (FileUploadService) SpringApplicationContex.getBean("fileUploadService");
		List<EventDTO> eventsSource = new ArrayList<EventDTO>();
        List<EventDTO> eventsTarget = new ArrayList<EventDTO>();
        events = new DualListModel<EventDTO>(eventsSource, eventsTarget);
	}

	public void searchAthlete(){
		System.out.println("serachBib "+serachBib+" searchname "+searchname);
		if((null!=serachBib && !"".equals(serachBib)) || (null!=searchname && !"".equals(searchname))){
			resetFeilds();
			searchResult = searchService.searchAthlete(serachBib, searchname);
			if(null==searchResult){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured."));
			}else if(searchResult.size()==0){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Nothing Found."));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Warn!", "Enter BIB or Name."));
		}
	}
	
	public void populateToEdit(Athlete athlete){
		if(null !=athlete){			
			try{
				this.athleteGroup = String.valueOf(athlete.getGroupId());
				this.athleteAgeGroup = athlete.getAgeGroup();
				this.athleteDoB = athlete.getDateOfBirth();
				this.athleteGender = athlete.getGender();
				this.athleteNic = athlete.getNic();
				this.athleteName = athlete.getName();
				this.athleteId = Integer.valueOf(athlete.getId());
				List<EventDTO> eventsSource = new ArrayList<EventDTO>();
		        List<EventDTO> eventsTarget = new ArrayList<EventDTO>();
		        //eventsSource = fileUploadService.getAllEvents();
		        if(athleteGender.equals("M")){
		        	eventsSource = searchService.getFilteredEventList("M", null);
		        }else if(athleteGender.equals("F")){
		        	eventsSource = searchService.getFilteredEventList("F", null);
		        }
		        eventsTarget = commonService.getEventsForAthletes(Integer.parseInt(athlete.getId()));
		        eventsSource.removeAll(eventsTarget);
		        events = new DualListModel<EventDTO>(eventsSource, eventsTarget);
			}catch(GenricSqlException e){
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured."));
			}			
		}
	}
	
	public void updateAthlete(){
		System.out.println("ath id "+athleteId+" athleteGroup "+athleteGroup+" athleteAgeGroup "+athleteAgeGroup+" athleteDoB "+athleteDoB+" athleteGender "+athleteGender+" athleteNic "+athleteNic+" athleteName "+athleteName);
		System.out.println("list size "+events.getTarget().size());
		AgeGroupDTO ageGroupSelect = commonService.getAgeGroup(Integer.parseInt(athleteAgeGroup));
		if(!CommonUtill.isDateBetween(athleteDoB, ageGroupSelect.getFromAge(), ageGroupSelect.getToAge())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error!", "Date of Birth Do not Match with Age Group"));
			return;
		}
		if(events.getTarget().size()>0 && events.getTarget().size()<4){
			Athlete athlete = new Athlete();
			athlete.setId(String.valueOf(athleteId));
			athlete.setName(athleteName);
			athlete.setDateOfBirth(athleteDoB);
			athlete.setGroupId(Integer.valueOf(athleteGroup));
			athlete.setNic(athleteNic);
			athlete.setGender(athleteGender);
			athlete.setAgeGroup(athleteAgeGroup);
			athlete.setEvents(events.getTarget());
			try{
				boolean ok = commonService.updateAthlete(athlete);
				if(ok){
					searchResult = searchService.searchAthlete(serachBib, searchname);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Updated Successfully."));
				}
			}catch(GenricSqlException e){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured."));
			}			
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Invalid Event Selection."));
		}
	}
	
	public void resetFeilds(){
		this.athleteGroup = null;
		this.athleteAgeGroup = null;
		this.athleteDoB = null;
		this.athleteGender = null;
		this.athleteNic = null;
		this.athleteName = null;
		List<EventDTO> eventsSource = new ArrayList<EventDTO>(0);
        List<EventDTO> eventsTarget = new ArrayList<EventDTO>(0);
        events = new DualListModel<EventDTO>(eventsSource, eventsTarget);
	}
	
	public void onTransferEvent(TransferEvent event) {
		if(events.getTarget().size()>3){			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error!", "Maximum Number of Events is 3."));
		}
	}
	
	public void onValueChange(){
		System.out.println("val "+athleteGender);
	}
	
	public String getSerachBib() {
		return serachBib;
	}

	public void setSerachBib(String serachBib) {
		this.serachBib = serachBib;
	}

	public String getSearchname() {
		return searchname;
	}

	public void setSearchname(String searchname) {
		this.searchname = searchname;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public SerchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SerchService searchService) {
		this.searchService = searchService;
	}

	public List<Athlete> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(List<Athlete> searchResult) {
		this.searchResult = searchResult;
	}

	public String getAthleteGroup() {
		return athleteGroup;
	}

	public void setAthleteGroup(String athleteGroup) {
		this.athleteGroup = athleteGroup;
	}

	public String getAthleteAgeGroup() {
		return athleteAgeGroup;
	}

	public void setAthleteAgeGroup(String athleteAgeGroup) {
		this.athleteAgeGroup = athleteAgeGroup;
	}

	public String getAthleteNic() {
		return athleteNic;
	}

	public void setAthleteNic(String athleteNic) {
		this.athleteNic = athleteNic;
	}

	public Date getAthleteDoB() {
		return athleteDoB;
	}

	public void setAthleteDoB(Date athleteDoB) {
		this.athleteDoB = athleteDoB;
	}

	public String getAthleteGender() {
		return athleteGender;
	}

	public void setAthleteGender(String athleteGender) {
		this.athleteGender = athleteGender;
	}

	public String getAthleteName() {
		return athleteName;
	}

	public void setAthleteName(String athleteName) {
		this.athleteName = athleteName;
	}

	public int getAthleteId() {
		return athleteId;
	}

	public void setAthleteId(int athleteId) {
		this.athleteId = athleteId;
	}

	public DualListModel<EventDTO> getEvents() {
		return events;
	}

	public void setEvents(DualListModel<EventDTO> events) {
		this.events = events;
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}	

}
