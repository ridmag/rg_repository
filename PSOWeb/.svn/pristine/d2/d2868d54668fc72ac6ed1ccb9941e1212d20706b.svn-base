package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.NdisAncillaryCost;
import com.itelasoft.pso.beans.NdisCommittedEvent;
import com.itelasoft.pso.beans.NdisInvoiceItem;
import com.itelasoft.pso.beans.Student;

public class NdisInvoiceItemDao extends GenericDao<NdisInvoiceItem, Long> implements
		INdisInvoiceItemDao {

	@Override
	public NdisInvoiceItem getInvoice(String ndisNum) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery(
						"from NdisInvoiceItem as ndisinvoiceitem where ndisinvoiceitem.ndisNumber = ?")
				.setParameter(0, ndisNum);
		NdisInvoiceItem ndisInvoice = (NdisInvoiceItem) query.uniqueResult();
		return ndisInvoice;
	}

	public List<NdisCommittedEvent> retrieveInvoiceItem(Date startDate,
			Date endDate, Long studentId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(NdisCommittedEvent.class)
				.createAlias("participant", "participant");
		if (startDate != null)
			criteria = criteria.add(Restrictions.ge("eventDate", startDate));
		if (startDate != null)
			criteria = criteria.add(Restrictions.le("eventDate", endDate));
		if (studentId != null)
			criteria = criteria.add(Restrictions.eq("participant.id", studentId));
			criteria = criteria.add(Restrictions.eq("claimed", false));
		
		criteria.addOrder(Order.asc("eventDate"));
		@SuppressWarnings("unchecked")
		List<NdisCommittedEvent> list = criteria.list();
		return list;
	}
	
	public List<NdisCommittedEvent> retriveCommitedEventsByInvoiceId(Long invoiceId) {
		Session session = getCurrentSession();
		Query query = session.createQuery("from NdisCommittedEvent as NCE where NCE.invoiceId = :invoiceid");
		query.setParameter("invoiceid", invoiceId);
		@SuppressWarnings("unchecked")
		List<NdisCommittedEvent> comittedEvents = query.list();
		return comittedEvents;
	}
	
	public List<NdisAncillaryCost> retriveNdisAncillaryCostByInvoiceId(Long invoiceId){
		Session session = getCurrentSession();
		Query query = session.createQuery("from NdisAncillaryCost as NAC where NAC.invoiceId = :invoiceid");
		query.setParameter("invoiceid", invoiceId);
		@SuppressWarnings("unchecked")
		List<NdisAncillaryCost> anciilary = query.list();
		return anciilary;
	}
	public List<NdisAncillaryCost> retrieveNdisAncillaryCostItems(Date startDate,
			Date endDate, Long studentId,Boolean claimed) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(NdisAncillaryCost.class)
				.createAlias("student", "student");
		if(claimed != null)
			criteria = criteria.add(Restrictions.eq("claimed", claimed));
		if (startDate != null)
			criteria = criteria.add(Restrictions.ge("date", startDate));
		if (startDate != null)
			criteria = criteria.add(Restrictions.le("date", endDate));
		if (studentId != null)
			criteria = criteria.add(Restrictions.eq("student.id", studentId));
			criteria = criteria.add(Restrictions.eq("authorized", true));
		
		criteria.addOrder(Order.asc("date"));
		@SuppressWarnings("unchecked")
		List<NdisAncillaryCost> list = criteria.list();
		return list;
	}
	
	public List<NdisAncillaryCost> allretrieveNdisAncillaryCostItems(Long studentId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(NdisAncillaryCost.class)
				.createAlias("student", "student");
		if (studentId != null)
			criteria = criteria.add(Restrictions.eq("student.id", studentId));
			criteria = criteria.add(Restrictions.eq("authorized", true));
		criteria.addOrder(Order.asc("date"));
		@SuppressWarnings("unchecked")
		List<NdisAncillaryCost> list = criteria.list();
		return list;
	}
	
	public List<NdisInvoiceItem> retriveInvoiceItemsByInvoiceId(Long invoiceId){
		Session session = getCurrentSession();
		Query query = session.createQuery("from NdisInvoiceItem as NI where NI.invoiceId = :invoiceid");
		query.setParameter("invoiceid", invoiceId);
		@SuppressWarnings("unchecked")
		List<NdisInvoiceItem> invoiceItems = query.list();
		return invoiceItems;
	}
}