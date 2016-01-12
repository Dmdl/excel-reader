package com.meetplanner.service;

import java.util.List;

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

public interface CommonService {

	public boolean saveAthlete(Athlete athlete) throws GenricSqlException;

	public List<Athlete> searchAthleteByGroupAndAge(int groupId, int ageGroupId);

	public boolean updateBibNumber(int number, int id) throws DuplicateValueException, GenricSqlException;

	public int addBibNumbers(List<Athlete> athletes) throws DuplicateValueException,GenricSqlException;

	public List<ResultDTO> getAthletesForEvents(int eventId, int ageGroupId, String gender);

	public boolean saveAthletesPerformances(List<ResultDTO> results);

	public List<Athlete> serachAthleteByBibOrName(String bib, String name,int group);

	public boolean updateAthlete(Athlete athlete) throws GenricSqlException;
	
	public Athlete getAthleteFromBibNumber(String bib,int ageGroupId,int eventId,String gender) throws GenricSqlException,NoDataException;
	
	public boolean updatePerformanceForEvent(int eventId, List<Athlete> athletes) throws GenricSqlException;
	
	public List<Athlete> getAllAthletesForGroup(int groupid);
	
	public List<EventDTO> getEventsForAthletes(int athleteId) throws GenricSqlException;
	
	public String getLastAssignBibNumber() throws GenricSqlException;
	
	public boolean addEvent(EventsDTO event);
	
	public boolean addGroup(GroupDTO group) throws Exception;
	
	public void updateEvent(EventDTO event) throws Exception;
	
	public boolean deleteEvent(int eventId) throws GenricSqlException;
	
	public void updateGroup(GroupDTO group) throws Exception;
	
	public boolean deleteGroup(int groupId);
	
	public boolean addAgeGroup(AgeGroupDTO ageGroup);
	
	public boolean updateAgeGroup(AgeGroupDTO ageGroup);
	
	public List<AgeGroupDTO> getAgeGroups() throws Exception;
	
	public void deleteAgeGroup(int id) throws Exception;
	
	public AgeGroupDTO getAgeGroup(int id);
	
	public void addEventCategory(EventCategoryDTO eventcategory) throws Exception;
	
	public List<EventCategoryDTO> getEventCategories() throws Exception;
	
	public List<AgeGroupDTO> getAgeGroupsForEvent(int eventId);
	
	public List<Athlete> searchAthleteByGenderAndAge(String gender, int ageGroupId);
	
	public String getLastAssignBibNumberForAgeGroup(int ageGroup) throws GenricSqlException;
	
	public int getStartBibForAgeGroup(int ageGroupId) throws GenricSqlException;
	
	public int getLstBibForAgeGroup(int ageGroupId);
	
	public List<EventDTO> getEventsForAgeGroupAndGender(int ageGroup,String gender);
}
