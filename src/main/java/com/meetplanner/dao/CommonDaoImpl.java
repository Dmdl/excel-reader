package com.meetplanner.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.meetplanner.dao.mappers.AthleteRowMapper;
import com.meetplanner.dao.mappers.EventResultMapper;
import com.meetplanner.dao.mappers.EventResultRowMapper;
import com.meetplanner.dao.mappers.EventRowMapper;
import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.EventsDTO;
import com.meetplanner.dto.GroupDTO;
import com.meetplanner.dto.ResultDTO;
import com.meetplanner.exception.DuplicateValueException;
import com.meetplanner.exception.GenricSqlException;
import com.meetplanner.exception.NoDataException;

public class CommonDaoImpl extends JdbcDaoSupport implements CommonDao,Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public boolean saveAthlete(final Athlete athlete) throws GenricSqlException{
		boolean success = false;
		try{
			final String sql = "INSERT INTO athlete " +
					"(name, date_of_birth, group_id,nic,employee_no,gender,age_group_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
						 
//			int count = getJdbcTemplate().update(sql, new Object[] { athlete.getName(),athlete.getDateOfBirth(),athlete.getGroup(),athlete.getNic(),athlete.getEmpNo(),athlete.getGender(),athlete.getAgeGroup()});
			KeyHolder keyHolder = new GeneratedKeyHolder();
			getJdbcTemplate().update(
			    new PreparedStatementCreator() {
			        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"name","date_of_birth","group_id","nic","employee_no","gender","age_group_id"});
			            ps.setString(1, athlete.getName());
			            ps.setDate(2, new java.sql.Date(athlete.getDateOfBirth().getTime()));
			            ps.setString(3, athlete.getGroup());
			            ps.setString(4, athlete.getNic());
			            ps.setString(5, athlete.getEmpNo());
			            ps.setString(6, athlete.getGender());
			            ps.setString(7, athlete.getAgeGroup());
			            return ps;
			        }
			    },keyHolder);
			int key = keyHolder.getKey().intValue();
			for(EventDTO each:athlete.getEvents()){
				this.addAthletesEvents(key, each.getId());
			}
			if(key>0){
				success = true;
			}
		}catch(Exception e){
			throw new GenricSqlException(e);
		}
		return success;
	}

	@Override
	public List<Athlete> searchAthleteByGroupAndAge(int groupId, int ageGroupId) {
		List<Athlete> resultList = null;
		try{			
			String sql = "SELECT athlete.id,athlete.name AS athlete_name,groups.name AS group_name,athlete.bib,athlete.group_id FROM athlete JOIN groups ON athlete.group_id=groups.id WHERE athlete.age_group_id=? AND athlete.group_id=?";
			resultList = getJdbcTemplate().query(sql, new Object[] {ageGroupId ,groupId }, new AthleteRowMapper());
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			return resultList;
		}
	}

	@Override
	public boolean updateBibNumber(int number,int id) throws DuplicateValueException,GenricSqlException{
		boolean result = false;
		try{
			String sql = "UPDATE athlete SET bib=? WHERE id=?";
			int count = getJdbcTemplate().update(sql, number,id);
			if(count>0){
				result = true;
			}
		}catch(DataIntegrityViolationException e){
			throw new DuplicateValueException(e);
		}catch (Exception e) {
			throw new GenricSqlException(e);
		}				
		return result;
	}

	@Override
	public int addBibNumbers(List<Athlete> athletes) throws DuplicateValueException,GenricSqlException{
		int count = 0;
		try{
			for(Athlete each:athletes){
				boolean ok = updateBibNumber(Integer.parseInt(each.getBibNumber()), Integer.parseInt(each.getId()));
				if(ok){
					count++;
				}
			}
		}catch(DataIntegrityViolationException e){
			throw new DuplicateValueException(e);
		}catch (Exception e) {
			throw new GenricSqlException(e);
		}	
		return count;
	}

	@Override
	public List<ResultDTO> getAthletesForEvents(int eventId, int ageGroupId,String gender) {
		List<ResultDTO> results = new ArrayList<ResultDTO>();
		try{
			String sql="SELECT athlete.id AS athlete_id,athlete_events.event_id AS event_id,athlete.name AS athlete_name,groups.name AS group_name,athlete.bib,athlete_events.performance "+
					"FROM athlete_events JOIN athlete ON athlete_events.athlete_id=athlete.id "+
					"LEFT JOIN groups ON athlete.group_id=groups.id "+
					"WHERE athlete_events.event_id=? AND athlete.gender=? AND athlete.age_group_id=?";
			
			results = getJdbcTemplate().query(sql, new Object[] {eventId ,gender, ageGroupId}, new EventResultMapper());
		}catch(Exception e){
			e.printStackTrace();
		}
		return results;
	}

	private void updatePerformance(int athleteid,int eventId,double performance){
		try{
			String sql = "UPDATE athlete_events SET performance=? WHERE athlete_id=? AND event_id=?";
			getJdbcTemplate().update(sql, performance,athleteid,eventId);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	@Override
	public boolean saveAthletesPerformances(List<ResultDTO> results) {
		boolean ok = false;
		try{
			for(ResultDTO each:results){
				updatePerformance(each.getAthleteId(), each.getEventId(), Double.parseDouble(each.getPerformance()));
			}
			ok = true;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return ok;
	}

	@Override
	public List<Athlete> serachAthleteByBibOrName(String bib, String name) {
		return null;
	}

	@Override
	public boolean updateAthlete(Athlete athlete) throws GenricSqlException{
		boolean ok = false;
		try{
			this.deleteEventsForAthlete(Integer.parseInt(athlete.getId()));
			for(EventDTO each:athlete.getEvents()){
				this.addAthletesEvents(Integer.parseInt(athlete.getId()), each.getId());
			}
			String sql = "UPDATE athlete SET name=? ,date_of_birth=? ,group_id=? ,nic=? ,gender=? ,age_group_id=? WHERE id=?";
			int rows = getJdbcTemplate().update(sql, athlete.getName(),athlete.getDateOfBirth(),athlete.getGroupId(),athlete.getNic(),athlete.getGender(),athlete.getAgeGroup(),athlete.getId());
			if(rows>0){
				ok=true;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new GenricSqlException(e);
		}
		return ok;
	}

	@Override
	public Athlete getAthleteFromBibNumber(String bib,int ageGroupId,int eventId,String gender) throws GenricSqlException,NoDataException{
		Athlete athlete = null;
		try{
			String sql = "SELECT athlete.id AS athlete_id,athlete.name AS thlete_name,athlete.nic,athlete.bib,groups.name AS group_name,athlete_events.performance"+
						" FROM athlete LEFT JOIN groups ON athlete.group_id=groups.id"+
						" LEFT JOIN athlete_events ON athlete.id=athlete_events.athlete_id"+
						" WHERE athlete.bib=? AND athlete.age_group_id=? AND athlete_events.event_id=? AND athlete.gender=?";
			athlete = getJdbcTemplate().queryForObject(sql, new Object[] {bib,ageGroupId,eventId,gender}, new EventResultRowMapper());
		}catch(EmptyResultDataAccessException e){
			throw new NoDataException(e);
		}catch(Exception e){
			throw new GenricSqlException(e);
		}
		return athlete;
	}

	@Override
	public boolean updatePerformanceForEvent(int eventId, List<Athlete> athletes) throws GenricSqlException{
		boolean ok = false;
		try{
			for(Athlete each:athletes){
				updatePerformance(Integer.parseInt(each.getId()),eventId,Double.parseDouble(each.getEventResult().getPerformance()));
			}
			ok = true;
		}catch(Exception e){
			throw new GenricSqlException(e);
		}
		return ok;
	}

	@Override
	public List<Athlete> getAllAthletesForGroup(int groupid) {
		List<Athlete> athletes = new ArrayList<Athlete>(0);
		try{
			String sql = "SELECT athlete.id,athlete.name AS athlete_name,athlete.bib,groups.name AS group_name,athlete.group_id AS group_id FROM athlete"+
						" JOIN groups ON athlete.group_id=groups.id WHERE athlete.group_id=?";
			athletes = getJdbcTemplate().query(sql, new Object[] {groupid}, new AthleteRowMapper());
		}catch(Exception e){
			e.printStackTrace();
		}
		return athletes;
	}
	
	private void addAthletesEvents(int athleteId,int eventId){
		System.out.println("insert athlete event "+eventId +" for athlete "+athleteId);
		String sql = "INSERT INTO athlete_events(athlete_id,event_id) VALUES(?,?)";
		getJdbcTemplate().update(sql, new Object[] {athleteId,eventId});
	}

	@Override
	public List<EventDTO> getEventsForAthletes(int athleteId) throws GenricSqlException{
		List<EventDTO> events;
		try{
			String sql = "SELECT athlete_events.event_id AS id,events.event_name FROM athlete_events"+
						" LEFT JOIN EVENTS ON athlete_events.event_id=events.id"+
						" WHERE athlete_events.athlete_id=?";
			events = getJdbcTemplate().query(sql, new Object[] {athleteId}, new EventRowMapper());
		}catch(Exception e){
			throw new GenricSqlException();
		}
		return events;
	}

	private void deleteEventsForAthlete(int athleteId){
		String sql="DELETE FROM athlete_events WHERE athlete_id=?";
		getJdbcTemplate().update(sql, new Object[] {athleteId});
	}

	@Override
	public String getLastAssignBibNumber() throws GenricSqlException{
		String bib= null;
		try{
//			String sql="SELECT bib FROM athlete WHERE bib IS NOT NULL ORDER BY id DESC LIMIT 1";
			String sql="SELECT bib FROM athlete ORDER BY bib DESC LIMIT 1";
			bib = (String)getJdbcTemplate().queryForObject(sql, String.class);
		}catch(Exception e){
			return bib;
		}
		return bib;
	}

	@Override
	public boolean addEvent(EventsDTO event) {
		boolean ok = false;
		String sql = "INSERT INTO EVENTS(event_name,type,participants) VALUES (?,?,?)";
		int count = getJdbcTemplate().update(sql, new Object[] {event.getEvent(),event.getType(),event.getParticipants()});
		if(count>0){
			ok = true;
		}					
		return ok;
	}

	@Override
	public boolean addGroup(GroupDTO group) throws Exception{
		boolean res = false;
		String sql = "INSERT INTO groups(NAME) VALUES (?)";
		int count = getJdbcTemplate().update(sql, new Object[] {group.getName()});
		if(count>0){
			res = true;
		}					
		return res;
	}

	@Override
	public void updateEvent(EventDTO event) throws Exception{
		String sql = "UPDATE events SET event_name=?,type=?,participants=? WHERE id=?";
		getJdbcTemplate().update(sql, event.getEventName(),event.getType(),event.getParticipants(),event.getId());
	}

	@Override
	public boolean deleteEvent(int eventId) {
		boolean ok = false;
		try{
			String sql = "DELETE FROM events WHERE id=?";
			int count = getJdbcTemplate().update(sql, new Object[] {eventId});
			if(count>0){
				ok = true;
			}
			return ok;
		}catch(Exception e){
			e.printStackTrace();
			return ok;
		}
	}

	@Override
	public void updateGroup(GroupDTO group) throws Exception {
		String sql = "UPDATE groups SET name=? WHERE id=?";
		getJdbcTemplate().update(sql, group.getName(),group.getId());
	}

	@Override
	public boolean deleteGroup(int groupId) {
		boolean ok = false;
		try{
			String sql = "DELETE FROM groups WHERE id=?";
			int count = getJdbcTemplate().update(sql, new Object[] {groupId});
			if(count>0){
				ok = true;
			}
			return ok;
		}catch(Exception e){
			e.printStackTrace();
			return ok;
		}
	}
}
