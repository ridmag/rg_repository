package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.ExternalOrganization;

public class ExternalOrganizationDao extends
		GenericDao<ExternalOrganization, Long> implements
		IExternalOrganizationDao {

	public List<ExternalOrganization> listByServiceArea(Long serviceAreaId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ExternalOrganization.class);
		criteria = criteria.createCriteria("serviceArea").add(
				Restrictions.eq("id", serviceAreaId));
		@SuppressWarnings("unchecked")
		List<ExternalOrganization> list = criteria.list();
		return list;
	}

	public List<ExternalOrganization> listByName(String name) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ExternalOrganization.class);
		if (name != null && !name.isEmpty()) {
			criteria = criteria.add(Restrictions.like("name", name,
					MatchMode.ANYWHERE).ignoreCase());
		}
		@SuppressWarnings("unchecked")
		List<ExternalOrganization> list = criteria.list();
		return list;
	}
}
