package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.itelasoft.pso.beans.NdisAncillaryCost;
import com.itelasoft.pso.beans.NdisContribution;

public class NdisContributionDao extends GenericDao<NdisContribution, Long> implements INdisContributionDao  {

	public Double getTotalDayProgramFund(Long studentId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select sum(nc.amount) from NdisContribution as nc where nc.student.id = :studentId   and nc.clusterType = 1");
		query.setParameter("studentId", studentId);
		Double amount = (Double) query.uniqueResult();
		if (amount != null && amount > 0)
			return amount;
		return 0.0;
	}

	public Double getTotalIndProgramFund(Long studentId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select sum(nc.amount) from NdisContribution as nc where nc.student.id = :studentId  and nc.clusterType = 2");
		query.setParameter("studentId", studentId);
		Double amount = (Double) query.uniqueResult();
		if (amount != null && amount > 0)
			return amount;
		return 0.0;
	}

	public Double getTotalTransProgramFund(Long studentId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select sum(nc.amount) from NdisContribution as nc where nc.student.id = :studentId   and nc.clusterType = 3");
		query.setParameter("studentId", studentId);
		Double amount = (Double) query.uniqueResult();
		if (amount != null && amount > 0)
			return amount;
		return 0.0;
	}
	
	@Override
	public List<NdisContribution> listNdisContributionByStudent(Long id) {
		Session session = getCurrentSession();
		Query query = session.createQuery("from NdisContribution as nc where nc.student.id = ? order by clusterType");
		query.setParameter(0, id);
		@SuppressWarnings("unchecked")
		List<NdisContribution> ndisContributiontList = query.list();
		if (ndisContributiontList != null) {
			return ndisContributiontList;
		}
		return null;
	}
}