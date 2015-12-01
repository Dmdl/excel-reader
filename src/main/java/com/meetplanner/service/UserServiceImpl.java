package com.meetplanner.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meetplanner.dao.Userdao;
import com.meetplanner.dto.RoleDTO;
import com.meetplanner.dto.UserDTO;
import com.meetplanner.exception.GenricSqlException;

@Service("userService")
@Transactional(rollbackFor=GenricSqlException.class)
public class UserServiceImpl implements UserService, Serializable {

	private static final long serialVersionUID = 1L;
	@Qualifier("userDao")
	@Autowired
	private Userdao userDao;

	@Override
	public UserDTO findUser(String name) {
		return userDao.findUser(name);
	}

	@Override
	public List<RoleDTO> getUserRoles() {
		return userDao.getUserRoles();
	}

	@Override
	public void saveUser(UserDTO user) throws GenricSqlException {
		user.setEnabled(true);
		BCryptPasswordEncoder encorder = new BCryptPasswordEncoder();
		user.setPassword(encorder.encode(user.getPassword()));
		int key = userDao.saveUser(user);
		List<RoleDTO> roles = user.getRoles();
		for(RoleDTO role:roles){
			this.addUserRoles(key, role.getId());
		}		
	}

	@Override
	public void addUserRoles(int userId, int roleId) {
		userDao.addUserRoles(userId, roleId);
	}

}
