package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import com.itelasoft.pso.beans.StudentEvent;

public class StudentEventDao extends GenericDao<StudentEvent, Long> implements
		IStudentEventDao {

	public List<StudentEvent> listByGroupedStudent(Long groupedStudentId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentEvent.class).add(
				Restrictions.eq("groupedStudent.id", groupedStudentId));
		@SuppressWarnings("unchecked")
		List<StudentEvent> events = criteria.list();
		return events;
	}

	public List<StudentEvent> listByCriteria(Long programEventId,
			Date fromDate, Date toDate, Long studentId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentEvent.class)
				.createAlias("proEvent", "event")
				.createAlias("event.group", "group")
				.createAlias("event.program", "pro")
				.createAlias("pro.type", "proType");
		if (programEventId != null)
			criteria.add(Restrictions.eq("event.id", programEventId));
		if (fromDate != null)
			criteria.add(Restrictions.ge("event.eventDate", fromDate));
		if (toDate != null)
			criteria.add(Restrictions.le("event.eventDate", toDate));
		if (studentId != null)
			criteria.createAlias("groupedStudent.student", "student").add(
					Restrictions.eq("student.id", studentId));
		@SuppressWarnings("unchecked")
		List<StudentEvent> events = criteria
				.addOrder(Order.asc("event.eventDate"))
				.addOrder(Order.asc("proType.name"))
				.addOrder(Order.asc("pro.id")).addOrder(Order.asc("group.id"))
				.list();
		// .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return events;
	}

	public List<StudentEvent> listByInvoiceNStudent(Long invoiceId,
			Long studentId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select se from StudentEvent se left join se.eventChargeTx ec left join se.paymentTxs p left join se.otherChargeTxs oc where "
						+ "se.groupedStudent.student.id = :studentId and (ec.invoiceId = :invoiceId or p.invoiceId = :invoiceId or oc.invoiceId = :invoiceId) "
						+ "group by se, se.amountPaid, se.proEvent.eventDate, se.proEvent.program.id order by se.proEvent.eventDate, se.proEvent.program.id");
		query.setParameter("invoiceId", invoiceId);
		query.setParameter("studentId", studentId);

		@SuppressWarnings("unused")
		Criteria criteria = session
				.createCriteria(StudentEvent.class)
				.createAlias("proEvent", "event")
				.createAlias("event.program", "program")
				.createAlias("groupedStudent.student", "student")
				.createAlias("eventChargeTx", "chargeTx",
						JoinType.LEFT_OUTER_JOIN)
				// .createAlias("paymentTxs", "paymentTx",
				// JoinType.LEFT_OUTER_JOIN)
				.createAlias("otherChargeTxs", "otherChargeTx",
						JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.eq("student.id", studentId))
				.add(Restrictions.or(
						Restrictions.eq("chargeTx.invoiceId", invoiceId),
						Restrictions.eq("otherChargeTx.invoiceId", invoiceId)))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.addOrder(Order.asc("program.id"))
				.addOrder(Order.asc("event.eventDate"));
		@SuppressWarnings("unchecked")
		List<StudentEvent> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<StudentEvent> listByProEventId(Long proEventId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentEvent.class)
				.createAlias("groupedStudent.student", "student")
				.add(Restrictions.eq("proEvent.id", proEventId))
				.addOrder(Order.asc("student.id"));
		return criteria.list();
	}

	public List<StudentEvent> listOutstandingByStudent(Long studentId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select se from StudentEvent se where se.groupedStudent.student.id = :studentId and "
						+ "(select sum(tx.amount) from Transaction tx where tx.studentId = :studentId and tx.transactionType = 'CREDIT' and tx.programEvent.id = se.proEvent.id) > se.amountPaid");
		query.setParameter("studentId", studentId);
		@SuppressWarnings("unchecked")
		List<StudentEvent> list = query.list();
		return list;
	}

	public void deleteList(List<StudentEvent> studentEventList) {
		Session session = getCurrentSession();
		for (StudentEvent event : studentEventList) {
			session.delete(event);
		}
	}

	public StudentEvent retrieveByProEventsAndStudent(Long programEventId,
			Long groupedStudentId) {
		Session session = getCurrentSession();
		// for (ProgramEvent proEvent : events) {
		Criteria criteria = session.createCriteria(StudentEvent.class)
				.add(Restrictions.eq("proEvent.id", programEventId))
				.add(Restrictions.eq("groupedStudent.id", groupedStudentId));
		return (StudentEvent) criteria.uniqueResult();
		// session.delete(stuEvent);
		// }
	}

	public boolean isEventExist(Long studentId, Date fromDate, Date toDate) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select se from StudentEvent se where "
						+ "se.proEvent.id in(select pe.id from ProgramEvent pe "
						+ "where eventDate >= :fromDate and eventDate <= :toDate) and "
						+ "se.groupedStudent.student.id = :studentId");
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		query.setParameter("studentId", studentId);
		@SuppressWarnings("unchecked")
		List<StudentEvent> events = query.list();
		if (events != null && !events.isEmpty())
			return true;
		return false;
	}

	public List<StudentEvent> retrieveStudentEvents(Long studentId,
			Date startDate, Date endDate) {		
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select se from StudentEvent se "
						+ "where (se.attended = false or (se.proEvent.chargeAmount = 0 and se.amountPaid = 0 and se.eventChargeTx = null)) and se.proEvent.eventDate >= :startDate and se.proEvent.eventDate <= :endDate and "
						+ "se.groupedStudent.student.id = :studentId "
						+ "and se.proEvent.status = 'banked') "
						+ "order by se.proEvent.eventDate");
		query.setParameter("studentId", studentId);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);

		@SuppressWarnings("unchecked")
		List<StudentEvent> list = query.list();
		return list;

	}

	public List<StudentEvent> programsByStudentsNDates(Long studentid,
			Date start, Date end) {
		Session session = getCurrentSession();
		Criteria crt = session.createCriteria(StudentEvent.class)
				.createAlias("proEvent", "proEvent")
				.createAlias("groupedStudent", "groupStudent")
				.createAlias("groupStudent.student", "student")
				.add(Restrictions.ge("proEvent.eventDate", start))
				.add(Restrictions.le("proEvent.eventDate", end))
				.add(Restrictions.eq("student.id", studentid))
				.addOrder(Order.asc("proEvent.eventDate"));
		@SuppressWarnings("unchecked")
		List<StudentEvent> list = crt.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}
	// public StudentEvent retrieve(Long studentId, int month, int year) {
	// if (studentId == null || month <= 0 || month > 12 || year <= 0)
	// return null;
	// Session session = getCurrentSession();
	// Query query = session
	// .createQuery("select i from StudentEvent as i where i.student.id = :studentId and date_part('year', i.groupedStudent.group.startDate) = :year and date_part('month', i.groupedStudent.group.startDate) = :month");
	// query.setParameter("studentId", studentId);
	// query.setParameter("year", year);
	// query.setParameter("month", month);
	// StudentEvent studentEvent = (StudentEvent) query.uniqueResult();
	// }

	public List<StudentEvent> retrieveStudentEventsByDate(Long studentId,
			Long groupId, Date eventDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentEvent.class)
				.createAlias("proEvent", "proEvent")
				.createAlias("groupedStudent", "groupStudent")
				.createAlias("groupStudent.student", "student")
				.createAlias("groupStudent.group", "group")
				.add(Restrictions.eq("proEvent.eventDate", eventDate))
				.add(Restrictions.eq("student.id", studentId))
				.add(Restrictions.eq("group.id", groupId))
				.add(Restrictions.eq("attended", true));

		@SuppressWarnings("unchecked")
		List<StudentEvent> list = criteria.list();
		return list;
	}
	public Date findMaxEventDateOfMonth(int month, int year,
			Long studentId){
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select max(stdevent.proEvent.eventDate) from StudentEvent as stdevent where  date_part('month', stdevent.proEvent.eventDate) = ? and date_part('year', stdevent.proEvent.eventDate) = ? and stdevent.groupedStudent.student.id=? ");
		query.setParameter(0, month);
		query.setParameter(1, year);
		query.setParameter(2, studentId);
		return (Date) query.uniqueResult();
	}
	
}
