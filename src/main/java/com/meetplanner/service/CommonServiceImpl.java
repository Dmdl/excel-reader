package com.meetplanner.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.meetplanner.dao.CommonDao;
import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.ResultDTO;

@Service("commonService")
public class CommonServiceImpl implements CommonService{

	@Qualifier("commondDao")
	@Autowired
	private CommonDao commonDao;
	
	@Override
	public boolean saveAthlete(Athlete athlete) {
		return commonDao.saveAthlete(athlete);
	}

	@Override
	public List<Athlete> searchAthleteByGroupAndAge(int groupId, int ageGroupId) {
		return commonDao.searchAthleteByGroupAndAge(groupId, ageGroupId);
	}

	@Override
	public boolean updateBibNumber(int number, int id) {
		return commonDao.updateBibNumber(number, id);
	}

	@Override
	public int addBibNumbers(List<Athlete> athletes) {
		return commonDao.addBibNumbers(athletes);
	}

	@Override
	public List<ResultDTO> getAthletesForEvents(int eventId, int ageGroupId,String gender) {
		return commonDao.getAthletesForEvents(eventId, ageGroupId, gender);
	}

	@Transactional
	@Override
	public boolean saveAthletesPerformances(List<ResultDTO> results) {
		return commonDao.saveAthletesPerformances(results);
	}

	@Override
	public List<Athlete> serachAthleteByBibOrName(String bib, String name) {
		return commonDao.serachAthleteByBibOrName(bib, name);
	}

}
