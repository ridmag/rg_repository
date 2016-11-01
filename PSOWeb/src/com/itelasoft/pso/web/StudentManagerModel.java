package com.itelasoft.pso.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.icesoft.faces.component.paneltabset.TabChangeEvent;
import com.itelasoft.pso.beans.Consent;
import com.itelasoft.pso.beans.Contact;
import com.itelasoft.pso.beans.EnumItemCategory;
import com.itelasoft.pso.beans.FundingSource;
import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.Guardian;
import com.itelasoft.pso.beans.Location;
import com.itelasoft.pso.beans.Outlet;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ReferenceItem;
import com.itelasoft.pso.beans.SpecialNeed;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentConsent;
import com.itelasoft.pso.beans.StudentFundingSource;
import com.itelasoft.pso.beans.StudentGroup;
import com.itelasoft.util.SortObjectByName;
import com.itelasoft.util.SortSelectItemsByLabel;

import net.sf.json.JSONObject;

@ManagedBean(name = "studentManagerModel")
@SessionScoped
public class StudentManagerModel extends UIModel {
	private List<Student> students;
	private List<SpecialNeed> availableSpecialNeeds;
	private HashMap<Long, SpecialNeed> selectedSpecialNeeds;
	private Student student, tmpStudent;
	private boolean visibleSpecialNeeds;
	private boolean visibleFunding;
	private boolean selectAllSpecialNeeds;
	private boolean selectAllGroups, selectAllEvents;
	private String searchSNText;
	private String searchGroupText;
	private List<StudentGroup> availableGroups;
	private HashMap<Long, StudentGroup> selectedGroups;
	private boolean visibleGroups, visibleTransportGroups, allGroups;
	private boolean visibleGuardian, newGuardian, editGuardian;
	private String searchStudentText;
	private int selectedTabIndex, photoH, photoW, guardianIndex;
	private List<FundingSource> fundingSources;
	private StudentFundingSource studentFundingSrc;
	private FundingSource fundingSource;
	private List<Contact> availableGuardians;
	private String searchGuardianText;
	private Guardian guardian;
	private Contact selectedContact;
	private List<SelectItem> fundingHours;
	private boolean defaultAddress;
	private Long selectedOutletId;
	private List<SelectItem> outletSelectItems;
	private GroupedStudent groupedStudent = new GroupedStudent();
	private List<SelectItem> locationSelectItems;
	private Long selectedLocationId;
	private StudentGroup studentGroup;
	private List<Consent> consentList;
	private List<ProgramEvent> events;
	private Date fromDate;
	private String jsonString;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE - dd/MM/yyyy");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
	private HashMap<String, List<StudentGroup>> groupsForDays;
	private List<Object> oneRowTable;
	private List<SelectItem> relationships;
	private List<SelectItem> tmpRelationships;
	private Long selectedRelationshipId;
	private String selectedStudentId, nxtBtnTxt;
	private boolean visibleActRecs;
	private List<Integer> wizPages;
	private int wizardPage;
	private List<ProgramEvent> actProEvents;
	private List<GroupedStudent> actGroupedStudents;
	private HashMap<Long, GroupedStudent> selectedGS;
	private HashMap<Long, ProgramEvent> selectedPE;
	private String status = "Active";

	public StudentManagerModel() {
		init();
	}

	public void init() {
		students = serviceLocator.getStudentService().listByName(null, false);
		student = tmpStudent = null;
		selectedTabIndex = 0;
		searchStudentText = "";
		visibleFunding = visibleGroups = visibleTransportGroups = visibleGuardian = visibleSpecialNeeds = false;
		consentList = serviceLocator.getConsentService().findAll();
		oneRowTable = new ArrayList<Object>();
		oneRowTable.add(new Object());
		fromDate = new Date();
		tmpRelationships = new ArrayList<SelectItem>();
		List<ReferenceItem> refItems = serviceLocator.getReferenceItemService()
				.findItemsByCategory(EnumItemCategory.RELATIONSHIP, "Active");
		if (!refItems.isEmpty()) {
			for (ReferenceItem item : refItems) {
				tmpRelationships.add(new SelectItem(item.getId(), item.getItemValue()));
			}
			Collections.sort(tmpRelationships, new SortSelectItemsByLabel());
			tmpRelationships.add(0, new SelectItem(new Long(0), "Not Assigned"));
		} else {
			tmpRelationships.add(new SelectItem(new Long(0), "Not Available"));
		}
		selectAllEvents = false;
	}

	public void tabChangeListner(TabChangeEvent event) {
		if (student != null) {
			if (event.getOldTabIndex() == 0) {
				if (validateStudentFields())
					selectedTabIndex = event.getNewTabIndex();
				else
					selectedTabIndex = 0;
			} else if (selectedTabIndex == 4) {
			}
			selectedTabIndex = event.getNewTabIndex();
		} else
			showError("Please select a participant.");
	}

	public void searchStudents() {
		List<Student> students = serviceLocator.getTextSearchService().searchStudentsByNameNId(searchStudentText,
				status.equals("all") ? null : status);
		if (students == null || students.isEmpty())
			showError("No results for the given search text.");
		else {
			this.students = students;
			Collections.sort(this.students, new SortObjectByName());
			student = tmpStudent = null;
			selectedTabIndex = 0;
		}
	}

	public void selectStudentsStatus() {
		List<Student> students = serviceLocator.getStudentService().listByStatus(status);
		if (students == null || students.isEmpty())
			showError("No results for the given search text.");
		else {
			this.students = students;
			student = tmpStudent = null;
			selectedTabIndex = 0;
		}
	}

	public void searchStudents360() {
		List<Student> students = serviceLocator.getTextSearchService().searchStudents360(searchStudentText,
				status == "all" ? null : status);
		if (students == null || students.isEmpty())
			showError("No results for the given search text.");
		else {
			this.students = students;
			Collections.sort(students, new SortObjectByName());
		}
	}

	public void selectStudent(ClickActionEvent re) {
		clearInputs();
		;
		selectAllEvents = false;
		if (tmpStudent != null)
			tmpStudent.setUi_selected(false);
		defaultAddress = false;
		tmpStudent = (Student) re.getComponent().getAttributes().get("student");
		tmpStudent.setUi_selected(true);
		student = serviceLocator.getStudentService().retrieveEager(tmpStudent.getId());
		setActiveStudent();
		loadConsents();
		initPhotoImage();
		selectedStudentId = "{studentId:" + student.getId() + "}";
	}

	public void selectStudentGroup(ValueChangeEvent event) {
		clearInputs();
		List<StudentGroup> studentProgramList = new ArrayList<StudentGroup>();
		List<StudentGroup> transportProgramList = new ArrayList<StudentGroup>();
		List<StudentGroup> individualProgramList = new ArrayList<StudentGroup>();

		if (student.getGroups() != null && !student.getGroups().isEmpty()) {
			for (GroupedStudent gs : student.getGroups()) {
				if (!gs.getStatus().equals("Excluded")) {
					if (gs.getGroup().getProgram().getType().getName().equals("Student")) {
						studentProgramList.add(gs.getGroup());
					} else if (gs.getGroup().getProgram().getType().getName().equals("Transport")
							&& gs.getGroup().getParentGroup() == null) {
						transportProgramList.add(gs.getGroup());
					} else if (gs.getGroup().getProgram().getType().getName().equals("Individual")) {
						individualProgramList.add(gs.getGroup());
					}
				}
			}
		}
	}

	private void setActiveStudent() {
		if (student != null) {
			allGroups = true;
			events = null;
			groupsForDays = null;
			fromDate = new Date();
			if (student.getServiceEndDate() != null)
				processActiveRecords();
			else
				wizPages = new ArrayList<Integer>();
			if (student.getGroups() != null) {
				for (GroupedStudent gs : student.getGroups()) {
					if (gs.getStatus().equals("Excluded"))
						gs.setUi_selected(true);
				}
			}
			if (student.getId() != null) {
				sessionContext.setActiveString(student.getContact().getName());
			} else
				sessionContext.setActiveString("New-Student");
		} else
			sessionContext.setActiveString("");
	}

	public void initPhotoImage() {
		byte[] tmpData = null;
		if (student.getPhoto() != null) {
			tmpData = student.getPhoto().getBlobFileData().getData();
			if (tmpData != null) {
				BufferedImage loadImg;
				try {
					loadImg = ImageIO.read(new ByteArrayInputStream(tmpData));
					int w = loadImg.getWidth();
					int h = loadImg.getHeight();
					if (w > h) {
						photoW = 230;
						photoH = photoW * h / w;
					} else {
						photoH = 180;
						photoW = photoH * w / h;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Student CRUD Functions
	 */

	public void newStudent() {
		if (tmpStudent != null)
			tmpStudent.setUi_selected(false);
		tmpStudent = null;
		student = new Student();
		defaultAddress = false;
		student.setContact(new Contact());
		student.setMailingAddress(new Contact());
		student.setGender("Male");
		student.setSpecialNeeds(new ArrayList<SpecialNeed>());
		setActiveStudent();
		loadConsents();
		selectedTabIndex = 0;
	}

	public void statusChanged(ValueChangeEvent event) {
		String status = (String) event.getNewValue();
		if (status == "Active") {
			student.setServiceEndDate(null);
		} else {
			student.setServiceEndDate(new Date());
		}
	}

	public void serviceEndDateChanged(ValueChangeEvent event) {
		student.setServiceEndDate((Date) event.getNewValue());
		processActiveRecords();
	}

	public void saveStudent(ActionEvent event) {
		if (validateStudentFields()) {
			if (tmpStudent != null && !tmpStudent.getStatus().equals(student.getStatus())
					&& student.getStatus().equals("Inactive") && !wizPages.isEmpty()) {
				openActiveRecords();
				return;
			}
			saveStudent();
		}
	}

	public void saveStudent() {
		if (student.getId() == null) {
			student = serviceLocator.getStudentService().create(student);
			tmpStudent = student;
			student = serviceLocator.getStudentService().retrieveEager(student.getId()); // for
																							// not
																							// to
																							// update
																							// table
																							// data
																							// if
																							// user
																							// have
																							// done
																							// any
																							// changes
																							// to
																							// student
			tmpStudent.setUi_selected(true);
			students.add(tmpStudent);
			showInfo(Util.getMessage("student_label") + " added succesfully.");
		} else {

			serviceLocator.getStudentService().update(student);
			student.setUi_selected(true);
			students.set(students.indexOf(tmpStudent), student);
			tmpStudent = student;
			student = serviceLocator.getStudentService().retrieveEager(student.getId());
			showInfo(Util.getMessage("student_label") + " saved succesfully.");
		}
		if(students != null){
			Collections.sort(students, new SortObjectByName());
		}
		setActiveStudent();
	}

	public void openActiveRecords() {
		selectAllGroups = selectAllEvents = false;
		selectedPE = new HashMap<Long, ProgramEvent>();
		selectedGS = new HashMap<Long, GroupedStudent>();
		wizardPage = 0;
		nxtBtnTxt = "Next >";
		visibleActRecs = true;
	}

	public void wizardBack() {
		do {
			wizardPage--;
			nxtBtnTxt = "Next >";
		} while (!wizPages.contains(wizardPage) && wizardPage > 0);
	}

	public void wizardNext() {
		if (nxtBtnTxt.equals("Save")) {
			try {
				if (!selectedPE.values().isEmpty()) {
					List<ProgramEvent> events = new ArrayList<ProgramEvent>(selectedPE.values());
					serviceLocator.getStudentEventService().deleteByProEventsAndStudent(events, student.getId());
				}
				visibleActRecs = false;
				showInfo(Util.getMessage("student_label") + " records updated successfully.");
				// if (saveOnExit)
				saveStudent();
				return;
			} catch (Exception e) {
				showError("Operation not allowed. Record(s) may be in use.");
				return;
			}
		}
		do {
			wizardPage++;
			nxtBtnTxt = "Next >";
		} while (!wizPages.contains(wizardPage) && wizardPage < 4);
		if (wizardPage == wizPages.get(wizPages.size() - 1)) {
			nxtBtnTxt = "Save";
		}
	}

	public void selectGS(ValueChangeEvent event) {
		boolean selected = (Boolean) event.getNewValue();
		GroupedStudent gs = (GroupedStudent) event.getComponent().getAttributes().get("student");
		// gs.setUi_selected(selected);
		if (selected) {
			gs.setStatus("Excluded");
			selectedGS.put(gs.getId(), gs);
			if (selectedGS.values().size() == actGroupedStudents.size())
				selectAllGroups = true;
		} else {
			gs.setStatus("Active");
			selectedGS.remove(gs.getId());
			selectAllGroups = false;
		}
	}

	public void selectAllGS(ValueChangeEvent event) {
		selectedGS.clear();
		selectAllGroups = (Boolean) event.getNewValue();
		for (GroupedStudent gs : actGroupedStudents) {
			gs.setUi_selected(selectAllGroups);
			if (selectAllGroups) {
				gs.setStatus("Excluded");
				selectedGS.put(gs.getId(), gs);
			} else {
				gs.setStatus("Active");
			}
		}
	}

	public void selectPE(ValueChangeEvent event) {
		boolean selected = (Boolean) event.getNewValue();
		ProgramEvent pe = (ProgramEvent) event.getComponent().getAttributes().get("event");
		pe.setUi_selected(selected);
		if (selected) {
			selectedPE.put(pe.getId(), pe);
			if (selectedPE.values().size() == actProEvents.size()) {
				selectAllEvents = true;
			}
		} else {
			selectedPE.remove(pe.getId());
			selectAllEvents = false;
		}
	}

	public void selectAllPE(ValueChangeEvent event) {
		selectedPE.clear();
		selectAllEvents = (Boolean) event.getNewValue();
		for (ProgramEvent pe : actProEvents) {
			pe.setUi_selected(selectAllEvents);
			if (selectAllEvents) {
				selectedPE.put(pe.getId(), pe);
			}
		}
	}

	public void deleteStudent() {
		try {
			serviceLocator.getStudentService().delete(student.getId());
			students.remove(tmpStudent);
			student = tmpStudent = null;
			showInfo(Util.getMessage("student_label") + " deleted succesfully.");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	private boolean validateStudentFields() {
		List<String> componentIds = new ArrayList<String>();
		clearInputs();
		String firstName = student.getContact().getFirstName();
		String lastName = student.getContact().getLastName();
		String street = student.getContact().getStreetAddress();
		String city = student.getContact().getCity();
		String state = student.getContact().getState();
		String postalCode = student.getContact().getPostCode();
		String country = student.getContact().getCountry();
		String homeNo = student.getContact().getHomePhone();
		String mobileNo = student.getContact().getMobilePhone();
		String officeNo = student.getContact().getOfficePhone();
		String email = student.getContact().getEmail();
		String fax = student.getContact().getFax();

		if (!validateString(firstName) || !validateString(lastName)) {
			if (firstName.isEmpty() || firstName == null) {
				showError("First name cannot be empty.");
				componentIds.add("input_StudentFirstName");
			}
			if (lastName.isEmpty() || lastName == null) {
				showError("Last name cannot be empty.");
				componentIds.add("input_StudentLastName");
			}
			highlightInputs(componentIds);
			return false;
		}
		/*
		 * if (!isNumeric(mobileNo)) { showError("Mobile Number is not valid.");
		 * } if (!isNumeric(homeNo)) { showError("Home Number is not valid."); }
		 * if (!isNumeric(officeNo)) { showError("Office Number is not valid.");
		 * }
		 */
		if (!validateString(student.getCisid()) || !validateString(student.getMdsid())) {
			if (student.getCisid().isEmpty() || student.getCisid() == null) {
				showError("CISID cannot be empty.");
				componentIds.add("input_StudentCISID");
				highlightInputs(componentIds);
			}
			if (student.getMdsid().isEmpty() || student.getMdsid() == null) {
				showError("MDSID cannot be empty.");
				componentIds.add("input_StudentMDSID");
				highlightInputs(componentIds);
			}
			return false;
		}
		if (student.getStartFrom() == null) {
			showError("Start date cannot be empty.");
			componentIds.add("input_StudentSD");
			highlightInputs(componentIds);
			return false;
		}
		if (student.getStatus().equals("Inactive") && student.getServiceEndDate() == null) {
			showError("Inactive from date cannot be empty.");
			return false;
		}

		if (student.getDob() == null || student.getDob().after(new Date())) {
			showError("Invalid date of birth.");
			componentIds.add("input_StudentDob");
			highlightInputs(componentIds);
			return false;
		}

		if (!validateString(street) || !validateString(city) || !validateString(state) || !validateString(postalCode)
				|| !validateString(country)) {
			if (street.isEmpty() || street == null) {
				showError("Street address can not be empty.");
				componentIds.add("input_StreetAddress");
				highlightInputs(componentIds);
			}
			if (city.isEmpty() || city == null) {
				showError("City  can not be empty.");
				componentIds.add("input_StudentCity");
				highlightInputs(componentIds);
			}
			if (state.isEmpty() || state == null) {
				showError("State  can not be empty.");
				componentIds.add("input_StudentState");
				highlightInputs(componentIds);
			}
			if (postalCode.isEmpty() || postalCode == null) {
				showError("Postal code can not be empty.");
				componentIds.add("input_StudentPostalCode");
				highlightInputs(componentIds);
			}
			return false;
		}

		if ((mobileNo == null || mobileNo.isEmpty()) && (homeNo == null || homeNo.isEmpty())
				&& (officeNo == null || officeNo.isEmpty())) {
			showError("Required at least one phone number.");
			return false;
		}
		if (!mobileNo.equals("")) {
			if (!Util.validatePhoneNumberAndFax(mobileNo, "Mobile")) {
				return false;
			}
		}
		if (!homeNo.equals("")) {
			if (!Util.validatePhoneNumberAndFax(homeNo, "Home")) {
				return false;
			}
		}
		if (!officeNo.equals("")) {
			if (!Util.validatePhoneNumberAndFax(officeNo, "Office")) {
				return false;
			}
		}
		/*
		 * if (!isNumeric(fax)) { showError("Fax is not valid."); }
		 */
		Util.validatePhoneNumberAndFax(fax, "Fax");
		// if (!validateString(mobileNo) && !validateString(homeNo) &&
		// !validateString(officeNo)) {
		// if (mobileNo.isEmpty() || mobileNo == null) {
		// componentIds.add("input_StudentMobileNumber");
		// highlightInputs(componentIds);
		// }
		// if (homeNo.isEmpty() || homeNo == null) {
		// componentIds.add("input_StudentHomeNumber");
		// highlightInputs(componentIds);
		// }
		// if (officeNo.isEmpty() || officeNo == null) {
		// componentIds.add("input_StudentOfficeNumber");
		// highlightInputs(componentIds);
		// }
		// showError("Required at least one phone number.");
		// return false;
		// }
		// email validation
		if (!Util.validateEmail(email)) {
			componentIds.add("input_StudentEmail");
			highlightInputs(componentIds);
			return false;
		}
		if (!student.getMailAddressDefault() && !(validateString(student.getMailingAddress().getStreetAddress())
				&& validateString(student.getMailingAddress().getCity())
				&& validateString(student.getMailingAddress().getState())
				&& validateString(student.getMailingAddress().getPostCode())
				&& validateString(student.getMailingAddress().getCountry()))) {
			if (student.getMailingAddress().getStreetAddress().isEmpty()
					|| student.getMailingAddress().getStreetAddress() == null) {
				showError("Street address can not be empty.");
				componentIds.add("input_StreetMAddress");
				highlightInputs(componentIds);
			}
			if (student.getMailingAddress().getCity().isEmpty() || student.getMailingAddress().getCity() == null) {
				showError("City  can not be empty.");
				componentIds.add("input_StudentMCity");
				highlightInputs(componentIds);
			}
			if (student.getMailingAddress().getState().isEmpty() || student.getMailingAddress().getState() == null) {
				showError("State  can not be empty.");
				componentIds.add("input_StudentMState");
				highlightInputs(componentIds);
			}
			if (student.getMailingAddress().getPostCode().isEmpty()
					|| student.getMailingAddress().getPostCode() == null) {
				showError("Postal code can not be empty.");
				componentIds.add("input_StudentMPostalCode");
				highlightInputs(componentIds);
			}
			return false;
		}
		return true;
	}

	private void processActiveRecords() {
		actGroupedStudents = new ArrayList<GroupedStudent>();
		for (GroupedStudent gs : student.getGroups()) {
			if (gs.getStatus().equals("Active"))
				actGroupedStudents.add(gs);
		}
		actProEvents = serviceLocator.getProgramEventService().listEditableEventsByStudent(student.getId(),
				student.getServiceEndDate());
		wizPages = new ArrayList<Integer>();
		if (!actGroupedStudents.isEmpty())
			wizPages.add(1);
		if (!actProEvents.isEmpty())
			wizPages.add(2);
	}

	public void activeRecordsPopup() {
		if (visibleActRecs)
			visibleActRecs = false;
		else
			visibleActRecs = true;
	}

	private boolean validateString(String string) {
		if (string != null && !string.isEmpty()) {
			// email validation
			if (string == student.getContact().getEmail()) {
				Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
				Matcher mat = pattern.matcher(string);
				if (mat.matches()) {
					return true;
				} else {
					return false;
				}
			}
			return true;
		} else
			return false;
	}

	public void setMailingAddress(ValueChangeEvent event) {
		defaultAddress = (Boolean) event.getNewValue();
		if (defaultAddress) {
			if (student.getMailingAddress() != null && student.getMailingAddress().getId() == null) {
				student.setMailingAddress(null);
			}
		} else {
			if (student.getMailingAddress() == null) {
				student.setMailingAddress(new Contact());
			}
		}
	}

	/*
	 * Special Needs Functions
	 */

	public void listAvailableSpecialNeeds() {
		searchSNText = "";
		selectAllSpecialNeeds = false;
		selectedSpecialNeeds = new HashMap<Long, SpecialNeed>();
		if (student.getId() != null) {
			availableSpecialNeeds = serviceLocator.getSpecialNeedService().listAvailableByStudent(student.getId());
		} else {
			availableSpecialNeeds = serviceLocator.getSpecialNeedService().findAll();
		}
		if (student.getSpecialNeeds() != null && !student.getSpecialNeeds().isEmpty() && availableSpecialNeeds != null
				&& !availableSpecialNeeds.isEmpty()) {
			List<SpecialNeed> list = new ArrayList<SpecialNeed>();
			for (SpecialNeed sn : availableSpecialNeeds) {
				for (SpecialNeed ssn : student.getSpecialNeeds()) {
					if (sn.getId().equals(ssn.getId())) {
						list.add(sn);
						break;
					}
				}
			}
			availableSpecialNeeds.removeAll(list);
		}
		if (availableSpecialNeeds == null || availableSpecialNeeds.isEmpty()) {
			showError("There are no special need(s) available for this "
					+ Util.getMessage("student_label").toLowerCase());
		} else {
			addSpecialNeedPopup();
		}
	}

	public void selectSpecialNeed(ValueChangeEvent ve) {
		SpecialNeed sn = (SpecialNeed) ve.getComponent().getAttributes().get("sneed");
		boolean selected = (Boolean) ve.getNewValue();
		sn.setUi_selected(selected);
		if (selected) {
			selectedSpecialNeeds.put(sn.getId(), sn);
			if (selectedSpecialNeeds.values().containsAll(availableSpecialNeeds))
				selectAllSpecialNeeds = true;
		} else {
			selectAllSpecialNeeds = false;
			selectedSpecialNeeds.remove(sn.getId());
		}
	}

	public void selectAllSpecialNeeds(ValueChangeEvent ve) {
		Boolean selected = (Boolean) ve.getNewValue();
		for (SpecialNeed sn : availableSpecialNeeds) {
			sn.setUi_selected(selected);
			if (selected)
				selectedSpecialNeeds.put(sn.getId(), sn);
			else
				selectedSpecialNeeds.remove(sn.getId());
		}
	}

	public void saveSelectedSpecialNeeds() {
		if (!selectedSpecialNeeds.isEmpty()) {
			student.getSpecialNeeds().addAll(selectedSpecialNeeds.values());
			// showInfo("Selected Special Need(s) added to Student succesfully.
			// Please save Student..");
			addSpecialNeedPopup();
		}
	}

	public void deleteSpecialNeed(ActionEvent ae) {
		student.getSpecialNeeds().remove(ae.getComponent().getAttributes().get("snid"));
		// showInfo("Special Need removed succesfully. Please save Student..");
	}

	public void addSpecialNeedPopup() {
		if (visibleSpecialNeeds)
			visibleSpecialNeeds = false;
		else
			visibleSpecialNeeds = true;
	}

	/*
	 * Student Group Functions
	 */

	public void loadGroups() {
		if (!allGroups) {
			groupsForDays = new HashMap<String, List<StudentGroup>>();
			if (fromDate == null) {
				showError("Date of the time table cannot be empty..");
				events = null;
				return;
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(fromDate);
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 1);
			Date startDate = cal.getTime();
			cal.add(Calendar.DATE, 5);
			Date toDate = cal.getTime();
			events = serviceLocator.getProgramEventService().listForTimeTable(startDate, toDate, student.getId());
			if (events == null || events.isEmpty())
				showInfo("No groups have events for the given date range..");
			else {
				loadGroupFromEvents(startDate, toDate);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				JSONObject json = new JSONObject();
				if (startDate != null)
					json.put("fromDate", dateFormat.format(startDate));
				else
					json.put("fromDate", null);
				if (toDate != null)
					json.put("toDate", dateFormat.format(toDate));
				else
					json.put("toDate", null);
				json.put("studentId", student.getId());
				json.put("studentName", student.getContact().getName().replaceAll("&", "%26"));
				jsonString = json.toString();
			}
		}
	}

	private void loadGroupFromEvents(Date startDate, Date toDate) {
		groupsForDays = new HashMap<String, List<StudentGroup>>();
		SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
		List<Date> dates = generateDateList(startDate, toDate);
		for (Date date : dates) {
			List<ProgramEvent> eventsForDay = new ArrayList<ProgramEvent>();
			if (events != null && !events.isEmpty()) {
				for (ProgramEvent event : events) {
					if (dayFormat.format(event.getEventDate()).equals(dayFormat.format(date))) {
						eventsForDay.add(event);
					}
				}
				List<StudentGroup> groups = new ArrayList<StudentGroup>();
				for (StudentGroup group : serviceLocator.getStudentGroupService().findAll()) {
					group.setEvents(new ArrayList<ProgramEvent>());
					for (ProgramEvent proEvent : eventsForDay) {
						if (group.getId().equals(proEvent.getGroup().getId())) {
							group.getEvents().add(proEvent);
						}
					}
					if (!group.getEvents().isEmpty())
						groups.add(group);
				}
				groupsForDays.put(dayFormat.format(date), groups);
			}
		}
	}

	public List<ProgramEvent> getTransportEvents(ProgramEvent event) {
		List<ProgramEvent> transports = null;
		if (event != null) {
			transports = serviceLocator.getProgramEventService().listTransportForStudentPrograms(event.getEventDate(),
					student.getId(), event.getLocation().getId());
		}
		return transports;
	}

	public GroupedStudent getGroupedStudent(ProgramEvent event) {
		GroupedStudent gstu = serviceLocator.getGroupedStudentService().retrieveGroupedStudent(event.getGroup().getId(),
				student.getId());
		return gstu;
	}

	public int getImageWidth(StudentGroup group, String dim) {
		byte[] imgData = null;
		int width = 0;
		int height = 0;
		if (group.getPhoto() != null) {
			imgData = group.getPhoto().getBlobFileData().getData();
			if (imgData != null) {
				BufferedImage loadImg;
				try {
					loadImg = ImageIO.read(new ByteArrayInputStream(imgData));
					int w = loadImg.getWidth();
					int h = loadImg.getHeight();
					if (w > h) {
						width = 160;
						height = width * h / w;
					} else {
						height = 120;
						width = height * w / h;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (dim.equals("w"))
			return width;
		else
			return height;
	}

	private List<Date> generateDateList(Date startDate, Date endDate) {
		Date rosterDate = null;
		Calendar calendar = Calendar.getInstance();
		List<Date> dates = new ArrayList<Date>();
		calendar.setTime(startDate);
		do {
			/*
			 * if (calendar.get(Calendar.DAY_OF_WEEK) == 1 ||
			 * calendar.get(Calendar.DAY_OF_WEEK) == 7) {
			 * calendar.add(Calendar.DATE, 1); continue; }
			 */
			dates.add(calendar.getTime());
			calendar.add(Calendar.DATE, 1);
			rosterDate = calendar.getTime();
		} while (rosterDate.equals(endDate) || rosterDate.before(endDate));
		return dates;
	}

	public void listAvailableGroups(ActionEvent event) {
		String programTypeName = (String) event.getComponent().getAttributes().get("proTypeName");
		searchGroupText = "";
		availableGroups = serviceLocator.getStudentGroupService().listAvailableByStudentNProgram(student.getId(),
				searchGroupText, programTypeName);
		if (availableGroups == null || availableGroups.isEmpty())
			showError("There are no new groups available for this " + Util.getMessage("student_label").toLowerCase()
					+ ".");
		else {
			if (programTypeName.equals("Transport")) {
				groupedStudent = null;
				selectedLocationId = null;
				locationSelectItems = new ArrayList<SelectItem>();
				List<Location> locations = serviceLocator.getLocationService().findAll();
				if (locations != null && !locations.isEmpty()) {
					for (Location loc : locations) {
						locationSelectItems.add(new SelectItem(loc.getId(), loc.getName()));
					}
				} else
					locationSelectItems.add(new SelectItem(0, "Not Available"));
				addTransportGroupPopup();
			} else {
				selectAllGroups = false;
				selectedGroups = new HashMap<Long, StudentGroup>();
				addGroupPopup();
			}
		}
	}

	public void searchGroups(ActionEvent event) {
		String programTypeName = (String) event.getComponent().getAttributes().get("proTypeName");
		availableGroups = new ArrayList<StudentGroup>();
		try {
			Long id = Long.parseLong(searchGroupText);
			List<StudentGroup> groups = (List<StudentGroup>) serviceLocator.getStudentGroupService()
					.retrieveAvailableByStudentNProgramId(student.getId(), id, programTypeName);
			if (groups == null || groups.isEmpty())
				showError("No result available for this code.");
			else
				availableGroups.addAll(groups);
		} catch (NumberFormatException nFE) {
			availableGroups = serviceLocator.getStudentGroupService().listAvailableByStudentNProgram(student.getId(),
					searchGroupText, programTypeName);
			if (availableGroups == null || availableGroups.isEmpty())
				showError("No results for the given search text.");
		}
		if (!programTypeName.equals("Transport") && availableGroups != null && !availableGroups.isEmpty()) {
			selectAllGroups = true;
			for (StudentGroup grp : availableGroups) {
				if (selectedGroups.containsKey(grp.getId())) {
					grp.setUi_selected(true);
					selectedGroups.put(grp.getId(), grp);
				} else
					selectAllGroups = false;
			}
		}
	}

	public void saveSelectedGroups() {
		if (!selectedGroups.isEmpty()) {
			for (StudentGroup gro : selectedGroups.values()) {
				GroupedStudent grostu = new GroupedStudent();
				grostu.setGroup(gro);
				grostu.setStudent(student);
				grostu.setStatus("Active");
				student.getGroups().add(grostu);
			}
			student = serviceLocator.getStudentService().update(student);
			showInfo("Selected group(s) added to " + Util.getMessage("student_label").toLowerCase() + " succesfully.");
			addGroupPopup();
		}
	}

	public void selectGroup(ValueChangeEvent ve) {
		StudentGroup grp = (StudentGroup) ve.getComponent().getAttributes().get("grp");
		boolean selected = (Boolean) ve.getNewValue();
		grp.setUi_selected(selected);
		if (selected) {
			selectedGroups.put(grp.getId(), grp);
			if (selectedGroups.values().containsAll(availableGroups))
				selectAllGroups = true;
		} else {
			selectAllGroups = false;
			selectedGroups.remove(grp.getId());
		}
	}

	public void selectGroup(ClickActionEvent re) {
		studentGroup = (StudentGroup) re.getComponent().getAttributes().get("grp");
		groupedStudent = new GroupedStudent();
		groupedStudent.setStudent(student);
		groupedStudent.setGroup(studentGroup);
		groupedStudent.setStatus("Active");
		selectedLocationId = null;
	}

	public void enrollGroup() {
		if (validateLocation()) {
			student.getGroups().add(groupedStudent);
			student = serviceLocator.getStudentService().update(student);
			availableGroups.remove(studentGroup);
			showInfo("Group enrolled successfully.");
			studentGroup = null;
			groupedStudent = null;
		}
	}

	public void enrollGroupNExit() {
		if (validateLocation()) {
			if (groupedStudent.getId() == null) {
				student.getGroups().add(groupedStudent);
				availableGroups.remove(studentGroup);
			}
			student = serviceLocator.getStudentService().update(student);
			if (groupedStudent.getId() == null)
				showInfo("Group enrolled successfully.");
			else
				showInfo("Group updated successfully.");
			visibleTransportGroups = false;
		}
	}

	public boolean validateLocation() {
		Location picup = groupedStudent.getPickup();
		Location dropoff = groupedStudent.getDropoff();
		if (picup != null && dropoff != null) {
			if (picup.getName().equals(dropoff.getName())) {
				showError("Pick-up and Drop-off locations can not be same..");
				return false;
			} else {
				return true;
			}
		} else {
			showError("Please select pick-up and drop-off locations..");
			return false;
		}
	}

	public void selectAllGroups(ValueChangeEvent ve) {
		Boolean selected = (Boolean) ve.getNewValue();
		for (StudentGroup gro : availableGroups) {
			gro.setUi_selected(selected);
			if (selected)
				selectedGroups.put(gro.getId(), gro);
			else
				selectedGroups.remove(gro.getId());
		}
	}

	public void excludeGroup(ValueChangeEvent vce) {
		Boolean excluded = (Boolean) vce.getNewValue();
		GroupedStudent group = (GroupedStudent) vce.getComponent().getAttributes().get("group");
		if (excluded != null && excluded) {
			group.setStatus("Excluded");
			student = serviceLocator.getStudentService().update(student);
			showInfo("Selected group excluded from the " + Util.getMessage("student_label").toLowerCase()
					+ " successfully..");
		} else {
			group.setStatus("Active");
			student = serviceLocator.getStudentService().update(student);
			showInfo("Selected group included to the " + Util.getMessage("student_label").toLowerCase()
					+ " successfully..");
		}
	}

	public void deleteGroup(ActionEvent ae) {
		GroupedStudent grpStd = (GroupedStudent) ae.getComponent().getAttributes().get("grp");
		try {
			serviceLocator.getGroupedStudentService().delete(grpStd.getId());
			student.getGroups().remove(grpStd);
			showInfo(Util.getMessage("student_label") + " group removed succesfully");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public void addGroupPopup() {
		if (visibleGroups)
			visibleGroups = false;
		else
			visibleGroups = true;
	}

	public void addTransportGroupPopup() {
		if (visibleTransportGroups)
			visibleTransportGroups = false;
		else
			visibleTransportGroups = true;
	}

	public void addToPicup() {
		if (selectedLocationId != null) {
			Location location = serviceLocator.getLocationService().retrieve(selectedLocationId);
			groupedStudent.setPickup(location);
		}
	}

	public void addToDropoff() {
		if (selectedLocationId != null) {
			Location location = serviceLocator.getLocationService().retrieve(selectedLocationId);
			groupedStudent.setDropoff(location);
		}
	}

	/*
	 * Funding Source Functions
	 */

	public void listActiveFundingSources() {
		fundingSources = serviceLocator.getFundingSourceService().listActiveFundingSources();
		if (fundingSources != null && !fundingSources.isEmpty()) {
			studentFundingSrc = new StudentFundingSource();
			studentFundingSrc.setStudent(student);
			fundingSource = null;
			addFundingPopup();
			showInfo(
					"Please select start date as a corresponding fortnight start date or it will be converted automatically..");
		} else {
			showError("No funding sources available.");
		}
	}

	public void selectFundingSource(ClickActionEvent re) {
		clearInputs();
		if (fundingSource != null)
			fundingSource.setUi_selected(false);
		fundingSource = (FundingSource) re.getComponent().getAttributes().get("src");
		fundingSource.setUi_selected(true);
		studentFundingSrc.setFundingSrc(fundingSource);
		selectedOutletId = new Long(0);
		outletSelectItems = new ArrayList<SelectItem>();
		List<Outlet> outlets = serviceLocator.getOutletService().listOutletsByFundingSource(fundingSource.getId());
		if (outlets != null && !outlets.isEmpty()) {
			outletSelectItems.add(new SelectItem(0, "Select One"));
			for (Outlet out : outlets) {
				outletSelectItems.add(new SelectItem(out.getId(), out.getName()));
			}
		} else
			outletSelectItems.add(new SelectItem(0, "Not Available"));
	}

	public void selectStartDate(ValueChangeEvent ve) {
		Date date = (Date) ve.getNewValue();
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			TimeTrackerModel timeTrackerModel = new TimeTrackerModel();
			Date fortnightStart = timeTrackerModel.getFortnightStartDate(date);
			if (!dateFormat.format(date).equals(dateFormat.format(fortnightStart)))
				showInfo("Start date changed from '" + dateFormat.format(date) + "' to '"
						+ dateFormat.format(fortnightStart) + "'");
			studentFundingSrc.setFundingStartDate(fortnightStart);
		}
	}

	public void saveFundingSource() {
		List<String> componentIds = new ArrayList<String>();
		clearInputs();
		if (studentFundingSrc.getFundingStartDate() == null) {
			showError("Start date can not be empty..");
			componentIds.add("input_FundingStartDate");
			highlightInputs(componentIds);
			return;
		}
		for (StudentFundingSource src : student.getFundingSrcs()) {
			if (dateFormat.format(src.getFundingStartDate())
					.equals(dateFormat.format(studentFundingSrc.getFundingStartDate()))) {
				showError("There is already a funding source exists for the same date..");
				return;
			}
		}
		TimeTrackerModel model = new TimeTrackerModel();
		Date start = model.getFortnightStartDate(studentFundingSrc.getFundingStartDate());
		Date end = model.getFortnightEnd();
		if (serviceLocator.getStudentEventService().isEventExist(student.getId(), start, end)) {
			showError(
					"Existing events found within the selected fortnight which are funded by another funding source.");
			showInfo("Please select a future date..");
			componentIds.add("input_FundingStartDate");
			highlightInputs(componentIds);
			return;
		}
		if (studentFundingSrc.getFundingHours() <= 0) {
			showError("Invalid contracted hours..");
			componentIds.add("input_Hours");
			highlightInputs(componentIds);
			return;
		}
		if (selectedOutletId == 0) {
			showError("Please select a outlet..");
			componentIds.add("select_OneOutlets");
			highlightInputs(componentIds);
			return;
		}
		studentFundingSrc.setOutlet(serviceLocator.getOutletService().retrieve(selectedOutletId));
		student.getFundingSrcs().add(studentFundingSrc);
		student = serviceLocator.getStudentService().update(student);
		showInfo("Funding source added successfully..");
		addFundingPopup();
	}

	public void addFundingPopup() {
		if (visibleFunding)
			visibleFunding = false;
		else
			visibleFunding = true;
	}

	// public void checkActive(ValueChangeEvent ve) {
	// Boolean selected = (Boolean) ve.getNewValue();
	// studentFundingSrc = (StudentFundingSource) ve.getComponent()
	// .getAttributes().get("src");
	// List<StudentFundingSource> sfc = student.getFundingSrcs();
	// if (selected) {
	// for (StudentFundingSource sfsrc : sfc) {
	// sfsrc.setActive(false);
	// }
	// studentFundingSrc.setActive(selected);
	// student.setActiveFundingSrc(studentFundingSrc);
	// student = serviceLocator.getStudentService().update(student);
	// showInfo("Funding source successfully activated..");
	// }
	// }

	public void removeFunding(ActionEvent ae) {
		try {
			StudentFundingSource sfs = (StudentFundingSource) ae.getComponent().getAttributes().get("funding");
			serviceLocator.getStudentFundingSourceService().delete(sfs.getId());
			showInfo("Funding source removed succesfully.");
			student.getFundingSrcs().remove(sfs);
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	/*
	 * Guardian Functions
	 */

	public void addNewGuardian() {
		guardian = new Guardian(student, new Contact());
		newGuardian = true;
		editGuardian = false;
		// selectedRelationshipId = new Long(0);
		refreshRelationship(guardian);
		searchGuardianText = "";
		// availableGuardians = new ArrayList<Contact>();
		addGuardianPopup();
	}

	public void searchGuardians() {
		// guardian = new Guardian(student, new Contact());
		try {
			// See if the user has entered an ID instead name
			Long id = Long.parseLong(searchGuardianText);
			Contact con = serviceLocator.getContactService().retrive(id);
			if (con == null)
				showError("No result available for this code.");
			else {
				availableGuardians = new ArrayList<Contact>();
				availableGuardians.add(con);
			}
		} catch (NumberFormatException nFE) {
			// User has entered a name
			availableGuardians = serviceLocator.getContactService().listByName(searchGuardianText);
			if (availableGuardians == null || availableGuardians.isEmpty())
				showError("No results for the given search text.");
		} catch (Exception exception) {
			showExceptionAsError(exception);
		}
	}

	public void selectGuardianType(ValueChangeEvent event) {
		newGuardian = (Boolean) event.getNewValue();
		if (newGuardian) {
			guardian = new Guardian(student, new Contact());
			availableGuardians.clear();
		} else {
			guardian.setContact(null);
			searchGuardians();
		}
	}

	public void selectContact(ClickActionEvent re) {
		if (selectedContact != null)
			selectedContact.setUi_selected(false);
		selectedContact = (Contact) re.getComponent().getAttributes().get("guardian");
		selectedContact.setUi_selected(true);
		guardian.setContact(serviceLocator.getContactService().retrive(selectedContact.getId()));
	}

	public void editGuardian(ActionEvent ae) {
		guardian = (Guardian) ae.getComponent().getAttributes().get("guardian");
		if (guardian != null) {
			guardianIndex = student.getGuardians().indexOf(guardian);
			guardian = serviceLocator.getGuardianService().retrive(guardian.getStudent().getId(),
					guardian.getContact().getId());
			editGuardian = true;
			refreshRelationship(guardian);
			addGuardianPopup();
		}
	}

	private void refreshRelationship(Guardian guardian) {
		relationships = new ArrayList<SelectItem>();
		relationships.addAll(tmpRelationships);
		if (guardian.getRelationship() != null) {
			boolean found = false;
			for (SelectItem si : relationships) {
				if (si.getValue().equals(guardian.getRelationship().getId())) {
					found = true;
					break;
				}
			}
			if (!found)
				relationships.add(
						new SelectItem(guardian.getRelationship().getId(), guardian.getRelationship().getItemValue()));
			selectedRelationshipId = guardian.getRelationship().getId();
		} else
			selectedRelationshipId = new Long(0);
	}

	public void saveGuardian() {
		if (validateGuardianFields()) {
			try {
				if (editGuardian) {
					guardian = serviceLocator.getGuardianService().update(guardian);
					student.getGuardians().set(guardianIndex, guardian);
					showInfo("Guardian updated succesfully.");
				} else {
					guardian = serviceLocator.getGuardianService().create(guardian);
					student.getGuardians().add(guardian);
					showInfo("Selected guardian added to the " + Util.getMessage("student_label").toLowerCase()
							+ " succesfully.");
				}
				Collections.sort(student.getGuardians(), new SortObjectByName());
				addGuardianPopup();
			} catch (DataIntegrityViolationException e) {
				showError("This " + Util.getMessage("student_label").toLowerCase()
						+ " is already assigned to this guardian.");
			}
		}
	}

	public void removeGuardian(ActionEvent ae) {
		try {
			guardian = (Guardian) ae.getComponent().getAttributes().get("guardian");
			serviceLocator.getGuardianService().delete(guardian);
			student.getGuardians().remove(guardian);
			guardian = null;
			showInfo("Guardian removed succesfully.");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	private boolean validateGuardianFields() {
		List<String> componentIds = new ArrayList<String>();
		clearInputs();
		if (guardian.getContact() == null) {
			showError("Please select a contact.");
			return false;
		}
		if (selectedRelationshipId == null || selectedRelationshipId == 0) {
			showError("Please select a Relationship.");
			componentIds.add("select_OneGuardianRelationship");
			highlightInputs(componentIds);
			return false;
		} else {
			guardian.setRelationship(serviceLocator.getReferenceItemService().retrieve(selectedRelationshipId));
		}
		if (guardian.getContact().getId() != null
				&& guardian.getContact().getId().equals(student.getContact().getId())) {
			showError("Cannot save own contact as a guardian..");
			return false;
		}
		String firstName = guardian.getContact().getFirstName();
		String lastName = guardian.getContact().getLastName();
		String address = guardian.getContact().getStreetAddress();
		String city = guardian.getContact().getCity();
		String state = guardian.getContact().getState();
		String country = guardian.getContact().getCountry();
		String postCode = guardian.getContact().getPostCode();
		String homeNo = guardian.getContact().getHomePhone();
		String mobileNo = guardian.getContact().getMobilePhone();
		String officeNo = guardian.getContact().getOfficePhone();
		String email = guardian.getContact().getEmail();
		if (!validateString(firstName) || !validateString(lastName)) {
			showError("First name & last name cannot be empty..");
			componentIds.add("input_GuardianFirstName");
			componentIds.add("input_GuardianLastName");
			highlightInputs(componentIds);
			return false;
		}
		if (!validateString(mobileNo) && !validateString(homeNo) && !validateString(officeNo)) {
			showError("Required atleast one phone number..");
			componentIds.add("input_GuardianOfficeNumber");
			componentIds.add("input_GuardianHomeNumber");
			componentIds.add("input_GuardianMobileNumber");
			highlightInputs(componentIds);
			return false;
		}
		if (!mobileNo.equals("")) {
			if (!Util.validatePhoneNumberAndFax(mobileNo, "Mobile")) {
				return false;
			}
		}
		if (!homeNo.equals("")) {
			if (!Util.validatePhoneNumberAndFax(homeNo, "Home")) {
				return false;
			}
		}
		if (!officeNo.equals("")) {
			if (!Util.validatePhoneNumberAndFax(officeNo, "Office")) {
				return false;
			}
		}
		if (!email.equals("")) {
			if (!Util.validateEmail(email)) {
				return false;
			}
		}

		if (!validateString(address) || !validateString(city) || !validateString(state) || !validateString(postCode)
				|| !validateString(country)) {
			if (address.isEmpty() || address == null) {
				showError("Street address can not be empty.");
				componentIds.add("input_GuardianAddress");
				highlightInputs(componentIds);
			}
			if (city.isEmpty() || city == null) {
				showError("City  can not be empty.");
				componentIds.add("input_GuardianCity");
				highlightInputs(componentIds);
			}
			if (state.isEmpty() || state == null) {
				showError("State  can not be empty.");
				componentIds.add("input_GuardianState");
				highlightInputs(componentIds);
			}
			if (postCode.isEmpty() || postCode == null) {
				showError("Postal code can not be empty.");
				componentIds.add("input_GuardianPostalCode");
				highlightInputs(componentIds);
			}
			return false;
		}
		return true;
	}

	public void addGuardianPopup() {
		clearInputs();
		if (visibleGuardian) {
			visibleGuardian = false;
		} else
			visibleGuardian = true;
	}

	/*
	 * Student Consent Methods
	 */

	public void selectConsent(ValueChangeEvent ve) {
		boolean selected = (Boolean) ve.getNewValue();
		Consent consent = (Consent) ve.getComponent().getAttributes().get("con");
		if (selected) {
			student.getStudentConsents().add(createStudentConsent(consent));
		} else {
			for (StudentConsent sc : student.getStudentConsents()) {
				if (sc.getConsent().getId().equals(consent.getId())) {
					student.getStudentConsents().remove(sc);
					break;
				}
			}
		}
		/*
		 * student.setStudentConsents(serviceLocator.getStudentConsentService()
		 * .listConsentsByStudent(student.getId())); if (selected) { if
		 * (serviceLocator.getStudentConsentService()
		 * .checkStudentConsentAvailability(student.getId(), con.getId())) {
		 * createStudentConsent(con); } } else { for (StudentConsent stuCon :
		 * student.getStudentConsents()) { if
		 * (stuCon.getConsent().getId().equals(con.getId())) {
		 * serviceLocator.getStudentConsentService().delete( stuCon.getId());
		 * break; } } } consentList =
		 * serviceLocator.getConsentService().listWithSelected(
		 * student.getId());
		 */
	}

	private boolean isNumeric(String str) {
		if (str == null || str.equals("") || str.isEmpty())
			return true;
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;

	}

	private void loadConsents() {
		for (Consent consent : consentList) {
			consent.setSelected(false);
			for (StudentConsent sc : student.getStudentConsents()) {
				if (sc.getConsent().getId().equals(consent.getId())) {
					consent.setSelected(true);
					break;
				}
			}
		}
	}

	private StudentConsent createStudentConsent(Consent consent) {
		StudentConsent stuConst = new StudentConsent();
		stuConst.setStudent(student);
		stuConst.setConsent(consent);
		// serviceLocator.getStudentConsentService().create(stuConst);
		return stuConst;
	}

	/*
	 * // NDIS Program Tab
	 * 
	 * public void generateNDISEvents() { if (validateDetails()) {
	 * selectAllEvents = false; int notGenerated = 0; selectedNdisEventsMap =
	 * new HashMap<Long, NdisCommittedEvent>(); selectedNdisEventsMapUncommit =
	 * new HashMap<Long, NdisCommittedEvent>(); ndisEventsMap = new
	 * HashMap<Long, List<NdisCommittedEvent>>(); ndisEvents = new
	 * ArrayList<NdisCommittedEvent>(); clusterMap = new HashMap<Long,
	 * List<NdisSupportItem>>(); priceMap = new HashMap<Long,
	 * List<NdisPrice>>(); if (selectedGroupId > 0) { if
	 * (stdGrpMap.get(selectedGroupId).getNdis() != null) {
	 * generateGroupNDISEvents(stdGrpMap.get(selectedGroupId)); } else {
	 * showError("Programs are not processed. Missing NDIS Support Cluster."); }
	 * } else { for (StudentGroup group : stdGrpMap.values()) {
	 * 
	 * if (group.getNdis() != null) { generateGroupNDISEvents(group); } else {
	 * notGenerated++; } } if (notGenerated > 0) showError(notGenerated +
	 * " Programs are not processed. Missing NDIS Support Cluster."); else if
	 * (notGenerated == 1) showError(notGenerated +
	 * " Program is not processed. Missing NDIS Support Cluster.");
	 * 
	 * else { showError(
	 * " No Programs are not processed. Missing NDIS Support Cluster." ); }
	 * 
	 * } } }
	 * 
	 * private void generateGroupNDISEvents(StudentGroup group) { hours =
	 * uncommittedHours = committedHours = new Double(0); uncommittedunits =
	 * committedunits = new Integer(0); flag = false;
	 * 
	 * individualStudentEvents = new ArrayList<StudentEvent>();
	 * transportStudentEvents = new ArrayList<StudentEvent>();
	 * 
	 * List<NdisCommittedEvent> claimedNdisCommittedEvents = new
	 * ArrayList<NdisCommittedEvent>(); claimedNdisCommittedEvents =
	 * serviceLocator
	 * .getNdisCommittedEventService().retrieveClaimedCommittedEvents(
	 * student.getId(), group.getId(), sDate, eDate); HashMap<String, WeekDay>
	 * weekDays = null;
	 * 
	 * if (group.getProgram().getType().getName().equals("Individual")) {
	 * individualStudentEvents = serviceLocator.getStudentEventService()
	 * .retrieveStudentEventsByDates(student.getId(), group.getId(), sDate,
	 * eDate); } if (group.getProgram().getType().getName().equals("Transport"))
	 * { transportStudentEvents = serviceLocator.getStudentEventService()
	 * .retrieveStudentEventsByDates(student.getId(), group.getId(), sDate,
	 * eDate); }
	 * 
	 * 
	 * // ndisEvents = new ArrayList<NdisCommittedEvent>(); Calendar eventDate =
	 * Calendar.getInstance(); eventDate.setTime(sDate); Calendar endDate =
	 * Calendar.getInstance(); endDate.setTime(eDate); SimpleDateFormat
	 * formatDay = new SimpleDateFormat("EEEE"); overrideCluster(group);
	 * 
	 * if (group.getProgram().getType().getName().equals("Transport")) {
	 * selectPrice(group); }
	 * 
	 * 
	 * if (!group.getWeekDays().isEmpty()) { weekDays = new HashMap<String,
	 * WeekDay>(); for (WeekDay day : group.getWeekDays()) {
	 * weekDays.put(day.getName(), day); } } do { if
	 * (eventDate.getTime().compareTo(group.getEndDate()) > 0) { break; } if
	 * (eventDate.getTime().compareTo(group.getStartDate()) < 0) {
	 * eventDate.setTime(group.getStartDate()); continue; }
	 * 
	 * if (serviceLocator.getHolidayService().isHoliday(new Long(1),
	 * eventDate.getTime())) { eventDate.add(Calendar.DATE, 1); continue; } if
	 * (!group.getWeekDays().isEmpty() &&
	 * !weekDays.containsKey(formatDay.format(eventDate .getTime()))) {
	 * eventDate.add(Calendar.DATE, 1); continue; }
	 * 
	 * 
	 * if (group.getProgram().getType().getName().equals("Individual")) {
	 * boolean flag = false; if (individualStudentEvents.size() != 0) { for
	 * (StudentEvent eve : individualStudentEvents) { Calendar eveDate =
	 * Calendar.getInstance();
	 * eveDate.setTime(eve.getProEvent().getEventDate()); int value =
	 * eveDate.compareTo(eventDate); if (value == 0) { flag = true; break; } }
	 * if (!flag) { eventDate.add(Calendar.DATE, 1); continue; } } else { //
	 * eventDate.add(Calendar.DATE, 1); // showError
	 * (student.getContact().getName()+" has no attended events for "
	 * +group.getName()+" in given time period"); break; } } if
	 * (group.getProgram().getType().getName().equals("Transport")) { boolean
	 * flag = false; if (transportStudentEvents.size() != 0) { for (StudentEvent
	 * eve : transportStudentEvents) { Calendar eveDate =
	 * Calendar.getInstance();
	 * eveDate.setTime(eve.getProEvent().getEventDate()); int value =
	 * eveDate.compareTo(eventDate); if (value == 0) { flag = true; break; } }
	 * if (!flag) { eventDate.add(Calendar.DATE, 1); continue; } } else { //
	 * eventDate.add(Calendar.DATE, 1); // showError
	 * (student.getContact().getName()+" has no attended events for "
	 * +group.getName()+" in given time period"); break; } }
	 * 
	 * if (claimedNdisCommittedEvents != null) { boolean flag = false; for
	 * (NdisCommittedEvent event : claimedNdisCommittedEvents) { Calendar
	 * eveDate = Calendar.getInstance(); eveDate.setTime(event.getEventDate());
	 * int value = eveDate.compareTo(eventDate); if (value == 0) { flag = true;
	 * break; } } if (flag) { eventDate.add(Calendar.DATE, 1); continue; } }
	 * 
	 * NdisCommittedEvent ndisEvent = createNdisCommittedEvent(student,
	 * eventDate.getTime(), group); if
	 * (ndisEventsMap.containsKey(group.getId())) {
	 * ndisEventsMap.get(group.getId()).add(ndisEvent); } else {
	 * List<NdisCommittedEvent> list = new ArrayList<NdisCommittedEvent>();
	 * list.add(ndisEvent); ndisEventsMap.put(group.getId(), list); }
	 * eventDate.add(Calendar.DATE, 1); } while
	 * (eventDate.getTime().compareTo(endDate.getTime()) != 0); if (flag) { if
	 * (programTypeId != 2) { List<NdisCommittedEvent> committedevents =
	 * serviceLocator .getNdisCommittedEventService().getCommittedEvents(
	 * student.getId(), group.getId(), sDate, eDate); if (committedevents !=
	 * null) { for (NdisCommittedEvent eve : committedevents) { committedHours
	 * += eve.getHours(); } } else { committedHours = 0.0; } ndisEvent
	 * .setUncommittedhours(uncommittedHours - committedHours);
	 * ndisEvent.setCommittedHours(committedHours); } else {
	 * List<NdisCommittedEvent> committedevents = serviceLocator
	 * .getNdisCommittedEventService().getCommittedEvents( student.getId(),
	 * group.getId(), sDate, eDate);
	 * 
	 * if (committedevents != null) {
	 * ndisEvent.setCommittedUnits(committedevents.size()); committedunits =
	 * committedevents.size(); } else { ndisEvent.setCommittedUnits(0);
	 * committedunits = 0; } ndisEvent .setUncommittedUnits(uncommittedunits -
	 * committedunits); }
	 * 
	 * 
	 * if (programTypeId == 2) { committedTransportEvents = serviceLocator
	 * .getNdisCommittedEventService().getCommittedEvents( student.getId(),
	 * group.getId(), sDate, eDate); if (committedTransportEvents != null) { for
	 * (NdisCommittedEvent eve : committedTransportEvents) { if
	 * (eve.getNoOfUnits() != null) { committedUnits += eve.getNoOfUnits(); } }
	 * } else { committedUnits = 0; }
	 * ndisEvent.setCommittedUnits(committedUnits); }
	 * 
	 * ndisEvents.add(ndisEvent); } else { // ndisEvents = new
	 * ArrayList<NdisCommittedEvent>(); ndisEvent = new NdisCommittedEvent();
	 * 
	 * showError(group.getName() + " has no event(s) for given Time Period."); }
	 * }
	 * 
	 * private NdisCommittedEvent createNdisCommittedEvent(Student student, Date
	 * eventDate, StudentGroup studentGroup) { ndisEvent = new
	 * NdisCommittedEvent(); ndisEvent.setParticipant(student);
	 * ndisEvent.setStudentGroup(studentGroup);
	 * ndisEvent.setEventDate(eventDate);
	 * ndisEvent.setStartTime(studentGroup.getStartTime());
	 * ndisEvent.setEndTime(studentGroup.getEndTime());
	 * ndisEvent.setNdisSupportCluster(studentGroup.getNdis()); //
	 * ndisEvent.setClusterOverride(studentGroup.getNdis());
	 * ndisEvent.setClaimed(false); if
	 * (!studentGroup.getProgram().getType().getName().equals("Transport")) {
	 * Date groupSTime = studentGroup.getStartTime(); Date groupETime =
	 * studentGroup.getEndTime(); try { double diff = groupETime.getTime() -
	 * groupSTime.getTime(); double diffHours = diff / 3600000.0; hours =
	 * diffHours; uncommittedHours = uncommittedHours + hours;
	 * ndisEvent.setHours(hours); } catch (Exception e) { e.printStackTrace(); }
	 * } else { // ndisEvent.setPrice(new NdisPrice());
	 * ndisEvent.setNoOfUnits(1); uncommittedunits = uncommittedunits + 1; }
	 * flag = true; return ndisEvent; }
	 * 
	 * public void overrideCluster(StudentGroup group) { List<NdisSupportItem>
	 * clusterList = new ArrayList<NdisSupportItem>(); double staffs, students;
	 * String type = group.getNdis().getNdisClusterType(); staffs =
	 * group.getNdis().getNumerator(); students =
	 * group.getNdis().getDenominator(); double supportClusterOriginal = staffs
	 * / students; List<NdisSupportItem> items = serviceLocator
	 * .getNdisSupportItemService().findAllByType(type); if (items != null) { if
	 * (group.getProgram().getType().getName().equals("Student") ||
	 * group.getProgram().getType().getName() .equals("Individual")) { for
	 * (NdisSupportItem item : items) { double staff, student; staff =
	 * item.getNumerator(); student = item.getDenominator(); double
	 * supportClusterOverride = staff / student; if (supportClusterOriginal >=
	 * supportClusterOverride) { clusterList.add(item);
	 * clusterMap.put(group.getId(), clusterList); } } } else { for
	 * (NdisSupportItem item : items) { clusterList.add(item);
	 * clusterMap.put(group.getId(), clusterList); } } } else { showError(
	 * "There are no NDIS Support cluster(s) for given Cluster Type."); } }
	 * 
	 * 
	 * public void selectPrice(StudentGroup group) { List<NdisPrice> priceList =
	 * new ArrayList<NdisPrice>(); List<NdisPrice> prices =
	 * group.getNdis().getNdisPrice(); for (NdisPrice price : prices) {
	 * priceList.add(price); priceMap.put(group.getId(), priceList); } }
	 * 
	 * public void selectAllNdisEvents(ValueChangeEvent ve) { committedList =
	 * new ArrayList<String>(); selectAllEvents = (Boolean) ve.getNewValue();
	 * for (NdisCommittedEvent event : ndisEvents) {
	 * event.setUi_selected(selectAllEvents); if (event.isUi_selected()) { if
	 * (programTypeId == 2) { if (event.getCommittedUnits() != null &&
	 * event.getCommittedUnits() > 0) { selectedNdisEventsMapUncommit.put(event
	 * .getStudentGroup().getId(), event); } else {
	 * selectedNdisEventsMap.put(event.getStudentGroup() .getId(), event); } }
	 * else { if (event.getCommittedHours() != null && event.getCommittedHours()
	 * > 0) { selectedNdisEventsMapUncommit.put(event
	 * .getStudentGroup().getId(), event); } else {
	 * selectedNdisEventsMap.put(event.getStudentGroup() .getId(), event); } } }
	 * else { selectedNdisEventsMapUncommit.clear();
	 * selectedNdisEventsMap.clear(); } } }
	 * 
	 * public void selectNdisEvent(ValueChangeEvent ve) { selectEvent =
	 * ((Boolean) ve.getNewValue() != null ? true : false); eventNdis =
	 * (NdisCommittedEvent) ve.getComponent().getAttributes() .get("event"); //
	 * Long groupId = eventNdis.getStudentGroup().getId(); //
	 * List<NdisCommittedEvent> list = ndisEventsMap.get(groupId);
	 * eventNdis.setUi_selected(selectEvent); if (eventNdis.isUi_selected()) {
	 * if (programTypeId == 2) { if (eventNdis.getCommittedUnits() != null &&
	 * eventNdis.getCommittedUnits() > 0) {
	 * selectedNdisEventsMapUncommit.put(eventNdis .getStudentGroup().getId(),
	 * eventNdis); } else {
	 * selectedNdisEventsMap.put(eventNdis.getStudentGroup() .getId(),
	 * eventNdis); } } else { if (eventNdis.getCommittedHours() != null &&
	 * eventNdis.getCommittedHours() > 0) {
	 * selectedNdisEventsMapUncommit.put(eventNdis .getStudentGroup().getId(),
	 * eventNdis); } else {
	 * selectedNdisEventsMap.put(eventNdis.getStudentGroup() .getId(),
	 * eventNdis); } }
	 * 
	 * } else { if ((eventNdis.getCommittedHours() != null && eventNdis
	 * .getCommittedHours() > 0) || (eventNdis.getCommittedUnits() != null &&
	 * eventNdis .getCommittedUnits() > 0)) {
	 * selectedNdisEventsMapUncommit.remove(eventNdis
	 * .getStudentGroup().getId()); } else {
	 * selectedNdisEventsMap.remove(eventNdis.getStudentGroup() .getId()); } }
	 * if (ndisEvents.size() == selectedNdisEventsMap.size()) { selectAllEvents
	 * = true; } else if (ndisEvents.size() ==
	 * selectedNdisEventsMapUncommit.size()) { selectAllEvents = true; } else {
	 * selectAllEvents = false; } }
	 * 
	 * public void commitAll(ActionEvent ae) { List<NdisCommittedEvent>
	 * finalList = new ArrayList<NdisCommittedEvent>(); for (NdisCommittedEvent
	 * eve : selectedNdisEventsMap.values()) {
	 * 
	 * if (!eve.getNdisSupportCluster().getId()
	 * .equals(eve.getClusterOverrideId())) { for (NdisCommittedEvent event :
	 * ndisEventsMap.get(eve .getStudentGroup().getId())) {
	 * event.setClusterOverride(new NdisSupportItem());
	 * event.getClusterOverride() .setId(eve.getClusterOverrideId());
	 * //event.setUncommittedhours(0.0); if
	 * (event.getStudentGroup().getProgram().getType() .getName() ==
	 * "Transport") { event.setPriceId(eve.getPriceId()); count++; }
	 * 
	 * //currentcommittedHours += event.getHours();
	 * //eve.setCommittedHours(currentcommittedHours);
	 * 
	 * } //committedHours = currentcommittedHours; }
	 * 
	 * 
	 * Double currentcommittedHours = 0.0; int count = 0; for
	 * (NdisCommittedEvent event : ndisEventsMap.get(eve
	 * .getStudentGroup().getId())) { if (!eve.getNdisSupportCluster().getId()
	 * .equals(eve.getClusterOverrideId())) { event.setClusterOverride(new
	 * NdisSupportItem()); event.getClusterOverride()
	 * .setId(eve.getClusterOverrideId()); } if (programTypeId == 2) { //
	 * event.setPrice(eve.getPrice()); event.setKmsPerDay(kmsPerDay); count++; }
	 * else { currentcommittedHours += event.getHours(); committedHours +=
	 * currentcommittedHours; } } eve.setCommittedHours(currentcommittedHours);
	 * eve.setUncommittedhours(0.0); eve.setCommittedUnits(count);
	 * eve.setUncommittedUnits(0);
	 * 
	 * finalList.addAll(ndisEventsMap.get(eve.getStudentGroup().getId()));
	 * eve.setUi_selected(false); } for (NdisCommittedEvent event : finalList) {
	 * serviceLocator.getNdisCommittedEventService().create(event); }
	 * selectedNdisEventsMap.clear(); if (ndisEvents.size() ==
	 * selectedNdisEventsMap.size()) { selectAllEvents = true; } else if
	 * (ndisEvents.size() == selectedNdisEventsMapUncommit.size()) {
	 * selectAllEvents = true; } else { selectAllEvents = false; } showInfo(
	 * "Uncommitted Events are committed successfully for " +
	 * student.getContact().getFirstName() + " " +
	 * student.getContact().getLastName()); }
	 * 
	 * public void uncommitEvents(ActionEvent ae) { for (NdisCommittedEvent eve
	 * : selectedNdisEventsMapUncommit.values()) {
	 * uncommitEvents(student.getId(), eve.getStudentGroup().getId(), sDate,
	 * eDate);
	 * 
	 * selectedNdisEventsMap.put(eve.getStudentGroup().getId(), eve);
	 * 
	 * Double currentcommittedHours = 0.0; int count = 0; for
	 * (NdisCommittedEvent event : ndisEventsMap.get(eve
	 * .getStudentGroup().getId())) { if (programTypeId == 2) { //
	 * event.setPrice(eve.getPrice()); count++; } else { currentcommittedHours
	 * += event.getHours(); // committedHours += currentcommittedHours; } }
	 * eve.setUncommittedhours(currentcommittedHours);
	 * eve.setCommittedHours(0.0); eve.setCommittedUnits(0); committedHours =
	 * 0.0; eve.setUncommittedUnits(count); }
	 * selectedNdisEventsMapUncommit.clear(); }
	 * 
	 * private void uncommitEvents(Long studentId, Long groupId, Date fromDate,
	 * Date toDate) {
	 * serviceLocator.getNdisCommittedEventService().deleteSelectedEvents(
	 * studentId, groupId, fromDate, toDate); showInfo(
	 * "All the committed Events are uncommitted successfully for " +
	 * student.getContact().getFirstName() + " " +
	 * student.getContact().getLastName()); }
	 */

	/*
	 * private boolean validateDetails() { List<String> componentIds = new
	 * ArrayList<String>(); clearInputs(); if (programTypeId == 0) { showError(
	 * "Program Type can not be empty..");
	 * componentIds.add("select_ProgramTypes"); highlightInputs(componentIds);
	 * return false; } if (sDate == null) { showError(
	 * "Start Date  can not be empty..");
	 * componentIds.add("input_EventFromDate"); highlightInputs(componentIds);
	 * return false; } if (eDate == null) { showError(
	 * "End Date  can not be empty.."); componentIds.add("input_EventToDate");
	 * highlightInputs(componentIds); return false; } if (eDate.compareTo(sDate)
	 * < 0) { showError("Start date should be before the end date.."); return
	 * false; } return true; }
	 */
	/*
	 * public void GenerateContribution() { addContributionPopup(); }
	 * 
	 * public void addContributionPopup() { visibleContribution =
	 * !visibleContribution; }
	 * 
	 * public void AddLOS() { losPopup(); }
	 * 
	 * public void losPopup() { if (visibleLOS) { visibleLOS = false; } else {
	 * visibleLOS = true; } }
	 * 
	 * 
	 * NdisAncillaryCost Methods
	 * 
	 * 
	 * public void showNDISAncillaryPopup() { clearInputs();
	 * visibleAncillaryPopUp = !visibleAncillaryPopUp;
	 * 
	 * ndisSupportItem = new NdisSupportItem(); ndisPrice=new NdisPrice();
	 * 
	 * }
	 * 
	 * public void addNewNdisAncillaryCost() { clearInputs(); ndisAncillaryCost
	 * = new NdisAncillaryCost(); date = null; noofunit = 0; ndisSupportItemId =
	 * new Long(0); ndisPricesMap.clear(); ndisPriceId = new Long(0); uom =
	 * null; showNDISAncillaryPopup(); }
	 * 
	 * public void addNewNdisAncillary() { clearInputs(); ndisAncillaryCost =
	 * new NdisAncillaryCost(); date = null; noofunit = 0; ndisSupportItemId =
	 * new Long(0); ndisPriceId = new Long(0); uom = ""; }
	 * 
	 * public boolean saveNdisAncillaryCost() { if (validateNdisAncyCost()) {
	 * ndisAncillaryCost.setNdisPrice(ndisPricesMap.get(ndisPriceId)); if
	 * (ndisAncillaryCost.getId() == null) {
	 * ndisAncillaryCost.setNdisSupportItem(ndisSupportItem);
	 * ndisAncillaryCost.setStudent(student); //
	 * ndisAncillaryCost.setNdisPrice(ndisPrice); ndisAncillaryCost.setUom(uom);
	 * ndisAncillaryCost.setDate(date); ndisAncillaryCost.setNoofunit(noofunit);
	 * ndisAncillaryCost.setClaimed(false); ndisAncillaryCost = serviceLocator
	 * .getNdisAncillaryCostService() .create(ndisAncillaryCost);
	 * tempNdisAncillaryCost = ndisAncillaryCost; ndisAncillaryCost =
	 * serviceLocator .getNdisAncillaryCostService().retrieve(
	 * tempNdisAncillaryCost.getId()); showInfo(" added successfully..");
	 * 
	 * } else { ndisAncillaryCost.setNdisSupportItem(ndisSupportItem);
	 * ndisAncillaryCost.setStudent(student); //
	 * ndisAncillaryCost.setNdisPrice(ndisPrice); ndisAncillaryCost.setUom(uom);
	 * ndisAncillaryCost.setDate(date); ndisAncillaryCost.setNoofunit(noofunit);
	 * ndisAncillaryCost.setClaimed(false); ndisAncillaryCost = serviceLocator
	 * .getNdisAncillaryCostService() .update(ndisAncillaryCost);
	 * 
	 * tempNdisAncillaryCost = ndisAncillaryCost; ndisAncillaryCost =
	 * serviceLocator .getNdisAncillaryCostService().retrieve(
	 * tempNdisAncillaryCost.getId()); showInfo(" updated successfully.."); }
	 * allNdisAncillaryCosts = serviceLocator
	 * .getNdisAncillaryCostService().listNdisAncyCostByStudent(
	 * student.getId());
	 * 
	 * return true;
	 * 
	 * }
	 * 
	 * return false; }
	 * 
	 * private boolean validateNdisAncyCost() { List<String> componentIds = new
	 * ArrayList<String>(); clearInputs(); if (date == null) { showError(
	 * "Select a date"); componentIds.add("input_NDISAncillaryCostDate");
	 * highlightInputs(componentIds); return false; } if (ndisSupportItemId ==
	 * 0) { showError("Please select NDIS Support item");
	 * componentIds.add("select_NDISAncillaryItem");
	 * highlightInputs(componentIds); return false; } if (ndisPriceId == 0) {
	 * showError("Please select NDIS price item");
	 * componentIds.add("select_NDISprice"); highlightInputs(componentIds);
	 * return false; } if (noofunit == 0) { showError(
	 * "No of Unit can not be empty"); componentIds.add("input_NoofUnit");
	 * highlightInputs(componentIds); return false; } return true; }
	 * 
	 * public void saveAndExit() { if (saveNdisAncillaryCost())
	 * showNDISAncillaryPopup(); }
	 * 
	 * public void selectNdisItem(ValueChangeEvent event) { ndisSupportItemId =
	 * (Long) event.getNewValue(); ndisSupportItem =
	 * ndisItemMap.get(ndisSupportItemId); ndisPricesMap = new HashMap<Long,
	 * NdisPrice>(); for (NdisPrice price : serviceLocator.getNdisPriceService()
	 * .selectedNdisPrice(ndisSupportItemId)) { ndisPricesMap.put(price.getId(),
	 * price);
	 * 
	 * } ndisPriceId = new Long(0); uom = null;
	 * 
	 * }
	 * 
	 * public void selectNdisPriceItem(ValueChangeEvent event) { Long id =
	 * (Long) event.getNewValue(); if (id > 0) { uom =
	 * serviceLocator.getNdisPriceService().retrieve(id).getUom(); } else { uom
	 * = ""; } }
	 * 
	 * public void removeNdisAncyCost(ActionEvent ae) { ndisAncillaryCost =
	 * (NdisAncillaryCost) ae.getComponent()
	 * .getAttributes().get("ndisAncyCost");
	 * serviceLocator.getNdisAncillaryCostService().delete(
	 * ndisAncillaryCost.getId());
	 * allNdisAncillaryCosts.remove(ndisAncillaryCost); allNdisAncillaryCosts =
	 * serviceLocator.getNdisAncillaryCostService()
	 * .listNdisAncyCostByStudent(student.getId()); }
	 * 
	 * public void editNdisAncyCost(ActionEvent ae) { clearInputs();
	 * ndisAncillaryCost = (NdisAncillaryCost) ae.getComponent()
	 * .getAttributes().get("ndisAncyCost"); ndisSupportItem =
	 * ndisAncillaryCost.getNdisSupportItem(); date =
	 * ndisAncillaryCost.getDate(); noofunit = ndisAncillaryCost.getNoofunit();
	 * ndisAncyCoststudent = ndisAncillaryCost.getStudent(); ndisSupportItemId =
	 * ndisAncillaryCost.getNdisSupportItem().getId(); ndisSupportItem =
	 * ndisItemMap.get(ndisSupportItemId); ndisPricesMap = new HashMap<Long,
	 * NdisPrice>(); for (NdisPrice price : serviceLocator.getNdisPriceService()
	 * .selectedNdisPrice(ndisSupportItemId)) { ndisPricesMap.put(price.getId(),
	 * price); } ndisPrice = ndisAncillaryCost.getNdisPrice(); uom =
	 * ndisAncillaryCost.getUom(); if (ndisPrice != null) { ndisPriceId =
	 * ndisPrice.getId(); } allNdisAncillaryCosts =
	 * serviceLocator.getNdisAncillaryCostService()
	 * .listNdisAncyCostByStudent(student.getId()); showNDISAncillaryPopup(); }
	 */
	/*
	 * getters and setters
	 */

	public List<Student> getStudents() {
		return students;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Student getStudent() {
		return student;
	}

	public boolean isVisibleSpecialNeeds() {
		return visibleSpecialNeeds;
	}

	public void setSelectAllSpecialNeeds(boolean selectAllSpecialNeeds) {
		this.selectAllSpecialNeeds = selectAllSpecialNeeds;
	}

	public boolean isSelectAllSpecialNeeds() {
		return selectAllSpecialNeeds;
	}

	public List<SpecialNeed> getAvailableSpecialNeeds() {
		return availableSpecialNeeds;
	}

	public void setSearchStudentText(String searchStudentText) {
		this.searchStudentText = searchStudentText;
	}

	public String getSearchStudentText() {
		return searchStudentText;
	}

	public void setSelectedTabIndex(int selectedTabIndex) {
		this.selectedTabIndex = selectedTabIndex;
	}

	public int getSelectedTabIndex() {
		return selectedTabIndex;
	}

	public List<FundingSource> getFundingSources() {
		return fundingSources;
	}

	public boolean isVisibleFunding() {
		return visibleFunding;
	}

	public void setStudentFundingSrc(StudentFundingSource studentFundingSrc) {
		this.studentFundingSrc = studentFundingSrc;
	}

	public StudentFundingSource getStudentFundingSrc() {
		return studentFundingSrc;
	}

	public void setFundingSource(FundingSource fundingSource) {
		this.fundingSource = fundingSource;
	}

	public FundingSource getFundingSource() {
		return fundingSource;
	}

	public List<Contact> getAvailableGuardians() {
		return availableGuardians;
	}

	public String getSearchGuardianText() {
		return searchGuardianText;
	}

	public void setSearchGuardianText(String searchGuardianText) {
		this.searchGuardianText = searchGuardianText;
	}

	public Guardian getGuardian() {
		return guardian;
	}

	public boolean isVisibleGuardian() {
		return visibleGuardian;
	}

	public HashMap<Long, SpecialNeed> getSelectedSpecialNeeds() {
		return selectedSpecialNeeds;
	}

	public void setFundingHours(List<SelectItem> fundingHours) {
		this.fundingHours = fundingHours;
	}

	public List<SelectItem> getFundingHours() {
		return fundingHours;
	}

	public boolean isSelectAllGroups() {
		return selectAllGroups;
	}

	public void setSelectAllGroups(boolean selectAllGroups) {
		this.selectAllGroups = selectAllGroups;
	}

	public List<StudentGroup> getAvailableGroups() {
		return availableGroups;
	}

	public void setAvailableGroups(List<StudentGroup> availableGroups) {
		this.availableGroups = availableGroups;
	}

	public HashMap<Long, StudentGroup> getSelectedGroups() {
		return selectedGroups;
	}

	public boolean isVisibleGroups() {
		return visibleGroups;
	}

	public void setVisibleGroups(boolean visibleGroups) {
		this.visibleGroups = visibleGroups;
	}

	public void setSearchGroupText(String searchGroupText) {
		this.searchGroupText = searchGroupText;
	}

	public String getSearchGroupText() {
		return searchGroupText;
	}

	public int getPhotoH() {
		return photoH;
	}

	public int getPhotoW() {
		return photoW;
	}

	public void setDefaultAddress(boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public boolean isDefaultAddress() {
		return defaultAddress;
	}

	public void setSearchSNText(String searchSNText) {
		this.searchSNText = searchSNText;
	}

	public String getSearchSNText() {
		return searchSNText;
	}

	public boolean isVisibleTransportGroups() {
		return visibleTransportGroups;
	}

	public void setNewGuardian(boolean newGuardian) {
		this.newGuardian = newGuardian;
	}

	public boolean isNewGuardian() {
		return newGuardian;
	}

	public void setTmpStudent(Student tmpStudent) {
		this.tmpStudent = tmpStudent;
	}

	public Student getTmpStudent() {
		return tmpStudent;
	}

	public void setSelectedOutletId(Long selectedOutletId) {
		this.selectedOutletId = selectedOutletId;
	}

	public Long getSelectedOutletId() {
		return selectedOutletId;
	}

	public void setOutletSelectItems(List<SelectItem> outletSelectItems) {
		this.outletSelectItems = outletSelectItems;
	}

	public List<SelectItem> getOutletSelectItems() {
		return outletSelectItems;
	}

	public void setLocationSelectItems(List<SelectItem> locationSelectItems) {
		this.locationSelectItems = locationSelectItems;
	}

	public List<SelectItem> getLocationSelectItems() {
		return locationSelectItems;
	}

	public void setSelectedLocationId(Long selectedLocationId) {
		this.selectedLocationId = selectedLocationId;
	}

	public Long getSelectedLocationId() {
		return selectedLocationId;
	}

	public void setStudentGroup(StudentGroup studentGroup) {
		this.studentGroup = studentGroup;
	}

	public StudentGroup getStudentGroup() {
		return studentGroup;
	}

	public void setGroupedStudent(GroupedStudent groupedStudent) {
		this.groupedStudent = groupedStudent;
	}

	public GroupedStudent getGroupedStudent() {
		return groupedStudent;
	}

	public List<Consent> getConsentList() {
		return consentList;
	}

	public void setConsentList(List<Consent> consentList) {
		this.consentList = consentList;
	}

	public boolean isEditGuardian() {
		return editGuardian;
	}

	public void setAllGroups(boolean allGroups) {
		this.allGroups = allGroups;
	}

	public boolean isAllGroups() {
		return allGroups;
	}

	public List<ProgramEvent> getEvents() {
		return events;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/*
	 * public Date getToDate() { return toDate; }
	 * 
	 * public void setToDate(Date toDate) { this.toDate = toDate; }
	 */

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getJsonString() {
		return jsonString;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public SimpleDateFormat getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(SimpleDateFormat timeFormat) {
		this.timeFormat = timeFormat;
	}

	public void setGroupsForDays(HashMap<String, List<StudentGroup>> groupsForDays) {
		this.groupsForDays = groupsForDays;
	}

	public HashMap<String, List<StudentGroup>> getGroupsForDays() {
		return groupsForDays;
	}

	public void setOneRowTable(List<Object> oneRowTable) {
		this.oneRowTable = oneRowTable;
	}

	public List<Object> getOneRowTable() {
		return oneRowTable;
	}

	public String getSelectedStudentId() {
		return selectedStudentId;
	}

	public void setSelectedStudentId(String selectedStudentId) {
		this.selectedStudentId = selectedStudentId;
	}

	public void setSelectedRelationshipId(Long selectedRelationshipId) {
		this.selectedRelationshipId = selectedRelationshipId;
	}

	public Long getSelectedRelationshipId() {
		return selectedRelationshipId;
	}

	public List<SelectItem> getRelationships() {
		return relationships;
	}

	public List<Integer> getWizPages() {
		return wizPages;
	}

	public List<ProgramEvent> getActProEvents() {
		return actProEvents;
	}

	public boolean isVisibleActRecs() {
		return visibleActRecs;
	}

	public int getWizardPage() {
		return wizardPage;
	}

	public List<GroupedStudent> getActGroupedStudents() {
		return actGroupedStudents;
	}

	public void setSelectAllEvents(boolean selectAllEvents) {
		this.selectAllEvents = selectAllEvents;
	}

	public boolean isSelectAllEvents() {
		return selectAllEvents;
	}

	public String getNxtBtnTxt() {
		return nxtBtnTxt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}