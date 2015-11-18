package com.meetplanner.service;

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

}
