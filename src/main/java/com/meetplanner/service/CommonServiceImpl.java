package com.meetplanner.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meetplanner.dao.CommonDao;
import com.meetplanner.dto.AgeGroupDTO;
import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.EventCategoryDTO;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.EventsDTO;
import com.meetplanner.dto.GroupDTO;
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
	
	@Transactional(rollbackFor=GenricSqlException.class)
	@Override
	public boolean saveAthlete(Athlete athlete) throws GenricSqlException{
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

	@Transactional(rollbackFor=GenricSqlException.class)
	@Override
	public boolean updateAthlete(Athlete athlete) throws GenricSqlException{
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

	@Override
	public List<EventDTO> getEventsForAthletes(int athleteId) throws GenricSqlException {
		return commonDao.getEventsForAthletes(athleteId);
	}

	@Override
	public String getLastAssignBibNumber() throws GenricSqlException {
		return commonDao.getLastAssignBibNumber();
	}

	@Override
	public boolean addEvent(EventsDTO event) {
		return commonDao.addEvent(event);
	}

	@Override
	public boolean addGroup(GroupDTO group) throws Exception {
		return commonDao.addGroup(group);
	}

	@Override
	public void updateEvent(EventDTO event) throws Exception {
		commonDao.updateEvent(event);
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public boolean deleteEvent(int eventId) throws GenricSqlException{
		return commonDao.deleteEvent(eventId);
	}

	@Override
	public void updateGroup(GroupDTO group) throws Exception {
		commonDao.updateGroup(group);
	}

	@Override
	public boolean deleteGroup(int groupId) {
		return commonDao.deleteGroup(groupId);
	}

	@Override
	public boolean addAgeGroup(AgeGroupDTO ageGroup) {
		return commonDao.addAgeGroup(ageGroup);
	}

	@Override
	public boolean updateAgeGroup(AgeGroupDTO ageGroup) {
		return commonDao.updateAgeGroup(ageGroup);
	}

	@Override
	public List<AgeGroupDTO> getAgeGroups() throws Exception {
		return commonDao.getAgeGroups();
	}

	@Override
	public void deleteAgeGroup(int id) throws Exception {
		commonDao.deleteAgeGroup(id);
	}

	@Override
	public AgeGroupDTO getAgeGroup(int id) {
		return commonDao.getAgeGroup(id);
	}

	@Override
	public void addEventCategory(EventCategoryDTO eventcategory) throws Exception {
		commonDao.addEventCategory(eventcategory);
	}

	@Override
	public List<EventCategoryDTO> getEventCategories() throws Exception {
		return commonDao.getEventCategories();
	}

	@Override
	public List<AgeGroupDTO> getAgeGroupsForEvent(int eventId) {
		return commonDao.getAgeGroupsForEvent(eventId);
	}

	@Override
	public List<Athlete> searchAthleteByGenderAndAge(String gender,int ageGroupId) {
		return commonDao.searchAthleteByGenderAndAge(gender,ageGroupId);
	}

	@Override
	public String getLastAssignBibNumberForAgeGroup(int ageGroup) throws GenricSqlException {
		return commonDao.getLastAssignBibNumberForAgeGroup(ageGroup);
	}

	@Override
	public int getStartBibForAgeGroup(int ageGroupId) throws GenricSqlException {
		return commonDao.getStartBibForAgeGroup(ageGroupId);
	}

	@Override
	public int getLstBibForAgeGroup(int ageGroupId) {
		return commonDao.getLstBibForAgeGroup(ageGroupId);
	}

}
