package com.itelasoft.pso.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.HoursUtilizedReport;

public class HoursUtilizedReportDao extends
		GenericDao<HoursUtilizedReport, Long> implements
		IHoursUtilizedReportDao {

	public HoursUtilizedReport retrieveUtilizationReport(Date fromDate,
			Date toDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(HoursUtilizedReport.class)
				.add(Restrictions.eq("fromDate", fromDate))
				.add(Restrictions.eq("toDate", toDate));
		HoursUtilizedReport report = (HoursUtilizedReport) criteria
				.uniqueResult();
		if (report != null) {
			return report;
		}
		return null;
	}

}
