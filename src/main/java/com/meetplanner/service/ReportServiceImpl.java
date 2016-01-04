package com.meetplanner.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.meetplanner.dao.ReportDao;
import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.GroupAthleteCountDTO;
import com.meetplanner.dto.GroupAthleteDTO;
import com.meetplanner.dto.GroupDTO;
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

	@Override
	public List<GroupAthleteCountDTO> getGroupWiseAthleteCount() throws GenricSqlException {
		return reportDao.getGroupWiseAthleteCount();
	}

	@Override
	public List<GroupAthleteDTO> getGroupAthletes() throws GenricSqlException {
		List<GroupDTO> groups = reportDao.getUniqeAthleteGroups();
		List<GroupAthleteDTO> result = new ArrayList<GroupAthleteDTO>(0);
		if(null!=groups && groups.size()>0){
			for(GroupDTO each:groups){
				GroupAthleteDTO e = new GroupAthleteDTO();
				List<Athlete> athletes = reportDao.searchAthleteByGroup(each.getId());
				e.setGroupName(each.getName());
				e.setAthletes(athletes);
				result.add(e);
			}
		}
		return result;
	}

}
