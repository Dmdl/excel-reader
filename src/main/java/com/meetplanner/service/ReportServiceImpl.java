package com.meetplanner.service;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.meetplanner.dao.ReportDao;
import com.meetplanner.dto.ReportDTO;
import com.meetplanner.exception.GenricSqlException;

@Transactional
@Service("reportService")
public class ReportServiceImpl implements ReportService,Serializable{

	private static final long serialVersionUID = 1L;
	@Qualifier("reportdao")
	@Autowired
	private ReportDao reportDao;

	@Override
	public List<ReportDTO> getAthleteData(String ageGroup, String event,String gender) throws GenricSqlException{
		return reportDao.getAthleteData(ageGroup, event, gender);
	}

}
