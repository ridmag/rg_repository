package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.NdisInvoice;

public class NdisInvoiceDao extends GenericDao<NdisInvoice, Long> implements INdisInvoiceDao{
	
	public List<NdisInvoice> retriveInvoiceByStartEndDate(Date startdate,Date enddate){
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(NdisInvoice.class);
		if (startdate != null)
			criteria = criteria.add(Restrictions.ge("startDay", startdate));
		if (enddate != null)
			criteria = criteria.add(Restrictions.le("endDay", enddate));
		
		//criteria.addOrder(Order.asc("GenerateDate"));
		@SuppressWarnings("unchecked")
		List<NdisInvoice> list = criteria.list();
		return list;	
	}
}
