package com.itelasoft.pso.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Hibernate;

import com.itelasoft.pso.beans.Invoice;
import com.itelasoft.pso.dao.IInvoiceDao;
import com.itelasoft.pso.dao.ITransactionDao;

public class InvoiceService extends GenericService<Invoice, Long> implements
		IInvoiceService {

	private ITransactionDao transactionDao;

	public List<Invoice> setData(List<Invoice> invoices) {
		List<Invoice> returnList = new ArrayList<Invoice>();
		for (Invoice inv : invoices)
			returnList.add(retrieveWithItems(inv.getId()));
		return returnList;
	}

	public Invoice retrieveWithItems(Long invoiceId) {
		Invoice invoice = retrieve(invoiceId);
		if (invoice != null) {
			Hibernate.initialize(invoice.getInvoiceItems());
			Hibernate.initialize(invoice.getAdditionalInvoiceItems());
		}
		return invoice;
	}

	public Invoice retrieve(Long studentId, int month, int year) {
		return ((IInvoiceDao) dao).retrieve(studentId, month, year);
	}

	public List<Invoice> listByStudentName(String studentName) {
		return ((IInvoiceDao) dao).listByStudentName(studentName);
	}

	public Invoice getLastInvoice(Date currentInvoiceDate, Long studentId) {
		return ((IInvoiceDao) dao).getLastInvoice(currentInvoiceDate, studentId);
	}

	public List<Invoice> listByStudentId(Long studentId, int year) {
		Calendar cal = new GregorianCalendar();
		List<Invoice> list = ((IInvoiceDao) dao).listByStudentId(studentId,
				year);
		for (Invoice inv : list) {
			cal.setTime(inv.getInvoiceDate());
			if (transactionDao.isNewTxAvailable(cal.get(Calendar.MONTH) + 1,
					cal.get(Calendar.YEAR), inv.getStudent().getId())) {
				inv.setStmntStatus("Following conflict(s) have been found.\n  - New unbilled transactions");
			}
			if (retrieve(
					inv.getStudent().getId(),
					(cal.get(Calendar.MONTH) + 1 == 12 ? 01 : cal
							.get(Calendar.MONTH) + 2),
					(cal.get(Calendar.MONTH) + 1 == 12 ? year + 1 : year)) != null) {
				inv.setNxtStmntFound(true);
			}
		}
		return list;
	}

	public List<Invoice> listByPreviousMonth(int months) {
		return ((IInvoiceDao) dao).listByPreviousMonth(months);
	}

	public List<Invoice> searchByAll(int months, Long studentId,
			String studentName) {
		return ((IInvoiceDao) dao).searchByAll(months, studentId, studentName);
	}

	public List<Invoice> searchByYM(int year, int month) {
		List<Invoice> list = ((IInvoiceDao) dao).searchByYM(year, month);
		for (Invoice inv : list) {
			if (transactionDao.isNewTxAvailable(month, year, inv.getStudent()
					.getId())) {
				inv.setStmntStatus("Following conflict(s) have been found.\n  - New unbilled transactions");
			}
			if (retrieve(inv.getStudent().getId(), (month == 12 ? 01
					: month + 1), (month == 12 ? year + 1 : year)) != null) {
				inv.setNxtStmntFound(true);
			}
		}
		return list;
	}

	public void setTransactionDao(ITransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

}
