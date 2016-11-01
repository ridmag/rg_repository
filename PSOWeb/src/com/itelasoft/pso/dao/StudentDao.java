package com.itelasoft.pso.dao;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentFundingSource;

public class StudentDao extends GenericDao<Student, Long> implements IStudentDao {

	public List<Student> listByFTSeach(String fullText) {

		EntityManager entityManager = Persistence.createEntityManagerFactory("iTelaSearch").createEntityManager();
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		// This will ensure that index for already inserted data is created.
		try {
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Add some more record, lucene will index every new object inserted,
		// removed or updated.
		addMoreRecords(entityManager);
		// Search for Book
		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Student.class).get();
		org.apache.lucene.search.Query query = qb.keyword().onFields("name", "author").matching("Pro Android 4")
				.createQuery();
		javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(query, Student.class);

		// execute search
		@SuppressWarnings("unchecked")
		List<Student> bookResult = jpaQuery.getResultList();

		if (bookResult != null) {
			for (Student s : bookResult) {
				System.out.println("Student found = " + s);
			}
		}
		// Seach for book shelf
		// qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
		// .forEntity(MHSBookShelfEntityBean.class).get();
		// query = qb.keyword().onFields("name").matching("Technical")
		// .createQuery();
		// jpaQuery = fullTextEntityManager.createFullTextQuery(query,
		// MHSBookShelfEntityBean.class);

		// execute search
		// List<MHSBookShelfEntityBean> bookShelfResult =
		// jpaQuery.getResultList();

		// if (bookShelfResult != null) {
		// for (MHSBookShelfEntityBean mhsBookShelfEntityBean : bookShelfResult)
		// {
		// System.out.println("Book Shelf Found = "
		// + mhsBookShelfEntityBean);
		// }
		// }
		return null;
	}

	/**
	 * Adds the more records.
	 * 
	 * @param entityManager
	 *            the entity manager
	 */
	private static void addMoreRecords(EntityManager entityManager) {
		Set<Student> books = new HashSet<Student>();
		Student s = new Student();
		// s.setName("Pro Spring 3");
		// s.setAuthor("Clarence Ho and Rob Harrop");
		books.add(s);
		s = new Student();
		// s.setName("Pro JPA 2 Mastering the Java Persistence API");
		// s.setAuthor("Mike Keith and Merrick Schincariol");
		books.add(s);

		// Fetch the book shelf id, in my db id was 3 so I added it as 3, you
		// can use a query or something
		// Query query = entityManager.createQuery("SELECT BOOKSHELF FROM "
		// + MHSBookShelfEntityBean.class.getName() + " BOOKSHELF");
		// MHSBookShelfEntityBean bookShelfEntityBean = (MHSBookShelfEntityBean)
		// query
		// .getSingleResult();
		// bookShelfEntityBean.setName("Technical Books");
		// bookShelfEntityBean.setBooks(books);

		entityManager.getTransaction().begin();
		// entityManager.persist(bookShelfEntityBean);
		entityManager.getTransaction().commit();
	}

	public List<Student> listByName(String studentName, boolean includeInactive) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class);
		if (!includeInactive)
			criteria = criteria.add(Restrictions.eq("status", "Active"));
		criteria = criteria.createCriteria("contact");
		if (studentName != null && !studentName.isEmpty())
			criteria = criteria
					.add(Restrictions.or(Restrictions.like("firstName", studentName, MatchMode.ANYWHERE).ignoreCase(),
							Restrictions.like("lastName", studentName, MatchMode.ANYWHERE).ignoreCase()));
		criteria.addOrder(Order.asc("firstName")).addOrder(Order.asc("lastName"));
		@SuppressWarnings("unchecked")
		List<Student> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public List<Student> retrieveAvailableByTwoGroups(Long groupId1, Long groupId2) {
		Session session = getCurrentSession();
		Query query;
		if (groupId2 != null) {
			query = session.createQuery(
					"from Student as stu where (stu not in(select grostu.student from GroupedStudent grostu where grostu.group.id = ?)) and (stu not in(select grostu.student from GroupedStudent grostu where grostu.group.id = ?) and stu.status = 'Active')");
		} else {
			query = session.createQuery(
					"from Student as stu where (stu not in(select grostu.student from GroupedStudent grostu where grostu.group.id = ?) and stu.status = 'Active') ");
		}
		query.setParameter(0, groupId1);
		if (groupId2 != null) {
			query.setParameter(1, groupId2);
		}
		return (List<Student>) query.list();
	}

	public List<Student> listAvailableByGroup(Long groupId, String studentName) {
		Session session = getCurrentSession();
		// Query query = session
		// .createQuery("from Student as stu where "
		// +
		// "(stu not in(select grostu.student from GroupedStudent grostu where
		// grostu.group.id = ?)) "
		// +
		// "and (stu in(select fundstu.student from StudentFundingSource fundstu
		// where fundstu.active = 'true')) "
		// +
		// "and (lower(stu.contact.firstName) like ? or
		// lower(stu.contact.lastName) like ?) "
		// + "order by stu.contact.firstName, stu.contact.lastName");
		Query query = session.createQuery("from Student as stu where "
				+ "(stu not in(select grostu.student from GroupedStudent grostu where grostu.group.id = ?)) "
				+ "and (lower(stu.contact.firstName) like ? or lower(stu.contact.lastName) like ?) and (stu.status='Active') order by stu.contact.firstName, stu.contact.lastName");
		query.setParameter(0, groupId);
		query.setParameter(1, "%" + studentName.toLowerCase() + "%");
		query.setParameter(2, "%" + studentName.toLowerCase() + "%");
		@SuppressWarnings("unchecked")
		List<Student> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public Student retrieveAvailableByGroup(Long groupId, Long studentId) {
		Session session = getCurrentSession();
		Query query = session.createQuery(
				"from Student as stu where (stu not in(select grostu.student from GroupedStudent grostu where grostu.group.id = ?)) and stu.id = ?");
		query.setParameter(0, groupId);
		query.setParameter(1, studentId);
		return (Student) query.uniqueResult();
	}

	public List<Student> listByCommunication(Long communicationId) {
		Session session = getCurrentSession();
		Query query = session.createQuery(
				"select stu from Student as stu, Communication as com where com.id = ? and stu in elements(com.involvedStudents)");
		query.setParameter(0, communicationId);
		@SuppressWarnings("unchecked")
		List<Student> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public List<Student> listNotInCommunication(Long communicationId) {
		Session session = getCurrentSession();
		Query query = session.createQuery(
				"select stu from Student as stu, Communication as com where com.id = ? and stu not in elements(com.involvedStudents)");
		query.setParameter(0, communicationId);
		@SuppressWarnings("unchecked")
		List<Student> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public StudentFundingSource getActiveFundingSource(Long studentId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentFundingSource.class).add(Restrictions.eq("active", true))
				.createCriteria("student").add(Restrictions.eq("id", studentId));
		StudentFundingSource sfs = (StudentFundingSource) criteria.uniqueResult();
		// Query query = session
		// .createQuery("from FundingSource as fund where fund in(select
		// stufund.fundingSrc from StudentFundingSource stufund where
		// stufund.student.id = ? and stufund.active = true)");
		// query.setParameter(0, studentId);
		// return (FundingSource) query.uniqueResult();
		return sfs;
	}

	@Override
	public Student retrive(Long id) {

		Student student = super.retrive(id);
		// Hibernate.initialize(student);
		return student;
	}

	public List<Student> listStudentsWithTxs(int month, int year) {
		Session session = getCurrentSession();
		Query oldQuery = session
				.createQuery("select distinct s from Student as s, Transaction as t where t.studentId = s.id and "
						+ "date_part('year', t.transactionDate) = :year and date_part('month', t.transactionDate) = :month order by s.id");
		oldQuery.setParameter("year", year);
		oldQuery.setParameter("month", month);

		Query query = session.createQuery(
				"select distinct s from Student as s, StudentEvent se where se.groupedStudent.student.id = s.id and "
						+ "date_part('year', se.proEvent.eventDate) = :year and date_part('month', se.proEvent.eventDate) = :month order by s.id");
		query.setParameter("year", year);
		query.setParameter("month", month);

		@SuppressWarnings("unchecked")
		List<Student> studentsWithSE = query.list();

		@SuppressWarnings("unchecked")
		List<Student> studentsWithTx = oldQuery.list();

		for (Student std : studentsWithTx) {
			if (!studentsWithSE.contains(std))
				studentsWithSE.add(std);
		}
		return studentsWithSE;
	}

	public List<Long> listStudentsWithNotBankedEvents(int month, int year) {
		Session session = getCurrentSession();
		String queryString = "select distinct t.studentid from transactions t join programevents pe on t.programeventid = pe.id where pe.status != 'banked' and date_part('year', t.transactiondate) = "
				+ year + " and date_part('month', t.transactiondate) = " + month;
		SQLQuery query = session.createSQLQuery(queryString);
		@SuppressWarnings("unchecked")
		List<BigInteger> list = query.list();
		List<Long> studentIDs = new ArrayList<Long>();
		for (BigInteger id : list) {
			studentIDs.add(id.longValue());
		}
		return studentIDs;
	}

	public List<Long> listStudentsMissingPreviousMonthsInvoice(int currentMonth, int currentYear,
			Date actStatementStartDate) {
		int invoiceMonth = currentMonth;
		int invoiceYear = currentYear;
		if (currentMonth == 1) {
			invoiceMonth = 12;
			invoiceYear--;
		} else
			invoiceMonth--;
		Calendar cal = new GregorianCalendar();
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		cal.set(currentYear, currentMonth - 1, 1, 0, 0, 0);

		// String queryStringOld = "select distinct t.studentid from
		// transactions t where "
		// + "date_part('month', t.transactiondate) = " + invoiceMonth
		// + " and date_part('year', t.transactiondate) = " + invoiceYear
		// + " and (select i from invoices i where i.studentid = t.studentid and
		// "
		// + "date_part('month', i.invoicedate) = " + invoiceMonth + " and
		// date_part('year', i.invoicedate) = "
		// + invoiceYear + ") is null";

		Session session = getCurrentSession();
		String queryString = "select distinct t.studentid from transactions t where t.invoiceid is null and t.transactiondate > '"
				+ dateFormatter.format(actStatementStartDate) + "' and t.transactiondate < '"
				+ dateFormatter.format(cal.getTime()) + "'";
		SQLQuery query = session.createSQLQuery(queryString);
		@SuppressWarnings("unchecked")
		List<BigInteger> list = query.list();
		List<Long> studentIDs = new ArrayList<Long>();
		List<Long> finalList = new ArrayList<Long>();
		for (BigInteger id : list) {
			studentIDs.add(id.longValue());
		}
		// :TODO Fix below
		// Query queryNew = session.createQuery(
		// "select distinct s.id from Student as s, StudentEvent se where
		// se.groupedStudent.student.id = s.id and "
		// + "se.proEvent.invoiced = false and se.proEvent.eventDate >
		// :actStatementStartDate and se.proEvent.eventDate < :monthStartDate");
		// queryNew.setParameter("monthStartDate", cal.getTime());
		// queryNew.setParameter("actStatementStartDate",
		// actStatementStartDate);
		Query query1 = session.createQuery(
				"select distinct s.id from Student as s, StudentEvent se where se.groupedStudent.student.id = s.id and "
						+ "date_part('year', se.proEvent.eventDate) = :year and date_part('month', se.proEvent.eventDate) = :month and "
						+ "(select count(i.id) from Invoice i where i.student.id = s.id and date_part('year', i.invoiceDate) = :year and date_part('month', i.invoiceDate) = :month)=0 order by s.id");
		query1.setParameter("year", invoiceYear);
		query1.setParameter("month", invoiceMonth);
		@SuppressWarnings("unchecked")
		List<Long> studentIDs1 = query1.list();
		for (Long id : studentIDs1) {
			if (!studentIDs.contains(id))
				finalList.add(id);
		}
		finalList.addAll(studentIDs);
		return finalList;
	}

	public List<Student> listStudentsByStatus(String status) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class);
		if (!status.equals("all"))
			criteria = criteria.add(Restrictions.eq("status", status));
		criteria = criteria.createCriteria("contact");
		criteria.addOrder(Order.asc("firstName")).addOrder(Order.asc("lastName"));
		@SuppressWarnings("unchecked")
		List<Student> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public List<Student> listActiveStudents() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class).createAlias("contact", "contact")
				.add(Restrictions.eq("status", "Active")).addOrder(Order.asc("contact.firstName"))
				.addOrder(Order.asc("contact.lastName"));
		@SuppressWarnings("unchecked")
		List<Student> members = criteria.list();
		return members;
	}

	public List<Student> listActiveStudentswithNdisnumber() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class).createAlias("contact", "contact")
				.add(Restrictions.eq("status", "Active")).add(Restrictions.ne("ndisNumber", ""))
				.addOrder(Order.asc("contact.firstName")).addOrder(Order.asc("contact.lastName"));
		@SuppressWarnings("unchecked")
		List<Student> members = criteria.list();
		return members;
	}

	@Override
	public List<Student> listAll() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class).createCriteria("contact")
				.addOrder(Order.asc("firstName")).addOrder(Order.asc("lastName"));
		@SuppressWarnings("unchecked")
		List<Student> members = criteria.list();
		return members;
	}

	public List<Student> listStudentByInvoice(Long invoiceId) {
		Session session = getCurrentSession();
		Query query = session.createQuery(
				"select distinct S from Student S, NdisCommittedEvent NCE, NdisAncillaryCost NAC where (NCE.invoiceId = :invoiceid and NCE.participant = S) or (NAC.invoiceId = :invoiceid and NAC.student = S)");
		query.setParameter("invoiceid", invoiceId);
		@SuppressWarnings("unchecked")
		List<Student> students = query.list();
		return students;
	}


}