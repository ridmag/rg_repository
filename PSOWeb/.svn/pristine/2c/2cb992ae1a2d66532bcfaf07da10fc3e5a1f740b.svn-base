package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.Holiday;
import com.itelasoft.pso.dao.IHolidayDao;

public class HolidayService extends GenericService<Holiday, Long> implements
		IHolidayService {
	private IHolidayDao holidayDao;

	/*
	 * public List<Holiday> listByDate(Date date) { return
	 * this.holidayDao.listByDate(date); }
	 */

	public void setHolidayDao(IHolidayDao holidayDao) {
		this.holidayDao = holidayDao;
	}

	public boolean containHolidays(Long calendarId, Date startDate, Date endDate) {
		return this.holidayDao.containHolidays(calendarId, startDate, endDate);
	}

	public List<Holiday> listByCriteria(Date fromDate, Date toDate,
			Long calendarId) {
		return this.holidayDao.listByCriteria(fromDate, toDate, calendarId);
	}

	public Holiday retrieveByCriteria(Date date, Long calendarId) {
		return holidayDao.retrieveByCriteria(date, calendarId);
	}

	public boolean isHoliday(Long calendarId, Date eventDate) {
		return this.holidayDao.isHoliday(calendarId, eventDate);
	}
	
	public Holiday getHolidayType(Date holiday) {
		return this.holidayDao.getHolidayType(holiday);
	}

}
