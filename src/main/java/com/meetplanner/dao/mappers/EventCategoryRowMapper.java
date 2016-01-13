package com.meetplanner.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meetplanner.dto.EventCategoryDTO;

public class EventCategoryRowMapper implements RowMapper<EventCategoryDTO>{

	@Override
	public EventCategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		EventCategoryDTO cat = new EventCategoryDTO();
		cat.setId(rs.getInt("id"));
		cat.setCategoryName(rs.getString("category_name"));
		cat.setPointFirst(rs.getDouble("point_first"));
		cat.setPointSecond(rs.getDouble("point_second"));
		cat.setPointThird(rs.getDouble("point_third"));
		cat.setPointForth(rs.getDouble("point_forth"));
		cat.setPointFifth(rs.getDouble("point_fifth"));
		cat.setPointSixth(rs.getDouble("point_sixth"));
		return cat;
	}

}
