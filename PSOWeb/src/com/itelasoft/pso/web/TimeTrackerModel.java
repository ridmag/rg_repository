package com.itelasoft.pso.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.EnumEmploymentType;
import com.itelasoft.pso.beans.EnumLeaveType;
import com.itelasoft.pso.beans.Leave;
import com.itelasoft.pso.beans.LeaveCategory;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.StaffEvent;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.WeekDay;

import jxl.Cell;
import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@ManagedBean
@SessionScoped
public class TimeTrackerModel extends UIModel {

	private Date yearStartDate;
	private Date fortnightStart;
	private Date fortnightEnd;
	private Date selectedDate;
	private Calendar calendar = Calendar.getInstance();
	private List<StaffMember> staffMembers;
	private StaffMember staffMember;
	private String searchText, searchKey;
	private String status, information;
	private List<StaffEvent> staffEvents;
	private StaffEvent staffEvent, tmpEvent;
	private boolean visibleActualTimes, flag;
	private TimeBean startTime;
	private TimeBean endTime;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("h:mma");

	/*
	 * Setting the financial year start date which could be a constant value the
	 * entire scope
	 */

	public TimeTrackerModel() {
		calendar.set(Calendar.YEAR, 2012);
		calendar.set(Calendar.MONTH, 5);
		calendar.set(Calendar.DATE, 30);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		yearStartDate = calendar.getTime();
		initStaffTracker();
	}

	// Initializing the model for staff time tracker

	public void initStaffTracker() {
		staffMembers = new ArrayList<StaffMember>();
		staffMember = null;
		searchKey = "name";
		searchText = "";
		staffEvents = null;
		selectedDate = new Date();
		createFortnight();
		searchStaff();
	}

	/*
	 * Search the staff by the staffId or name according to the searchKey which
	 * is either "id" or "name"
	 */

	public void searchStaff() {
		if (searchKey.equals("id")) {
			StaffMember staff = serviceLocator.getStaffMemberService().searchByStaffId(searchText);
			if (staff == null)
				showError("No result available for this Id.");
			else {
				staffMembers.clear();
				staffMembers.add(staff);
				staffMember = null;
			}
		} else {
			List<StaffMember> staffMembers = serviceLocator.getStaffMemberService().listByName(searchText);
			if (staffMembers == null || staffMembers.isEmpty())
				showError("No results for the given search text.");
			else {
				this.staffMembers = staffMembers;
				staffMember = null;
			}
		}
	}

	// download the excelsheet
	public void downloadExcelSheet() {
		generateExcelSheet();
		sendExecl();
	}

	// Setting the selected staff member of the datatable in the UI

	public void selectStaff(ClickActionEvent re) {
		if (staffMember != null)
			staffMember.setUi_selected(false);
		staffMember = (StaffMember) re.getComponent().getAttributes().get("staff");
		staffMember.setUi_selected(true);
		staffEvents = null;
	}

	// Setting the selected date and calculate fortnight start/end dates

	public void selectDate(ValueChangeEvent vce) {
		selectedDate = (Date) vce.getNewValue();
		createFortnight();
	}

	// Search staff events within the fortnight start/end

	public void searchStaffEvents() {
		if (staffMember == null) {
			showError("Please select a staff member from the table below..");
			return;
		}
		if (selectedDate == null) {
			showError("Date of the fortnight can not be empty..");
			return;
		}
		staffEvents = serviceLocator.getStaffEventService().listByStaffNDates(staffMember.getStaffId(), fortnightStart,
				fortnightEnd);
		if (staffEvents == null || staffEvents.isEmpty())
			showError("No events found with selected staff member and the given period.");
	}

	/*
	 * Retrieves the selected staff event as a attribute and setting start/end
	 * times if exists then opens the popup to allow edit them as well as the
	 * lunch minutes if lunch is included. status and information is used to
	 * show additional informations about staff status on the UI in situations
	 * like leaves/holidays/unavailability
	 */

	public void editStaffEvent(ActionEvent ae) {
		tmpEvent = (StaffEvent) ae.getComponent().getAttributes().get("event");
		if (tmpEvent == null)
			return;
		staffEvent = serviceLocator.getStaffEventService().retrieve(tmpEvent.getId());
		if (!isStaffAvailable(staffEvent)) {
			status = "Not Available";
			information = "This staff member isn't available on";
			for (WeekDay day : staffEvent.getStaffMember().getUnAvailableDays())
				information = information + " " + day.getName() + "s, ";
		} else {
			Leave leave = serviceLocator.getLeaveService().getByStaffIdAndDate(staffEvent.getStaffMember().getId(),
					staffEvent.getProgramEvent().getEventDate());
			if (leave != null) {
				status = "On-Leave";
				information = leave.getLeaveType().getId();
			} else {
				List<ProgramEvent> proEvents = serviceLocator.getProgramEventService().listConflictingEventsForStaffDB(
						staffEvent.getProgramEvent().getStartTime(), staffEvent.getProgramEvent().getEndTime(),
						staffEvent.getStaffMember().getId(), staffEvent.getProgramEvent().getId());
				if (proEvents != null) {
					status = "Busy";
					information = "Assigned Events, \n";
					for (ProgramEvent event : proEvents) {
						information = information
								.concat("   " + event.getName() + " (" + timeFormat.format(event.getStartTime()) + " - "
										+ timeFormat.format(event.getEndTime()) + ")\n");
					}
				} else {
					status = "OK";
					information = "Staff member was available on this date..";
				}
			}
		}
		if (staffEvent.getStartTime() != null && staffEvent.getEndTime() != null) {
			flag = true;
			startTime = new TimeBean(staffEvent.getStartTime());
			endTime = new TimeBean(staffEvent.getEndTime());
		} else {
			flag = false;
			startTime = new TimeBean(staffEvent.getProgramEvent().getStartTime());
			endTime = new TimeBean(staffEvent.getProgramEvent().getEndTime());
		}
		actualTimesPopup();
	}

	/*
	 * Saves updated staff event. Flag used to identify whether the event is
	 * having actualtimes or not
	 */

	public void saveStaffEvent() {
		if (flag) {
			if (staffEvent.getProgramEvent().getProgram().getType().getName().equals("Individual")
					|| validateTimes(staffEvent.getProgramEvent().getEventDate())) {
				staffEvent.setStartTime(startTime.getDateTime(staffEvent.getProgramEvent().getEventDate()));
				staffEvent.setEndTime(endTime.getDateTime(staffEvent.getProgramEvent().getEventDate()));
			} else
				return;
		} else {
			staffEvent.setStartTime(null);
			staffEvent.setEndTime(null);
			staffEvent.setLunchMinutes(0);
		}
		staffEvent = serviceLocator.getStaffEventService().update(staffEvent);
		staffEvents.set(staffEvents.indexOf(tmpEvent), staffEvent);
		// tmpEvent = staffEvent;
		showInfo("Staff event updated successfully.");
		if (staffMember.getEmploymentType() == EnumEmploymentType.FULLTIME
				|| staffMember.getEmploymentType() == EnumEmploymentType.PARTTIME) {
			updateLeaveInfo(EnumLeaveType.ANNUAL);
			updateLeaveInfo(EnumLeaveType.PERSONAL);
		}
		actualTimesPopup();
	}

	/*
	 * Used to calculate the number of leave hours earned by actual work hours.
	 * If updating a existing record, it will get the old values from tmpEvent,
	 * calculate and update the record as necessary
	 */

	private void updateLeaveInfo(EnumLeaveType leaveType) {
		double oldEventHours = calculateTimeDifferences(tmpEvent);
		double newEventHours = calculateTimeDifferences(staffEvent);
		double oldActualHours = 0, newActualHours = 0;
		oldActualHours = (oldEventHours - (tmpEvent.getLunchMinutes() / 60))
				* serviceLocator.getBusinessRulesService().getLeavePercentage(leaveType);
		newActualHours = ((newEventHours - (staffEvent.getLunchMinutes() / 60))
				* serviceLocator.getBusinessRulesService().getLeavePercentage(leaveType)) - oldActualHours;
		if (newActualHours != 0) {
			LeaveCategory category = serviceLocator.getLeaveCategoryService()
					.getByStaffIdNLeaveType(staffEvent.getStaffMember().getStaffId(), leaveType);
			if (category != null) {
				category.setEarnedHours(category.getEarnedHours() + newActualHours);
				serviceLocator.getLeaveCategoryService().update(category);
			} else {
				category = new LeaveCategory();
				category.setLeaveType(leaveType);
				category.setStaffMember(staffEvent.getStaffMember());
				category.setEarnedHours(newActualHours);
				serviceLocator.getLeaveCategoryService().create(category);
			}
		}
	}

	// Calculates the time different of start/end times and return it in hours

	private double calculateTimeDifferences(StaffEvent staffEvent) {
		if (staffEvent.getStartTime() != null && staffEvent.getEndTime() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(staffEvent.getStartTime());
			long startingMili = calendar.getTimeInMillis();
			calendar.setTime(staffEvent.getEndTime());
			long endMilis = calendar.getTimeInMillis();
			long durationMilis = endMilis - startingMili;
			return durationMilis / (60 * 60 * 1000);
		}
		return 0;
	}

	/*
	 * Checks if the staffMember isn't a full-time, whether they are available
	 * on event date or not.
	 */

	private boolean isStaffAvailable(StaffEvent staffEvent) {
		for (WeekDay day : staffEvent.getStaffMember().getUnAvailableDays()) {
			if (day.getName().equals(dateFormat.format(staffEvent.getProgramEvent().getEventDate()))) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Validates start/end times, return true if values are valid or false
	 * otherwise.
	 */

	private boolean validateTimes(Date eventDate) {
		if (startTime.getDateTime(eventDate).before(endTime.getDateTime(eventDate))) {
			return true;
		} else {
			showError("Invalid time. Start time should be before the end time");
			return false;
		}
	}

	// Opens and closes the setting actual start/end times popup

	public void actualTimesPopup() {
		if (visibleActualTimes) {
			visibleActualTimes = false;
		} else {
			visibleActualTimes = true;
		}
	}

	/*
	 * Calculates the fortnight start and end dates for the given date. It uses
	 * two methods to calculate future or past fortnight dates by comparing the
	 * given date with the year start date.
	 */

	public void createFortnight() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if (!dateFormat.format(yearStartDate).equals(dateFormat.format(selectedDate))) {
			if (selectedDate.after(yearStartDate))
				createFutureFortnight(selectedDate);
			else
				createPastFortnight(selectedDate);
		} else
			createFutureFortnight(selectedDate);
	}

	// Calculates fortnight dates if the given date is after the year start date

	private void createFutureFortnight(Date currentDate) {
		calendar.setTime(yearStartDate);
		fortnightStart = calendar.getTime();
		calendar.add(Calendar.DATE, 13);
		fortnightEnd = calendar.getTime();
		calendar.add(Calendar.DATE, 1);
		while (fortnightEnd.before(currentDate)) {
			fortnightStart = calendar.getTime();
			calendar.add(Calendar.DATE, 13);
			fortnightEnd = calendar.getTime();
			calendar.add(Calendar.DATE, 1);
		}
	}

	/*
	 * Calculates fortnight dates if the given date is before the year start
	 * date
	 */

	private void createPastFortnight(Date currentDate) {
		calendar.setTime(yearStartDate);
		calendar.add(Calendar.DATE, -1);
		fortnightEnd = calendar.getTime();
		calendar.add(Calendar.DATE, -13);
		fortnightStart = calendar.getTime();
		calendar.add(Calendar.DATE, -1);
		while (fortnightStart.after(currentDate)) {
			fortnightEnd = calendar.getTime();
			calendar.add(Calendar.DATE, -13);
			fortnightStart = calendar.getTime();
			calendar.add(Calendar.DATE, -1);
		}
	}

	// generate EXcel Shets
	private void generateExcelSheet() {
		WritableWorkbook workbook;
		String path = Util.getMessage("staff_event_Excel_path") + staffMember.getContact().getFirstName() + ".xls";
		File xlFile = new File(path);
		try {
			workbook = Workbook.createWorkbook(xlFile);
			WritableSheet sheet = workbook.createSheet(staffMember.getContact().getFirstName(), 0);
			Label header1 = addHeader(0, 0, "Program Type");
			Label header2 = addHeader(1, 0, "Event Date");
			Label header3 = addHeader(2, 0, "Program");
			Label header4 = addHeader(3, 0, "Rostered Start Time");
			Label header5 = addHeader(4, 0, "Rostered End Time");
			Label header6 = addHeader(5, 0, "Actual Start Time");
			Label header7 = addHeader(6, 0, "Actual End Time");
			Label header8 = addHeader(7, 0, "Lunch Minutes");
			sheet.addCell(header1);
			sheet.addCell(header2);
			sheet.addCell(header3);
			sheet.addCell(header4);
			sheet.addCell(header5);
			sheet.addCell(header6);
			sheet.addCell(header7);
			sheet.addCell(header8);
			// expand columns
			for (int i = 0; i < sheet.getColumns(); i++) {
				CellView cellV = sheet.getColumnView(i);
				Cell cell = sheet.getColumn(i)[0];
				int size = cell.getContents().trim().length();
				cellV.setSize((size + 10) * 256);
				sheet.setColumnView(i, cellV);
			}

			// write data to excel
			int i = 1;
			for (StaffEvent se : staffEvents) {
				String rosterStartTime, rosterEndTime, actualStartTime, actualEndTime, lunchMins;
				SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
				rosterStartTime = formatter.format(se.getRosterStartTime());
				rosterEndTime = formatter.format(se.getRosterEndTime());

				if (se.getStartTime() != null && se.getEndTime() != null) {
					actualStartTime = formatter.format(se.getStartTime());
					actualEndTime = formatter.format(se.getEndTime());
				} else {
					actualStartTime = "-";
					actualEndTime = "-";
				}
				if (se.getProgramEvent().isLunchIncluded()) {
					lunchMins = String.valueOf(se.getLunchMinutes());
				} else {
					lunchMins = "-";
				}
				Label label1 = new Label(0, i, se.getProgramEvent().getProgram().getType().getName());
				Label label2 = new Label(1, i, se.getProgramEvent().getEventDate().toString().substring(0, 10));
				Label label3 = new Label(2, i, se.getProgramEvent().getGroup().getName());
				Label label4 = new Label(3, i, rosterStartTime);
				Label label5 = new Label(4, i, rosterEndTime);
				Label label6 = new Label(5, i, actualStartTime);
				Label label7 = new Label(6, i, actualEndTime);
				Label label8 = new Label(7, i, lunchMins);
				sheet.addCell(label1);
				sheet.addCell(label2);
				sheet.addCell(label3);
				sheet.addCell(label4);
				sheet.addCell(label5);
				sheet.addCell(label6);
				sheet.addCell(label7);
				sheet.addCell(label8);
				i++;
			}
			workbook.write();
			workbook.close();
		} catch (IOException ex) {
			showError("Error Please contact admin");
		} catch (RowsExceededException rxex) {
			showError("Error Please contact admin");
		} catch (WriteException wex) {
			showError("Error Please contact admin");
		}
	}

	// adding headers to Excel
	private Label addHeader(int col, int row, String header) {
		WritableFont cellFont = new WritableFont(WritableFont.COURIER, 11);
		try {
			cellFont.setBoldStyle(WritableFont.BOLD);
		} catch (WriteException ex) {
			showError("Error occurred");
		}
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

		// Create the label, specifying content and format
		Label label = new Label(col, row, header, cellFormat);
		return label;
	}

	// Send created Execl
	private void sendExecl() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
		String path = Util.getMessage("staff_event_Excel_path") + staffMember.getContact().getFirstName() + ".xls";
		File file = new File(path);
		String fileName = file.getName();
		ExternalContext ec = fc.getExternalContext();
		String contentType = ec.getMimeType(fileName);
		int contentLength = (int) file.length();

		response.reset();
		response.setContentType(contentType);
		response.setContentLength(contentLength);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		try {
			OutputStream output = response.getOutputStream();
			Files.copy(file.toPath(), output);
			fc.responseComplete();
			file.delete();
		} catch (Exception nfe) {
			showError("Error Occurred please contact admin");
		}
	}

	// Returns the fortnight start date of the given date.
	public Date getFortnightStartDate(Date selectedDate) {
		this.selectedDate = selectedDate;
		createFortnight();
		return fortnightStart;
	}

	// Getters and setters for the properties

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}

	public Date getSelectedDate() {
		return selectedDate;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public Date getFortnightStart() {
		return fortnightStart;
	}

	public Date getFortnightEnd() {
		return fortnightEnd;
	}

	public List<StaffMember> getStaffMembers() {
		return staffMembers;
	}

	public StaffMember getStaffMember() {
		return staffMember;
	}

	public List<StaffEvent> getStaffEvents() {
		return staffEvents;
	}

	public boolean isVisibleActualTimes() {
		return visibleActualTimes;
	}

	public TimeBean getStartTime() {
		return startTime;
	}

	public void setStartTime(TimeBean startTime) {
		this.startTime = startTime;
	}

	public TimeBean getEndTime() {
		return endTime;
	}

	public void setEndTime(TimeBean endTime) {
		this.endTime = endTime;
	}

	public StaffEvent getStaffEvent() {
		return staffEvent;
	}

	public void setStaffEvent(StaffEvent staffEvent) {
		this.staffEvent = staffEvent;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isFlag() {
		return flag;
	}

	public String getStatus() {
		return status;
	}

	public String getInformation() {
		return information;
	}

}
