package com.meetplanner.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meetplanner.dao.CommonDao;
import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.ResultDTO;
import com.meetplanner.exception.DuplicateValueException;
import com.meetplanner.exception.GenricSqlException;
import com.meetplanner.exception.NoDataException;

@Service("commonService")
public class CommonServiceImpl implements CommonService,Serializable{

	private static final long serialVersionUID = 1L;
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
	public boolean updateBibNumber(int number, int id) throws DuplicateValueException,GenricSqlException{
		return commonDao.updateBibNumber(number, id);
	}

	@Override
	public int addBibNumbers(List<Athlete> athletes) throws DuplicateValueException,GenricSqlException{
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

	@Override
	public boolean updateAthlete(Athlete athlete) {
		return commonDao.updateAthlete(athlete);
	}

	@Override
	public Athlete getAthleteFromBibNumber(String bib,int ageGroupId,int eventId,String gender) throws GenricSqlException,NoDataException {
		return commonDao.getAthleteFromBibNumber(bib,ageGroupId,eventId,gender);
	}

	@Transactional(rollbackFor=GenricSqlException.class)
	@Override
	public boolean updatePerformanceForEvent(int eventId, List<Athlete> athletes) throws GenricSqlException {
		return commonDao.updatePerformanceForEvent(eventId, athletes);
	}

	@Override
	public List<Athlete> getAllAthletesForGroup(int groupid) {
		return commonDao.getAllAthletesForGroup(groupid);
	}

}
