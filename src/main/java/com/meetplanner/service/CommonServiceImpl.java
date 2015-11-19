package com.meetplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetplanner.dao.CommonDao;
import com.meetplanner.dto.Athlete;

@Service("commonService")
public class CommonServiceImpl implements CommonService{

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

}
