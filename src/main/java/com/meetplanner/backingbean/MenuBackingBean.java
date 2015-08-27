package com.meetplanner.backingbean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * @author lakmal.d
 */

@ManagedBean(name="menuBean")
@ApplicationScoped
public class MenuBackingBean {

	public String goToFileUpload(){
		System.out.println("in goToFileUpload");
		return "faces/pages/fileUpload";
	}
}
