package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import com.itelasoft.pso.beans.Outlet;

public class OutletDao extends GenericDao<Outlet, Long> implements IOutletDao {

	public List<Outlet> listOutletsByFundingSource(Long sourceId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Outlet.class);
		criteria = criteria.createCriteria("fundingSource").add(
				Restrictions.eq("id", sourceId));
		@SuppressWarnings("unchecked")
		List<Outlet> list = criteria.list();
		return list;
	}

}
