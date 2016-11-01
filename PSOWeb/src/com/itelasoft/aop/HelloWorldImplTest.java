package com.itelasoft.aop;

import junit.framework.TestCase;

import org.springframework.aop.framework.ProxyFactory;

public class HelloWorldImplTest extends TestCase {

	public void testAop(){
		 HelloWorldImpl helloWorldImpl = new HelloWorldImpl();
         SimpleAdvice advice = new SimpleAdvice();
         ProxyFactory proxyFactory = new ProxyFactory();
         proxyFactory.addAdvice(advice);
         proxyFactory.setTarget(helloWorldImpl);
         HelloWorldImpl proxy = (HelloWorldImpl) proxyFactory.getProxy();
         proxy.sayHello();
         proxy.greet();
	}
}
