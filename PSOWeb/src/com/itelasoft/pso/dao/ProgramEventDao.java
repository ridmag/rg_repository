package com.itelasoft.pso.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.beans.StaffEvent;

public class ProgramEventDao extends GenericDao<ProgramEvent, Long> implements IProgramEventDao {

	public ProgramEvent retrieveWithData(Long eventId, boolean loadStudentEvents, boolean loadStaffEvents) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class).add(Restrictions.eq("id", eventId));
		if (loadStudentEvents)
			criteria.setFetchMode("studentEvents", FetchMode.JOIN);
		ProgramEvent event = (ProgramEvent) criteria.uniqueResult();
		if (event != null && loadStaffEvents) {
			Criteria criteria1 = session.createCriteria(StaffEvent.class);
			criteria1 = criteria1.add(Restrictions.eq("programEvent.id", eventId));
			@SuppressWarnings("unchecked")
			List<StaffEvent> list = criteria1.list();
			event.setStaffEvents(list);
		}
		return event;
	}

	public ProgramEvent getMainEvent(ProgramEvent returnEvent) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class)
				.add(Restrictions.eq("eventDate", returnEvent.getEventDate()))
				.add(Restrictions.eq("group.id", returnEvent.getGroup().getParentGroup().getId()));
		return (ProgramEvent) criteria.uniqueResult();
	}

	public ProgramEvent getReturnEvent(ProgramEvent mainEvent) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class)
				.add(Restrictions.eq("eventDate", mainEvent.getEventDate())).createCriteria("group")
				.add(Restrictions.eq("parentGroup.id", mainEvent.getGroup().getId()));
		return (ProgramEvent) criteria.uniqueResult();
	}

	public List<ProgramEvent> listByStudentGroup(Long groupId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class).addOrder(Order.asc("eventDate"))
				.createCriteria("group").add(Restrictions.eq("id", groupId));
		@SuppressWarnings("unchecked")
		List<ProgramEvent> list = criteria.list();
		return list;
	}

	public List<ProgramEvent> listByStudentGroupNDates(Long groupId, Date startDate, Date endDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class).add(Restrictions.ge("eventDate", startDate))
				.add(Restrictions.le("eventDate", endDate)).addOrder(Order.asc("eventDate")).createCriteria("group")
				.add(Restrictions.eq("id", groupId));

		@SuppressWarnings("unchecked")
		List<ProgramEvent> list = criteria.list();
		return list;
	}

	public List<ProgramEvent> listByCriteria(Long programId, List<ProgramType> programTypes, Long groupId,
			Date fromDate, Date toDate, List<String> statuses, Long studentId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class).createAlias("program", "program")
				.createAlias("program.type", "programType").createAlias("group", "group")
				.add(Restrictions.eq("program.status", "Active"));

		if (programId != null && programId != 0) {
			criteria = criteria.add(Restrictions.eq("program.id", programId));
		} else if (programTypes != null && !programTypes.isEmpty()) {
			criteria = criteria.add(Restrictions.in("program.type", programTypes));
		}
		if (groupId != null && groupId != 0) {
			criteria = criteria.add(Restrictions.eq("group.id", groupId));
		}
		if (fromDate != null) {
			criteria = criteria.add(Restrictions.gt("eventDate", fromDate));
		}
		if (toDate != null) {
			criteria = criteria.add(Restrictions.le("eventDate", toDate));
		}
		if (statuses != null && !statuses.isEmpty()) {
			criteria = criteria.add(Restrictions.in("status", statuses));
		}
		if (studentId != null) {
			criteria.createAlias("group.students", "groupedStudent").createAlias("groupedStudent.student", "student");
			criteria = criteria.add(Restrictions.eq("student.id", studentId));
		}
		@SuppressWarnings("unchecked")
		List<ProgramEvent> events = criteria.addOrder(Order.asc("eventDate")).addOrder(Order.asc("program.name"))
				.addOrder(Order.asc("group.name")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return events;
	}

	public List<ProgramEvent> listForCoordinatorEnd(Long staffId, Date serviceEndDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class).createAlias("program", "pro")
				.add(Restrictions.eq("coordinator.id", staffId)).add(Restrictions.ge("eventDate", serviceEndDate))
				.add(Restrictions.ne("status", "finished")).addOrder(Order.asc("pro.status"))
				.addOrder(Order.asc("eventDate"));
		@SuppressWarnings("unchecked")
		List<ProgramEvent> events = criteria.list();
		return events;
	}

	public List<ProgramEvent> listForTimeTable(Date fromDate, Date toDate, Long studentId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class).createAlias("program", "program")
				.createAlias("program.type", "programType").createAlias("group", "group")
				.add(Restrictions.eq("program.status", "Active"))
				.add(Restrictions.or(Restrictions.eq("programType.name", "Student"),
						Restrictions.eq("programType.name", "Individual")));
		if (fromDate != null) {
			criteria = criteria.add(Restrictions.ge("eventDate", fromDate));
		}
		if (toDate != null) {
			criteria = criteria.add(Restrictions.le("eventDate", toDate));
		}
		if (studentId != null) {
			criteria.createAlias("studentEvents", "event").createAlias("event.groupedStudent", "gs")
					.createAlias("gs.student", "student");
			criteria = criteria.add(Restrictions.eq("student.id", studentId));
		}
		@SuppressWarnings("unchecked")
		List<ProgramEvent> events = criteria.addOrder(Order.asc("eventDate")).addOrder(Order.asc("program.name"))
				.addOrder(Order.asc("group.name")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return events;
	}

	public List<ProgramEvent> listTransportForStudentPrograms(Date eventDate, Long studentId, Long locationId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class).createAlias("group", "group")
				.createAlias("program", "program").createAlias("program.type", "type")
				.add(Restrictions.eq("program.status", "Active")).add(Restrictions.eq("type.name", "Transport"))
				.add(Restrictions.eq("eventDate", eventDate));
		criteria.createAlias("studentEvents", "event").createAlias("event.groupedStudent", "gs")
				.createAlias("gs.pickup", "pickup").createAlias("gs.dropoff", "dropoff")
				.createAlias("gs.student", "student").add(Restrictions.eq("student.id", studentId));
		criteria = criteria.add(
				Restrictions.or(Restrictions.eq("pickup.id", locationId), Restrictions.eq("dropoff.id", locationId)));
		@SuppressWarnings("unchecked")
		List<ProgramEvent> events = criteria.addOrder(Order.asc("eventDate"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return events;
	}

	public boolean searchByDate(Date date, Long programId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class);
		criteria = criteria.add(Restrictions.ge("eventDate", date));
		criteria = criteria.createCriteria("program").add(Restrictions.eq("id", programId));
		@SuppressWarnings("unchecked")
		List<ProgramEvent> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean isEventExist(Date fromDate, Date toDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class).createAlias("group", "group")
				.add(Restrictions.and(Restrictions.ge("eventDate", fromDate), Restrictions.le("eventDate", toDate),
						Restrictions.eq("group.allowProgramtorunonaholiday", false)));
		@SuppressWarnings("unchecked")
		List<ProgramEvent> events = criteria.list();
		if (events != null && !events.isEmpty()) {
			return true;
		}
		return false;
	}

	/*
	 * public ProgramEvent getLatestEventForStaff(Long StaffId) { Session
	 * session = getCurrentSession(); Query query = session .createQuery(
	 * "select event from ProgramEvent as event, StaffMember as staff where event.eventDate = (select max(event.eventDate) from event where staff.id = ? and staff in(event.coordinator))"
	 * ); query.setParameter(0, StaffId);
	 * 
	 * @SuppressWarnings("unchecked") List<ProgramEvent> list = query.list();
	 * ProgramEvent event = new ProgramEvent(); if (list != null &&
	 * !list.isEmpty()) event = (ProgramEvent) list.get(0); if (event != null)
	 * return event; return null; }
	 */

	public ProgramEvent getLatestEventForLocation(Long locId) {
		Session session = getCurrentSession();
		Query query = session.createQuery(
				"select event from ProgramEvent as event, Location as loc where event.eventDate = (select max(event.eventDate) from event where loc.id = ? and loc in(event.location))");
		query.setParameter(0, locId);
		@SuppressWarnings("unchecked")
		List<ProgramEvent> list = query.list();
		ProgramEvent event = new ProgramEvent();
		if (list != null && !list.isEmpty())
			event = (ProgramEvent) list.get(0);
		if (event != null)
			return event;
		return null;
	}

	public ProgramEvent getLatestEventForVehicle(Long vehiId) {
		Session session = getCurrentSession();
		Query query = session.createQuery(
				"select event from ProgramEvent as event, Vehicle as vehi where event.eventDate = (select max(event.eventDate) from event where vehi.id = ? and vehi in(event.vehicle))");
		query.setParameter(0, vehiId);
		@SuppressWarnings("unchecked")
		List<ProgramEvent> list = query.list();
		ProgramEvent event = new ProgramEvent();
		if (list != null && !list.isEmpty())
			event = (ProgramEvent) list.get(0);
		if (event != null)
			return event;
		return null;
	}

	public List<ProgramEvent> getRosterForStaff(Date start, Date end, String staffId) {
		Session session = getCurrentSession();
		Criteria crt = session.createCriteria(ProgramEvent.class).createAlias("staffEvents", "staffEvent")
				.createAlias("staffEvent.staffMember", "coordinator").add(Restrictions.ge("eventDate", start))
				.add(Restrictions.le("eventDate", end)).add(Restrictions.eq("coordinator.staffId", staffId))
				.addOrder(Order.asc("eventDate"));
		@SuppressWarnings("unchecked")
		List<ProgramEvent> list = crt.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ProgramEvent> listEditableEventsByGroupedStudent(GroupedStudent groupedStudent, Date cutOffDate) {
		Session session = getCurrentSession();
		List<ProgramEvent> list = null;
		if (groupedStudent.getStatus().equals("Excluded")) {
			Criteria criteria = session.createCriteria(ProgramEvent.class).createAlias("group", "group")
					.createAlias("studentEvents", "stuEvent").createAlias("stuEvent.groupedStudent", "student");
			criteria = criteria.add(Restrictions.ne("status", "banked")).add(Restrictions.isNotNull("generatedDate"));
			if (cutOffDate != null)
				criteria.add(Restrictions.ge("eventDate", cutOffDate));
			criteria = criteria.add(Restrictions.eq("group.id", groupedStudent.getGroup().getId()));
			criteria = criteria.add(Restrictions.eq("student.id", groupedStudent.getId()));
			criteria = criteria.add(Restrictions.ne("stuEvent.attended", true));
			criteria = criteria.addOrder(Order.asc("eventDate"));
			list = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			if (list != null && !list.isEmpty() && list.get(0).getProgram().getType().getName().equals("Transport"))
				for (ProgramEvent event : list)
					event.setStatus("Remove");
		} else {
			Query query = session.createQuery("select distinct pe from ProgramEvent pe join pe.studentEvents se "
					+ "where pe.group.id = :groupId and (select s from se s where s.groupedStudent.id = :gsId and s.proEvent.id = pe.id) is null "
					+ "and pe.generatedDate is not null and pe.invoiced = 'false' and pe.status != 'banked' "
					+ (cutOffDate != null ? "and pe.eventDate >= :eventDate " : "") + "order by pe.eventDate");
			query.setParameter("groupId", groupedStudent.getGroup().getId());
			query.setParameter("gsId", groupedStudent.getId());
			if (cutOffDate != null)
				query.setParameter("eventDate", cutOffDate);
			list = query.list();
			if (list != null && !list.isEmpty() && list.get(0).getProgram().getType().getName().equals("Transport"))
				for (ProgramEvent event : list)
					event.setStatus("Add");
		}
		return list;
	}

	public List<ProgramEvent> listEditableEventsByStudent(Long studentId, Date cutOffDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class).createAlias("group", "g")
				.createAlias("studentEvents", "se").createAlias("se.groupedStudent", "gs")
				.createAlias("gs.student", "s").add(Restrictions.ne("status", "banked"))
				.add(Restrictions.isNotNull("generatedDate"));
		if (cutOffDate != null)
			criteria.add(Restrictions.ge("eventDate", cutOffDate));
		criteria.add(Restrictions.eq("s.id", studentId)).add(Restrictions.eq("se.attended", false))
				.addOrder(Order.asc("eventDate")).addOrder(Order.asc("g.id"));
		@SuppressWarnings("unchecked")
		List<ProgramEvent> list = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return list;
	}

	public List<ProgramEvent> listConflictingEventsForStaff(Date startTime, Date endTime, Long staffMemberId,
			Long eventId) {
		Session session = getCurrentSession();
		Criteria criteria = session
				.createCriteria(ProgramEvent.class).add(
						Restrictions.or(
								Restrictions.and(Restrictions.eq("startTime", startTime),
										Restrictions.eq("endTime",
												endTime)),
				Restrictions.or(
						Restrictions.or(
								Restrictions.and(Restrictions.lt("startTime", startTime),
										Restrictions.gt("endTime", startTime)),
						Restrictions.and(Restrictions.lt("startTime", endTime), Restrictions.gt("endTime", endTime))),
						Restrictions.or(
								Restrictions.and(Restrictions.gt("startTime", startTime),
										Restrictions.lt("startTime", endTime)),
								Restrictions.and(Restrictions.gt("endTime", startTime),
										Restrictions.lt("endTime", endTime))))))
				.createAlias("staffEvents", "staffEvent", JoinType.LEFT_OUTER_JOIN)
				.createAlias("staffEvent.staffMember", "staff").add(Restrictions.eq("staff.id", staffMemberId))
				.add(Restrictions.ne("id", eventId)).add(Restrictions.eq("status", "pending"));
		@SuppressWarnings("unchecked")
		List<ProgramEvent> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public List<ProgramEvent> listConflictingEventsForVehicle(Date startTime, Date endTime, Long vehicleId,
			Long eventId) {
		Session session = getCurrentSession();
		Query query = session.createQuery(
				"select event from ProgramEvent as event where ((event.startTime > :start and event.startTime < :end) "
						+ "or (event.endTime > :start and event.endTime < :end) "
						+ "or (:start > event.startTime and :start < event.endTime) "
						+ "or (:end > event.startTime and :end < event.endTime)) "
						+ "and event.vehicle.id = :vehicleId and event.id != :eventId and event.status = 'pending'");
		query.setParameter("start", startTime);
		query.setParameter("end", endTime);
		query.setParameter("vehicleId", vehicleId);
		query.setParameter("eventId", eventId);
		@SuppressWarnings("unchecked")
		List<ProgramEvent> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public boolean isEventExist(Long studentGroupId, Date eventDate, Date startTime, Date endTime) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class).createAlias("group", "group")
				.add(Restrictions.eq("group.id", studentGroupId)).add(Restrictions.eq("eventDate", eventDate))
				.add(Restrictions.eq("startTime", startTime)).add(Restrictions.eq("endTime", endTime));
		@SuppressWarnings("unchecked")
		List<ProgramEvent> events = criteria.list();
		if (events != null && !events.isEmpty()) {
			return true;
		}
		return false;
	}

	public String getMonthStatementStatus(int year, int month) {
		if (month != 0) {
			BigInteger count = new BigInteger("0");
			Session session = getCurrentSession();
			String queryString = "select count(*) from programevents pe where pe.invoiced and date_part('year', pe.eventdate) = "
					+ year + " and date_part('month', pe.eventdate) = " + month;
			SQLQuery query = session.createSQLQuery(queryString);
			count = (BigInteger) query.uniqueResult();
			if (count.intValue() > 0) {
				queryString = "select count(*) from programevents pe where pe.invoiced = false and date_part('year', pe.eventdate) = "
						+ year + " and date_part('month', pe.eventdate) = " + month;
				query = session.createSQLQuery(queryString);
				count = (BigInteger) query.uniqueResult();
				if (count.intValue() > 0)
					return "partial";
				else
					return "completed";
			} else
				return "incomplete";
		}
		return "";
	}

	public void updateMoneyCollected(Long eventId, double totalMoneyCollected, double totalEFTCollected) {
		if (eventId != null) {
			Session session = getCurrentSession();
			Query query = session.createQuery(
					"update ProgramEvent set totalMoneyCollected = :totalMoneyCollected, totalEFTCollected = :totalEFTCollected where id = :eventId");
			query.setParameter("totalMoneyCollected", totalMoneyCollected);
			query.setParameter("totalEFTCollected", totalEFTCollected);
			query.setParameter("eventId", eventId);
			query.executeUpdate();
		}
	}
}