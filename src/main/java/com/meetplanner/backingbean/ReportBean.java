package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.List;

import com.meetplanner.dto.Athlete;
import com.meetplanner.service.CommonService;
import com.meetplanner.util.ReportPrinter;
import com.meetplanner.util.SpringApplicationContex;

public class ReportBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String selectedReportType;
	private CommonService commonService;
	private ReportPrinter reportPrinter;

	public ReportBean() {
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
		reportPrinter = new ReportPrinter();
	}

	public void onReportTypeChange(){
		System.out.println("selected "+selectedReportType);
		if(null!=selectedReportType){
			if(selectedReportType.equals("At")){
				List<Athlete> athletes = commonService.getAllAthletesForGroup(1);
				reportPrinter.printReport(athletes);
			}
		}
	}
	
	public String getSelectedReportType() {
		return selectedReportType;
	}

	public void setSelectedReportType(String selectedReportType) {
		this.selectedReportType = selectedReportType;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

}
