package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.EventDTO;

public class EventRowMapper implements RowMapper<EventDTO> {

	@Override
	public EventDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		EventDTO event = new EventDTO();
		event.setId(rs.getInt("id"));
		event.setEventName(rs.getString("event_name"));
		return event;
	}

}
