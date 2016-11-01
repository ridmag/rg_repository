package com.itelasoft.sample;

import javax.faces.event.ActionEvent;

import com.itelasoft.pso.services.IServiceLocator;
import com.itelasoft.pso.services.ServiceLocator;


public class SampleBackingBean {

	private String testText="testText";
	private int testInt = 10;
	private int test;
	
	protected IServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	public SampleBackingBean() {
		init();
	}
	public void init(){
		//testText=serviceLocator.getUserService().getUser(1).getFirstName();
	}
	public String getTestText() {
		return testText;
	}
	public void setTestText(String testText) {
		this.testText = testText;
	}
	public int getTest() {
		return test;
	}
	public void setTest(int test) {
		this.test = test;
	}
	public int getTestInt() {
		return testInt;
	}
	public void setTestInt(int testInt) {
		this.testInt = testInt;
	}
	
	public void listen(ActionEvent event){
		testText = "chanded text";
		testInt+=1;
		
	}
	
}
