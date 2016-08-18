package com.meetplanner.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.service.FileUploadService;

/**
 * @author lakmal dasanayake
 *
 */
@Service
public class XssfWorkbookReader implements Reader {

	private static final int START_INDEX = 2;
	private static final int EVENTS_ATART_INDEX = 9;
	@Autowired
	private FileUploadService fileUploadService;
	Map<Integer, String> eventMap = new HashMap<>();

	@Override
	public void read(String path, String gender) throws Exception {
		System.out.println("gender --- " + gender);
		this.readExcelFile(path, gender);
	}

	private void readExcelFile(String path, String gender) {
		try {
			FileInputStream in = new FileInputStream(path);
			OPCPackage pkg = OPCPackage.open(in);
			XSSFWorkbook wb = new XSSFWorkbook(pkg);

			List<List<String>> eachSheetContent = new ArrayList<>(0);

			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				LinkedList<String> sheetHeaders = new LinkedList<String>();
				eachSheetContent.clear();
				Sheet sheet = wb.getSheetAt(i);
				String ageGroup = sheet.getSheetName();
				System.out.println("--------------- " + sheet.getSheetName() + " -----------------");
				int lastRowNum = sheet.getLastRowNum();

				List<String> eachRowContent = new ArrayList<>(0);

				for (int k = START_INDEX; k <= lastRowNum; k++) {

					if (k == 2) {
						Row row = sheet.getRow(k);
						if (null != row) {
							XSSFRow headerRow = (XSSFRow) row;
							Iterator<Cell> headerIterator = headerRow.iterator();
							while (headerIterator.hasNext()) {
								XSSFCell headerCell = (XSSFCell) headerIterator.next();
								if (XSSFCell.CELL_TYPE_STRING == headerCell.getCellType()) {
									sheetHeaders.add(headerCell.getStringCellValue());
								}
							}
							getEventsFromHeader(sheetHeaders);
						}
					} else {
						Row row = sheet.getRow(k);
						if (null != row) {
							XSSFRow eachRow = (XSSFRow) row;
							Iterator<Cell> iterator = eachRow.cellIterator();

							while (iterator.hasNext()) {
								String cellVal = "";
								XSSFCell eachCell = (XSSFCell) iterator.next();

								if (XSSFCell.CELL_TYPE_NUMERIC == eachCell.getCellType()) {
									// System.out.print(eachCell.getNumericCellValue()
									// + " ");
									double numericCellValue = eachCell.getNumericCellValue();
									cellVal = String.valueOf((int) numericCellValue);									
								}

								if (XSSFCell.CELL_TYPE_BLANK == eachCell.getCellType()) {
									// System.out.print("BLANK ");
								}

								if (XSSFCell.CELL_TYPE_STRING == eachCell.getCellType()) {
									// System.out.print(eachCell.getStringCellValue()
									// + " ");
									cellVal = eachCell.getStringCellValue();
								}
								eachRowContent.add(cellVal);
							}
						}
						eachSheetContent.add(new ArrayList<String>(eachRowContent));
						eachRowContent.clear();
					}
				}
				System.out.println();
				processEachSheet(eachSheetContent, gender,ageGroup);
			}
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processEachSheet(List<List<String>> eachSheetContent, String gender,String ageGroup) {
		List<Athlete> athletes = new ArrayList<>(0);
		for (List<String> row : eachSheetContent) {
			if (null != row && row.size() > 0 && !"".equals(row.get(0))) {
				Athlete athlete = new Athlete();
				athlete.setBibNumber(row.get(2));
				athlete.setName(row.get(3));
				athlete.setDateOfBirth(new Date());
				athlete.setNic(row.get(4));
				athlete.setEmpNo(row.get(1));
				athlete.setGroup(row.get(8));
				athlete.setGender(gender);
				List<String> events = getMatchingEvents(row);
				List<EventDTO> athleteEvents = new ArrayList<>(0);
				for (String ev : events) {
					EventDTO event = new EventDTO();
					event.setEventName(ev);
					event.setParticipants(gender);
					event.setType("T");
					event.setEventCategoryId(1);
					athleteEvents.add(event);
				}
				athlete.setEvents(athleteEvents);
				athletes.add(athlete);
			}
		}
		print(athletes);
		try{
			fileUploadService.saveAthlete(athletes,ageGroup);
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	public void getEventsFromHeader(LinkedList<String> header) {
		eventMap.clear();
		int count = 0;
		for (int k = EVENTS_ATART_INDEX; k < header.size() - 2; k++) {
			System.out.print(header.get(k) + "   ");
			eventMap.put(count, header.get(k));
			count++;
		}
	}

	private List<String> getMatchingEvents(List<String> eachRow) {
		List<String> events = new ArrayList<>(0);
		int count = 0;
		for (int k = EVENTS_ATART_INDEX; k < eachRow.size(); k++) {
			String cellVal = eachRow.get(k);
			if ("x".equalsIgnoreCase(cellVal)) {
				String eventname = eventMap.get(count);
				events.add(eventname);
			}
			count++;
		}
		return events;
	}

	private void print(List<Athlete> athletes) {
		for (Athlete ath : athletes) {
			List<EventDTO> events = ath.getEvents();
			System.out.println("bib -> " + ath.getBibNumber() + " name -> " + ath.getName()+" group ->"+ath.getGroup());
			for (EventDTO ev : events) {
				System.out.print(ev.getEventName());
			}
			System.out.println();
		}
		System.out.println();
	}
}
