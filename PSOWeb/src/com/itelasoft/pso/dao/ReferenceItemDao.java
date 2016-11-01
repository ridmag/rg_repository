package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.EnumItemCategory;
import com.itelasoft.pso.beans.ReferenceItem;

public class ReferenceItemDao extends GenericDao<ReferenceItem, Long> implements
		IReferenceItemDao {

	public List<ReferenceItem> findItemsByCategory(EnumItemCategory category,
			String status) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ReferenceItem.class);
		criteria = criteria.add(Restrictions.eq("category", category));
		if (status != null)
			criteria.add(Restrictions.eq("status", status));
		@SuppressWarnings("unchecked")
		List<ReferenceItem> list = criteria.list();
		return list;
	}

	public List<ReferenceItem> listAvailableByStaff(Long staffId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from ReferenceItem as ri where ((ri.status = 'Active' and ri.category = 'STAFF_SKILL') and (ri not in(select ss.skill from StaffSkill ss where ss.staffMember.id = ?)))");
		query.setParameter(0, staffId);
		@SuppressWarnings("unchecked")
		List<ReferenceItem> list = query.list();
		return list;
	}

}
