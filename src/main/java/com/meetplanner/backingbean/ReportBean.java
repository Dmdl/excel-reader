package com.meetplanner.backingbean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.meetplanner.dto.AgeGroupDTO;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.GroupAthleteCountDTO;
import com.meetplanner.dto.GroupAthleteDTO;
import com.meetplanner.dto.ReportDTO;
import com.meetplanner.exception.GenricSqlException;
import com.meetplanner.service.CommonService;
import com.meetplanner.service.FileUploadService;
import com.meetplanner.service.ReportService;
import com.meetplanner.util.ReportPrinter;
import com.meetplanner.util.SpringApplicationContex;

public class ReportBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String selectedReportType;
	private CommonService commonService;
	private HashMap<Integer, String> ageList = new HashMap<Integer, String>();
	private HashMap<Integer, String> eventList = new HashMap<Integer, String>();
	private FileUploadService fileUploadService;
	private String selectedAgeGroup;
	private String selectedEvent;
	private String gender;
	private ReportService reportService;
	private List<ReportDTO> result;
	private String reportType;
	private List<GroupAthleteCountDTO> groupAthlete;
	private List<GroupAthleteDTO> groupWiseAthletes;
	private ReportPrinter reportPrinter;

	public ReportBean() {
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
		fileUploadService = (FileUploadService) SpringApplicationContex.getBean("fileUploadService");
		reportService = (ReportService) SpringApplicationContex.getBean("reportService");
		List<AgeGroupDTO> ageGroups = fileUploadService.getAllAgeGroups();
		if (ageGroups.size() > 0) {
			for (AgeGroupDTO e : ageGroups) {
				ageList.put(e.getId(), e.getAgeGroup());
			}
		}
		List<EventDTO> events = fileUploadService.getAllEvents();
		if (events.size() > 0) {
			for (EventDTO e : events) {
				eventList.put(e.getId(), e.getEventName());
			}
		}
		try{
			groupAthlete = reportService.getGroupWiseAthleteCount();
		}catch(GenricSqlException e){
			e.printStackTrace();
		}
		reportPrinter = new ReportPrinter();
	}

	public void showAthleteData(){
		System.out.println("selectedAgeGroup "+selectedAgeGroup+" selectedEvent "+selectedEvent+" gender "+gender);
		try{
			result = reportService.getAthleteData(selectedAgeGroup, selectedEvent, gender);
			if(null==result || result.size()==0){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "No Matching Records"));
			}
		}catch(GenricSqlException e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured"));
		}		
	}
	
	public void genarateReport() throws JRException, IOException{
		System.out.println("report type "+reportType);
		if(null!=reportType){
			InputStream in = null;
	        JasperDesign jasperDesign = null;
	        JasperReport report = null;
	        JasperPrint jasperPrint = null;
	        JRBeanCollectionDataSource beanCollectionDataSource = null;
	        try{       
	            in = this.getClass().getClassLoader().getResourceAsStream("/com/meetplanner/reports/certificate.jrxml");
	            jasperDesign = JRXmlLoader.load(in);
	            report = JasperCompileManager.compileReport(jasperDesign);            
	            beanCollectionDataSource = new JRBeanCollectionDataSource(result);   
	            jasperPrint = JasperFillManager.fillReport(report, new HashMap<String,Object>(), beanCollectionDataSource);
	            
	            FacesContext fc = FacesContext.getCurrentInstance();
	            ExternalContext ec = fc.getExternalContext();
	            OutputStream output = ec.getResponseOutputStream();
	            ec.responseReset();
	            if(reportType.equals("pdf")){
	            	ec.setResponseContentType("application/pdf");            
		            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + "testfile.pdf" + "\"");
		            JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	            }else if(reportType.equals("excel")){
	            	ec.setResponseContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");      
	                ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + "testfile.xlsx" + "\"");
	                JRXlsxExporter exporter = new JRXlsxExporter();
	                exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	                exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
	                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
	                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	                exporter.exportReport();
	            }	            
	            fc.responseComplete();
	        }finally{
	            jasperPrint = null;
	            jasperDesign = null;
	            report = null;
	            beanCollectionDataSource = null;
	            if (in != null) {
	                try {
	                    in.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            jasperPrint = null;
	        }
		}
	}
	
	public void printGrpWiseAthleteReport() throws JRException, IOException{
        //downloadPdf(groupAthlete, "/com/meetplanner/reports/groupWiseAthlete.jrxml",new HashMap<String, Object>());
        reportPrinter.printReport(groupAthlete, "/com/meetplanner/reports/groupWiseAthlete.jrxml", new HashMap<String, Object>(), 1);
	}
	
	public void printGroupAthleteReport() throws JRException, IOException{
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("SUBREPORT_DIR", "/com/meetplanner/reports/");
        downloadPdf(groupWiseAthletes, "/com/meetplanner/reports/groupAthletes.jrxml",params);
	}
	
	public void showAthleteTable(){
		try{
			groupWiseAthletes = reportService.getGroupAthletes();		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param data data to be generated on pdf
	 * @param path report path in class path
	 * @param params parameter map
	 * @throws JRException
	 * @throws IOException
	 */
	private <T> void downloadPdf(List<T> data,String path,Map<String, Object> params) throws JRException, IOException{
		InputStream in = null;
        JasperDesign jasperDesign = null;
        JasperReport report = null;
        JasperPrint jasperPrint = null;
        JRBeanCollectionDataSource beanCollectionDataSource = null;
        try{       
            in = this.getClass().getClassLoader().getResourceAsStream(path);
            jasperDesign = JRXmlLoader.load(in);
            report = JasperCompileManager.compileReport(jasperDesign);            
            beanCollectionDataSource = new JRBeanCollectionDataSource(data);   
            jasperPrint = JasperFillManager.fillReport(report, params, beanCollectionDataSource);
            
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            OutputStream output = ec.getResponseOutputStream();
            ec.responseReset();
        	ec.setResponseContentType("application/pdf");           
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + "athlete.pdf" + "\"");
            JasperExportManager.exportReportToPdfStream(jasperPrint, output);                     
            fc.responseComplete();
        }finally{
            jasperPrint = null;
            jasperDesign = null;
            report = null;
            beanCollectionDataSource = null;
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            jasperPrint = null;
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

	public HashMap<Integer, String> getAgeList() {
		return ageList;
	}

	public void setAgeList(HashMap<Integer, String> ageList) {
		this.ageList = ageList;
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public String getSelectedAgeGroup() {
		return selectedAgeGroup;
	}

	public void setSelectedAgeGroup(String selectedAgeGroup) {
		this.selectedAgeGroup = selectedAgeGroup;
	}

	public HashMap<Integer, String> getEventList() {
		return eventList;
	}

	public void setEventList(HashMap<Integer, String> eventList) {
		this.eventList = eventList;
	}

	public String getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(String selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public List<ReportDTO> getResult() {
		return result;
	}

	public void setResult(List<ReportDTO> result) {
		this.result = result;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public List<GroupAthleteCountDTO> getGroupAthlete() {
		return groupAthlete;
	}

	public void setGroupAthlete(List<GroupAthleteCountDTO> groupAthlete) {
		this.groupAthlete = groupAthlete;
	}

	public List<GroupAthleteDTO> getGroupWiseAthletes() {
		return groupWiseAthletes;
	}

	public void setGroupWiseAthletes(List<GroupAthleteDTO> groupWiseAthletes) {
		this.groupWiseAthletes = groupWiseAthletes;
	}

}
