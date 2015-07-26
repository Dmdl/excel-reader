package com.meetplanner.dto;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class TestDaoImpl extends JdbcDaoSupport{

	public void test(){
		try{
			String sql="INSERT INTO invoices VALUES (10,'2015-07-10')";
			getJdbcTemplate().update(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
}
