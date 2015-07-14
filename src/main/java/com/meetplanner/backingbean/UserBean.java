package com.meetplanner.backingbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.meetplanner.service.UserBo;

@Component
@ManagedBean
@SessionScoped
public class UserBean {

	@Autowired
	UserBo userBo;
	private String value = "Test Message";

	public void setUserBo(UserBo userBo) {
		this.userBo = userBo;
	}

	public String printMsgFromSpring() {
		System.out.println("in print method....");
		return userBo.getMessage();
	}

	public UserBo getUserBo() {
		return userBo;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		System.out.println("set val "+value);
		this.value = value;
	}
}
