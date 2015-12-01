package com.meetplanner.service;

import java.util.List;

import com.meetplanner.dto.RoleDTO;
import com.meetplanner.dto.UserDTO;
import com.meetplanner.exception.GenricSqlException;

public interface UserService {

public UserDTO findUser(String name);
	
	public List<RoleDTO> getUserRoles();
	
	public void saveUser(UserDTO user) throws GenricSqlException;
	
	public void addUserRoles(int userId,int roleId);
}
