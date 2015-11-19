package com.meetplanner.service;

import java.util.List;

import com.meetplanner.dto.Athlete;

public interface CommonService {

	public boolean saveAthlete(Athlete athlete);
	
	public List<Athlete> searchAthleteByGroupAndAge(int groupId, int ageGroupId);
}
