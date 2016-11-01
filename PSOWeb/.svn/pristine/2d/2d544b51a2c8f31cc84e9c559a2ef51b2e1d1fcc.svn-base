package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import com.itelasoft.pso.beans.Holiday;

public class HolidayDao extends GenericDao<Holiday, Long> implements
		IHolidayDao {

	/*
	 * public List<Holiday> listByDate(Date date) { Session session =
	 * getCurrentSession(); Query query = session
	 * .createQuery("from Holiday as day where day.date = ? order by day.id");
	 * query.setParameter(0, date);
	 * 
	 * @SuppressWarnings("unchecked") List<Holiday> list = query.list(); if
	 * (list != null && !list.isEmpty()) { return list; } return null; }
	 */
	public boolean containHolidays(Long calendarId, Date startDate, Date endDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Holiday.class);
		criteria = criteria.add(Restrictions.or(Restrictions.or(Restrictions
				.and(Restrictions.le("startDate", startDate),
						Restrictions.ge("endDate", startDate)), Restrictions
				.and(Restrictions.le("startDate", endDate),
						Restrictions.ge("endDate", endDate))),

		Restrictions.and(Restrictions.ge("startDate", startDate),
				Restrictions.le("endDate", endDate))));
		criteria = criteria.createCriteria("calendar").add(
				Restrictions.eq("id", calendarId));
		Holiday result = (Holiday) criteria.uniqueResult();
		if (result != null) {
			return true;
		}
		return false;
	}

	public List<Holiday> listByCriteria(Date fromDate, Date toDate,
			Long calendarId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Holiday.class);
		if (fromDate != null) {
			criteria = criteria.add(Restrictions.or(
					Restrictions.ge("startDate", fromDate),
					Restrictions.ge("endDate", fromDate)));
		}
		if (toDate != null) {
			criteria = criteria.add(Restrictions.or(
					Restrictions.le("startDate", toDate),
					Restrictions.le("endDate", toDate)));
		}
		if (calendarId != null && calendarId > 0) {
			criteria = criteria.createCriteria("calendar").add(
					Restrictions.eq("id", calendarId));
		}
		@SuppressWarnings("unchecked")
		List<Holiday> days = criteria.list();
		return days;
	}

	public Holiday retrieveByCriteria(Date date, Long calendarId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Holiday.class);
		criteria = criteria
				.add(Restrictions.and(Restrictions.le("startDate", date),
						Restrictions.ge("endDate", date)))
				.createCriteria("calendar")
				.add(Restrictions.eq("id", calendarId));
		return (Holiday) criteria.uniqueResult();
	}

	public boolean isHoliday(Long calendarId, Date eventDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Holiday.class);
		criteria = criteria.add(Restrictions.and(
				Restrictions.le("startDate", eventDate),
				Restrictions.ge("endDate", eventDate)));
		criteria = criteria.createCriteria("calendar").add(
				Restrictions.eq("id", calendarId));
		Holiday result = (Holiday) criteria.uniqueResult();
		if (result != null) {
			return true;
		}
		return false;
	}

	public Holiday getHolidayType(Date holiday){
		
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Holiday.class);
		criteria = criteria.add(Restrictions.and(
				Restrictions.le("startDate", holiday),
				Restrictions.ge("endDate", holiday)));
		return (Holiday) criteria.uniqueResult();
	}
}
