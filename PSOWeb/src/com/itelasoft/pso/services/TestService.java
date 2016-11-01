package com.itelasoft.pso.services;

import org.springframework.stereotype.Component;

@Component(value = "testService")
public class TestService implements ITestService {

	@Override
	public void testMe(String arg) {
		System.out.println("tested Me:" + arg);

	}

}
