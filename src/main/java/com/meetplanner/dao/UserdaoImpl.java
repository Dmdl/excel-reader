package com.meetplanner.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.meetplanner.dao.mappers.RolesRowMapper;
import com.meetplanner.dao.mappers.UserRowMapper;
import com.meetplanner.dto.RoleDTO;
import com.meetplanner.dto.UserDTO;
import com.meetplanner.exception.GenricSqlException;

public class UserdaoImpl extends JdbcDaoSupport implements Userdao,Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public UserDTO findUser(String name) {
		UserDTO user = null;
		try {
			String sql = "SELECT * FROM users WHERE user_name=?";
			user = (UserDTO) getJdbcTemplate().queryForObject(sql,
					new Object[] { name }, new UserRowMapper());
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return user;
		}
	}

	@Override
	public List<RoleDTO> getUserRoles() {
		List<RoleDTO> roles = null;
		try{
			String sql = "SELECT roles.id,roles.name FROM roles";
			roles = getJdbcTemplate().query(sql, new RolesRowMapper());
			return roles;
		}catch(Exception e){
			e.printStackTrace();
			return roles;
		}
	}

	@Override
	public int saveUser(final UserDTO user) throws GenricSqlException{
		try{
			final String sql = "INSERT INTO users (user_name,password,enabled) VALUES(?,?,?)";
			KeyHolder keyHolder = new GeneratedKeyHolder();
			getJdbcTemplate().update(
			    new PreparedStatementCreator() {
			        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"user_name","password","enabled"});
			            ps.setString(1, user.getName());
			            ps.setString(2, user.getPassword());
			            ps.setBoolean(3, user.isEnabled());			            
			            return ps;
			        }
			    },keyHolder);
			int key = keyHolder.getKey().intValue();
			return key;
		}catch(Exception e){
			e.printStackTrace();
			throw new GenricSqlException();
		}
	}

	public void addUserRoles(int userId,int roleId){
		String sql = "INSERT INTO user_roles (user_id,role_id) VALUES(?,?)";
		getJdbcTemplate().update(sql, new Object[] {userId,roleId});
	}
}
