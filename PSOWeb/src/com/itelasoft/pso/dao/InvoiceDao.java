package com.itelasoft.pso.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.Invoice;

/**
 * Dao class for Invoice.
 */
public class InvoiceDao extends GenericDao<Invoice, Long> implements IInvoiceDao {

	public Invoice retrieve(Long studentId, int month, int year) {
		if (studentId == null || month <= 0 || month > 12 || year <= 0)
			return null;
		Session session = getCurrentSession();
		Query query = session.createQuery(
				"select i from Invoice as i where i.student.id = :studentId and date_part('year', i.invoiceDate) = :year and date_part('month', i.invoiceDate) = :month");
		query.setParameter("studentId", studentId);
		query.setParameter("year", year);
		query.setParameter("month", month);
		Invoice invoice = (Invoice) query.uniqueResult();
		return invoice;
	}

	public List<Invoice> listByStudentName(String studentName) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Invoice.class);
		if (!studentName.isEmpty())
			criteria = criteria.createCriteria("student").createCriteria("contact")
					.add(Restrictions.or(Restrictions.like("firstName", studentName, MatchMode.ANYWHERE).ignoreCase(),
							Restrictions.like("lastName", studentName, MatchMode.ANYWHERE).ignoreCase()));
		else {
			Calendar calendar = Calendar.getInstance();
			Date today = new Date();
			calendar.setTime(today);
			calendar.add(Calendar.MONTH, -1);
			criteria = criteria.add(Restrictions.between("invoiceDate", calendar.getTime(), today));
		}
		@SuppressWarnings("unchecked")
		List<Invoice> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public List<Invoice> listByStudentId(Long studentId, int year) {
		if (studentId != null && year > 0) {
			Session session = getCurrentSession();
			Query query = session.createQuery(
					"select i from Invoice as i where i.student.id = :studentId and date_part('year', i.invoiceDate) = :year");
			query.setParameter("studentId", studentId);
			query.setParameter("year", year);
			@SuppressWarnings("unchecked")
			List<Invoice> list = query.list();
			return list;
		}
		return new ArrayList<Invoice>();
	}

	public List<Invoice> searchByAll(int months, Long studentId, String studentName) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Invoice.class);
		Calendar calendar = Calendar.getInstance();
		Date today = new Date();
		calendar.setTime(today);
		calendar.add(Calendar.MONTH, -months);
		criteria = criteria.add(Restrictions.between("invoiceDate", calendar.getTime(), today));
		if (studentId != null) {
			criteria = criteria.createCriteria("student").add(Restrictions.eq("id", studentId));
		}
		if (!studentName.isEmpty()) {
			criteria = criteria.createCriteria("student").createCriteria("contact")
					.add(Restrictions.or(Restrictions.like("firstName", studentName, MatchMode.ANYWHERE).ignoreCase(),
							Restrictions.like("lastName", studentName, MatchMode.ANYWHERE).ignoreCase()));
		}
		@SuppressWarnings("unchecked")
		List<Invoice> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public List<Invoice> listByPreviousMonth(int months) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Invoice.class);
		Calendar calendar = Calendar.getInstance();
		Date today = new Date();
		calendar.setTime(today);
		calendar.add(Calendar.MONTH, -months);
		criteria = criteria.add(Restrictions.between("invoiceDate", calendar.getTime(), today));
		@SuppressWarnings("unchecked")
		List<Invoice> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public Invoice getLastInvoice(Date currentInvoiceDate, Long studentId) {
		Session session = getCurrentSession();
		/*
		 * DetachedCriteria maxId = DetachedCriteria.forClass(Invoice.class)
		 * .setProjection(Projections.max("id")).createCriteria("student")
		 * .add(Restrictions.eq("id", studentId)); Criteria criteria =
		 * session.createCriteria(Invoice.class).add(
		 * Property.forName("id").eq(maxId));
		 */
		Query query = session.createQuery("select i from Invoice i where i.invoiceDate = "
				+ "(select max(inv.invoiceDate) from Invoice inv where inv.invoiceDate < :invoiceDate and inv.student.id = :studentId) "
				+ "and i.student.id = :studentId");
		query.setParameter("invoiceDate", currentInvoiceDate);
		query.setParameter("studentId", studentId);
		return (Invoice) query.uniqueResult();
	}

	public List<Invoice> searchByYM(int year, int month) {
		if (year != 0 && month != 0) {
			Session session = getCurrentSession();
			Query query = session.createQuery(
					"select i from Invoice as i where date_part('year', i.invoiceDate) = :year and date_part('month', i.invoiceDate) = :month");
			query.setParameter("year", year);
			query.setParameter("month", month);
			@SuppressWarnings("unchecked")
			List<Invoice> list = query.list();
			return list;
		}
		return new ArrayList<Invoice>();
	}

	public boolean isInvoiceAvailableByStudent(Long studentId, int invoiceYear, int invoiceMonth) {
		if (invoiceYear != 0 && invoiceMonth != 0) {
			Session session = getCurrentSession();
			Query query = session.createQuery(
					"select i from Invoice as i where date_part('year', i.invoiceDate) = :year and date_part('month', i.invoiceDate) = :month and i.student.id = :studentId");
			query.setParameter("year", invoiceYear);
			query.setParameter("month", invoiceMonth);
			query.setParameter("studentId", studentId);
			Invoice invoice = (Invoice) query.uniqueResult();
			return invoice != null;
		}
		return false;
	}
}
