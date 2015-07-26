package com.meetplanner.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.meetplanner.dto.AllEvents;
import com.meetplanner.dto.Athlete;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Override
	public void saveFileDataToDb(AllEvents events) {
		List<List<Athlete>> athletes = events.getAthletes();
		for (List<Athlete> each : athletes) {
			for (Athlete a : each) {
				String name = a.getName();
				List<String> eventList = a.getEvents();
				System.out.println(name + " has " + eventList.size()+ " events...");
			}
		}
	}

}
