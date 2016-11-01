package com.itelasoft.pso.services;

import java.util.HashMap;
import java.util.List;

import com.itelasoft.pso.beans.Transaction;

public interface ITransactionService extends IGenericService<Transaction, Long> {

	/**
	 * Returns the given map updated with statement statuses for the given
	 * months
	 * 
	 * @param year
	 * @param months
	 * @param monthClassesMap
	 * @return
	 */
	public HashMap<Integer, String> refreshMonthClasses(int year, int[] months,
			HashMap<Integer, String> monthClassesMap);

	/**
	 * This will return all unbilled transactions of the given month/year
	 * without transactions of students of given IDs
	 * 
	 * @param month
	 * @param year
	 * @param excludingStdIDs
	 * @return
	 */
	public List<Transaction> listUnbilledTxs(int month, int year,
			List<Long> excludingStdIDs);

	public List<Transaction> listUnbilledTxsByStudent(int month, int year,
			Long studentId);

	public List<Transaction> listUnbilledAdHocTxsByStudent(int month, int year,
			Long studentId);

	public List<Transaction> listAdHocTxsByStudent(Long studentId);

	/**
	 * This will return all unbilled ad-hoc transactions of the given month/year
	 * without transactions of students of given IDs
	 * 
	 * @param month
	 * @param year
	 * @param excludingStdIDs
	 * @return
	 */
	public List<Transaction> listUnbilledAdHocTxs(int month, int year,
			List<Long> excludingStdIDs);

	public void deleteAll(List<Transaction> txs);

}
/*
 * <bean id="transactionService"
 * class="com.itelasoft.pso.services.TransactionService"> <property name="dao"
 * ref="transactionDao" /> </bean> public ITransactionService
 * getTransactionService(){ return
 * (ITransactionService)context.getBean("transactionService"); }
 */