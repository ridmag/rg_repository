package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.NdisAncillaryCost;

public class NdisAncillaryCostDao extends GenericDao<NdisAncillaryCost, Long> implements INdisAncillaryCostDao {

	@Override
	public List<NdisAncillaryCost> listNdisAncyCostByStudent(Long id) {
		Session session = getCurrentSession();
		Query query = session.createQuery("from NdisAncillaryCost as ancyCost where ancyCost.student.id = ? ");
		query.setParameter(0, id);
		@SuppressWarnings("unchecked")
		List<NdisAncillaryCost> ndisAncillaryCostList = query.list();
			return ndisAncillaryCostList;
	}

	public Double getTotalAncProgramFund(Long studentId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select sum(nc.amount) from NdisContribution as nc where nc.student.id = :studentId   and nc.clusterType = 4");
		query.setParameter("studentId", studentId);
		Double amount = (Double) query.uniqueResult();
		if (amount != null && amount > 0)
			return amount;
		return 0.0;
	}
	
	public List<NdisAncillaryCost> getAncillaryCostOfInvoiceByStudentId(Long invoiceId,Long studentId){
		Session session1 = getCurrentSession();
		Criteria criteria = session1.createCriteria(NdisAncillaryCost.class)
				.createAlias("student", "student")
				.add(Restrictions.eq("student.id", studentId)).add(Restrictions.eq("invoiceId", invoiceId));
		List<NdisAncillaryCost> list = criteria.list();
		return list;
	}
}
