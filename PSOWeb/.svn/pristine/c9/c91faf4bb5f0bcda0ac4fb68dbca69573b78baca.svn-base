package com.itelasoft.pso.dao;

import java.util.HashMap;
import java.util.List;

import com.itelasoft.pso.beans.Transaction;

/**
 * Dao class for Transaction.
 */
public interface ITransactionDao extends IGenericDao<Transaction, Long> {

	public boolean isNewTxAvailable(int month, int year, Long studentId);

	public HashMap<Integer, String> refreshMonthClasses(int year, int[] months,
			HashMap<Integer, String> monthClassesMap);

	public List<Transaction> listUnbilledTxs(int month, int year,
			List<Long> excludingStdIDs);

	public List<Transaction> listUnbilledTxsByStudent(int month, int year,
			Long studentId);

	public List<Transaction> listUnbilledAdHocTxsByStudent(int month, int year,
			Long studentId);

	public List<Transaction> listAdHocTxsByStudent(Long studentId);

	public List<Transaction> listUnbilledAdHocTxs(int month, int year,
			List<Long> excludingStdIDs);

}
/*
 * <bean id="transactionDao" class="com.itelasoft.pso.dao.TransactionDao">
 * <property name="sessionFactory" ref="mySessionFactory" /> </bean>
 */