package com.meetplanner.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.meetplanner.dao.mappers.AthleteEventRowMapper;
import com.meetplanner.dao.mappers.AthleteReportRowMapper;
import com.meetplanner.dao.mappers.AthleteRowMapper;
import com.meetplanner.dao.mappers.GroupRowMapper;
import com.meetplanner.dao.mappers.GroupWiseAthleteCountMapper;
import com.meetplanner.dao.mappers.ReportRowMapper;
import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.GroupAthleteCountDTO;
import com.meetplanner.dto.GroupDTO;
import com.meetplanner.dto.PlayerEventDTO;
import com.meetplanner.dto.PlayerListDTO;
import com.meetplanner.dto.ReportDTO;
import com.meetplanner.exception.GenricSqlException;

public class ReportDaoImpl extends JdbcDaoSupport implements ReportDao,Serializable {

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

	@Override
	public List<PlayerListDTO> getGroupWiseAthlete(int groupId, int ageGroupId,String gender) {
		try{
			String sql = "SELECT athlete.id AS athlete_id,athlete.name AS athlete_name,athlete.date_of_birth,athlete.bib,athlete.gender,groups.name AS group_name,events.event_name AS event_name,age_groups.age_group AS age_group"+
						" FROM athlete JOIN groups ON athlete.group_id=groups.id"+
						" JOIN age_groups ON age_groups.id=athlete.age_group_id"+
						" LEFT JOIN athlete_events ON athlete.id=athlete_events.athlete_id"+
						" JOIN EVENTS ON events.id=athlete_events.event_id"+
						" WHERE athlete.group_id=? AND athlete.age_group_id=? AND athlete.gender=?";
			
			return getJdbcTemplate().query(sql, new Object[] {groupId,ageGroupId,gender}, new AthleteReportRowMapper());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<PlayerEventDTO> getEventWiseAthletes(int eventId,int ageGroupId, String gender) {
		try{
			String sql = "SELECT athlete.id AS athlete_id,athlete.bib AS bib_number,athlete.name AS athlete_name,athlete.date_of_birth,groups.name AS group_name,athlete_events.performance,athlete_events.place"+
						" FROM athlete_events"+
						" LEFT JOIN athlete ON athlete.id=athlete_events.athlete_id"+
						" LEFT JOIN groups ON athlete.group_id=groups.id"+
						" WHERE athlete_events.event_id=? AND athlete.age_group_id=? AND athlete.gender=?";
			
			return getJdbcTemplate().query(sql, new Object[] {eventId,ageGroupId,gender}, new AthleteEventRowMapper());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<PlayerEventDTO> getEventWiseAthletesWithPlace(int selectedEvent, int selectedAgeGroup, String gender) {
		try{
			String sql = "SELECT athlete.id AS athlete_id,athlete.bib AS bib_number,athlete.name AS athlete_name,athlete.date_of_birth,groups.name AS group_name,athlete_events.performance,athlete_events.place"+
						" FROM athlete_events"+
						" LEFT JOIN athlete ON athlete.id=athlete_events.athlete_id"+
						" LEFT JOIN groups ON athlete.group_id=groups.id"+
						" WHERE athlete_events.event_id=? AND athlete.age_group_id=? AND athlete.gender=? AND athlete_events.place!=0 ORDER BY athlete_events.place ASC";
			
			return getJdbcTemplate().query(sql, new Object[] {selectedEvent,selectedAgeGroup,gender}, new AthleteEventRowMapper());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
