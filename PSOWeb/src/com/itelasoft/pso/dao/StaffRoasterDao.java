package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.ProgramEvent;

public class StaffRoasterDao extends DataAccessObject implements
		IStaffRoasterDao {

	public List<ProgramEvent> listEventsByDates(Date firstDate, Date secondDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class);
		if (firstDate != null) {
			criteria = criteria.add(Restrictions.ge("eventDate", firstDate));
		}
		if (secondDate != null) {
			criteria = criteria.add(Restrictions.le("eventDate", secondDate));
		}
		@SuppressWarnings("unchecked")
		List<ProgramEvent> events = criteria.list();
		return events;
	}

	public List<ProgramEvent> searchEventsByDates(Date eventDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class);
		criteria = criteria.add(Restrictions.eq("eventDate", eventDate));
		@SuppressWarnings("unchecked")
		List<ProgramEvent> events = criteria.list();
		return events;
	}

}
