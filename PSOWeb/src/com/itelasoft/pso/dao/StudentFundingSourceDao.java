package com.itelasoft.pso.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.StudentEvent;
import com.itelasoft.pso.beans.StudentFundingSource;

/**
 * Dao class for FundingSourceStudentXRef.
 */
public class StudentFundingSourceDao extends
		GenericDao<StudentFundingSource, Long> implements
		IStudentFundingSourceDao {

	public List<StudentFundingSource> listByStudent(Long studentId) {
		Session session = getCurrentSession();
		Criteria c = session.createCriteria(StudentFundingSource.class)
				.addOrder(Order.asc("fundingStartDate"))
				.createCriteria("student")
				.add(Restrictions.eq("id", studentId));
		@SuppressWarnings("unchecked")
		List<StudentFundingSource> list = c.list();
		if (list != null && !list.isEmpty()) {
			for (StudentFundingSource sfs : list) {
				Criteria criteria = session.createCriteria(StudentEvent.class)
						.add(Restrictions.eq("stdFundingSrc.id", sfs.getId()))
						.setProjection(Projections.rowCount());
				Long count = (Long) criteria.uniqueResult();
				if (count != null && count > 0)
					sfs.setActive(true);
			}
		}
		return list;
	}

	public StudentFundingSource getRelatedStudentFundingSource(Long studentId,
			Date eventDate) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from StudentFundingSource where studentId = :studentId and "
						+ "fundingStartDate = (select max(sfs.fundingStartDate) "
						+ "from StudentFundingSource sfs where sfs.student.id = :studentId and "
						+ "sfs.fundingStartDate <= :eventDate)");
		query.setParameter("studentId", studentId);
		query.setParameter("eventDate", eventDate);
		return (StudentFundingSource) query.uniqueResult();
	}

	public double getUsedHoursForTheFortnight(Long studentId, Date fromDate,
			Date toDate) {
		Session session = getCurrentSession();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String queryString = "SELECT sum((date_part('hour', pe.endtime - pe.starttime)*60) + "
				+ "date_part('minute', pe.endtime - pe.starttime)) as minutes "
				+ "FROM studentevents AS se JOIN programevents AS pe ON se.eventid = pe.id "
				+ "JOIN groupedstudents AS gs ON se.groupedstudentid = gs.id "
				+ "JOIN students AS s ON gs.studentid = s.id "
				+ "WHERE pe.EVENTDATE >= '"
				+ dateFormat.format(fromDate)
				+ "' AND pe.EVENTDATE <= '"
				+ dateFormat.format(toDate)
				+ "' AND s.id = " + studentId.toString();
		SQLQuery query = session.createSQLQuery(queryString);
		Double minutes = (Double) query.uniqueResult();
		if (minutes != null && minutes > 0)
			return minutes;
		return 0;
	}

}
