package com.meetplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.meetplanner.dao.CommonDao;
import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.EventDTO;

@Service("searchService")
public class SerchServiceImpl implements SerchService {

	@Qualifier("commondDaoNamed")
	@Autowired
	private CommonDao commonDao;

	@Override
	public List<Athlete> searchAthlete(String bib, String name) {
		return commonDao.serachAthleteByBibOrName(bib, name);
	}

	@Override
	public List<EventDTO> getFilteredEventList(String gender, String eventType) {
		return commonDao.getFilteredEventList(gender, eventType);
	}

	@Override
	public List<Integer> checkForExistingBibNumbers(List<Integer> bib) {
		return commonDao.checkForExistingBibNumbers(bib);
	}

}
