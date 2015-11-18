package com.meetplanner.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetplanner.dto.AllEvents;
import com.meetplanner.dto.Athlete;
import com.meetplanner.service.FileUploadService;

@Service
public class ExcelReader implements Reader {

	private static final int START_INDEX = 6;
	private static final int EVENTS_ATART_INDEX=6;
	Map<Integer, String> eventMap=new HashMap<Integer, String>();
	private AllEvents events=new AllEvents();
	@Autowired
	private FileUploadService fileUploadService;

	@Override
	public void read(String path) throws Exception{
		readExcelFile(path);
	}

	public void readExcelFile(String file) {
		try {
			FileInputStream in = new FileInputStream(file);
			POIFSFileSystem fs = new POIFSFileSystem(in);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			List<LinkedList<String>> allRows=new LinkedList<LinkedList<String>>();
			for(int i=0;i<wb.getNumberOfSheets();i++){
				allRows.clear();
				LinkedList<String> sheetHeaders=new LinkedList<String>();
				Sheet sheet = wb.getSheetAt(i);
				if(checkForEmptySheet("A8",sheet)){
					continue;
				}	
				String comName=getCellInfo("A5", sheet);
				String ageGroup=getCellInfo("A4", sheet);
				events.setCompany(comName);
				System.out.println("~~~~~~"+comName+"~~~~~~~~~~~"+ageGroup+"~~~~~~~");
				System.out.println();
				int lastRowNum = sheet.getLastRowNum();				
				LinkedList<String> eachRow=new  LinkedList<String>();
				for(int k=START_INDEX;k<lastRowNum;k++){					
					boolean isReachEndOfContent=false;					
					if(k==6){
						HSSFRow headerRow = (HSSFRow) sheet.getRow(k);
						Iterator<Cell> headerIterator = headerRow.cellIterator();
						while(headerIterator.hasNext()){
							HSSFCell headerCell=(HSSFCell)headerIterator.next();
							if (HSSFCell.CELL_TYPE_STRING == headerCell.getCellType()){
								System.out.print(headerCell.getStringCellValue() + "     ");
								sheetHeaders.add(headerCell.getStringCellValue());
							}								
						}
						getEventsFromHeader(sheetHeaders);
					}else{						
					HSSFRow next = (HSSFRow) sheet.getRow(k);
					Iterator<Cell> cellIterator = next.cellIterator();					
						while (cellIterator.hasNext()) {						
							HSSFCell cell = (HSSFCell) cellIterator.next();
							if(cell.getColumnIndex()==0 && cell.getCellType()==HSSFCell.CELL_TYPE_BLANK){
								isReachEndOfContent=true;
								break;
							}
							String cellStringValue="";
							if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()){
								cellStringValue=String.valueOf(cell.getNumericCellValue());
								System.out.print(cell.getNumericCellValue() + "     ");
							}else if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()){
								cellStringValue=cell.getStringCellValue();
								System.out.print(cell.getStringCellValue() + "     ");
							}else if (HSSFCell.CELL_TYPE_BOOLEAN == cell.getCellType()){
								cellStringValue=String.valueOf(cell.getNumericCellValue());
								System.out.print(cell.getBooleanCellValue() + "     ");
							}else if (HSSFCell.CELL_TYPE_BLANK == cell.getCellType()){
								cellStringValue="blank";
								System.out.print("BLANK     ");
							}else
								System.out.print("Unknown cell type");	
							eachRow.add(cellStringValue);							
						}						
					}						
					if(isReachEndOfContent){
						System.out.println("============================================");
						break;
					}	
					allRows.add(new LinkedList<String>(eachRow));					
					System.out.println();
					eachRow.clear();
				}
				processEachAgeGroup(allRows,ageGroup);
//				getEventsFromHeader(sheetHeaders);
			}
			wb.close();
			fileUploadService.saveFileDataToDb(events);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void processEachAgeGroup(List<LinkedList<String>> each,String ageGroup) {
		List<Athlete> athletes=new ArrayList<Athlete>();
		for (LinkedList<String> e : each) {
			int i = 0;
			if (e.size() > 0) {
				LinkedList<String> eachRowEvents = getEachRowEvents(e);
				Athlete athlete = extractEachPersonData(e);
				List<String> athleteEvents=new ArrayList<String>();
				for (String ea : eachRowEvents) {
					if (!ea.equalsIgnoreCase("blank")) {
						System.out.println("user has event "
								+ getFromEventList(i + EVENTS_ATART_INDEX));
						athleteEvents.add(getFromEventList(i + EVENTS_ATART_INDEX));
					}
					i++;
				}
				athlete.setEvents(athleteEvents);
				athlete.setAgeGroup(ageGroup);
				athletes.add(athlete);				
			}
		}
		events.getAthletes().add(athletes);
		System.out.println("number of athletes "+athletes.size());
	}

	public LinkedList<String> getEachRowEvents(LinkedList<String> each){
		LinkedList<String> eventsList=new LinkedList<String>();
		for(int k=EVENTS_ATART_INDEX;k<each.size()-2;k++){
			eventsList.add(each.get(k));
		}
		return eventsList;
	}
	
	private boolean checkForEmptySheet(String ref,Sheet sheet){
		boolean isEmpty=false;
		try{
			CellReference cellReference = new CellReference(ref); 
			Row row = sheet.getRow(cellReference.getRow());
			Cell speccell = row.getCell(cellReference.getCol());
			if(HSSFCell.CELL_TYPE_BLANK==speccell.getCellType()){
				isEmpty=true;
			}
			return isEmpty;
		}catch(Exception e){
			e.printStackTrace();
			return isEmpty;
		}
	}
	
	public String getCellInfo(String ref,Sheet sheet){
		String retString=null;
		try{
			CellReference cellReference = new CellReference(ref);
			Row row = sheet.getRow(cellReference.getRow());
			Cell speccell = row.getCell(cellReference.getCol());
			if(HSSFCell.CELL_TYPE_STRING==speccell.getCellType()){
				retString=speccell.getStringCellValue();
			}
			return retString;
		}catch(Exception e){
			e.printStackTrace();
			return retString;
		}
	}
	
	public void getEventsFromHeader(LinkedList<String> header){	
		eventMap.clear();
		for(int k=EVENTS_ATART_INDEX;k<header.size()-2;k++){
			System.out.print(header.get(k)+"   ");
			eventMap.put(k, header.get(k));
		}
	}
	
	public Athlete extractEachPersonData(LinkedList<String> each){
		Athlete athlete=new Athlete();
		athlete.setId(each.get(0));
		athlete.setEmpNo(each.get(1));
		athlete.setName(each.get(2));
		athlete.setNic(each.get(3));
		//athlete.setDateOfBirth(each.get(4));
		athlete.setAge(each.get(5));
		String no = each.get(0);
		String empNo = each.get(1);
		String name = each.get(2);
		String nic = each.get(3);
		String dateOfBirth = each.get(4);
		String age = each.get(5);
		System.out.println("no "+no+" empNo "+empNo+" name "+name+" nic "+nic+" dateOfBirth "+dateOfBirth+" age "+age);
		return athlete;
	}
	
	public String getFromEventList(int index){
		return eventMap.get(index);
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}
	
}
