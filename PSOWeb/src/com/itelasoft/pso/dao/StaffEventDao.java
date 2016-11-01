package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.StaffEvent;

public class StaffEventDao extends GenericDao<StaffEvent, Long> implements IStaffEventDao {

	public List<StaffEvent> listForStaffEnd(Long staffId, Date serviceEndDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StaffEvent.class).createAlias("programEvent", "pe")
				.createAlias("pe.program", "pro").add(Restrictions.eq("staffMember.id", staffId))
				.add(Restrictions.ge("pe.eventDate", serviceEndDate)).add(Restrictions.ne("pe.status", "finished"))
				.addOrder(Order.asc("pro.status")).addOrder(Order.asc("pe.eventDate"));
		@SuppressWarnings("unchecked")
		List<StaffEvent> list = criteria.list();
		return list;
	}

	public List<StaffEvent> listByStaffNDates(String staffId, Date fromDate, Date toDate) {
		String[] types = { "Student", "Transport", "Individual", "Staff" };
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StaffEvent.class).createAlias("staffMember", "staffMember")
				.createAlias("programEvent", "programEvent").createAlias("programEvent.group", "group")
				.createAlias("programEvent.program", "program").createAlias("program.type", "programType")
				.add(Restrictions.in("programType.name", types));
		if (fromDate != null)
			criteria = criteria.add(Restrictions.ge("programEvent.eventDate", fromDate));
		if (toDate != null)
			criteria = criteria.add(Restrictions.le("programEvent.eventDate", toDate));
		if (staffId != null && !staffId.isEmpty())
			criteria = criteria.add(Restrictions.eq("staffMember.staffId", staffId));
		criteria.addOrder(Order.asc("programEvent.eventDate")).addOrder(Order.asc("programType.name"))
				.addOrder(Order.asc("group.name"));
		@SuppressWarnings("unchecked")
		List<StaffEvent> list = criteria.list();
		return list;
	}

	public List<StaffEvent> listByStaffNDatesWithAttended(String staffId, Date fromDate, Date toDate) {
		String[] types = { "Student", "Transport", "Individual", "Staff" };
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StaffEvent.class).createAlias("staffMember", "staffMember")
				.createAlias("programEvent", "programEvent").createAlias("programEvent.group", "group")
				.createAlias("programEvent.program", "program").createAlias("program.type", "programType")
				.add(Restrictions.in("programType.name", types)).add(Restrictions.eq("attended", true));
		if (fromDate != null)
			criteria = criteria.add(Restrictions.ge("programEvent.endTime", fromDate));
		if (toDate != null)
			criteria = criteria.add(Restrictions.le("programEvent.endTime", toDate));
		if (staffId != null && !staffId.isEmpty())
			criteria = criteria.add(Restrictions.eq("staffMember.staffId", staffId));
		criteria.addOrder(Order.asc("programEvent.endTime")).addOrder(Order.asc("programType.name"))
				.addOrder(Order.asc("group.name"));
		@SuppressWarnings("unchecked")
		List<StaffEvent> list = criteria.list();
		return list;
	}

	public void deleteList(List<StaffEvent> saffEventList) {
		Session session = getCurrentSession();
		for (StaffEvent event : saffEventList) {
			session.delete(event);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<StaffEvent> listByProEventId(Long proEventId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StaffEvent.class)
				.createAlias("staffMember", "staff")
				.add(Restrictions.eq("programEvent.id", proEventId))
				.addOrder(Order.asc("staff.id"));
		return criteria.list();
	}
}
