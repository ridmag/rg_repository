package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.FundingSource;

/**
 * Dao class for FundingSource.
 */
public class FundingSourceDao extends GenericDao<FundingSource, Long> implements
		IFundingSourceDao {

	public boolean validateFundingId(Long fundingId, String code) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(FundingSource.class).add(
				Restrictions.eq("fundingCode", code).ignoreCase());
		if (fundingId != null)
			criteria.add(Restrictions.ne("id", fundingId));
		FundingSource fs = (FundingSource) criteria.uniqueResult();
		if (fs == null) {
			return true;
		} else {
			return false;
		}
	}

	public List<FundingSource> listAvailableByStudent(Long studentId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from FundingSource as fs where fs not in(select sfs.fundingSrc from StudentFundingSource sfs where sfs.student.id = ?)");
		query.setParameter(0, studentId);
		@SuppressWarnings("unchecked")
		List<FundingSource> list = query.list();
		return list;
	}

	public List<FundingSource> listActiveFundingSources() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(FundingSource.class).add(
				Restrictions.eq("status", "Active"));
		@SuppressWarnings("unchecked")
		List<FundingSource> frcs = criteria.list();
		return frcs;
	}

}
