package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.Holiday;

public interface IHolidayService extends IGenericService<Holiday, Long> {

	// public List<Holiday> listByDate(Date date);

	public boolean containHolidays(Long calendarId, Date startDate, Date endDate);

	public List<Holiday> listByCriteria(Date fromDate, Date toDate,
			Long calendarId);

	public Holiday retrieveByCriteria(Date date, Long calendarId);

	public boolean isHoliday(Long calendarId, Date eventDate);
	
	public Holiday getHolidayType(Date holiday);

}
