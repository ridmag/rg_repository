package com.itelasoft.pso.dao;


import org.hibernate.Session;

import com.itelasoft.pso.beans.Authority;

public class AuthorityDao extends GenericDao<Authority, Long> implements
		IAuthorityDao {

	public Authority save(Authority authority) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(authority);
		return authority;
	}

	public Authority update(Authority authority) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(authority);
		return authority;
	}

	public Authority delete(Authority authority) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(authority);
		return authority;
	}
}
