package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.HoursUtilizedReport;
import com.itelasoft.pso.beans.Invoice;
import com.itelasoft.pso.beans.StaffCheckRecord;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.Transaction;
import com.itelasoft.pso.dao.IReportDao;

public class ReportService implements IReportService {

	private IReportDao reportDao;

	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}

	public IReportDao getReportDao() {
		return reportDao;
	}

	public List<StaffMember> listStaffByJoinedDate(int month) {
		return this.reportDao.listStaffByJoinedDate(month);
	}

	public List<Object[]> getFundingRecords() {
		return this.reportDao.getFundingRecords();
	}

	public List<Object[]> getAbsentees(String studentId, String startDate,
			String endString) {
		return this.reportDao.getAbsentees(studentId, startDate, endString);
	}

	public List<Object[]> getGroupAbsentees(String fromDate, String toDate) {
		return this.reportDao.getGroupAbsentees(fromDate, toDate);
	}

	public List<Student> listForNonConsentReport(List<Long> consentIds) {
		return this.reportDao.listForNonConsentReport(consentIds);
	}

	public List<Transaction> getCurrentCollections(Date transDate) {
		return this.reportDao.getCurrentCollections(transDate);
	}

	public List<Transaction> getPreviousCollections(Date transDate) {
		return this.reportDao.getPreviousCollections(transDate);
	}

	public List<Invoice> listOutstandingInvoices(Date startDate, Date endDate) {
		return this.reportDao.listOutstandingInvoices(startDate, endDate);
	}

	public List<StaffCheckRecord> listStaffCheckRecords(Date cutOffDate) {
		return this.reportDao.listStaffCheckRecords(cutOffDate);
	}

	public boolean validateCompletedProEvents(Date fromDate, Date toDate,
			Long studentId) {
		return this.reportDao.validateCompletedProEvents(fromDate, toDate,
				studentId);
	}

	public HoursUtilizedReport generateHoursUtilizedReport(Date fromDate,
			Date toDate) {
		return this.reportDao.generateHoursUtilizedReport(fromDate, toDate);
	}

	public List<Object[]> listStudentsWithEvents(String startDate,
			String endDate) {
		return this.reportDao.listStudentsWithEvents(startDate, endDate);
	}

	public List<Transaction> listCollectionTxs(Long studentId, Long collectionId) {
		return this.reportDao.listCollectionTxs(studentId, collectionId);
	}

}
