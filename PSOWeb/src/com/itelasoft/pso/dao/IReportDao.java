package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.HoursUtilizedReport;
import com.itelasoft.pso.beans.Invoice;
import com.itelasoft.pso.beans.StaffCheckRecord;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.Transaction;

public interface IReportDao {

	public List<StaffMember> listStaffByJoinedDate(int month);

	public List<Object[]> getFundingRecords();

	public List<Object[]> getAbsentees(String studentId, String startDate,
			String endString);

	public List<Object[]> getGroupAbsentees(String fromDate, String toDate);

	public List<Student> listForNonConsentReport(List<Long> consentIds);

	public List<Invoice> listOutstandingInvoices(Date startDate, Date endDate);

	public List<com.itelasoft.pso.beans.Transaction> getCurrentCollections(
			Date transDate);

	public List<Transaction> getPreviousCollections(Date transDate);

	public List<StaffCheckRecord> listStaffCheckRecords(Date cutOffDate);

	public boolean validateCompletedProEvents(Date fromDate, Date toDate,
			Long studentId);

	public HoursUtilizedReport generateHoursUtilizedReport(Date fromDate,
			Date toDate);

	public List<Object[]> listStudentsWithEvents(String startDate,
			String endDate);

	public List<Transaction> listCollectionTxs(Long studentId, Long collectionId);

}
