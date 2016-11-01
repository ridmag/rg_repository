package com.itelasoft.pso.web.reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import net.sf.json.JSONObject;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.Collection;
import com.itelasoft.pso.beans.Consent;
import com.itelasoft.pso.beans.HoursUtilizedReport;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.web.RosterManagerModel;
import com.itelasoft.pso.web.Student360ViewModel;
import com.itelasoft.pso.web.UIModel;
import com.itelasoft.pso.web.Util;
import com.itelasoft.util.SortObjectByName;

@ManagedBean(name = "reportManagerModel")
@SessionScoped
public class ReportManagerModel extends UIModel {

	private Date fromDate;
	private Date toDate;
	private int year;
	private int month;
	private String status = "";
	private JSONObject json = new JSONObject();
	private String jsonString = "";
	private String studentSearchText, timePeriod;
	private List<Student> students;
	private Student student;
	private Date weekDate;
	private Calendar calendar = Calendar.getInstance();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private HoursUtilizedReport hoursUtilizedReport;
	private List<SelectItem> reportSelectItems;
	private Long selectedReportId;
	private List<String> selectedConsentIds;
	private String reportType,staffId;
	private List<Consent> consents;
	private Long collectionId;
	private List<Collection> collections;
	private List<StaffMember> staffList;
	private List<SelectItem> staffSelectItemList;

	public void generateReport(String reportName, String params) {
		if (reportName.contains("dailyReceipt")) {
			generateDailyReceiptReport();
		} else if (reportName.contains("absenteeReportIndividual")) {
			generateIndividualAbsenteeReport();
		} else if (reportName.contains("absenteeReportAll")) {
			generateAllAbsenteeReport();
		} else if (reportName.contains("anniversaryReport")) {
			generateAnniversaryReport();
		} else if (reportName.contains("invoiceReport")) {
			generateOutstandingInvoiceReport();
		} else if (reportName.contains("eventCompletion")) {
			generateEventCompletionReport();
		} else if (reportName.contains("consentReport")) {
			generateConsentReport();
		} else if (reportName.contains("criminalReport")) {
			generateCriminalReport();
		} else if (reportName.contains("studentEventsReport")) {
			generateStudentEventsReport();
		} else if (reportName.contains("timeSheet")) {
			RosterManagerModel model = (RosterManagerModel) Util
					.getManagedBean("rosterManagerModel");
			model.generateTimeSheet();
		} else if (reportName.contains("rosterReport")) {
			RosterManagerModel model = (RosterManagerModel) Util
					.getManagedBean("rosterManagerModel");
			model.generateRosterReport();
		} else if (reportName.contains("collectionsReportIndividual")) {
			generateIndividualCollectionReport();
		} else if (reportName.contains("student360View")) {
			generateStudent360View(params);
		} else if (reportName.contains("staffEventsReport")) {
			generateStaffEventsReport();
		}
	}

	// Daily Receipt Report Functions

	public void initDailyReceiptReport() {
		weekDate = null;
	}

	public void generateDailyReceiptReport() {
		json = new JSONObject();
		if (weekDate != null)
			json.put("eventDate", dateFormat.format(weekDate));
		jsonString = json.toString();
	}

	// Individual Absentee Report Functions

	public void initIndividualAbsenteeReport() {
		students = serviceLocator.getStudentService().findAll();
		if(!students.isEmpty()){
			Collections.sort(students, new SortObjectByName());
		}
		timePeriod = "1W";
		student = null;
		toDate = null;
		// json = new JSONObject();
		studentSearchText = "";
	}

	public void searchStudents() {
		try {
			// See if the user has entered an ID instead name
			Long id = Long.parseLong(studentSearchText);
			Student student = serviceLocator.getStudentService().retrieve(id);
			if (student == null)
				showError("No result available for this Id.");
			else {
				students.clear();
				students.add(student);
				this.student = null;
			}
		} catch (NumberFormatException nFE) {
			List<Student> students = serviceLocator.getStudentService()
					.listByName(studentSearchText, true);
			if (students == null || students.isEmpty())
				showError("No results for the given search text.");
			else {
				this.students = students;
				student = null;
			}
		}
	}

	public void selectStudent(ClickActionEvent event) {
		if (student != null)
			student.setUi_selected(false);
		student = (Student) event.getComponent().getAttributes().get("student");
		studentSearchText = student.getContact().getFirstName();
	}

	/*
	 * public void searchStudents(ValueChangeEvent event) { // get the number of
	 * displayable records from the component SelectInputText autoComplete =
	 * (SelectInputText) event.getComponent(); // get the new value typed by
	 * component user. String newWord = (String) event.getNewValue();
	 * List<Student> students = new ArrayList<Student>(); studentSearchResults =
	 * new ArrayList<SelectItem>(); student = null; if (newWord != null)
	 * students = serviceLocator.getStudentService().listByName(newWord); if
	 * (students != null && !students.isEmpty()) { int index = 0; for (Student s
	 * : students) { if (index++ >= 15) break; studentSearchResults.add(new
	 * SelectItem(s, String.format( "%s %s %s", s.getContact().getTitle(), s
	 * .getContact().getFirstName(), s.getContact() .getLastName()))); } }else
	 * studentSearchResults.add(new SelectItem(null,
	 * "Type Student Name to search..")); // if there is a selected item then
	 * find the city object of the // same name if
	 * (autoComplete.getSelectedItem() != null) { student = (Student)
	 * autoComplete.getSelectedItem().getValue(); } }
	 */

	public void generateIndividualAbsenteeReport() {
		// calendar.setTime(weekDate);
		// calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 1);
		// fromDate = calendar.getTime();
		// calendar.add(Calendar.DATE, 4);
		// toDate = calendar.getTime();

		// if (serviceLocator.getReportService().validateCompletedProEvents(
		// fromDate, toDate, student.getId())) {
		json = new JSONObject();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		json.put("fromDate", dateFormat.format(getStartDate()));
		json.put("toDate", dateFormat.format(toDate));
		json.put("studentId", student.getId().toString());
		status = "success";
		jsonString = json.toString();
		// } else {
		// status =
		// "Generating report aborted. Incomplete event(s) found within the selected week..";
		// }
	}

	// All Student's Absentee Report Functions

	public void initAllAbsenteeReport() {
		toDate = new Date();
		timePeriod = "1W";
	}

	public void generateAllAbsenteeReport() {
		// calendar.setTime(weekDate);
		// calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 1);
		// fromDate = calendar.getTime();
		// calendar.add(Calendar.DATE, 4);
		// toDate = calendar.getTime();
		// if (serviceLocator.getReportService().validateCompletedProEvents(
		// fromDate, toDate, null)) {
		json = new JSONObject();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		json.put("startDate", dateFormat.format(getStartDate()));
		json.put("endDate", dateFormat.format(toDate));
		status = "success";
		jsonString = json.toString();
		// } else {
		// status =
		// "Generating report aborted. Incomplete event(s) found within the selected week..";
		// }
	}

	// Anniversary Report Functions

	public void initAnniversaryReport() {
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
	}

	public void generateAnniversaryReport() {
		status = "";
		json = new JSONObject();
		if (month == 0) {
			status = "Please select a month";
			return;
		}
		if (year == 0) {
			status = "Year is not valid";
			return;
		}
		json.put("month", month);
		json.put("year", year);
		status = "success";
		jsonString = json.toString();
	}

	// Outstanding Invoices report functions

	public void initOutstandingInvoiceReport() {
		fromDate = toDate = null;
	}

	public void generateOutstandingInvoiceReport() {
		if (fromDate.after(toDate)) {
			status = "Invalid date range..";
			return;
		}
		json = new JSONObject();
		status = "success";
		json.put("startDate", dateFormat.format(fromDate));
		json.put("endDate", dateFormat.format(toDate));
		jsonString = json.toString();
	}

	// Hours Utilized Report Functions

	public void initHoursUtilizedReport() {
		weekDate = null;
		hoursUtilizedReport = null;
		reportSelectItems = new ArrayList<SelectItem>();
		selectedReportId = null;
		List<HoursUtilizedReport> reports = serviceLocator
				.getUtilizedReportService().findAll();
		if (reports != null && !reports.isEmpty()) {
			for (HoursUtilizedReport report : reports) {
				reportSelectItems.add(new SelectItem(report.getId(), dateFormat
						.format(report.getFromDate())
						+ " - "
						+ dateFormat.format(report.getToDate())));
			}
		}
	}

	public void selectReport(ValueChangeEvent ve) {
		selectedReportId = (Long) ve.getNewValue();
		if (selectedReportId != null){
			hoursUtilizedReport = serviceLocator.getUtilizedReportService()
					.retrieve(selectedReportId);
			Collections.sort(hoursUtilizedReport.getReportItems(), new SortObjectByName());
		}	
	}

	public void saveReport() {
		if (hoursUtilizedReport.getId() != null)
			hoursUtilizedReport = serviceLocator.getUtilizedReportService()
					.update(hoursUtilizedReport);
		else
			hoursUtilizedReport = serviceLocator.getUtilizedReportService()
					.create(hoursUtilizedReport);
		showInfo("Report saved successfully");
	}

	public void genereteHoursUtilizedReport() {
		if (weekDate != null) {
			calendar.setTime(weekDate);
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 1);
			fromDate = calendar.getTime();
			calendar.add(Calendar.DATE, 4);
			toDate = calendar.getTime();
			hoursUtilizedReport = serviceLocator.getUtilizedReportService()
					.retrieveUtilizationReport(fromDate, toDate);
			if (hoursUtilizedReport == null) {
				if (serviceLocator.getReportService()
						.validateCompletedProEvents(fromDate, toDate, null)) {
					hoursUtilizedReport = serviceLocator.getReportService()
							.generateHoursUtilizedReport(fromDate, toDate);
					if (hoursUtilizedReport == null)
						showError("No records found..");
					else {
						hoursUtilizedReport = serviceLocator
								.getUtilizedReportService().create(
										hoursUtilizedReport);
						reportSelectItems.add(new SelectItem(
								hoursUtilizedReport.getId(), dateFormat
										.format(hoursUtilizedReport
												.getFromDate())
										+ " - "
										+ dateFormat.format(hoursUtilizedReport
												.getToDate())));
						selectedReportId = hoursUtilizedReport.getId();
						showInfo("Report generated successfully..");
					}
				} else
					showError("Generating report aborted. Incomplete event(s) found within the selected week..");
			} else {
				selectedReportId = hoursUtilizedReport.getId();
				showInfo("A Report is already exists..");
			}
		} else
			showError("Please select a date of the week.");

	}

	// Event Completion report functions

	public void initEventCompletionReport() {
		reportType = "all";
	}

	public void generateEventCompletionReport() {
		json = new JSONObject();
		json.put("reportType", reportType);
		jsonString = json.toString();
	}

	// Students' Consent report functions

	public void initConsentReport() {
		selectedConsentIds = new ArrayList<String>();
		consents = serviceLocator.getConsentService().findAll();
		for (Consent consent : consents) {
			consent.setUi_selected(true);
			selectedConsentIds.add(consent.getId().toString());
		}
	}

	public void consentChecked(ValueChangeEvent event) {
		Boolean checked = (Boolean) event.getNewValue();
		if (checked != null) {
			if (checked)
				selectedConsentIds.add(((Long) event.getComponent()
						.getAttributes().get("consentId")).toString());
			else
				selectedConsentIds.remove(((Long) event.getComponent()
						.getAttributes().get("consentId")).toString());
		}
	}

	public void generateConsentReport() {
		if (selectedConsentIds != null && !selectedConsentIds.isEmpty()) {
			json = new JSONObject();
			for (String id : selectedConsentIds) {
				json.put(id, id);
			}
			jsonString = json.toString();
		} else {
			showError("Please select at least one consent..");
			return;
		}
	}

	public void generateCriminalReport() {
		json = new JSONObject();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		json.put("cutOffDate", dateFormat.format(calendar.getTime()));
		jsonString = json.toString();
	}

	// Student Events report functions

	public void initStudentEventsReport() {
		fromDate = toDate = null;
		generateStudentEventsReport();
	}

	public void generateStudentEventsReport() {
		SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (fromDate != null && toDate != null && fromDate.after(toDate)) {
			status = "Invalid date range..";
			return;
		}
		json = new JSONObject();
		if (fromDate != null)
			json.put("startDate", sqlDateFormat.format(fromDate));
		else {
			json.put("startDate", "");
		}
		if (toDate != null)
			json.put("endDate", sqlDateFormat.format(toDate));
		else
			json.put("endDate", "");
		status = "success";
		jsonString = json.toString();
	}
	
	public void generateStaffEventsReport() {
		SimpleDateFormat sqlDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if (fromDate == null){
			status = "Start date can not be empty";
			return;
		}
		if(toDate == null){
			status = "End date can not be empty";
			return;
		}
		if (fromDate != null && toDate != null && fromDate.after(toDate)) {
			status = "Invalid date range..";
			return;
		}
		json = new JSONObject();
		if (fromDate != null)
			json.put("startDate", sqlDateFormat.format(fromDate));
		else {
			json.put("startDate", "");
		}
		if (toDate != null)
			json.put("endDate", sqlDateFormat.format(toDate));
		else
			json.put("endDate", "");
		status = "success";
		if(staffId != null){
			json.put("staffId", staffId.toString());
		}
		jsonString = json.toString();
	}


	public void initCollectionsIndvReport() {
		student = null;
		collectionId = new Long(0);
		studentSearchText = "";
		students = serviceLocator.getStudentService().findAll();
		if(!students.isEmpty()){
			Collections.sort(students, new SortObjectByName());
		}
		collections = serviceLocator.getCollectionService().findAll();
	}


	private void generateIndividualCollectionReport() {
		json = new JSONObject();
		json.put("studentId", student.getId());
		json.put("collectionId", collectionId);
		jsonString = json.toString();
		status = "success";
	}

	private void generateStudent360View(String params) {
		JSONObject jsonObject = JSONObject.fromObject(params);
		Long studentId = new Long((Integer) jsonObject.get("studentId"));
		((Student360ViewModel) Util.getManagedBean("studentViewModel"))
				.init(studentId);
	}

	private Date getStartDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate);
		calendar.add(Calendar.DATE, 1);
		if (timePeriod.equals("1W")) {
			calendar.add(Calendar.DATE, -7);
		} else if (timePeriod.equals("2W")) {
			calendar.add(Calendar.DATE, -14);
		} else if (timePeriod.equals("1M")) {
			calendar.add(Calendar.MONTH, -1);
		} else if (timePeriod.equals("3M")) {
			calendar.add(Calendar.MONTH, -3);
		} else if (timePeriod.equals("6M")) {
			calendar.add(Calendar.MONTH, -6);
		}
		Date toDate = calendar.getTime();
		return toDate;
	}
	//staff event record functions
	public void initStaffEventsReport() {
		
	}

	/*
	 * getters and setters
	 */

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

	public JSONObject getJson() {
		return json;
	}

	public String getStudentSearchText() {
		return studentSearchText;
	}

	public void setStudentSearchText(String studentSearchText) {
		this.studentSearchText = studentSearchText;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setWeekDate(Date reportDate) {
		this.weekDate = reportDate;
	}

	public Date getWeekDate() {
		return weekDate;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getYear() {
		return year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getMonth() {
		return month;
	}

	public void setHoursUtilizedReport(HoursUtilizedReport hoursUtilizedReport) {
		this.hoursUtilizedReport = hoursUtilizedReport;
	}

	public HoursUtilizedReport getHoursUtilizedReport() {
		return hoursUtilizedReport;
	}

	public void setReportSelectItems(List<SelectItem> reportSelectItems) {
		this.reportSelectItems = reportSelectItems;
	}

	public List<SelectItem> getReportSelectItems() {
		return reportSelectItems;
	}

	public void setSelectedReportId(Long selectedReportId) {
		this.selectedReportId = selectedReportId;
	}

	public Long getSelectedReportId() {
		return selectedReportId;
	}

	public void setSelectedConsentIds(List<String> selectedConsentIds) {
		this.selectedConsentIds = selectedConsentIds;
	}

	public List<String> getSelectedConsentIds() {
		return selectedConsentIds;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportType() {
		return reportType;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getJsonString() {
		return jsonString;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Consent> getConsents() {
		return consents;
	}

	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	public List<Collection> getCollections() {
		return collections;
	}

	public String getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public List<StaffMember> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<StaffMember> staffList) {
		this.staffList = staffList;
	}

	public List<SelectItem> getStaffSelectItemList() {
		return staffSelectItemList;
	}

	public void setStaffSelectItemList(List<SelectItem> staffSelectItemList) {
		this.staffSelectItemList = staffSelectItemList;
	}

}
