package com.itelasoft.pso.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.Consent;
import com.itelasoft.pso.beans.EnumPaymentType;
import com.itelasoft.pso.beans.HoursUtilizedReport;
import com.itelasoft.pso.beans.HoursUtilizedReportItem;
import com.itelasoft.pso.beans.Invoice;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.StaffCheckRecord;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentConsent;
import com.itelasoft.pso.beans.Transaction;
import com.itelasoft.pso.beans.Transaction.EnumTransactionType;
import com.itelasoft.util.SortObjectByName;

public class ReportDao extends DataAccessObject implements IReportDao {

	public List<StaffMember> listStaffByJoinedDate(int month) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from StaffMember as sta where month(sta.joinedDate) = ?");
		query.setParameter(0, month);
		@SuppressWarnings("unchecked")
		List<StaffMember> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public List<Object[]> getFundingRecords() {
		Session session = getCurrentSession();
		String queryString = "  SELECT programs.name, studentgroups.name as groupName, fundingsources.fundingtype, sum(transactions.amount) as total "
				+ "FROM studentgroups JOIN students JOIN groupedstudents ON students.id = groupedstudents.studentid "
				+ "ON studentgroups.id = groupedstudents.groupid JOIN fundingsources JOIN studentfundingsources "
				+ "ON fundingsources.id = studentfundingsources.fundingsrcid "
				+ "ON students.id = studentfundingsources.studentid "
				+ "JOIN programs JOIN programevents ON programs.id = programevents.programid "
				+ "ON studentgroups.id = programevents.studentgroupid JOIN studentevents "
				+ "ON groupedstudents.id = studentevents.groupedstudentid "
				+ "AND programevents.id = studentevents.eventid JOIN transactions ON studentevents.id = transactions.studenteventid "
				+ "WHERE transactions.paymenttype = 'EVENT' AND programevents.status != 'banked' "
				+ "AND programevents.eventdate <= now()"
				+ "group by fundingsources.fundingtype , programs.name, studentgroups.name ";
		SQLQuery query = session.createSQLQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.list();
		return list;
	}

	public List<Object[]> getAbsentees(String studentId, String fromDate,
			String toDate) {
		Session session = getCurrentSession();
		String queryString = "SELECT programs.name, pe.eventDate, "
				+ "(date_part('hour', pe.endtime - pe.starttime)*60) + date_part('minute', pe.endtime - pe.starttime) as minutes "
				+ "FROM students JOIN groupedstudents ON students.id = groupedstudents.studentid "
				+ "JOIN programs JOIN programevents pe ON programs.id = pe.programid "
				+ "JOIN studentevents ON pe.id = studentevents.eventid "
				+ "ON groupedstudents.id = studentevents.groupedstudentid "
				+ "WHERE studentevents.attended = FALSE AND (pe.status = 'finished' or pe.status = 'banked') "
				+ "AND students.id = " + studentId
				+ " AND pe.eventDate BETWEEN '" + fromDate + "' AND '" + toDate
				+ "' ORDER BY students.id, pe.eventDate";
		SQLQuery query = session.createSQLQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.list();
		return list;
	}

	public List<Object[]> getGroupAbsentees(String fromDate, String toDate) {
		Session session = getCurrentSession();
		String queryString = "SELECT case when q.name = 'MDS Sharing' then true else false end as consentgiven, "
				+ "s.mdsid, c.title, c.firstname, c.lastname, pe.eventDate, "
				+ "(date_part('hour', pe.endtime - pe.starttime)*60) + date_part('minute', pe.endtime - pe.starttime) as minutes "
				+ "FROM studentevents se JOIN groupedstudents gs JOIN students s left outer join "
				+ "(select * from consents c, studentconsents sc where c.name = 'MDS Sharing' and c.id = sc.consentid) as q "
				+ "on s.id = q.studentid ON gs.studentid = s.id JOIN contacts c ON s.contactid = c.id "
				+ "ON se.groupedstudentid = gs.id JOIN programevents pe ON se.eventid = pe.id WHERE se.attended = FALSE AND (pe.status = 'finished' or pe.status = 'banked') and pe.eventDate BETWEEN '"
				+ fromDate + "' AND '" + toDate + "'"+" ORDER BY c.firstname,c.lastname ASC";
		SQLQuery query = session.createSQLQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.list();
		return list;
	}

	public List<Student> listForNonConsentReport(List<Long> consentIds) {
		Session session = getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Consent> consents = session.createCriteria(Consent.class).list();
		List<Long> otherIds = new ArrayList<Long>();
		for (Consent consent : consents) {
			boolean found = false;
			for (Long id : consentIds) {
				if (consent.getId().equals(id)) {
					found = true;
					break;
				}
				if (!found)
					otherIds.add(consent.getId());
			}
		}
		HashMap<String, Long> map = new HashMap<String, Long>();
		for (Long id : consentIds) {
			map.put("c.id", id);
		}
		// Query query = session
		// .createQuery("from Student as s, StudentConsent as sc, Consent as c where c.id not in = ? and c in sc.consent and ");
		Criteria criteria = session
				.createCriteria(Student.class)
				.createAlias("studentConsents", "sc",
						CriteriaSpecification.LEFT_JOIN)
				.createAlias("sc.consent", "c")
				.add(Restrictions.not(Restrictions.allEq(map)));
		// .add(Restrictions.eq("sc.consentGiven", false))
		// .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		// .addOrder(Order.asc("id"));
		@SuppressWarnings("unchecked")
		List<Student> list = criteria.list();
		/*
		 * if (list != null) for (Student std : list) { Criteria crt =
		 * session.createCriteria(Student.class)
		 * .setFetchMode("studentConsents", FetchMode.JOIN)
		 * .add(Restrictions.eq("id", std.getId())); std =
		 * (com.itelasoft.pso.beans.Student) crt.uniqueResult(); }
		 */
		return list;
	}

	public List<Invoice> listOutstandingInvoices(Date startDate, Date endDate) {
		Session session = getCurrentSession();
		Criteria crt = session.createCriteria(Invoice.class)
				.createAlias("student", "student")
				.add(Restrictions.gt("total", new Double(0)));
		if (startDate != null)
			crt = crt.add(Restrictions.ge("invoiceDate", startDate));
		if (endDate != null)
			crt = crt.add(Restrictions.le("invoiceDate", endDate));
		crt.addOrder(Order.asc("student.id"))
				.addOrder(Order.asc("invoiceDate"));
		@SuppressWarnings("unchecked")
		List<Invoice> invoices = (List<Invoice>) crt.list();
		return invoices;
	}

	public List<Transaction> getPreviousCollections(Date transDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Transaction.class);
		criteria = criteria
				.add(Restrictions.eq("transactionType",
						EnumTransactionType.DEBIT))
				.add(Restrictions.eq("paymentType", EnumPaymentType.EVENT))
				.createAlias("programEvent", "programEvent")
				.add(Restrictions.ltProperty("programEvent.eventDate",
						"transactionDate"))
				.add(Restrictions.eq("transactionDate", transDate));
		@SuppressWarnings("unchecked")
		List<Transaction> trans = criteria.list();
		return trans;
	}

	public List<Transaction> getCurrentCollections(Date transDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Transaction.class);
		criteria = criteria
				.add(Restrictions.eq("transactionType",
						EnumTransactionType.DEBIT))
				.add(Restrictions.eq("paymentType", EnumPaymentType.EVENT))
				.createAlias("programEvent", "programEvent")
				.add(Restrictions.eqProperty("programEvent.eventDate",
						"transactionDate"))
				.add(Restrictions.eq("transactionDate", transDate));
		@SuppressWarnings("unchecked")
		List<Transaction> trans = criteria.list();
		return trans;
	}

	public List<StaffCheckRecord> listStaffCheckRecords(Date cutOffDate) {
		Session session = getCurrentSession();
		Criteria criteria = session
				.createCriteria(StaffCheckRecord.class)
				.add(Restrictions.or(Restrictions.isNull("expiryDate"),
						Restrictions.le("expiryDate", cutOffDate)))
				.createCriteria("checkRecord")
				.add(Restrictions.eq("id", new Long(1)));
		@SuppressWarnings("unchecked")
		List<StaffCheckRecord> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public boolean validateCompletedProEvents(Date fromDate, Date toDate,
			Long studentId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class)
				.createAlias("group.students", "groupedStudent")
				.createAlias("groupedStudent.student", "student")
				.add(Restrictions.between("eventDate", fromDate, toDate))
				.add(Restrictions.isNotNull("generatedDate"))
				.add(Restrictions.isNull("completedDate"));
		if (studentId != null)
			criteria = criteria.add(Restrictions.eq("student.id", studentId));
		@SuppressWarnings("unchecked")
		List<ProgramEvent> list = criteria.list();
		if (list == null || list.isEmpty())
			return true;
		else
			return false;
	}

	public HoursUtilizedReport generateHoursUtilizedReport(Date fromDate,
			Date toDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Session session = getCurrentSession();
		/*
		 * String queryString = "SELECT students.id AS studentid, " +
		 * "fundingsources.fundingtype AS fundingtype, " +
		 * "(date_part('hour', pe.endtime - pe.starttime)*60) + date_part('minute', pe.endtime - pe.starttime) as totalminutes, "
		 * // + //
		 * "sum(date_part('hour', programevents.endtime) - date_part('hour', programevents.starttime)) AS totalhours, "
		 * +
		 * "case when programtypes.name != 'Transport' and studentevents.attended = 'true' then date_part('hour', pe.endtime - pe.starttime)*60) + date_part('minute', pe.endtime - pe.starttime) else 0 end) as programminutes , "
		 * // + //
		 * "sum(case when programtypes.name != 'Transport' and studentevents.attended = 'true' then date_part('hour', programevents.endtime) - date_part('hour', programevents.starttime) else 0 end) AS programhours, "
		 * +
		 * "case when programtypes.name = 'Transport' and studentevents.attended = 'true' then date_part('hour', pe.endtime - pe.starttime)*60) + date_part('minute', pe.endtime - pe.starttime) else 0 end) AS transportminutes, "
		 * // + //
		 * "sum(case when programtypes.name = 'Transport' and studentevents.attended = 'true' then date_part('hour', programevents.endtime) - date_part('hour', programevents.starttime) else 0 end) AS transporthours "
		 * +
		 * "FROM studentevents JOIN programevents ON studentevents.eventid = programevents.id JOIN groupedstudents ON studentevents.groupedstudentid = groupedstudents.id JOIN programs ON programevents.programid = programs.id JOIN programtypes ON programs.programtypeid = programtypes.id JOIN students ON groupedstudents.studentid = students.id JOIN studentfundingsources ON students.activefundingsrcid = studentfundingsources.id AND studentfundingsources.studentid = students.id JOIN fundingsources ON studentfundingsources.fundingsrcid = fundingsources.id "
		 * + "WHERE programevents.EVENTDATE >= '" + dateFormat.format(fromDate)
		 * + "' AND programevents.EVENTDATE <= '" + dateFormat.format(toDate) +
		 * "' GROUP BY students.id, fundingtype " +
		 * "ORDER BY studentid ASC, fundingtype ASC";
		 */
		String queryString = "SELECT students.id AS studentid, fundingsources.fundingtype AS fundingtype, "
				+ "sum((date_part('hour', programevents.endtime - programevents.starttime)*60) + "
				+ "date_part('minute', programevents.endtime - programevents.starttime)) as totalminutes, "
				+ "sum(case when programtypes.name != 'Transport' and studentevents.attended = 'true' "
				+ "then (date_part('hour', programevents.endtime - programevents.starttime)*60) + "
				+ "date_part('minute', programevents.endtime - programevents.starttime) else 0 end) "
				+ "as programminutes , sum(case when programtypes.name = 'Transport' and studentevents.attended = 'true' "
				+ "then (date_part('hour', programevents.endtime - programevents.starttime)*60) + "
				+ "date_part('minute', programevents.endtime - programevents.starttime) else 0 end) "
				+ "AS transportminutes FROM studentevents JOIN programevents ON studentevents.eventid = programevents.id "
				+ "JOIN groupedstudents ON studentevents.groupedstudentid = groupedstudents.id JOIN programs "
				+ "ON programevents.programid = programs.id JOIN programtypes ON programs.programtypeid = programtypes.id "
				+ "JOIN students ON groupedstudents.studentid = students.id JOIN studentfundingsources "
				+ "ON studentevents.studentFundingSourceId = studentfundingsources.id AND studentfundingsources.studentid = students.id "
				+ "JOIN fundingsources ON studentfundingsources.fundingsrcid = fundingsources.id "
				+ "WHERE programevents.EVENTDATE >= '"
				+ dateFormat.format(fromDate)
				+ "' AND programevents.EVENTDATE <= '"
				+ dateFormat.format(toDate)
				+ "' GROUP BY students.id, fundingtype "
				+ "ORDER BY studentid ASC, fundingtype ASC";
		SQLQuery query = session.createSQLQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Object[]> items = query.list();
		HoursUtilizedReport report = null;
		if ((items != null) && !items.isEmpty()) {
			report = new HoursUtilizedReport();
			for (Object[] item : items) {
				BigInteger studentId = (BigInteger) item[0];
				String fundingType = (String) item[1];
				double totalMinutes = (Double) item[2];
				double programMins = (Double) item[3];
				double transportMins = (Double) item[4];
				HoursUtilizedReportItem reportItem = new HoursUtilizedReportItem();
				Student student = (Student) session.get(Student.class,
						studentId.longValue());
				if (checkConsents(student.getId())) {
					reportItem.setStudent(student);
					reportItem.setFundingType(fundingType);
					reportItem.setTotalMinutes(totalMinutes);
					reportItem.setProgramMinsUsed(programMins);
					reportItem.setTransportMinsUsed(transportMins);
					reportItem.setBalanceMins(totalMinutes - programMins
							- transportMins);
					report.getReportItems().add(reportItem);
				}
			}
			if (report.getReportItems() != null
					&& !report.getReportItems().isEmpty()) {
				Collections.sort(report.getReportItems(), new SortObjectByName());
				report.setGeneratedDate(new Date());
				report.setFromDate(fromDate);
				report.setToDate(toDate);
			} else {
				report = null;
			}
		}
		return report;
	}

	public boolean checkConsents(Long studentId) {
		Session session = getCurrentSession();
		Criteria criteria = session
				.createCriteria(StudentConsent.class)
				.createAlias("student", "student")
				.createAlias("consent", "consent")
				.add(Restrictions.eq("student.id", studentId))
				.add(Restrictions.or(
						Restrictions.eq("consent.name", "Monitoring"),
						Restrictions.eq("consent.name", "MDS Sharing")));
		// Query query = session
		// .createQuery("from StudentConsent as sc, Student as s, Consent as c where s.id = ? and (c.name ='Monitoring' or c.name='MDS Sharing') and (s in(sc.student)) and (c in(sc.consent))");
		// query.setParameter(0, studentId);
		@SuppressWarnings("unchecked")
		List<StudentConsent> list = criteria.list();
		if (list != null && !list.isEmpty() && list.size() == 2)
			return true;
		else {
			return false;
		}
	}

	public List<Object[]> listStudentsWithEvents(String startDate,
			String endDate) {
		Session session = getCurrentSession();
		String condition = "";
		if ((startDate != null && !startDate.isEmpty())
				&& (endDate != null && !endDate.isEmpty())) {
			condition = "WHERE programevents.EVENTDATE >= '" + startDate
					+ "' AND programevents.EVENTDATE <= '" + endDate + "'";
		} else if ((startDate == null || startDate.isEmpty())
				&& (endDate != null && !endDate.isEmpty())) {
			condition = "WHERE programevents.EVENTDATE <= '" + endDate + "'";
		} else if ((startDate != null && !startDate.isEmpty())
				&& (endDate == null || endDate.isEmpty())) {
			condition = "WHERE programevents.EVENTDATE >= '" + startDate + "'";
		}

		String queryString = "SELECT contacts.title, contacts.firstname, contacts.lastname , "
				+ "studentgroups.name as groupname, programs.name as program, programevents.eventdate "
				+ "FROM contacts JOIN students ON contacts.id = students.contactid JOIN groupedstudents "
				+ "JOIN studentgroups JOIN programs JOIN programevents ON programs.id = programevents.programid "
				+ "ON studentgroups.id = programevents.studentgroupid AND programs.id = studentgroups.programid "
				+ "JOIN studentevents ON programevents.id = studentevents.eventid "
				+ "ON groupedstudents.id = studentevents.groupedstudentid AND studentgroups.id = groupedstudents.groupid "
				+ "ON students.id = groupedstudents.studentid "
				+ condition
				+ " group by contacts.title, contacts.firstname, contacts.lastname , "
				+ "studentgroups.name, programs.name, programevents.eventdate, students.id, "
				+ "programs.id, studentgroups.id, programevents.id "
				+ "ORDER BY contacts.firstname, contacts.lastname,students.id, programs.id, studentgroups.id, programevents.id";
		SQLQuery query = session.createSQLQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.list();
		return list;
	}

	public List<Transaction> listCollectionTxs(Long studentId, Long collectionId) {
		Session session = getCurrentSession();
		Criteria criteria = session
				.createCriteria(Transaction.class)
				.add(Restrictions.eq("studentId", studentId))
				.createAlias("collection", "collection")
				.add(Restrictions.eq("collection.id", collectionId))
				.add(Restrictions.eq("transactionType",
						EnumTransactionType.DEBIT))
				.addOrder(Order.asc("transactionDate"));
		@SuppressWarnings("unchecked")
		List<Transaction> list = criteria.list();
		return list;
	}
}
