package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.CommunicationCat;
import com.itelasoft.pso.beans.Communication;
import com.itelasoft.pso.beans.CommunicationNote;
import com.itelasoft.pso.beans.ServiceArea;

/**
 * Dao class for Communication.
 */
public class CommunicationDao extends GenericDao<Communication, Long> implements
		ICommunicationDao {

	public List<Communication> listByCategory(Long catId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Communication.class)
				.addOrder(Order.desc("createdDate"))
				.addOrder(Order.desc("createdTime"));
		criteria = criteria.createCriteria("category").add(
				Restrictions.or(Restrictions.eq("id", catId),
						Restrictions.eq("parentId", catId)));
		@SuppressWarnings("unchecked")
		List<Communication> list = criteria.list();
		return list;
	}
	
	public List<Communication> listBySubCategories(List<Long> subCatIds){
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Communication.class)
				.addOrder(Order.desc("createdDate"))
				.addOrder(Order.desc("createdTime"));
		criteria = criteria.createCriteria("category").add(
				Restrictions.or(Restrictions.in("id", subCatIds),
						Restrictions.in("parentId", subCatIds)));
		@SuppressWarnings("unchecked")
		List<Communication> list = criteria.list();
		return list;
	}

	// Not in use
	public List<Communication> listByStudents(Long categoryId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Communication.class)
				.addOrder(Order.desc("createdDate"))
				.addOrder(Order.desc("createdTime"))
				.add(Restrictions.isNotNull("keyPersonStudent"));
		if (categoryId != null) {
			criteria.createCriteria("category").add(
					Restrictions.eq("id", categoryId));
		}
		@SuppressWarnings("unchecked")
		List<Communication> list = criteria.list();
		return list;
	}

	public List<Communication> listByStaffType_Status_Category(
			Long staffTypeId, String staffStatus, CommunicationCat category) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Communication.class)
				.add(Restrictions.eq("category", category))
				.addOrder(Order.desc("createdDate"))
				.addOrder(Order.desc("createdTime"))
				.createCriteria("keyPersonStaff")
				.add(Restrictions.eq("status", staffStatus));
		if (staffTypeId != null)
			criteria.createCriteria("type").add(
					Restrictions.eq("id", staffTypeId));
		@SuppressWarnings("unchecked")
		List<Communication> list = criteria.list();
		return list;
	}

	public List<CommunicationNote> listNotesByCommunication(Long communicationId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Communication.class)
				.add(Restrictions.eq("id", communicationId))
				.setFetchMode("notes", FetchMode.JOIN);
		Communication com = (Communication) criteria.uniqueResult();
		if (com != null)
			return com.getNotes();
		else
			return null;
	}

	public List<Communication> listByCategoryNServiceArea(
			CommunicationCat category, ServiceArea area) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Communication.class)
				.add(Restrictions.eq("category", category))
				.addOrder(Order.desc("createdDate"))
				.addOrder(Order.desc("createdTime"))
				.createCriteria("keyPersonOrg")
				.add(Restrictions.eq("serviceArea", area));
		@SuppressWarnings("unchecked")
		List<Communication> list = criteria.list();
		return list;
	}

	public List<Communication> listByVehicleType(String vehicleType) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Communication.class)
				.addOrder(Order.desc("createdDate"))
				.addOrder(Order.desc("createdTime"))
				.createCriteria("keyPersonVehicle")
				.add(Restrictions.eq("type", vehicleType));
		@SuppressWarnings("unchecked")
		List<Communication> list = criteria.list();
		return list;
	}
}
