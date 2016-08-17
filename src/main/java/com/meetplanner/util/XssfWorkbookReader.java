package com.meetplanner.util;

import java.io.FileInputStream;
import java.util.ArrayList;
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
import org.springframework.stereotype.Service;

/**
 * @author lakmal dasanayake
 *
 */
@Service
public class XssfWorkbookReader implements Reader {

	private static final int START_INDEX = 2;
	private static final int EVENTS_ATART_INDEX = 9;
	// private static final int EVENTS_ATART_INDEX = 9;
	private String[] eventsMen = { "100M", "200M", "400M", "800M", "1500M", "5000M", "1500M W", "110M H", "400M H",
			"L/J", "H/J", "T/J", "S/P", "D/T", "J/T", "H/T" };

	Map<Integer, String> eventMap = new HashMap<>();

	@Override
	public void read(String path) throws Exception {
		this.readExcelFile(path);
	}

	private void readExcelFile(String path) {
		try {
			FileInputStream in = new FileInputStream(path);
			OPCPackage pkg = OPCPackage.open(in);
			XSSFWorkbook wb = new XSSFWorkbook(pkg);

			List<List<String>> eachSheetContent = new ArrayList<>(0);

			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				LinkedList<String> sheetHeaders = new LinkedList<String>();
				eachSheetContent.clear();
				Sheet sheet = wb.getSheetAt(i);
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
									cellVal = String.valueOf(eachCell.getNumericCellValue());
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
						System.out.println();
					}
				}
				System.out.println();
				processEachSheet(eachSheetContent);
			}
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processEachSheet(List<List<String>> eachSheetContent) {
		for (List<String> row : eachSheetContent) {
			for (String cell : row) {
				if(!"".equals(cell)){
					System.out.print(cell + "   ");
				}				
			}
			System.out.println();
			List<String> events = getMatchingEvents(row);
			for(String ev :events){
				System.out.print(ev +" ");
			}
			System.out.println();
		}
	}

	public void getEventsFromHeader(LinkedList<String> header) {
		eventMap.clear();
		int count = 0;
		for (int k = EVENTS_ATART_INDEX; k < header.size() - 2; k++) {
			System.out.print(header.get(k) + "   ");
			//eventMap.put(k, header.get(k));
			eventMap.put(count, header.get(k));
			count++;
		}
	}

	private List<String> getEvents(List<String> eachRow) {
		List<String> events = new ArrayList<>(0);
		for (int k = EVENTS_ATART_INDEX ; k < eachRow.size(); k++) {
			String event = eachRow.get(k);
			if (!"".equals(event)) {
				String matchingEvent = eventMap.get(k);
				if(null!=matchingEvent){
					events.add(matchingEvent);
				}				
			}
		}
		return events;
	}
	
	private List<String> getMatchingEvents(List<String> eachRow) {
		List<String> events = new ArrayList<>(0);
		int count = 0;
		for (int k = EVENTS_ATART_INDEX ; k < eachRow.size(); k++) {
			String cellVal = eachRow.get(k);
			if("x".equalsIgnoreCase(cellVal)){
				String eventname = eventMap.get(count);
				events.add(eventname);
			}
			count++;
		}
		return events;
	}
}
