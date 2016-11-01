package com.itelasoft.aop;

import org.springframework.aop.ThrowsAdvice;

import com.itelasoft.exceptions.DataAccessException;

public class ExceptionTranslator implements ThrowsAdvice {
	public void translateException(Exception e) throws Throwable {
		
		throw new DataAccessException("test");
	}
}