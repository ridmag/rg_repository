package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.StudentEvent;


public interface IStudentEventService extends
		IGenericService<StudentEvent, Long> {

	public List<StudentEvent> listByGroupedStudent(Long groupedStudentId);

	public List<StudentEvent> listByCriteria(Long programEventId,
			Date fromDate, Date toDate, Long studentId);

	public List<StudentEvent> listByProEventId(Long proEventId);

	public List<StudentEvent> listByInvoiceNStudent(Long invoiceId,
			Long studentId);

	public List<StudentEvent> listOutstandingByStudent(Long studentId);

	public List<StudentEvent> updateList(List<StudentEvent> studentEventList);

	public void deleteList(List<StudentEvent> studentEventList);

	public void deleteByProEventsAndStudent(List<ProgramEvent> events,
			Long studentId);

	public boolean isEventExist(Long studentId, Date fromDate, Date toDate);
	
	public List<StudentEvent> retrieveStudentEvents(Long studentId,Date startDate,Date endDate);
	
	public List<StudentEvent> programsByStudentsNDates(Long studentid, Date start , Date end);

	public List<StudentEvent> retrieveStudentEventsByDate(Long studentId,
			Long groupId, Date eventDate);
	
	public Date findMaxEventDateOfMonth(int month, int year,
			Long studentId);
}
