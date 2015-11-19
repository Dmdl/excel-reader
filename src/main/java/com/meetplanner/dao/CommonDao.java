package com.meetplanner.dao;

import java.util.List;

import com.meetplanner.dto.Athlete;

public interface CommonDao {

	public boolean saveAthlete(Athlete athlete);
	
	public List<Athlete> searchAthleteByGroupAndAge(int groupId,int ageGroupId);
}
