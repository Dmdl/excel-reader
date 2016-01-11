package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.EventDTO;

public class AgeGroupEventRowMapper implements RowMapper<EventDTO>{

	@Override
	public EventDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		EventDTO event = new EventDTO();
		event.setId(rs.getInt("event_id"));
		event.setEventName(rs.getString("event_name"));
		event.setType(rs.getString("type"));
		event.setParticipants(rs.getString("participants"));
		return event;
	}

}
