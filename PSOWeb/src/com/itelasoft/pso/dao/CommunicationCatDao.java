package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.CommunicationCat;

/**
 * Dao class for Category.
 */
public class CommunicationCatDao extends GenericDao<CommunicationCat, Long>
		implements ICommunicationCatDao {

	@SuppressWarnings("unchecked")
	public List<CommunicationCat> getRootCategories() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(CommunicationCat.class).add(
				Restrictions.isNull("parentId"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<CommunicationCat> getSubCategories(Long parentId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(CommunicationCat.class).add(
				Restrictions.eq("parentId", parentId));
		return criteria.list();
	}
}
