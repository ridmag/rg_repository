package com.itelasoft.pso.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.Holiday;

@ManagedBean(name = "holidayManagerModel")
@SessionScoped
public class HolidayManagerModel extends UIModel {

	private List<Holiday> holidays;
	private Holiday holiday, tmpHoliday;
	// private boolean visibleConfirmation;
	private Date fromDate, toDate;
	// private String message;
	private Calendar cal = new GregorianCalendar();
	private com.itelasoft.pso.beans.Calendar calendar;
	private Long selectedCalId;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public HolidayManagerModel() {
		init();
	}
	
	public void init() {
		selectedCalId = new Long(0);
		fromDate = toDate = null;
		// calendar = serviceLocator.getCalendarService().retrieve(new Long(1));
		// if (calendar != null) {
		// holidays =
		// serviceLocator.getHolidayService().listByCriteria(fromDate, toDate,
		// calendar.getId());
		// } else
		// showError("There is an error with the holiday calendar. Please
		// contact the system administrator.");
		calendar = serviceLocator.getCalendarService().retrieve(1L);
		holidays = serviceLocator.getHolidayService().listByCriteria(fromDate, toDate, 1L);
		holiday = null;
		tmpHoliday = null;
	}

	public void calendarChanged(ValueChangeEvent event) {
		selectedCalId = (Long) event.getNewValue();
		calendar = serviceLocator.getCalendarService().retrieve(selectedCalId);
		holidays = serviceLocator.getHolidayService().listByCriteria(fromDate, toDate, selectedCalId);
	}

	public void searchHoliday() {
		if (validateSearch()) {
			holiday = null;
			tmpHoliday = null;
			//holidays = serviceLocator.getHolidayService().listByCriteria(fromDate, toDate, calendar.getId());
			holidays = serviceLocator.getHolidayService().listByCriteria(fromDate, toDate, 1L);
			if (holidays == null || holidays.isEmpty())
				showError("No holidays found..");
		}
	}

	public void addNewHoliday() {
		if (tmpHoliday != null)
			tmpHoliday.setUi_selected(false);
		holiday = new Holiday();
	}

	public void selectHoliday(ClickActionEvent re) {
		if (tmpHoliday != null)
			tmpHoliday.setUi_selected(false);
		tmpHoliday = (Holiday) re.getComponent().getAttributes().get("holiday");
		tmpHoliday.setUi_selected(true);
		holiday = serviceLocator.getHolidayService().retrieve(tmpHoliday.getId());
	}

	public void saveHoliday(ActionEvent ae) {
		if (holiday.getStartDate() == null) {
			showError("Start date could not be empty..");
			return;
		} else {
			if (holiday.getEndDate() == null) {
				holiday.setEndDate(holiday.getStartDate());
			}
		}
		if ((!holiday.getStartDate().equals(holiday.getEndDate()))
				&& (holiday.getStartDate().after(holiday.getEndDate()))) {
			showError("Start date must be before the end date.");
			return;
		}
		cal.setTime(holiday.getStartDate());
		/*
		 * if (cal.get(java.util.Calendar.DAY_OF_WEEK) == 1 ||
		 * cal.get(java.util.Calendar.DAY_OF_WEEK) == 7) { showError(
		 * "Invalid start date.."); showInfo(
		 * "Please select dates other than weekends."); return; }
		 * cal.setTime(holiday.getEndDate()); if
		 * (cal.get(java.util.Calendar.DAY_OF_WEEK) == 1 ||
		 * cal.get(java.util.Calendar.DAY_OF_WEEK) == 7) { showError(
		 * "Invalid end date.."); showInfo(
		 * "Please select dates other than weekends."); return; }
		 */
		if (holiday.getId() != null) {
			if ((dateFormat.format(tmpHoliday.getStartDate()).equals(dateFormat.format(holiday.getStartDate())))
					&& (dateFormat.format(tmpHoliday.getEndDate()).equals(dateFormat.format(holiday.getEndDate())))) {
				saveHoliday();
			} else {
				if (!serviceLocator.getHolidayService().containHolidays(calendar.getId(), holiday.getStartDate(),
						holiday.getEndDate())) {
					if (serviceLocator.getProgramEventService().isEventExist(holiday.getStartDate(),
							holiday.getEndDate())) {
						showError("Events have been already planned within this date range ["
								+ dateFormat.format(holiday.getStartDate()) + " - "
								+ dateFormat.format(holiday.getEndDate()) + "]");
						showError("Please delete them first..");
					} else
						saveHoliday();
				} else
					showError("There are existing holidays within the given date range..");
			}
		} else {
			if (!serviceLocator.getHolidayService().containHolidays(calendar.getId(), holiday.getStartDate(),
					holiday.getEndDate())) {
				if (serviceLocator.getProgramEventService().isEventExist(holiday.getStartDate(),
						holiday.getEndDate())) {
					showError("Events have been already planned within this date range ["
							+ dateFormat.format(holiday.getStartDate()) + " - "
							+ dateFormat.format(holiday.getEndDate()) + "]");
					showError("Please delete them first..");
				} else
					saveHoliday();
			} else
				showError("There are existing holidays within the given date range..");
		}
	}

	public void saveHoliday() {
		if (holiday.getId() == null) {
			holiday.setCalendar(calendar);
			holiday.setCreateOn(new Date());
			tmpHoliday = serviceLocator.getHolidayService().create(holiday);
			tmpHoliday.setUi_selected(true);
			holidays.add(tmpHoliday);
			// holidays =
			// serviceLocator.getHolidayService().listByCriteria(fromDate,
			// toDate, calendar.getId());
		} else {
			holiday = serviceLocator.getHolidayService().update(holiday);
			holiday.setUi_selected(true);
			holidays.set(holidays.indexOf(tmpHoliday), holiday);
			tmpHoliday = holiday;
			showInfo("Holiday updated successfully..");
		}
		holiday = serviceLocator.getHolidayService().retrieve(tmpHoliday.getId());
		fromDate = toDate = null;
		//searchHoliday();
	}

	public void deleteHoliday() {
		try {
			serviceLocator.getHolidayService().delete(tmpHoliday.getId());
			holidays.remove(tmpHoliday);
			holiday = tmpHoliday = null;
			showInfo("Holiday deleted succesfully..");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public void selectedDateChanged(ValueChangeEvent event) {
		sessionContext.setActiveString(dateFormat.format(event.getNewValue()));
	}

	private boolean validateSearch() {
		/*if (calendar == null || calendar.getId() == 0) {
			showError("Calendar Type can not be empty..");
			return false;
		}*/
		/*if (fromDate == null) {
			showError("From Date can not be empty..");
			return false;
		}
		if (toDate == null) {
			showError("To Date can not be empty..");
			return false;
		}*/
		if(fromDate != null && toDate != null){
			if(fromDate.compareTo(toDate) > 0){
				showError("'To' date must be greater than the 'From' date.");
				return false;
			}
		}
		return true;
	}

	/*
	 * getters and setters
	 */

	public List<Holiday> getHolidays() {
		return holidays;
	}

	public void setHoliday(Holiday holiday) {
		this.holiday = holiday;
	}

	public Holiday getHoliday() {
		return holiday;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public void setSearchToDate(Date searchToDate) {
		this.toDate = searchToDate;
	}

	public Date getSearchToDate() {
		return toDate;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public com.itelasoft.pso.beans.Calendar getCalendar() {
		return calendar;
	}

	public Long getSelectedCalId() {
		return selectedCalId;
	}

	public void setSelectedCalId(Long selectedCalId) {
		this.selectedCalId = selectedCalId;
	}

}
