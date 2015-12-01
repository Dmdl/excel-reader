package com.meetplanner.backingbean;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.meetplanner.dto.Athlete;
import com.meetplanner.service.CommonService;
import com.meetplanner.util.SpringApplicationContex;

public class ReportBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String selectedReportType;
	private CommonService commonService;
	//private ReportPrinter reportPrinter;
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

	public ReportBean() {
		commonService = (CommonService) SpringApplicationContex.getBean("commonService");
		//reportPrinter = new ReportPrinter();
	}

	public void onReportTypeChange(){
		System.out.println("selected "+selectedReportType);
		if(null!=selectedReportType){
			if(selectedReportType.equals("At")){
				List<Athlete> athletes = commonService.getAllAthletesForGroup(1);
				generateAthleteReport(athletes);
			}
		}
	}
	
	public void generateAthleteReport(List<Athlete> athletes){
		try{
			Document document = new Document();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();
            Paragraph preface = new Paragraph();
            addEmptyLine(preface, 1);
            preface.add(new Paragraph("Group wise employe report", catFont));
            addEmptyLine(preface, 1);
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(90);
        	table.addCell("Name");
        	table.addCell("Group");
        	table.addCell("BIB Number");

            for(Athlete each:athletes){
            	PdfPCell cell1 = new PdfPCell(new Paragraph(each.getName()));
            	PdfPCell cell2 = new PdfPCell(new Paragraph(each.getGroup()));
            	PdfPCell cell3 = new PdfPCell(new Paragraph(each.getBibNumber()));
            	table.addCell(cell1);
            	table.addCell(cell2);
            	table.addCell(cell3);
            }
            document.add(preface);
            document.add(table);
            document.close();            
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            ec.responseReset();
            ec.setResponseContentType("application/pdf");
            ec.setResponseContentLength(baos.size());
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + "testfile.pdf" + "\"");
            OutputStream output = ec.getResponseOutputStream();
            baos.writeTo(output);
            fc.responseComplete();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
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
