package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.NdisCommittedEvent;
import com.itelasoft.pso.beans.ProgramType;

public interface INdisCommittedEventService extends
		IGenericService<NdisCommittedEvent, Long> {

	public int deleteCommittedEvents(long studentId, Date startDate,
			Date endDate);

	public List<NdisCommittedEvent> getCommittedEvents(Long studentID,
			Long groupId, Date eventDate, Date endDate);

	public void saveAllEvents(List<NdisCommittedEvent> value);

	public int deleteSelectedEvents(Long studentId,Long groupId, Date fromDate, Date toDate);
	
	public List<NdisCommittedEvent>ndisCommittedEventsListByGroup(Long groupId);
	
	public List<NdisCommittedEvent> retrieveClaimedCommittedEvents(Long studentID,
			Long groupId, Date eventDate, Date endDate);
	
	public int deleteSelectedStudentEvents(Long studentId,
			Date fromDate,Long studentGroupid);
	
	public List<NdisCommittedEvent> SelectedStudentEvents(Long studentId,
			Date fromDate,Long studentGroupid);
	
	public List<NdisCommittedEvent> allCommittedAmountForStudent(Long studentID, ProgramType proType);
	
	public void calculateCommittedEventPrice(List<NdisCommittedEvent> finalCommittedEventsList,boolean save);
	
	public List<NdisCommittedEvent> getCommitedEventsOfInvoiceByStudentId(Long invoiceId,Long studentId);
}