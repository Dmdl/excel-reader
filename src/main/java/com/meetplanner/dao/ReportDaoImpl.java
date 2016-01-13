package com.meetplanner.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.meetplanner.dao.mappers.AthleteRowMapper;
import com.meetplanner.dao.mappers.GroupRowMapper;
import com.meetplanner.dao.mappers.GroupWiseAthleteCountMapper;
import com.meetplanner.dao.mappers.ReportRowMapper;
import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.GroupAthleteCountDTO;
import com.meetplanner.dto.GroupDTO;
import com.meetplanner.dto.ReportDTO;
import com.meetplanner.exception.GenricSqlException;

public class ReportDaoImpl extends JdbcDaoSupport implements ReportDao,
		Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<ReportDTO> getAthleteData(String ageGroup, String event,String gender) throws GenricSqlException{
		List<ReportDTO> result = null;
		try {
			String sql = "SELECT athlete.id,athlete.name,athlete.date_of_birth,athlete.nic ,athlete.gender,athlete.bib,athlete_events.performance,age_groups.age_group"
					+ " FROM athlete"
					+ " JOIN athlete_events ON athlete.id=athlete_events.athlete_id"
					+ " LEFT JOIN age_groups ON athlete.age_group_id=age_groups.id"
					+ " WHERE athlete.age_group_id=? AND athlete.gender=? AND athlete_events.event_id=?";
			result = getJdbcTemplate().query(sql,new Object[] { ageGroup, gender, event },new ReportRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new GenricSqlException(e);
		}
		return result;
	}

	@Override
	public List<GroupAthleteCountDTO> getGroupWiseAthleteCount() throws GenricSqlException {
		List<GroupAthleteCountDTO> result = null;
		try{
			String sql = "SELECT groups.name,COUNT(athlete.group_id) AS total FROM athlete"+
						" JOIN groups ON athlete.group_id=groups.id"+
						" GROUP BY athlete.group_id";
			result = getJdbcTemplate().query(sql,new GroupWiseAthleteCountMapper());
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public List<GroupDTO> getUniqeAthleteGroups() throws GenricSqlException{
		List<GroupDTO> ids = null;
		try{
			String sql = "SELECT DISTINCT(group_id),groups.name FROM athlete"+
						" JOIN groups ON athlete.group_id=groups.id";
			ids = getJdbcTemplate().query(sql,new GroupRowMapper());
		}catch(Exception e){
			throw new GenricSqlException(e);
		}
		return ids;
	}
	
	public List<Athlete> searchAthleteByGroup(int groupId){
		List<Athlete> resultList = null;
		try{			
			String sql = "SELECT athlete.id,athlete.name AS athlete_name,groups.name AS group_name,athlete.bib,athlete.group_id FROM athlete JOIN groups ON athlete.group_id=groups.id WHERE athlete.group_id=?";
			resultList = getJdbcTemplate().query(sql, new Object[] {groupId }, new AthleteRowMapper());
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			return resultList;
		}
	}

}