package com.meetplanner.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import com.meetplanner.dto.RoleDTO;
import com.meetplanner.dto.UserDTO;
import com.meetplanner.exception.GenricSqlException;
import com.meetplanner.service.UserService;
import com.meetplanner.util.SpringApplicationContex;


public class UserBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private UserService userService;
	private String userName;
	private String password;
	private DualListModel<RoleDTO> roles;
	
	public UserBean(){
		userService = (UserService)SpringApplicationContex.getBean("userService");
		List<RoleDTO> rolesSource = new ArrayList<RoleDTO>();
        List<RoleDTO> rolesTarget = new ArrayList<RoleDTO>();
        rolesSource = userService.getUserRoles();
        roles = new DualListModel<RoleDTO>(rolesSource, rolesTarget);
	}

	public void saveUser(){
		System.out.println("user name "+userName+" number of roles "+roles.getTarget().size());
		if(null==roles.getTarget() || roles.getTarget().size()==0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Select Roles"));
			return;
		}
		UserDTO user = new UserDTO();
		user.setName(userName);
		user.setPassword(password);
		user.setRoles(roles.getTarget());
		try{
			userService.saveUser(user);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error!", "Successfully Saved"));
		}catch(GenricSqlException e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error Occured"));
		}		
	}
	
	public void onTransferEvent(TransferEvent event){
		
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public DualListModel<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(DualListModel<RoleDTO> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
