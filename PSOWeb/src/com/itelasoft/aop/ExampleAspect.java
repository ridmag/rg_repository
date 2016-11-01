package com.itelasoft.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component(value="exampleAspect")
public class ExampleAspect {
	@Around("execution(public * testMe(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("aspect Around started..."+pjp.getArgs()[0]);
        String[] args={"changed hello"};
        Object retVal = pjp.proceed(args);
        System.out.println("aspect Around ended...");
        return retVal+"tested";
    }

	@Before("execution(public * testMe*(..))")
	public void doBefore() throws Throwable {
	    System.out.println("aspect Before started...");
	}

}