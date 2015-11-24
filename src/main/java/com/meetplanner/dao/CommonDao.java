package com.meetplanner.dao;

import java.util.List;

import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.ResultDTO;
import com.meetplanner.exception.DuplicateValueException;
import com.meetplanner.exception.GenricSqlException;
import com.meetplanner.exception.NoDataException;

public interface CommonDao {

	public boolean saveAthlete(Athlete athlete);

	public List<Athlete> searchAthleteByGroupAndAge(int groupId, int ageGroupId);

	public boolean updateBibNumber(int number, int id) throws DuplicateValueException, GenricSqlException;

	public int addBibNumbers(List<Athlete> athletes) throws DuplicateValueException,GenricSqlException;

	public List<ResultDTO> getAthletesForEvents(int eventId, int ageGroupId, String gender);

	public boolean saveAthletesPerformances(List<ResultDTO> results);

	public List<Athlete> serachAthleteByBibOrName(String bib, String name);

	public boolean updateAthlete(Athlete athlete);
	
	public Athlete getAthleteFromBibNumber(String bib,int ageGroupId,int eventId,String gender) throws GenricSqlException,NoDataException;
}
