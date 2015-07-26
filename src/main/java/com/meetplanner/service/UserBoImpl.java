package com.meetplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.meetplanner.dto.TestDaoImpl;

@Service
public class UserBoImpl implements UserBo {
	
	@Autowired
	@Qualifier("testDAO")
	private TestDaoImpl testDao;

	public String getMessage() {
		System.out.println("in service.......");
		return "JSF 2 + Spring Integration";
	}

	@Override
	public void insert() {
		testDao.test();
	}
}
