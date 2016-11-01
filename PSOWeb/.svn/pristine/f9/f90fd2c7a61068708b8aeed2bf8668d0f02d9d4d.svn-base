package com.itelasoft.pso.services;

import java.util.HashMap;
import java.util.List;

import com.itelasoft.pso.beans.Transaction;
import com.itelasoft.pso.dao.ITransactionDao;

public class TransactionService extends GenericService<Transaction, Long>
		implements ITransactionService {

	public HashMap<Integer, String> refreshMonthClasses(int year, int[] months,
			HashMap<Integer, String> monthClassesMap) {
		return ((ITransactionDao) dao).refreshMonthClasses(year, months,
				monthClassesMap);
	}

	public List<Transaction> listUnbilledTxs(int month, int year,
			List<Long> excludingStdIDs) {
		return ((ITransactionDao) dao).listUnbilledTxs(month, year,
				excludingStdIDs);
	}

	public List<Transaction> listUnbilledTxsByStudent(int month, int year,
			Long studentId) {
		return ((ITransactionDao) dao).listUnbilledTxsByStudent(month, year, studentId);
	}

	public List<Transaction> listUnbilledAdHocTxsByStudent(int month, int year,
			Long studentId) {
		return ((ITransactionDao) dao).listUnbilledAdHocTxsByStudent(month,
				year, studentId);
	}

	public List<Transaction> listAdHocTxsByStudent(Long studentId) {
		return ((ITransactionDao) dao).listAdHocTxsByStudent(studentId);
	}

	public List<Transaction> listUnbilledAdHocTxs(int month, int year,
			List<Long> excludingStdIDs) {
		return ((ITransactionDao) dao).listUnbilledAdHocTxs(month, year,
				excludingStdIDs);
	}

	public void deleteAll(List<Transaction> txs) {
		if (txs != null) {
			for (Transaction tx : txs) {
				((ITransactionDao) dao).delete(tx);
			}
		}
	}
}
