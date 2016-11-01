package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.StudentEvent;
import com.itelasoft.pso.dao.IProgramEventDao;
import com.itelasoft.pso.dao.IStudentEventDao;

public class StudentEventService extends GenericService<StudentEvent, Long>
		implements IStudentEventService {

	private IProgramEventDao programEventDao;

	public List<StudentEvent> listByGroupedStudent(Long groupedStudentId) {
		return ((IStudentEventDao) dao).listByGroupedStudent(groupedStudentId);
	}

	public List<StudentEvent> listByCriteria(Long programEventId,
			Date fromDate, Date toDate, Long studentId) {
		return ((IStudentEventDao) dao).listByCriteria(programEventId,
				fromDate, toDate, studentId);
	}

	public List<StudentEvent> listByProEventId(Long proEventId) {
		return ((IStudentEventDao) dao).listByProEventId(proEventId);
	}

	public List<StudentEvent> listByInvoiceNStudent(Long invoiceId,
			Long studentId) {
		return ((IStudentEventDao) dao).listByInvoiceNStudent(invoiceId,
				studentId);
	}

	public List<StudentEvent> listOutstandingByStudent(Long studentId) {
		return ((IStudentEventDao) dao).listOutstandingByStudent(studentId);
	}

	public List<StudentEvent> updateList(List<StudentEvent> studentEventList) {
		for (StudentEvent se : studentEventList) {
			se = update(se);
		}
		return studentEventList;
	}

	public void deleteList(List<StudentEvent> studentEventList) {
		for (StudentEvent se : studentEventList) {
			if (se.getEventChargeTx() != null
					&& se.getEventChargeTx().getId() == null)
				se.setEventChargeTx(null);
		}
		((IStudentEventDao) dao).deleteList(studentEventList);
	}

	public void deleteByProEventsAndStudent(List<ProgramEvent> events,
			Long studentId) {
		for (ProgramEvent pe : events) {
			ProgramEvent event = programEventDao.retrive(pe.getId());
			if (event.getStudentEvents() != null) {
				for (StudentEvent se : event.getStudentEvents()) {
					if (se.getGroupedStudent().getStudent().getId()
							.equals(studentId)) {
						event.getStudentEvents().remove(se);
						((IStudentEventDao) dao).delete(se);
						break;
					}
				}
			}
		}
	}

	public boolean isEventExist(Long studentId, Date fromDate, Date toDate) {
		return ((IStudentEventDao) dao).isEventExist(studentId, fromDate,
				toDate);
	}

	public void setProgramEventDao(IProgramEventDao programEventDao) {
		this.programEventDao = programEventDao;
	}

	public List<StudentEvent> retrieveStudentEvents(Long studentId,
			Date startDate, Date endDate) {

		return ((IStudentEventDao) dao).retrieveStudentEvents(studentId,
				startDate, endDate);
	}

	public List<StudentEvent> programsByStudentsNDates(Long studentid,
			Date start, Date end) {
		return ((IStudentEventDao) dao).programsByStudentsNDates(studentid,
				start, end);
	}

	public List<StudentEvent> retrieveStudentEventsByDate(Long studentId,
			Long groupId, Date eventDate) {
		return ((IStudentEventDao) dao).retrieveStudentEventsByDate(studentId,
				groupId, eventDate);
	}
	public Date findMaxEventDateOfMonth(int month, int year,
			Long studentId) {
		return ((IStudentEventDao) dao).findMaxEventDateOfMonth(month, year, studentId);
	}
}
