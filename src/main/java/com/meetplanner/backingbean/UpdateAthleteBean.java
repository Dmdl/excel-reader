package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.meetplanner.dto.Athlete;
import com.meetplanner.service.CommonService;
import com.meetplanner.service.SerchService;
import com.meetplanner.util.SpringApplicationContex;

public class UpdateAthleteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String serachBib;
	private String searchname;
	private CommonService commonService;
	private SerchService searchService;
	private List<Athlete> searchResult;

	public UpdateAthleteBean() {
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
		searchService = (SerchService) SpringApplicationContex.getBean("searchService");
	}

	public void searchAthlete(){
		System.out.println("serachBib "+serachBib+" searchname "+searchname);
		searchResult = searchService.searchAthlete(serachBib, searchname);
		if(null==searchResult){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured."));
		}else if(searchResult.size()==0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Nothing Found."));
		}
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

}
