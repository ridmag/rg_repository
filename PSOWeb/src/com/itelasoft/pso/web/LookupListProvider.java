package com.itelasoft.pso.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.itelasoft.pso.beans.Calendar;
import com.itelasoft.pso.beans.EnumCollectionType;
import com.itelasoft.pso.beans.EnumEmploymentType;
import com.itelasoft.pso.beans.EnumHolidayType;
import com.itelasoft.pso.beans.EnumLeaveType;
import com.itelasoft.pso.beans.EnumPaymentType;
import com.itelasoft.pso.beans.EnumSkillLevel;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.beans.StaffType;
import com.itelasoft.pso.beans.WeekDay;

@ManagedBean(name = "lookupListProvider")
public class LookupListProvider extends UIModel {
	private List<SelectItem> proStatusSelectItems;
	private List<SelectItem> proEventStatusSelectItems;
	private List<SelectItem> gender;
	private List<SelectItem> userTypes;
	private List<SelectItem> title;
	private List<SelectItem> authorities;
	private List<SelectItem> vehicleType;
	private List<SelectItem> recurrenceType;
	private List<SelectItem> calendars;
	private List<SelectItem> fundingLevel;
	private List<SelectItem> comMethod;
	private List<SelectItem> fundingStatus;
	private List<SelectItem> staffStatus;
	private List<SelectItem> states;
	private List<SelectItem> staffSkillStatus;
	private List<SelectItem> leavePolicyStatus;
	private List<SelectItem> reminderStatus;
	private List<SelectItem> monthList;
	private List<SelectItem> paymentMethods;
	private List<SelectItem> elapsedTime;
	private List<SelectItem> timePeriods;
	private List<SelectItem> lunchMinutes;
	private List<StaffType> staffTypes;
	private List<WeekDay> weekDays;
	private List<SelectItem> status;
	private List<SelectItem> actStmntYears;
	private List<SelectItem> uom;
	private List<SelectItem> timeofday;
	private List<SelectItem> timeofweek;
	private List<SelectItem> studentPaymentMethods;
	private List<SelectItem> ancillaryCostList;
	private List<SelectItem> programTimes;
	private List<SelectItem> transportTimes;
	private List<SelectItem> ancillaryTimes;
	private List<SelectItem> gstCodeList;
	private List<SelectItem> programTypes;
	private List<ProgramType> allProgramTypes;
	private List<SelectItem> programTypesList;

	public List<SelectItem> getUom() {
		if (uom == null) {
			uom = new ArrayList<SelectItem>();
			uom.add(new SelectItem("Hour", "Hour"));
			uom.add(new SelectItem("Each", "Each"));
			uom.add(new SelectItem("Day", "Day"));
		}
		return uom;
	}

	public List<SelectItem> getTimeofday() {
		if (timeofday == null) {
			timeofday = new ArrayList<SelectItem>();
			timeofday.add(new SelectItem("Daytime", "Daytime"));
			timeofday.add(new SelectItem("Overnight", "Overnight"));
		}
		return timeofday;
	}

	public List<SelectItem> getTimeofweek() {
		if (timeofweek == null) {
			timeofweek = new ArrayList<SelectItem>();
			timeofweek.add(new SelectItem("Weekday", "Weekday"));
			timeofweek.add(new SelectItem("Saturday", "Saturday"));
		}
		return timeofweek;
	}

	public List<SelectItem> getProStatusSelectItems() {
		if (proStatusSelectItems == null) {
			proStatusSelectItems = new ArrayList<SelectItem>();
			proStatusSelectItems.add(new SelectItem("Active", "Active"));
			proStatusSelectItems.add(new SelectItem("Inactive", "Inactive"));
		}
		return proStatusSelectItems;
	}

	public List<SelectItem> getProEventStatusSelectItems() {
		if (proEventStatusSelectItems == null) {
			proEventStatusSelectItems = new ArrayList<SelectItem>();
			proEventStatusSelectItems.add(new SelectItem("pending", "Generated"));
			proEventStatusSelectItems.add(new SelectItem("finished", "Completed"));
			proEventStatusSelectItems.add(new SelectItem("banked", "Banked"));
		}
		return proEventStatusSelectItems;
	}

	public List<SelectItem> getGender() {
		if (gender == null) {
			gender = new ArrayList<SelectItem>();
			gender.add(new SelectItem("Male", "Male"));
			gender.add(new SelectItem("Female", "Female"));
		}
		return gender;
	}

	public List<SelectItem> getUserTypes() {
		if (userTypes == null) {
			userTypes = new ArrayList<SelectItem>();
			userTypes.add(new SelectItem("STF", "Staff"));
			userTypes.add(new SelectItem("FIN", "Finance"));
			userTypes.add(new SelectItem("MGR", "Manager"));
			userTypes.add(new SelectItem("SUP", "Supervisor"));
		}
		return userTypes;
	}

	public List<SelectItem> getTitle() {
		if (title == null) {
			title = new ArrayList<SelectItem>();
			title.add(new SelectItem("Mr", "Mr"));
			title.add(new SelectItem("Ms", "Ms"));
			title.add(new SelectItem("Mrs", "Mrs"));
			title.add(new SelectItem("Miss", "Miss"));
		}
		return title;
	}

	public List<SelectItem> getAuthorities() {
		if (authorities == null) {
			authorities = new ArrayList<SelectItem>();
			authorities.add(new SelectItem("ROLE_USER", "ROLE_USER"));
			authorities.add(new SelectItem("ROLE_ADMIN", "ROLE_ADMIN"));
			authorities.add(new SelectItem("ROLE_STAFF", "ROLE_STAFF"));
			authorities.add(new SelectItem("ROLE_FINANCE", "ROLE_FINANCE"));
			authorities.add(new SelectItem("ROLE_SUPERUSER", "ROLE_SUPERUSER"));
			authorities.add(new SelectItem("ROLE_SUPERVISOR", "ROLE_SUPERVISOR"));
		}
		return authorities;
	}

	public List<SelectItem> getVehicleType() {
		if (vehicleType == null) {
			vehicleType = new ArrayList<SelectItem>();
			vehicleType.add(new SelectItem("Bus", "Bus"));
			vehicleType.add(new SelectItem("Car", "Car"));
			vehicleType.add(new SelectItem("Van", "Van"));
		}
		return vehicleType;
	}

	public List<SelectItem> getCalendars() {
		if (calendars == null) {
			calendars = new ArrayList<SelectItem>();
			List<Calendar> cals = serviceLocator.getCalendarService().findAll();
			if (cals != null && !cals.isEmpty()) {
				calendars.add(new SelectItem(0, "Select One"));
				for (Calendar c : cals) {
					calendars.add(new SelectItem(c.getId(), c.getName()));
				}
			} else
				calendars.add(new SelectItem(0, "Not Available"));
		}
		return calendars;
	}

	public List<SelectItem> getRecurrenceType() {
		if (recurrenceType == null) {
			recurrenceType = new ArrayList<SelectItem>();
			recurrenceType.add(new SelectItem("Daily", "Daily"));
			recurrenceType.add(new SelectItem("Weekly", "Weekly"));
		}
		return recurrenceType;
	}

	public List<SelectItem> getFundingLevel() {
		if (fundingLevel == null) {
			fundingLevel = new ArrayList<SelectItem>();
			fundingLevel.add(new SelectItem("Moderate", "Moderate"));
			fundingLevel.add(new SelectItem("High", "High"));
			fundingLevel.add(new SelectItem("Very High", "Very High"));
			fundingLevel.add(new SelectItem("Exceptional", "Exceptional"));
		}
		return fundingLevel;
	}

	public List<SelectItem> getComMethod() {
		if (comMethod == null) {
			comMethod = new ArrayList<SelectItem>();
			comMethod.add(new SelectItem("E-mail", "E-mail"));
			comMethod.add(new SelectItem("Face to Face", "Face to Face"));
			comMethod.add(new SelectItem("Fax", "Fax"));
			comMethod.add(new SelectItem("Letter", "Letter"));
			comMethod.add(new SelectItem("Telephone", "Telephone"));
		}
		return comMethod;
	}

	public List<SelectItem> getFundingStatus() {
		if (fundingStatus == null) {
			fundingStatus = new ArrayList<SelectItem>();
			fundingStatus.add(new SelectItem("Not-Banked", "Not-Banked"));
			fundingStatus.add(new SelectItem("Banked", "Banked"));
		}
		return fundingStatus;
	}

	public List<SelectItem> getStaffStatus() {
		if (staffStatus == null) {
			staffStatus = new ArrayList<SelectItem>();
			staffStatus.add(new SelectItem("Current", "Current"));
			staffStatus.add(new SelectItem("Exited", "Exited"));
			staffStatus.add(new SelectItem("Returning", "Returning"));
			staffStatus.add(new SelectItem("Prospective", "Prospective"));
		}
		return staffStatus;
	}

	public List<SelectItem> getStates() {
		if (states == null) {
			states = new ArrayList<SelectItem>();
			states.add(new SelectItem("", "Please select", null, true));
			states.add(new SelectItem("ACT", "Australian Capital Territory"));
			states.add(new SelectItem("NSW", "New South Wales"));
			states.add(new SelectItem("NT", "Northern Territory"));
			states.add(new SelectItem("QLD", "Queensland"));
			states.add(new SelectItem("SA", "South Australia"));
			states.add(new SelectItem("TAS", "Tasmania"));
			states.add(new SelectItem("VIC", "Victoria"));
			states.add(new SelectItem("WA", "Western Australia"));
		}
		return states;
	}

	public List<SelectItem> getStaffSkillStatus() {
		if (staffSkillStatus == null) {
			staffSkillStatus = new ArrayList<SelectItem>();
			staffSkillStatus.add(new SelectItem("Active", "Active"));
			staffSkillStatus.add(new SelectItem("Inactive", "Inactive"));
		}
		return staffSkillStatus;
	}

	public List<SelectItem> getLeavePolicyStatus() {
		if (leavePolicyStatus == null) {
			leavePolicyStatus = new ArrayList<SelectItem>();
			leavePolicyStatus.add(new SelectItem("Active", "Active"));
			leavePolicyStatus.add(new SelectItem("Inactive", "Inactive"));
		}
		return leavePolicyStatus;
	}

	public List<SelectItem> getReminderStatus() {
		if (reminderStatus == null) {
			reminderStatus = new ArrayList<SelectItem>();
			reminderStatus.add(new SelectItem("New", "New"));
			reminderStatus.add(new SelectItem("Read", "Read"));
		}
		return reminderStatus;
	}

	public List<SelectItem> getMonthList() {
		if (monthList == null) {
			monthList = new ArrayList<SelectItem>();
			monthList.add(new SelectItem(1, "January"));
			monthList.add(new SelectItem(2, "February"));
			monthList.add(new SelectItem(3, "March"));
			monthList.add(new SelectItem(4, "April"));
			monthList.add(new SelectItem(5, "May"));
			monthList.add(new SelectItem(6, "June"));
			monthList.add(new SelectItem(7, "July"));
			monthList.add(new SelectItem(8, "August"));
			monthList.add(new SelectItem(9, "September"));
			monthList.add(new SelectItem(10, "October"));
			monthList.add(new SelectItem(11, "November"));
			monthList.add(new SelectItem(12, "December"));
		}
		return monthList;
	}

	public List<SelectItem> getPaymentMethods() {
		if (paymentMethods == null) {
			paymentMethods = new ArrayList<SelectItem>();
			paymentMethods.add(new SelectItem("Cash", "Cash"));
			paymentMethods.add(new SelectItem("Cheque", "Cheque"));
			paymentMethods.add(new SelectItem("EFT", "EFT"));
		}
		return paymentMethods;
	}

	public List<SelectItem> getElapsedTime() {
		if (elapsedTime == null) {
			elapsedTime = new ArrayList<SelectItem>();
			elapsedTime.add(new SelectItem(1, "Last one Month"));
			elapsedTime.add(new SelectItem(2, "Last 2 Months"));
			elapsedTime.add(new SelectItem(3, "Last 3 Months"));
			elapsedTime.add(new SelectItem(4, "Last 4 Months"));
			elapsedTime.add(new SelectItem(5, "Last 5 Months"));
			elapsedTime.add(new SelectItem(6, "Last 6 Months"));
		}
		return elapsedTime;
	}

	public List<SelectItem> getTimePeriods() {
		if (timePeriods == null) {
			timePeriods = new ArrayList<SelectItem>();
			timePeriods.add(new SelectItem("1W", "1 Week"));
			timePeriods.add(new SelectItem("2W", "2 Weeks"));
			timePeriods.add(new SelectItem("1M", "1 Month"));
			timePeriods.add(new SelectItem("3M", "3 Months"));
			timePeriods.add(new SelectItem("6M", "6 Months"));
		}
		return timePeriods;
	}

	public List<SelectItem> getStatus() {
		if (status == null) {
			status = new ArrayList<SelectItem>();
			status.add(new SelectItem("Active", "Active"));
			status.add(new SelectItem("Inactive", "Inactive"));
		}
		return status;
	}

	public EnumLeaveType[] getLeaveTypes() {
		return EnumLeaveType.values();
	}

	public EnumHolidayType[] getHolidayTypes() {
		return EnumHolidayType.values();
	}
	
	public EnumHolidayType[] getHolidayTypeForHolidayManager() {
		EnumHolidayType tmp[] = new EnumHolidayType[1];
		tmp[0] = EnumHolidayType.PUBLIC;
		return tmp;
	}

	public EnumSkillLevel[] getStaffSkillLevels() {
		return EnumSkillLevel.values();
	}

	public EnumEmploymentType[] getEmpTypes() {
		return EnumEmploymentType.values();
	}

	public EnumCollectionType[] getCollecitionType() {
		return EnumCollectionType.values();
	}

	public EnumPaymentType[] getPaymentType() {
		return EnumPaymentType.values();

	}

	public List<StaffType> getStaffTypes() {
		if (staffTypes == null || staffTypes.isEmpty()) {
			staffTypes = serviceLocator.getStaffTypeService().findAll();
		}
		return staffTypes;
	}

	public List<WeekDay> getWeekDays() {
		if (weekDays == null) {
			HashMap<Long, WeekDay> daysMap = new HashMap<Long, WeekDay>();
			for (WeekDay wd : serviceLocator.getWeekDayService().findAll()) {
				daysMap.put(wd.getId(), wd);
			}
			weekDays = new ArrayList<WeekDay>();
			weekDays.add(daysMap.get(new Long(6)));
			weekDays.add(daysMap.get(new Long(7)));
			for (int i = 1; i <= 5; i++)
				weekDays.add(daysMap.get(new Long(i)));
		}
		return weekDays;
	}

	public List<SelectItem> getLunchMinutes() {
		if (lunchMinutes == null) {
			lunchMinutes = new ArrayList<SelectItem>();
			lunchMinutes.add(new SelectItem("0", "Not Allocated"));
			lunchMinutes.add(new SelectItem("24", "24Mins"));
			lunchMinutes.add(new SelectItem("30", "30Mins"));
			lunchMinutes.add(new SelectItem("60", "60Mins"));
		}
		return lunchMinutes;
	}

	public List<SelectItem> getActStmntYears() {
		if (actStmntYears == null) {
			actStmntYears = new ArrayList<SelectItem>();
			java.util.Calendar cal = new GregorianCalendar();
			cal.setTime(serviceLocator.getBusinessRulesService().getActStmtStartDate());
			int start = cal.get(java.util.Calendar.YEAR);
			cal.setTime(new Date());
			int end = cal.get(java.util.Calendar.YEAR);
			while (start <= end) {
				actStmntYears.add(new SelectItem(start, String.valueOf(start)));
				start++;
			}
		}
		return actStmntYears;
	}

	public List<SelectItem> getStudentPaymentMethods() {
		if (studentPaymentMethods == null) {
			studentPaymentMethods = new ArrayList<SelectItem>();
			studentPaymentMethods.add(new SelectItem("Cash", "Cash"));
			studentPaymentMethods.add(new SelectItem("Cheque", "Cheque"));
			studentPaymentMethods.add(new SelectItem("EFT", "EFT"));
		}
		return studentPaymentMethods;
	}

	public List<SelectItem> getAncillaryCostList() {

		if (ancillaryCostList == null) {
			ancillaryCostList = new ArrayList<SelectItem>();
			ancillaryCostList.add(new SelectItem("DayProgram", "DayProgram"));
			ancillaryCostList.add(new SelectItem("Transport", "Transport"));
			ancillaryCostList.add(new SelectItem("Ancillary", "Ancillary"));
			ancillaryCostList.add(new SelectItem("Individual", "Individual"));
		}
		return ancillaryCostList;
	}

	public List<SelectItem> getProgramTimes() {

		if (programTimes == null) {
			programTimes = new ArrayList<SelectItem>();
			programTimes.add(new SelectItem("Weekday Daytime", "Weekday Daytime"));
			programTimes.add(new SelectItem("Weekday Evening", "Weekday Evening"));
			programTimes.add(new SelectItem("Weekday Overnight Active", "Weekday Overnight Active"));
			programTimes.add(new SelectItem("Overnight Inactive", "Overnight Inactive"));
			programTimes.add(new SelectItem("Saturday", "Saturday"));
			programTimes.add(new SelectItem("Sunday", "Sunday"));
			programTimes.add(new SelectItem("Public Holiday", "Public Holiday"));

		}

		return programTimes;
	}

	public void setProgramTimes(List<SelectItem> programTimes) {
		this.programTimes = programTimes;
	}

	public List<SelectItem> getTransportTimes() {
		if (transportTimes == null) {
			transportTimes = new ArrayList<SelectItem>();
			transportTimes.add(new SelectItem("Tarnsport", "Tarnsport"));
		}
		return transportTimes;

	}

	public void setTransportTimes(List<SelectItem> transportTimes) {
		this.transportTimes = transportTimes;
	}

	public List<SelectItem> getAncillaryTimes() {
		if (ancillaryTimes == null) {
			ancillaryTimes = new ArrayList<SelectItem>();
			ancillaryTimes.add(new SelectItem("Ancillary", "Ancillary"));
		}

		return ancillaryTimes;
	}

	public void setAncillaryTimes(List<SelectItem> ancillaryTimes) {
		this.ancillaryTimes = ancillaryTimes;
	}

	public List<SelectItem> getGSTCodes() {
		if (gstCodeList == null) {
			gstCodeList = new ArrayList<SelectItem>();
			gstCodeList.add(new SelectItem("Out of Scope", "Out of Scope"));
			gstCodeList.add(new SelectItem("Pay As You Go", "Pay As You Go"));
			gstCodeList.add(new SelectItem("Tax Claimable", "Tax Claimable"));
			gstCodeList.add(new SelectItem("Tax Free (0%)", "Tax Free (0%)"));
			gstCodeList.add(
					new SelectItem("Purchase (0%) Tax exempt/Input taxed", "Purchase (0%) Tax exempt/Input taxed"));
			gstCodeList.add(new SelectItem("Purchase (10%) for input taxed", "Purchase (10%) for input taxed"));
		}
		return gstCodeList;
	}

	public List<SelectItem> getProgramTypes() {
		if (programTypes == null) {
			programTypes = new ArrayList<SelectItem>();
			programTypes.add(new SelectItem(1, "Day Program"));
			programTypes.add(new SelectItem(2, "Transport"));
			programTypes.add(new SelectItem(3, "Individual"));
		}

		return programTypes;
	}

	public List<ProgramType> getAllProgramTypes() {
		if (allProgramTypes == null) {
			allProgramTypes = serviceLocator.getProgramTypeService().findAll();
		}
		return allProgramTypes;
	}
	
	public List<SelectItem> getProgramTypesList() {
		if (programTypesList == null) {
			programTypesList = new ArrayList<SelectItem>();
			programTypesList.add(new SelectItem("0", "Please select"));
			programTypesList.add(new SelectItem("1", "Day Program"));
			programTypesList.add(new SelectItem("2", "Individual"));
			programTypesList.add(new SelectItem("3", "Transport"));
			programTypesList.add(new SelectItem("4", "Ancillary"));
		}
		return programTypesList;
	}

}