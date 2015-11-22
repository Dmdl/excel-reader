package com.meetplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.meetplanner.dao.CommonDao;
import com.meetplanner.dto.Athlete;

@Service("searchService")
public class SerchServiceImpl implements SerchService {

	@Qualifier("commondDaoNamed")
	@Autowired
	private CommonDao commonDao;

	@Override
	public List<Athlete> searchAthlete(String bib, String name) {
		return commonDao.serachAthleteByBibOrName(bib, name);
	}

}
