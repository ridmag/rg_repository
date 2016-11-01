package com.itelasoft.pso.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.EnumLeaveType;
import com.itelasoft.pso.beans.Holiday;
import com.itelasoft.pso.beans.Leave;
import com.itelasoft.pso.beans.WeekDay;

public class LeaveDao extends GenericDao<Leave, Long> implements ILeaveDao {

	public List<Leave> listByStaffId(Long staffId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Leave.class).createCriteria("staffMember")
				.add(Restrictions.eq("id", staffId));
		@SuppressWarnings("unchecked")
		List<Leave> list = criteria.list();
		return list;
	}

	public Leave getByStaffIdAndDate(Long staffId, Date eventDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Leave.class)
				.add(Restrictions.and(Restrictions.le("startDate", eventDate), Restrictions.ge("endDate", eventDate)))
				.createCriteria("staffMember").add(Restrictions.eq("id", staffId));
		Leave leave = (Leave) criteria.uniqueResult();
		return leave;
	}

	@SuppressWarnings("unchecked")
	public boolean isFinalized(Leave leave) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
		Session session = getCurrentSession();
		Calendar cal = new GregorianCalendar();
		cal.setTime(leave.getStartDate());
		int count = 0;
		do {
			/*
			 * if (cal.get(Calendar.DAY_OF_WEEK) == 1 ||
			 * cal.get(Calendar.DAY_OF_WEEK) == 7) { cal.add(Calendar.DATE, 1);
			 * continue; }
			 */
			boolean found = false;
			for (WeekDay day : leave.getStaffMember().getUnAvailableDays()) {
				if (dateFormat.format(cal.getTime()).equals(day.getName())) {
					found = true;
					break;
				}
			}
			if (found) {
				cal.add(Calendar.DATE, 1);
				continue;
			}
			/*
			 * Criteria criteria = session .createCriteria(Holiday.class)
			 * .add(Restrictions.and( Restrictions.le("startDate",
			 * cal.getTime()), Restrictions.ge("endDate", cal.getTime())))
			 * .createCriteria("calendar") .add(Restrictions.eq("id", new
			 * Long(1))); List<Holiday> holidays = criteria.list(); if (holidays
			 * != null && !holidays.isEmpty()) { cal.add(Calendar.DATE, 1);
			 * continue; }
			 */
			Criteria criteria = session.createCriteria(Leave.class).add(Restrictions
					.and(Restrictions.le("startDate", cal.getTime()), Restrictions.ge("endDate", cal.getTime())));
			if (leave.getId() != null)
				criteria.add(Restrictions.ne("id", leave.getId()));
			criteria.createCriteria("staffMember").add(Restrictions.eq("id", leave.getStaffMember().getId()));
			List<Leave> leaves = criteria.list();
			if (leaves != null && !leaves.isEmpty()) {
				return false;
				// cal.add(Calendar.DATE, 1);
				// continue;
			}
			count++;
			cal.add(Calendar.DATE, 1);
		} while (cal.getTime().before(leave.getEndDate()) || cal.getTime().equals(leave.getEndDate()));
		leave.setDays(count);
		return true;
	}

	public int getTotalNumberOfLeaveDays(Long staffId, EnumLeaveType type) {
		Session session = getCurrentSession();
		Query query = session.createQuery(
				"select sum(leave.days) from Leave as leave where leave.staffMember.id = ? and leave.leaveType = ?");
		query.setParameter(0, staffId);
		query.setParameter(1, type);
		Long total = (Long) query.uniqueResult();
		if (total != null)
			return total.intValue();
		else
			return 0;
	}
}
