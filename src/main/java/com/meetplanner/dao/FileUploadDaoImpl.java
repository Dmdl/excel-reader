package com.meetplanner.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.meetplanner.dao.mappers.EventRowMapper;
import com.meetplanner.dto.EventDTO;
import com.meetplanner.dto.GroupDTO;

public class FileUploadDaoImpl extends JdbcDaoSupport implements FileUploadDao {

	@Override
	public List<GroupDTO> getAllGroups() {
		List<GroupDTO> groups = new ArrayList<GroupDTO>();
		try {
			String sql = "SELECT * FROM groups";
			groups = (List<GroupDTO>) getJdbcTemplate().query(sql,
					new BeanPropertyRowMapper<>(GroupDTO.class));
			return groups;
		} catch (Exception e) {
			e.printStackTrace();
			return groups;
		}
	}

	@Override
	public int getEventId(String eventName) {
		int eventId = -1;
		try {
			String sql = "SELECT * FROM events WHERE event_name = ?";
			EventDTO event = (EventDTO) getJdbcTemplate().queryForObject(sql,
					new Object[] { eventName }, new EventRowMapper());
			eventId = event.getId();
			return eventId;
		} catch (Exception e) {
			e.printStackTrace();
			return eventId;
		}
	}

}
