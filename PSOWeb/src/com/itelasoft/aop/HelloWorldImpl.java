package com.itelasoft.aop;
public class HelloWorldImpl implements HelloInterface {

        @Override
        public void greet() {
                // TODO Auto-generated method stub
                System.out.println("Have a nice Day");
        }

        @Override
        public void sayHello() {
                // TODO Auto-generated method stub
                System.out.println("Hello World");
        }

}