package com.meetplanner.service;

import java.util.List;

import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.ResultDTO;

public interface CommonService {

	public boolean saveAthlete(Athlete athlete);
	
	public List<Athlete> searchAthleteByGroupAndAge(int groupId, int ageGroupId);
	
	public boolean updateBibNumber(int number,int id);
	
	public int addBibNumbers(List<Athlete> athletes);
	
	public List<ResultDTO> getAthletesForEvents(int eventId, int ageGroupId,String gender);
	
	public boolean saveAthletesPerformances(List<ResultDTO> results);
}
