package com.meetplanner.backingbean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author lakmal.d
 */

public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;

	public String doLogin() throws IOException, ServletException {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward((ServletRequest) context.getRequest(),
				(ServletResponse) context.getResponse());
		FacesContext.getCurrentInstance().responseComplete();
		return null;
	}

	public String doLogout(){
		try{
			SecurityContextHolder.clearContext();
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	        return "/login.xhtml?faces-redirect=true";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}			
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
