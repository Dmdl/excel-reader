package com.meetplanner.service;

import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.EventDTO;

import java.util.List;

public interface SerchService {

	public List<Athlete> searchAthlete(String bib, String name,int group);
	
	public List<EventDTO> getFilteredEventList(String gender, String eventType);
	
	public List<Integer> checkForExistingBibNumbers(List<Integer> bib);
}
