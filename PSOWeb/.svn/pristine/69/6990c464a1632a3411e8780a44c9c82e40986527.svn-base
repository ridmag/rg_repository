package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ProgramType;

public interface IProgramEventDao extends IGenericDao<ProgramEvent, Long> {

	public ProgramEvent retrieveWithData(Long eventId,
			boolean loadStudentEvents, boolean loadStaffEvents);

	public ProgramEvent getMainEvent(ProgramEvent returnEvent);

	public ProgramEvent getReturnEvent(ProgramEvent mainEvent);

	public List<ProgramEvent> listByStudentGroup(Long groupId);

	public List<ProgramEvent> listByStudentGroupNDates(Long groupId,
			Date startDate, Date endDate);

	public List<ProgramEvent> listByCriteria(Long programId,
			List<ProgramType> programTypes, Long groupId, Date fromDate,
			Date toDate, List<String> statuses, Long studentId);

	public List<ProgramEvent> listForCoordinatorEnd(Long staffId,
			Date serviceEndDate);

	public List<ProgramEvent> listForTimeTable(Date fromDate, Date toDate,
			Long studentId);

	public boolean searchByDate(Date date, Long programId);

	public ProgramEvent getLatestEventForLocation(Long locId);

	public ProgramEvent getLatestEventForVehicle(Long vehiId);

	public List<ProgramEvent> getRosterForStaff(Date start, Date end,
			String staffId);

	public boolean isEventExist(Date fromDate, Date toDate);

	public List<ProgramEvent> listEditableEventsByGroupedStudent(
			GroupedStudent groupedStudent, Date cutOffDate);

	public List<ProgramEvent> listEditableEventsByStudent(Long studentId,
			Date cutOffDate);

	public List<ProgramEvent> listConflictingEventsForStaff(Date startTime,
			Date endTime, Long staffMemberId, Long eventId);

	public List<ProgramEvent> listConflictingEventsForVehicle(Date startTime,
			Date endTime, Long vehicleId, Long eventId);

	public List<ProgramEvent> listTransportForStudentPrograms(Date eventDate,
			Long studentId, Long locationId);

	public boolean isEventExist(Long studentGroupId, Date eventDate,
			Date startTime, Date endTime);

	public String getMonthStatementStatus(int year, int month);

	public void updateMoneyCollected(Long eventId, double totalMoneyCollected,
			double totalEFTCollected);
	
}
