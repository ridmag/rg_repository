package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.dao.IStaffRoasterDao;

public class StaffRoasterService implements IStaffRoasterService {

	private IStaffRoasterDao roasterDao;

	public void setRoasterDao(IStaffRoasterDao roasterDao) {
		this.roasterDao = roasterDao;
	}

	public IStaffRoasterDao getRoasterDao() {
		return roasterDao;
	}

	public List<ProgramEvent> listEventsByDates(Date firstDate, Date secondDate) {
		return this.roasterDao.listEventsByDates(firstDate, secondDate);
	}

	public List<ProgramEvent> searchEventsByDates(Date eventDate) {
		return this.roasterDao.searchEventsByDates(eventDate);
	}

}
