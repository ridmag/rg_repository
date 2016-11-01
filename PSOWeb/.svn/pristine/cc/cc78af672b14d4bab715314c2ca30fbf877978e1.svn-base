package com.itelasoft.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class SimpleAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
                // TODO Auto-generated method stub
                System.out.println("Hello");
                Object object = methodInvocation.proceed();
                System.out.println("Done.......");
                return object;
        }

}