package com.meetplanner.util;

import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ReportPrinter {

	/**
	 * 
	 * @param data List of data to print
	 * @param reportPath jrxml path
	 * @param params parameter map to report
	 * @param copies number of copies
	 */
	public <T> void printReport(List<T> data,String reportPath,Map<String, Object> params,int copies){
		InputStream in =null;
		JasperDesign jasperDesign =null;
		JasperReport report =null;
		JRBeanCollectionDataSource beanCollectionDataSource=null;
		JasperPrint jasperPrint=null;					
		PrinterJob job = PrinterJob.getPrinterJob();
		PrintService defaultPrintService = null;
		try{
			in = this.getClass().getClassLoader().getResourceAsStream(reportPath);
			jasperDesign = JRXmlLoader.load(in);
			report = JasperCompileManager.compileReport(jasperDesign);
			beanCollectionDataSource = new JRBeanCollectionDataSource(data);
			jasperPrint = JasperFillManager.fillReport(report, params, beanCollectionDataSource);
			defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
			
			if(null!=defaultPrintService){
				job.setPrintService(defaultPrintService);
				PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
	               printRequestAttributeSet.add(new Copies(copies));
	               JRPrintServiceExporter exporter;            
	               exporter = new JRPrintServiceExporter();
	               exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	               exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, defaultPrintService);
	               exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
	               exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
	               exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
	               exporter.exportReport();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			job = null;
			defaultPrintService = null;
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
