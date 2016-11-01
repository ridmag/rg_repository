package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.StudentEvent;

public interface IStudentEventDao extends IGenericDao<StudentEvent, Long> {

	public List<StudentEvent> listByGroupedStudent(Long groupedStudentId);

	public List<StudentEvent> listByCriteria(Long programEventId,
			Date fromDate, Date toDate, Long studentId);

	public List<StudentEvent> listByProEventId(Long proEventId);

	public List<StudentEvent> listByInvoiceNStudent(Long invoiceId,
			Long studentId);

	public List<StudentEvent> listOutstandingByStudent(Long studentId);

	public void deleteList(List<StudentEvent> studentEventList);

	public StudentEvent retrieveByProEventsAndStudent(Long programEventId,
			Long groupedStudentId);

	public boolean isEventExist(Long studentId, Date fromDate, Date toDate);

	public List<StudentEvent> retrieveStudentEvents(Long studentId,
			Date startDate, Date endDate);
	
	public Date findMaxEventDateOfMonth(int month, int year,
			Long studentId);

	public List<StudentEvent> programsByStudentsNDates(Long studentid,
			Date start, Date end);

	public List<StudentEvent> retrieveStudentEventsByDate(Long studentId,
			Long groupId, Date eventDate);
}
