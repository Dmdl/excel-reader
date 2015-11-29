package com.meetplanner.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.meetplanner.dao.mappers.UpdateAthleteRowMapper;
import com.meetplanner.dto.Athlete;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.EventsDTO;
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
	public List<Athlete> serachAthleteByBibOrName(String bib, String name) {
		List<Athlete> results = null;
		try{
			String sql = "SELECT * FROM athlete WHERE athlete.name LIKE :pname";
			String quName = "%" + name + "%";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("pname", quName);
			if(null!=bib && !"".equals(bib)){
				System.out.println("Appending bib number...");
				sql +=" AND bib=:pbib";
				parameters.put("pbib", bib);
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

}
