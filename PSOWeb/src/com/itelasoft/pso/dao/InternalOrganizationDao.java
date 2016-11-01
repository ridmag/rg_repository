package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;


import com.itelasoft.pso.beans.InternalOrganization;
import com.itelasoft.pso.beans.StaffMember;

public class InternalOrganizationDao extends
		GenericDao<InternalOrganization, Long> implements
		IInternalOrganizationDao {

	public List<InternalOrganization> listByServiceArea(Long serviceAreaId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(InternalOrganization.class);
		criteria = criteria.createCriteria("serviceArea").add(
				Restrictions.eq("id", serviceAreaId));
		@SuppressWarnings("unchecked")
		List<InternalOrganization> list = criteria.list();
		return list;
	}

	public List<InternalOrganization> listByName(String name) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(InternalOrganization.class);
		if (name != null && !name.isEmpty()) {
			criteria = criteria.add(Restrictions.like("name", name,
					MatchMode.ANYWHERE).ignoreCase());
		}
		@SuppressWarnings("unchecked")
		List<InternalOrganization> list = criteria.list();
		return list;
	}

	@Override
	public InternalOrganization getOrganization(String orgName) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(InternalOrganization.class).add(
				Restrictions.eq("name", orgName).ignoreCase());
		return (InternalOrganization) criteria.uniqueResult();
	}
}