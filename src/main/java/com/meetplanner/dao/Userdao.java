package com.meetplanner.dao;

import java.util.List;

import com.meetplanner.dto.RoleDTO;
import com.meetplanner.dto.UserDTO;
import com.meetplanner.exception.GenricSqlException;

public interface Userdao {

	public UserDTO findUser(String name);
	
	public List<RoleDTO> getUserRoles();
	
	public int saveUser(UserDTO user) throws GenricSqlException;
	
	public void addUserRoles(int userId,int roleId);
}
