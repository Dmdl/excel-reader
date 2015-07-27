package com.meetplanner.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author lakmal.d
 */

public class FacesUtils {

	public static void addErrorMessage(String message){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	}
}
