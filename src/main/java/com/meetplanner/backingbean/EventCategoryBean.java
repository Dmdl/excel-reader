package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.meetplanner.dto.EventCategoryDTO;
import com.meetplanner.service.CommonService;
import com.meetplanner.util.SpringApplicationContex;

public class EventCategoryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String eventCategoryName;
	private double pointFirst;
	private double pointSecond;
	private double pointThird;
	private double pointForth;
	private double pointFifth;
	private double pointSixth;
	private CommonService commonService;
	private List<EventCategoryDTO> eventCategories;
	private int eventCatId;

	public EventCategoryBean() {
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
		try{
			eventCategories = commonService.getEventCategories();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	public void addEventCategory(){
		if(null!=eventCategoryName){
			EventCategoryDTO event = new EventCategoryDTO();
			event.setCategoryName(eventCategoryName);
			event.setPointFirst(pointFirst);
			event.setPointSecond(pointSecond);
			event.setPointThird(pointThird);
			event.setPointForth(pointForth);
			event.setPointFifth(pointFifth);
			event.setPointSixth(pointSixth);
			try{
				commonService.addEventCategory(event);
				eventCategories = commonService.getEventCategories();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Added succsesfully"));
			}catch(Exception e){
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured"));
			}			
		}
	}
	
	public void populateToEdit(EventCategoryDTO event){
		this.eventCategoryName = event.getCategoryName();
		this.pointFirst = event.getPointFirst();
		this.pointSecond = event.getPointSecond();
		this.pointThird = event.getPointThird();
		this.pointForth = event.getPointForth();
		this.pointFifth = event.getPointFifth();
		this.pointSixth = event.getPointSixth();
		this.eventCatId = event.getId();
	}
	
	public void update(){
		EventCategoryDTO update = new EventCategoryDTO();
		update.setId(eventCatId);
		update.setCategoryName(eventCategoryName);
		update.setPointFirst(pointFirst);
		update.setPointSecond(pointSecond);
		update.setPointThird(pointThird);
		update.setPointForth(pointForth);
		update.setPointFifth(pointFifth);
		update.setPointSixth(pointSixth);
		try{
			commonService.updateEventCategory(update);
			eventCategories = commonService.getEventCategories();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Updated successfully"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured"));
		}		
	}
	
	public void showConfirmDialog(EventCategoryDTO ev){
		this.eventCatId = ev.getId();
		RequestContext.getCurrentInstance().execute("PF('confirmDialog').show();");
	}
	
	public void deleteEventcategory(){
		try{
			commonService.deleteEventCategory(this.eventCatId);
			eventCategories = commonService.getEventCategories();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Deleted successfully"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured"));
		}		
	}
	
	public String getEventCategoryName() {
		return eventCategoryName;
	}

	public void setEventCategoryName(String eventCategoryName) {
		this.eventCategoryName = eventCategoryName;
	}

	public double getPointFirst() {
		return pointFirst;
	}

	public void setPointFirst(double pointFirst) {
		this.pointFirst = pointFirst;
	}

	public double getPointSecond() {
		return pointSecond;
	}

	public void setPointSecond(double pointSecond) {
		this.pointSecond = pointSecond;
	}

	public double getPointThird() {
		return pointThird;
	}

	public void setPointThird(double pointThird) {
		this.pointThird = pointThird;
	}

	public List<EventCategoryDTO> getEventCategories() {
		return eventCategories;
	}

	public void setEventCategories(List<EventCategoryDTO> eventCategories) {
		this.eventCategories = eventCategories;
	}

	public double getPointForth() {
		return pointForth;
	}

	public void setPointForth(double pointForth) {
		this.pointForth = pointForth;
	}

	public double getPointFifth() {
		return pointFifth;
	}

	public void setPointFifth(double pointFifth) {
		this.pointFifth = pointFifth;
	}

	public double getPointSixth() {
		return pointSixth;
	}

	public void setPointSixth(double pointSixth) {
		this.pointSixth = pointSixth;
	}

}
