package com.meetplanner.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.meetplanner.dao.mappers.UpdateAthleteRowMapper;
import com.meetplanner.dto.AgeGroupDTO;
import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.EventCategoryDTO;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.EventsDTO;
import com.meetplanner.dto.GroupDTO;
import com.meetplanner.dto.ResultDTO;
import com.meetplanner.exception.GenricSqlException;

public class CommonDaoNamedImpl extends NamedParameterJdbcDaoSupport implements CommonDao {
	
	@Autowired
    public void setDs(DataSource dataSource) {
        setDataSource(dataSource);
    }

	@Override
	public boolean saveAthlete(Athlete athlete) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Athlete> searchAthleteByGroupAndAge(int groupId, int ageGroupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateBibNumber(int number, int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int addBibNumbers(List<Athlete> athletes) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ResultDTO> getAthletesForEvents(int eventId, int ageGroupId,
			String gender) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveAthletesPerformances(List<ResultDTO> results) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Athlete> serachAthleteByBibOrName(String bib, String name,int group) {
		List<Athlete> results = null;
		try{
			String sql = "SELECT athlete.id AS athlete_id,athlete.name AS athlete_name,date_of_birth,group_id,nic,gender,age_group_id,bib,groups.name AS group_name,age_groups.age_group FROM athlete LEFT JOIN groups ON athlete.group_id=groups.id LEFT JOIN age_groups ON athlete.age_group_id=age_groups.id WHERE athlete.name LIKE :pname";
			String quName = "%" + name + "%";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("pname", quName);
			if(null!=bib && !"".equals(bib)){
				System.out.println("Appending bib number...");
				sql +=" AND bib=:pbib";
				parameters.put("pbib", bib);
			}
			if(group!=0){
				System.out.println("Appending group...");
				sql +=" AND group_id=:pgroup_id";
				parameters.put("pgroup_id", group);
			}
			results = getNamedParameterJdbcTemplate().query(sql,parameters,new UpdateAthleteRowMapper());
		}catch(Exception e){
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public boolean updateAthlete(Athlete athlete) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Athlete getAthleteFromBibNumber(String bib,int ageGroupId,int eventId,String gender) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updatePerformanceForEvent(int eventId, List<Athlete> athletes)
			throws GenricSqlException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Athlete> getAllAthletesForGroup(int groupid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EventDTO> getEventsForAthletes(int athleteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLastAssignBibNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addEvent(EventsDTO event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addGroup(GroupDTO group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateEvent(EventDTO event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteEvent(int eventId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateGroup(GroupDTO group) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteGroup(int groupId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<AgeGroupDTO> getAgeGroups() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateAgeGroup(AgeGroupDTO ageGroup) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAgeGroup(AgeGroupDTO ageGroup) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteAgeGroup(int id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EventDTO> getFilteredEventList(String gender, String eventType) {
		List<EventDTO> result = new ArrayList<EventDTO>(0);
		try{
			String sql = "SELECT * FROM events";
			Map<String, Object> parameters = new HashMap<String, Object>();
			if(null!=gender || null!=eventType){
				sql+=" WHERE";
			}
			if(null!=eventType){
				sql+=" TYPE=:type";
				parameters.put("type", eventType);
			}
			if(null!=eventType){
				sql +=" AND";
			}
			if(null!=gender){
				sql +=" participants=:gender";
				parameters.put("gender", gender);
			}
			System.out.println("sql -> "+sql);
			result = getNamedParameterJdbcTemplate().query(sql,parameters,new BeanPropertyRowMapper<>(EventDTO.class));
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public AgeGroupDTO getAgeGroup(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addEventCategory(EventCategoryDTO eventcategory)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EventCategoryDTO> getEventCategories() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AgeGroupDTO> getAgeGroupsForEvent(int eventId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Athlete> searchAthleteByGenderAndAge(String gender,int ageGroupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLastAssignBibNumberForAgeGroup(int ageGroup)
			throws GenricSqlException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStartBibForAgeGroup(int ageGroupId) throws GenricSqlException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLstBibForAgeGroup(int ageGroupId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> checkForExistingBibNumbers(List<Integer> bib) {
		try{
			List<Integer> finalList = new ArrayList<Integer>(0);
			String sql = "SELECT athlete.bib FROM athlete WHERE athlete.bib IN (:ids)";
			//List<String> ids = getJdbcTemplate().queryForList(sql, new Object[]{bib},String.class);
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("ids", bib);
			List<String> ids = getNamedParameterJdbcTemplate().queryForList(sql, parameters,String.class);			
			
			if(null!=ids && ids.size()>0){
				for(String e:ids){
					finalList.add(Integer.parseInt(e));
				}				
			}
			return finalList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	public List<EventDTO> getEventsForAgeGroupAndGender(int ageGroup,
			String gender) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateEventCategory(EventCategoryDTO update) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEventCategory(int eventCatId) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
