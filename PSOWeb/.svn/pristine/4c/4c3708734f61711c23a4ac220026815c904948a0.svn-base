package com.itelasoft.pso.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.ProgramType;

public class ProgramTypeDao extends GenericDao<ProgramType, Long> implements
		IProgramTypeDao {

	public ProgramType retrieveByName(String programTypeName) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramType.class).add(
				Restrictions.eq("name", programTypeName));
		return (ProgramType) criteria.uniqueResult();
	}
}
