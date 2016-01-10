package com.meetplanner.backingbean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.meetplanner.dto.EventCategoryDTO;
import com.meetplanner.service.CommonService;
import com.meetplanner.util.SpringApplicationContex;

public class EventCategoryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String eventCategoryName;
	private double pointFirst;
	private double pointSecond;
	private double pointThird;
	private CommonService commonService;	

	public EventCategoryBean() {
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
	}

	public void addEventCategory(){
		if(null!=eventCategoryName){
			EventCategoryDTO event = new EventCategoryDTO();
			event.setCategoryName(eventCategoryName);
			event.setPointFirst(pointFirst);
			event.setPointSecond(pointSecond);
			event.setPointThird(pointThird);
			try{
				commonService.addEventCategory(event);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Added succsesfully"));
			}catch(Exception e){
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured"));
			}			
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

}
