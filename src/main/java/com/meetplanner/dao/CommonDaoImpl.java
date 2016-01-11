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

import com.meetplanner.dao.mappers.AgeGroupEventRowMapper;
import com.meetplanner.dao.mappers.AgeGroupRowMapper;
import com.meetplanner.dao.mappers.AthleteRowMapper;
import com.meetplanner.dao.mappers.EventAgeGroupRowMapper;
import com.meetplanner.dao.mappers.EventCategoryRowMapper;
import com.meetplanner.dao.mappers.EventResultMapper;
import com.meetplanner.dao.mappers.EventResultRowMapper;
import com.meetplanner.dao.mappers.EventRowMapper;
import com.meetplanner.dto.AgeGroupDTO;
import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.EventCategoryDTO;
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
	public boolean addEvent(final EventsDTO event) {
		boolean ok = false;
		try{
			final String sql = "INSERT INTO EVENTS(event_name,type,participants,event_category_id) VALUES (?,?,?,?)";
//			int count = getJdbcTemplate().update(sql, new Object[] {event.getEvent(),event.getType(),event.getParticipants(),event.getEventCategoryId()});
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			getJdbcTemplate().update(
			    new PreparedStatementCreator() {
			        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"event_name","type","participants","event_category_id"});
			            ps.setString(1, event.getEvent());
			            ps.setString(2, event.getType());
			            ps.setString(3, event.getParticipants());
			            ps.setInt(4, event.getEventCategoryId());	            
			            return ps;
			        }
			    },keyHolder);
			int key = keyHolder.getKey().intValue();
			if(null!=event.getAgeGroups() && event.getAgeGroups().size()>0){
				for(AgeGroupDTO e:event.getAgeGroups()){
					addEventAgeGroups(key, e.getId());
				}
			}			
			ok = true;
		}catch(Exception e){
			e.printStackTrace();
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
		String sql = "UPDATE events SET event_name=?,type=?,participants=?,event_category_id=? WHERE id=?";
		getJdbcTemplate().update(sql, event.getEventName(),event.getType(),event.getParticipants(),event.getEventCategoryId(),event.getId());
		
		deleteEventAgeGroup(event.getId());
		if(null!=event.getAgeGroups() && event.getAgeGroups().size()>0){
			for(AgeGroupDTO e:event.getAgeGroups()){
				addEventAgeGroups(event.getId(), e.getId());
			}
		}
	}

	@Override
	public boolean deleteEvent(int eventId) throws GenricSqlException{
		boolean ok = false;
		try{
			String sql = "DELETE FROM events WHERE id=?";
			int count = getJdbcTemplate().update(sql, new Object[] {eventId});
			deleteEventAgeGroup(eventId);
			if(count>0){
				ok = true;
			}
			return ok;
		}catch(Exception e){
			throw new GenricSqlException();
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

	@Override
	public List<AgeGroupDTO> getAgeGroups() throws Exception {
		List<AgeGroupDTO> result = null;
		String sql="SELECT * FROM age_groups";
		result = getJdbcTemplate().query(sql, new AgeGroupRowMapper());
		return result;
	}

	@Override
	public boolean updateAgeGroup(AgeGroupDTO ageGroup) {
		boolean ok = false;
		try{
			String sql = "UPDATE age_groups SET age_group=?,from_date=?,to_date=?,bib_from=?,bib_to=? WHERE id=?";
			int count = getJdbcTemplate().update(sql, ageGroup.getAgeGroup(),ageGroup.getFromAge(),ageGroup.getToAge(),ageGroup.getFromBibNumber(),ageGroup.getToBibNumber(),ageGroup.getId());
			if(count>0){
				ok = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ok;
	}

	@Override
	public boolean addAgeGroup(AgeGroupDTO ageGroup) {
		boolean ok = false;
		try{
			String sql = "INSERT INTO age_groups(age_group,from_date,to_date,bib_from,bib_to) VALUES (?,?,?,?,?)";
			int count = getJdbcTemplate().update(sql, new Object[] {ageGroup.getAgeGroup(),ageGroup.getFromAge(),ageGroup.getToAge(),ageGroup.getFromBibNumber(),ageGroup.getToBibNumber()});
			if(count>0){
				ok = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ok;
	}

	@Override
	public void deleteAgeGroup(int id) throws Exception{
		String sql = "DELETE FROM age_groups WHERE id=?";
		getJdbcTemplate().update(sql, new Object[] {id});
	}

	@Override
	public List<EventDTO> getFilteredEventList(String gender, String eventType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AgeGroupDTO getAgeGroup(int id) {
		try{
			String sql = "SELECT * FROM age_groups WHERE id=?";
			AgeGroupDTO ageGroup = getJdbcTemplate().queryForObject(sql, new Object[] {id},new AgeGroupRowMapper());
			return ageGroup;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void addEventCategory(EventCategoryDTO eventcategory) throws Exception {
		String sql = "INSERT INTO event_category(category_name,point_first,point_second,point_third) VALUES (?,?,?,?)";
		getJdbcTemplate().update(sql,new Object[] {eventcategory.getCategoryName(),eventcategory.getPointFirst(),eventcategory.getPointSecond(),eventcategory.getPointThird()});
	}

	@Override
	public List<EventCategoryDTO> getEventCategories() throws Exception {
		String sql = "SELECT * FROM event_category";
		return getJdbcTemplate().query(sql, new EventCategoryRowMapper());
	}
	
	private void addEventAgeGroups(int eventId,int ageGroupId){
		String sql = "INSERT INTO event_age_groups(event_id,age_group_id) VALUES (?,?)";
		getJdbcTemplate().update(sql,new Object[] {eventId,ageGroupId});
	}

	@Override
	public List<AgeGroupDTO> getAgeGroupsForEvent(int eventId) {
		try{
			String sql = "SELECT age_groups.id,age_groups.age_group,age_groups.from_date,age_groups.to_date,age_groups.bib_from,age_groups.bib_to FROM event_age_groups"+
						" JOIN age_groups ON event_age_groups.age_group_id=age_groups.id"+
						" WHERE event_age_groups.event_id=?";
			return getJdbcTemplate().query(sql, new Object[]{eventId},new EventAgeGroupRowMapper());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	private void deleteEventAgeGroup(int eventId){
		String sql = "DELETE FROM event_age_groups WHERE event_id=?";
		getJdbcTemplate().update(sql, new Object[] {eventId});
	}

	@Override
	public List<Athlete> searchAthleteByGenderAndAge(String gender,int ageGroupId) {
		List<Athlete> resultList = null;
		try{			
			String sql = "SELECT athlete.id,athlete.name AS athlete_name,groups.name AS group_name,athlete.bib,athlete.group_id FROM athlete JOIN groups ON athlete.group_id=groups.id WHERE athlete.age_group_id=? AND athlete.gender=?";
			resultList = getJdbcTemplate().query(sql, new Object[] {ageGroupId ,gender}, new AthleteRowMapper());
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			return resultList;
		}
	}
	
	public String getLastAssignBibNumberForAgeGroup(int ageGroup) throws GenricSqlException{
		String bib= null;
		try{
			String sql="SELECT bib FROM athlete WHERE age_group_id=? ORDER BY bib DESC LIMIT 1";
			bib = (String)getJdbcTemplate().queryForObject(sql,new Object[]{ageGroup}, String.class);
		}catch(Exception e){
			return bib;
		}
		return bib;
	}

	@Override
	public int getStartBibForAgeGroup(int ageGroupId) throws GenricSqlException {
		String sql = "SELECT bib_from FROM age_groups WHERE id=?";
		int fromBib = getJdbcTemplate().queryForObject(sql,new Object[]{ageGroupId}, Integer.class);
		return fromBib;
	}

	@Override
	public int getLstBibForAgeGroup(int ageGroupId) {
		String sql = "SELECT bib_to FROM age_groups WHERE id=?";
		int fromBib = getJdbcTemplate().queryForObject(sql,new Object[]{ageGroupId}, Integer.class);
		return fromBib;
	}

	@Override
	public List<Integer> checkForExistingBibNumbers(List<Integer> bib) {
		return null;
	}

	@Override
	public List<EventDTO> getEventsForAgeGroupAndGender(int ageGroup,String gender) {
		List<EventDTO> events = new ArrayList<EventDTO>(0);
		try{
			String sql = "SELECT DISTINCT(event_age_groups.event_id),events.event_name,events.type,events.participants"+
						" FROM event_age_groups"+
						" JOIN EVENTS ON events.id=event_age_groups.event_id"+
						" WHERE age_group_id=? AND events.participants=?"+
						" ORDER BY events.id";
			events = getJdbcTemplate().query(sql, new Object[]{ageGroup,gender},new AgeGroupEventRowMapper());
			return events;
		}catch(Exception e){
			e.printStackTrace();
			return events;
		}
	}
		
}
