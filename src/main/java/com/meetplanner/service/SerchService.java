package com.meetplanner.service;

import com.meetplanner.dto.Athlete;

import java.util.List;

public interface SerchService {

	public List<Athlete> searchAthlete(String bib, String name);
}
