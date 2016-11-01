package com.itelasoft.pso.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.Communication;
import com.itelasoft.pso.beans.Reminder;

public class ReminderDao extends GenericDao<Reminder, Long> implements
		IReminderDao {

	@SuppressWarnings("unchecked")
	public List<Reminder> listNewReminders() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Reminder.class)
				.add(Restrictions.le("remindOn", new Date()))
				.add(Restrictions.eq("status", "New"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Reminder> listCommByKeyPerson(Long staffId) {
		SimpleDateFormat mydateFormat = new SimpleDateFormat("dd/MM/yyyy");
		List<Communication> comms = new ArrayList<Communication>();
		List<Reminder> rems = new ArrayList<Reminder>();
		Session session = getCurrentSession();
		Criteria criteria;
		try {
			criteria = session
					.createCriteria(Communication.class)
					.createAlias("keyPersonStaff", "staff")
					.createAlias("reminder", "rem")
					.add(Restrictions.eq("staff.id", staffId))
					.add(Restrictions.or(Restrictions.and(
							Restrictions.lt("rem.remindOn", new Date()),
							Restrictions.eq("rem.status", "New")), Restrictions
							.eq("rem.remindOn", mydateFormat.parse(mydateFormat
									.format(new Date())))))
					.addOrder(Order.desc("rem.remindOn"));
			comms = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (Communication com : comms) {
			rems.add(com.getReminder());
		}
		return rems;
	}
}
