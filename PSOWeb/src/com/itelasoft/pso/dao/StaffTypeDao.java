package com.itelasoft.pso.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.StaffType;

public class StaffTypeDao extends GenericDao<StaffType, Long> implements
		IStaffTypeDao {
	@Override
	public StaffType findByName(String name) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StaffType.class).add(
				Restrictions.eq("name", name));
		return (StaffType) criteria.uniqueResult();

	}
}