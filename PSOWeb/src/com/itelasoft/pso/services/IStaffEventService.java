package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.StaffEvent;

public interface IStaffEventService extends IGenericService<StaffEvent, Long> {

	public List<StaffEvent> listForStaffEnd(Long staffId, Date serviceEndDate);

	public List<StaffEvent> listByStaffNDates(String staffId, Date fromDate, Date toDate);

	public List<StaffEvent> listByStaffNDatesWithAttended(String staffId, Date fromDate, Date toDate);

	public void deleteList(List<StaffEvent> saffEventList);
	
	public List<StaffEvent> listByProEventId(Long proEventId);

}
