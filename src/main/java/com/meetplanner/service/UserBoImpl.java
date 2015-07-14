package com.meetplanner.service;

import org.springframework.stereotype.Service;

@Service
public class UserBoImpl implements UserBo {

	public String getMessage() {
		System.out.println("in service.......");
		return "JSF 2 + Spring Integration";
	}
}
