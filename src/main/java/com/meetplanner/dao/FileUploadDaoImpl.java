package com.meetplanner.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.meetplanner.dto.GroupDTO;

public class FileUploadDaoImpl extends JdbcDaoSupport implements FileUploadDao {

	@Override
	public List<GroupDTO> getAllGroups() {
		List<GroupDTO> groups = new ArrayList<GroupDTO>();
		try {
			String sql = "SELECT * FROM groups";
			groups = (List<GroupDTO>)getJdbcTemplate().query(sql,
					new BeanPropertyRowMapper<>(GroupDTO.class));
			return groups;
		} catch (Exception e) {
			e.printStackTrace();
			return groups;
		}
	}

}
