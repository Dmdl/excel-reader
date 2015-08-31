package com.meetplanner.backingbean;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author lakmal.d
 */

public class LoginBean {

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
		// It's OK to return null here because Faces is just going to exit.
		return null;
	}

	public void doLogout(){
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		/*try{
			Object session = externalContext.getSession(false);
			HttpSession httpSession = (HttpSession) session;
			SecurityContextHolder.clearContext();
			httpSession.invalidate();
			userName=null;
			return "login";
//			externalContext.redirect("faces/pages/login.xhtml");
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}*/
		SecurityContextHolder.clearContext();
//        return "login";
		try{
			externalContext.redirect("login.xhtml");
		}catch(Exception e){
			e.printStackTrace();
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
