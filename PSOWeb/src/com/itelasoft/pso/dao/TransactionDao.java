package com.itelasoft.pso.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.Transaction;

/**
 * Dao class for Transaction.
 */
public class TransactionDao extends GenericDao<Transaction, Long> implements
		ITransactionDao {

	public boolean isNewTxAvailable(int month, int year, Long studentId) {
		Session session = getCurrentSession();
		String queryString = "select count(t) from Transaction as t where "
				+ "date_part('year', t.transactionDate) = :year and date_part('month', t.transactionDate) = :month and "
				+ "t.invoiceId is null and t.studentId = :studentId";
		Query query = session.createQuery(queryString);
		query.setParameter("year", year);
		query.setParameter("month", month);
		query.setParameter("studentId", studentId);
		Long count = (Long) query.uniqueResult();
		if (count > 0)
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public HashMap<Integer, String> refreshMonthClasses(int year, int[] months,
			HashMap<Integer, String> monthClassesMap) {
		if (months != null && months.length != 0) {
			Session session = getCurrentSession();
			for (int month : months) {
				Query query = session.createQuery(
						"select count(i.id) from Invoice i where date_part('year', i.invoiceDate) = :year and date_part('month', i.invoiceDate) = :month");
				query.setParameter("year", year);
				query.setParameter("month", month);
				Long invoiceCount = (Long) query.uniqueResult();
				
//				String queryString = "select count(t) from Transaction as t where "
//						+ "date_part('year', t.transactionDate) = :year and date_part('month', t.transactionDate) = :month and "
//						+ "t.invoiceId is not null";
//				Query query = session.createQuery(queryString);
//				query.setParameter("year", year);
//				query.setParameter("month", month);
//				Long count = (Long) query.uniqueResult();
				if (invoiceCount != null && invoiceCount > 0) {
					List<Transaction> unbilled = listUnbilledTxs(month, year,
							null);
					if (unbilled.isEmpty())
						unbilled = listUnbilledAdHocTxs(month, year, null);
					List<Long> studentIDs = new ArrayList<Long>();
					if (unbilled.isEmpty()){
						Query query1 = session.createQuery(
								"select distinct s.id from Student as s, StudentEvent se where se.groupedStudent.student.id = s.id and "
										+ "date_part('year', se.proEvent.eventDate) = :year and date_part('month', se.proEvent.eventDate) = :month and " +
												"(select count(i.id) from Invoice i where i.student.id = s.id and date_part('year', i.invoiceDate) = :year and date_part('month', i.invoiceDate) = :month)=0 order by s.id");
						query1.setParameter("year", year);
						query1.setParameter("month", month);
						studentIDs = query1.list();
					}
					if (unbilled.isEmpty() && studentIDs.isEmpty())
						monthClassesMap.put(month, "completed");
					else
						monthClassesMap.put(month, "partial");
				} else {
					monthClassesMap.put(month, "incomplete");
				}
			}
		}
		return monthClassesMap;
	}

	public List<Transaction> listUnbilledTxs(int month, int year,
			List<Long> excludingStdIDs) {
		Session session = getCurrentSession();
		String queryString = "select t from Transaction as t where "
				+ "date_part('year', t.transactionDate) = :year and date_part('month', t.transactionDate) = :month and "
				+ "t.invoiceId is null";
		if (excludingStdIDs != null && !excludingStdIDs.isEmpty()) {
			queryString += " and t.studentId not in (:studentIDs)";
		}
		queryString += " order by t.studentId asc, t.programEvent.eventDate asc";
		Query query = session.createQuery(queryString);
		query.setParameter("year", year);
		query.setParameter("month", month);
		if (excludingStdIDs != null && !excludingStdIDs.isEmpty()) {
			query.setParameterList("studentIDs", excludingStdIDs);
		}
		@SuppressWarnings("unchecked")
		List<Transaction> list = query.list();
		return list;
	}

	public List<Transaction> listUnbilledAdHocTxs(int month, int year,
			List<Long> excludingStdIDs) {
		Session session = getCurrentSession();
		String queryString = "select t from Transaction as t where "
				+ "date_part('year', t.transactionDate) = :year and date_part('month', t.transactionDate) = :month and "
				+ "t.invoiceId is null and t.programEvent is null";
		if (excludingStdIDs != null && !excludingStdIDs.isEmpty()) {
			queryString += " and t.studentId not in :studentIDs";
		}
		queryString += " order by t.transactionDate asc";
		Query query = session.createQuery(queryString);
		query.setParameter("year", year);
		query.setParameter("month", month);
		if (excludingStdIDs != null && !excludingStdIDs.isEmpty()) {
			query.setParameterList("studentIDs", excludingStdIDs);
		}
		@SuppressWarnings("unchecked")
		List<Transaction> list = query.list();
		return list;
	}

	public List<Transaction> listUnbilledTxsByStudent(int month, int year,
			Long studentId) {
		Session session = getCurrentSession();
		String queryString = "select t from Transaction as t where "
				+ "date_part('year', t.transactionDate) = :year and date_part('month', t.transactionDate) = :month and "
				+ "t.invoiceId is null and t.studentId = :studentId and (t.chargeType = 'EVENT' or t.paymentType = 'EVENT')";
		queryString += " order by t.programEvent.eventDate, t.programEvent.group.name, t.programEvent.id, t.transactionType";
		Query query = session.createQuery(queryString);
		query.setParameter("year", year);
		query.setParameter("month", month);
		query.setParameter("studentId", studentId);
		@SuppressWarnings("unchecked")
		List<Transaction> list = query.list();
		return list;
	}

	public List<Transaction> listUnbilledAdHocTxsByStudent(int month, int year,
			Long studentId) {
		Session session = getCurrentSession();
		String queryString = "select t from Transaction as t left join t.programEvent pe where "
				+ "date_part('year', t.transactionDate) = :year and date_part('month', t.transactionDate) = :month and "
				+ "t.invoiceId is null and t.studentId = :studentID and (t.chargeType != 'EVENT' or t.paymentType != 'EVENT') "
				+ "order by pe.eventDate, t.transactionDate asc";
		Query query = session.createQuery(queryString);
		query.setParameter("year", year);
		query.setParameter("month", month);
		query.setParameter("studentID", studentId);
		@SuppressWarnings("unchecked")
		List<Transaction> list = query.list();
		return list;
	}

	public List<Transaction> listAdHocTxsByStudent(Long studentId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Transaction.class)
				.add(Restrictions.isNull("programEvent"))
				.add(Restrictions.eq("studentId", studentId))
				.addOrder(Order.asc("transactionDate"));
		@SuppressWarnings("unchecked")
		List<Transaction> list = criteria.list();
		return list;
	}

}
