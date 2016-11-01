package com.itelasoft.util;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class JdbcDriverLeakPreventer implements ServletContextListener {
	  @Override
	  public void contextInitialized(ServletContextEvent sce) {
	    //Nothing to do
	  }

	  @Override
	  public void contextDestroyed(ServletContextEvent sce) {
	    ClassLoader applicationClassLoader = this.getClass().getClassLoader();
	    Enumeration driverEnumeration = DriverManager.getDrivers();
	    while (driverEnumeration.hasMoreElements()) {
	      Driver driver = (Driver) driverEnumeration.nextElement();
	      ClassLoader driverClassLoader = driver.getClass().getClassLoader();
	      if (driverClassLoader != null 
	          && driverClassLoader.equals(applicationClassLoader)){
	        try {
	          DriverManager.deregisterDriver(driver);
	        } catch (SQLException e) {
	          e.printStackTrace(); //TODO Replace with your exception handling
	        }
	      }
	    }
	  }
}