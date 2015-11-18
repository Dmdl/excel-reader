package com.meetplanner.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.meetplanner.dto.Athlete;

public class CommonDaoImpl extends JdbcDaoSupport implements CommonDao{

	@Override
	public boolean saveAthlete(Athlete athlete) {
		boolean success = false;
		try{
			String sql = "INSERT INTO athlete " +
					"(name, date_of_birth, group_id,nic,employee_no) VALUES (?, ?, ?, ?, ?)";
						 
			int count = getJdbcTemplate().update(sql, new Object[] { athlete.getName(),athlete.getDateOfBirth(),athlete.getGroup(),athlete.getNic(),athlete.getEmpNo()});
			if(count>0){
				success = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return success;
	}

}
