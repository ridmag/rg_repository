package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.NdisSupportItem;
import com.itelasoft.pso.beans.Program;

public class NdisSupportItemDao extends GenericDao<NdisSupportItem, Long>
		implements INdisSupportItemDao {
	/**
	 * public List<ExternalOrganization> listByServiceArea(Long serviceAreaId) {
	 * Session session = getCurrentSession(); Criteria criteria =
	 * session.createCriteria(ExternalOrganization.class); criteria =
	 * criteria.createCriteria("serviceArea").add( Restrictions.eq("id",
	 * serviceAreaId));
	 * 
	 * @SuppressWarnings("unchecked") List<ExternalOrganization> list =
	 *                                criteria.list(); return list; }
	 **/

	@Override
	public NdisSupportItem retrive(Long id) {

		NdisSupportItem ndissupportitem = super.retrive(id);
		return ndissupportitem;
	}

	public List<NdisSupportItem> listByName(String itemName) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(NdisSupportItem.class);
		if (itemName != null && !itemName.isEmpty()) {
			criteria = criteria.add(Restrictions.like("itemName", itemName,
					MatchMode.ANYWHERE).ignoreCase());
		}
		@SuppressWarnings("unchecked")
		List<NdisSupportItem> list = criteria.list();
		return list;
	}

	public NdisSupportItem listById(Long ndisitemid) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from NdisSupportItem as nsi where nsi.id = ?");
		query.setParameter(0, ndisitemid);
		@SuppressWarnings("unchecked")
		NdisSupportItem item = (NdisSupportItem) query.uniqueResult();
		return item;
	}

	public NdisSupportItem searchItemByRefNo(Long ndisItemNo) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(NdisSupportItem.class).add(
				Restrictions.eq("id", ndisItemNo));
		return (NdisSupportItem) criteria.uniqueResult();
	}
	
	public List<NdisSupportItem> findAllByType(String type) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(NdisSupportItem.class)
				.add(Restrictions.eq("ndisClusterType", type));
		@SuppressWarnings("unchecked")
		List<NdisSupportItem> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}
	@Override
	public List<NdisSupportItem> listByType(String groupType) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from NdisSupportItem where ndisClusterType = ? order by itemName");
		query.setParameter(0, groupType);
		@SuppressWarnings("unchecked")
		List<NdisSupportItem> itemList = query.list();
		return itemList;
	}
}
