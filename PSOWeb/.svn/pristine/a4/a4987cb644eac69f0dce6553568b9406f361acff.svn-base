package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.StaffEvent;
import com.itelasoft.pso.dao.IStaffEventDao;

public class StaffEventService extends GenericService<StaffEvent, Long>
		implements IStaffEventService {

	public List<StaffEvent> listForStaffEnd(Long staffId, Date serviceEndDate) {
		return ((IStaffEventDao) dao).listForStaffEnd(staffId, serviceEndDate);
	}

	public List<StaffEvent> listByStaffNDates(String staffId, Date fromDate,
			Date toDate) {
		return ((IStaffEventDao) dao).listByStaffNDates(staffId, fromDate,
				toDate);
	}
	
	public List<StaffEvent> listByStaffNDatesWithAttended(String staffId, Date fromDate,
			Date toDate) {
		return ((IStaffEventDao) dao).listByStaffNDatesWithAttended(staffId, fromDate,
				toDate);
	}
	public void deleteList(List<StaffEvent> staffEvents) {
		if (staffEvents != null)
			((IStaffEventDao) dao).deleteList(staffEvents);
	}
	
	public List<StaffEvent> listByProEventId(Long proEventId) {
		return ((IStaffEventDao) dao).listByProEventId(proEventId);
	}
	
}
