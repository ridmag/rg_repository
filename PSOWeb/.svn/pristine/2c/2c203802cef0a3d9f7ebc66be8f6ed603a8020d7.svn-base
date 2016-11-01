package com.itelasoft.pso.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.Invoice;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ProgramType;

public interface IProgramEventService extends
		IGenericService<ProgramEvent, Long> {

	public ProgramEvent getMainEventWithData(ProgramEvent returnEvent,
			boolean loadStudentEvents, boolean loadStaffEvents);

	public ProgramEvent getReturnEvent(ProgramEvent mainEvent);

	/**
	 * Retrieves the return event with data
	 * 
	 * @param mainEvent
	 * @param loadStudentEvents
	 * @param loadStaffEvents
	 */
	public ProgramEvent getReturnEventWithData(ProgramEvent mainEvent,
			boolean loadStudentEvents, boolean loadStaffEvents);

	public List<ProgramEvent> listByStudentGroup(Long groupId);

	public List<ProgramEvent> listByStudentGroupNDates(Long groupId,
			Date startDate, Date endDate);

	/**
	 * If ProgramEvent is a transport event, returning event will be set to
	 * "returnEvent" of ProgramEvent if available
	 * 
	 * @param programId
	 * @param programTypes
	 * @param groupId
	 * @param fromDate
	 * @param toDate
	 * @param statuses
	 * @param studentId
	 * @return List<ProgramEvent>
	 */
	public List<ProgramEvent> listByCriteria(Long programId,
			List<ProgramType> programTypes, Long groupId, Date fromDate,
			Date toDate, List<String> statuses, Long studentId);

	public List<ProgramEvent> listForCoordinatorEnd(Long staffId,
			Date serviceEndDate);

	public List<ProgramEvent> listForTimeTable(Date fromDate, Date toDate,
			Long studentId);

	public boolean searchByDate(Date date, Long programId);

	public void markEventsInvoiced(Invoice invoice);

	public boolean isEventExist(Date fromDate, Date toDate);

	public ProgramEvent getLatestEventForLocation(Long locId);

	public ProgramEvent getLatestEventForVehicle(Long vehiId);

	public List<ProgramEvent> getRosterForStaff(Date start, Date end,
			String staffId);

	/**
	 * When retrieving, this will return with requested lists pre-loaded to the
	 * program event
	 * 
	 * @param eventId
	 * @param loadStudentEvents
	 * @param loadStaffEvents
	 */
	public ProgramEvent retrieveWithData(Long eventId,
			boolean loadStudentEvents, boolean loadStaffEvents);

	/**
	 * List all not banked program events of the given student. If a cutoff date
	 * is provided, it will query the database from that date onwards.
	 * 
	 * @param studentId
	 * @param cutOffDate
	 * @return
	 */
	public List<ProgramEvent> listEditableEventsByStudent(Long studentId,
			Date cutOffDate);

	/**
	 * List all not banked program events of the given grouped student. If the
	 * student is an excluded one, this will return a list of events which has
	 * student in their attendance list(to remove the student). otherwise it
	 * will return a list which has attendance sheets without this student(to
	 * add the student). If a cutoff date is provided, it will query the
	 * database from that date onwards. Action will be set to the program
	 * event's status as "Add/Remove" according to the grouped student's exclude
	 * status only for transport events
	 * 
	 * @param groupedStudent
	 * @param cutOffDate
	 * @return
	 */
	public List<ProgramEvent> listEditableEventsByGroupedStudent(
			GroupedStudent groupedStudent, Date cutOffDate);

	/**
	 * Returns a list of conflicting program events with the provided times and
	 * staff member. This process get the values directly from the database
	 * 
	 * @param startTime
	 * @param endTime
	 * @param staffMemberId
	 * @param eventId
	 */
	public List<ProgramEvent> listConflictingEventsForStaffDB(Date startTime,
			Date endTime, Long staffMemberId, Long eventId);

	/**
	 * Returns a list of conflicting program events with the provided times and
	 * vehicle. This process get the values directly from the database
	 * 
	 * @param startTime
	 * @param endTime
	 * @param staffMemberId
	 * @param eventId
	 */
	public List<ProgramEvent> listConflictingEventsForVehicleDB(Date startTime,
			Date endTime, Long vehicleId, Long eventId);

	public List<ProgramEvent> listTransportForStudentPrograms(Date eventDate,
			Long studentId, Long locationId);

	public boolean isEventExist(Long studentGroupId, Date eventDate,
			Date startTime, Date endTime);

	/**
	 * Returns the given map updated with statement statuses for the given
	 * months
	 * 
	 * @param months
	 * @param monthClassesMap
	 * 
	 */
	public HashMap<Integer, String> refreshMonthClasses(int year, int[] months,
			HashMap<Integer, String> monthClassesMap);

	/**
	 * Updates only the totalMoneyCollected and totalEFTCollected on the given
	 * programEvent
	 * 
	 * @param eventId
	 * @param totalMoneyCollected
	 * @param totalEFTCollected
	 */
	public void updateMoneyCollected(Long eventId, double totalMoneyCollected,
			double totalEFTCollected);
	
	/**
	 * When deleting an invoice reset invoiced flag of the program event which is associated with the invoice to false
	 * programEvent
	 * 
	 * @param invoice
	 */
	public void unmarkEventsInvoiced(int year, int month,Invoice invoice);
	
}
