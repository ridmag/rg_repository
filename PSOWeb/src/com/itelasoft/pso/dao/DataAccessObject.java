package com.itelasoft.pso.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DataAccessObject {
	@Autowired
	protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Session getCurrentSession() {
		try {
			return this.sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			return openSession();
		}
	}
	
	public Session openSession() {
		return this.sessionFactory.openSession();
	}
}
