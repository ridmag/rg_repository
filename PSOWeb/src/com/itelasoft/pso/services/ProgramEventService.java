package com.itelasoft.pso.services;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Hibernate;

import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.Invoice;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.beans.StudentEvent;
import com.itelasoft.pso.beans.Transaction;
import com.itelasoft.pso.dao.IInvoiceDao;
import com.itelasoft.pso.dao.IProgramEventDao;
import com.itelasoft.util.SortObjectById;

public class ProgramEventService extends GenericService<ProgramEvent, Long> implements IProgramEventService {

	private IInvoiceDao invoiceDao;

	public ProgramEvent getMainEventWithData(ProgramEvent returnEvent, boolean loadStudentEvents,
			boolean loadStaffEvents) {
		ProgramEvent event = ((IProgramEventDao) dao).getMainEvent(returnEvent);
		if (event != null && loadStudentEvents)
			Hibernate.initialize(event.getStudentEvents());
		if (event != null && loadStaffEvents)
			Hibernate.initialize(event.getStaffEvents());
		return event;
	}

	public ProgramEvent getReturnEvent(ProgramEvent mainEvent) {
		return ((IProgramEventDao) dao).getReturnEvent(mainEvent);
	}

	public ProgramEvent getReturnEventWithData(ProgramEvent mainEvent, boolean loadStudentEvents,
			boolean loadStaffEvents) {
		ProgramEvent event = ((IProgramEventDao) dao).getReturnEvent(mainEvent);
		if (event != null)
			event = ((IProgramEventDao) dao).retrieveWithData(event.getId(), loadStudentEvents, loadStaffEvents);
		return event;
	}

	public void markEventsInvoiced(Invoice invoice) {
		Invoice inv = invoiceDao.retrive(invoice.getId());
		Hibernate.initialize(inv.getTransactions());
		Collections.sort(invoice.getTransactions(), new SortObjectById());
		Long eventId = null;
		for (Transaction tx : invoice.getTransactions()) {
			// if (tx.getTransactionType().equals(EnumTransactionType.CREDIT)) {
			if (tx.getProgramEvent() != null) {
				if (tx.getProgramEvent().getId() == eventId) {
					continue;
				} else {
					eventId = tx.getProgramEvent().getId();
					ProgramEvent event = retrieve(eventId);

					event.setInvoiced(true);
					tx.setProgramEvent(update(event));

				}
			}
			// }
		}
	}

	public void unmarkEventsInvoiced(int year, int month, Invoice invoice) {
		Invoice inv = invoiceDao.retrive(invoice.getId());
		Hibernate.initialize(inv.getTransactions());
		Collections.sort(inv.getTransactions(), new SortObjectById());
		Long eventId = null;
		for (Transaction tx : inv.getTransactions()) {
			if (tx.getProgramEvent() != null) {
				if (tx.getProgramEvent().getId() == eventId) {
					continue;
				} else {
					boolean invoiceFound = false;
					eventId = tx.getProgramEvent().getId();
					ProgramEvent event = retrieve(eventId);
					if (event.isInvoiced()) {
						Hibernate.initialize(event.getStudentEvents());
						for (StudentEvent studentEvent : event.getStudentEvents()) {
							if (!(invoice.getStudent().getId().equals(studentEvent.getGroupedStudent().getStudent().getId()))) {
								invoiceFound = invoiceDao.isInvoiceAvailableByStudent(
										studentEvent.getGroupedStudent().getStudent().getId(), year, month);
							}
							if (invoiceFound) {
								break;
							}
						}
						if (!invoiceFound) {
							event.setInvoiced(false);
							tx.setProgramEvent(update(event));
						}
					}
				}
			}
		}
	}

	public List<ProgramEvent> listByStudentGroup(Long groupId) {
		return ((IProgramEventDao) dao).listByStudentGroup(groupId);
	}

	public List<ProgramEvent> listByStudentGroupNDates(Long groupId, Date startDate, Date endDate) {
		return ((IProgramEventDao) dao).listByStudentGroupNDates(groupId, startDate, endDate);
	}

	public List<ProgramEvent> listForCoordinatorEnd(Long staffId, Date serviceEndDate) {
		return ((IProgramEventDao) dao).listForCoordinatorEnd(staffId, serviceEndDate);
	}

	public boolean searchByDate(Date date, Long programId) {
		return ((IProgramEventDao) dao).searchByDate(date, programId);
	}

	public boolean isEventExist(Date fromDate, Date toDate) {
		return ((IProgramEventDao) dao).isEventExist(fromDate, toDate);
	}

	public ProgramEvent getLatestEventForLocation(Long locId) {
		return ((IProgramEventDao) dao).getLatestEventForLocation(locId);
	}

	public ProgramEvent getLatestEventForVehicle(Long vehiId) {
		return ((IProgramEventDao) dao).getLatestEventForVehicle(vehiId);
	}

	public List<ProgramEvent> getRosterForStaff(Date start, Date end, String staffId) {
		return ((IProgramEventDao) dao).getRosterForStaff(start, end, staffId);
	}

	public List<ProgramEvent> listByCriteria(Long programId, List<ProgramType> programTypes, Long groupId,
			Date fromDate, Date toDate, List<String> statuses, Long studentId) {
		return ((IProgramEventDao) dao).listByCriteria(programId, programTypes, groupId, fromDate, toDate, statuses,
				studentId);
	}

	public List<ProgramEvent> listForTimeTable(Date fromDate, Date toDate, Long studentId) {
		return ((IProgramEventDao) dao).listForTimeTable(fromDate, toDate, studentId);
	}

	public ProgramEvent retrieveWithData(Long eventId, boolean loadStudentEvents, boolean loadStaffEvents) {
		return ((IProgramEventDao) dao).retrieveWithData(eventId, loadStudentEvents, loadStaffEvents);
	}

	public List<ProgramEvent> listEditableEventsByStudent(Long studentId, Date cutOffDate) {
		return ((IProgramEventDao) dao).listEditableEventsByStudent(studentId, cutOffDate);
	}

	public List<ProgramEvent> listEditableEventsByGroupedStudent(GroupedStudent groupedStudent, Date cutOffDate) {
		return ((IProgramEventDao) dao).listEditableEventsByGroupedStudent(groupedStudent, cutOffDate);
	}

	public List<ProgramEvent> listConflictingEventsForStaffDB(Date startTime, Date endTime, Long staffMemberId,
			Long eventId) {
		return ((IProgramEventDao) dao).listConflictingEventsForStaff(startTime, endTime, staffMemberId, eventId);
	}

	public List<ProgramEvent> listConflictingEventsForVehicleDB(Date startTime, Date endTime, Long vehicleId,
			Long eventId) {
		return ((IProgramEventDao) dao).listConflictingEventsForVehicle(startTime, endTime, vehicleId, eventId);
	}

	public List<ProgramEvent> listTransportForStudentPrograms(Date eventDate, Long studentId, Long locationId) {
		return ((IProgramEventDao) dao).listTransportForStudentPrograms(eventDate, studentId, locationId);
	}

	public boolean isEventExist(Long studentGroupId, Date eventDate, Date startTime, Date endTime) {
		return ((IProgramEventDao) dao).isEventExist(studentGroupId, eventDate, startTime, endTime);
	}

	public HashMap<Integer, String> refreshMonthClasses(int year, int[] months,
			HashMap<Integer, String> monthClassesMap) {
		if (months != null && months.length != 0) {
			for (int month : months) {
				monthClassesMap.put(month, ((IProgramEventDao) dao).getMonthStatementStatus(year, month));
			}
		}
		return monthClassesMap;
	}

	public void updateMoneyCollected(Long eventId, double totalMoneyCollected, double totalEFTCollected) {
		((IProgramEventDao) dao).updateMoneyCollected(eventId, totalMoneyCollected, totalEFTCollected);
	}

	public void setInvoiceDao(IInvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}
}