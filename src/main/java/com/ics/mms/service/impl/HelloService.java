package com.ics.mms.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.ics.mms.service.IHelloService;


public class HelloService implements IHelloService {

	@Override
	@Transactional
	public String getHello() {
		// TODO Auto-generated method stub
		return "hello 3";
	}

}
