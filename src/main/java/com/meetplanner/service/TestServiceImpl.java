package com.meetplanner.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meetplanner.dto.TestDaoImpl;

public class TestServiceImpl {

	@Autowired
	@Qualifier("testDAO")
	private TestDaoImpl testDao;
	
	@PostConstruct
	public void insert(){
		testDao.test();
	}
}
