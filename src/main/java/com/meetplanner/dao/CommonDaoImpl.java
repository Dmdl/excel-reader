package com.meetplanner.dao;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.meetplanner.dao.mappers.AthleteRowMapper;
import com.meetplanner.dto.Athlete;

public class CommonDaoImpl extends JdbcDaoSupport implements CommonDao{

	@Override
	public boolean saveAthlete(Athlete athlete) {
		boolean success = false;
		try{
			String sql = "INSERT INTO athlete " +
					"(name, date_of_birth, group_id,nic,employee_no,gender,age_group_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
						 
			int count = getJdbcTemplate().update(sql, new Object[] { athlete.getName(),athlete.getDateOfBirth(),athlete.getGroup(),athlete.getNic(),athlete.getEmpNo(),athlete.getGender(),athlete.getAgeGroup()});
			if(count>0){
				success = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public List<Athlete> searchAthleteByGroupAndAge(int groupId, int ageGroupId) {
		List<Athlete> resultList = null;
		try{			
			String sql = "SELECT athlete.id,athlete.name AS athlete_name,groups.name AS group_name,athlete.bib,athlete.group_id FROM athlete JOIN groups ON athlete.group_id=groups.id WHERE athlete.age_group_id=? AND athlete.group_id=?";
			resultList = getJdbcTemplate().query(sql, new Object[] { groupId,ageGroupId }, new AthleteRowMapper());
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			return resultList;
		}
	}

}
