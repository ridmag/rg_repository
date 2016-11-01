package com.itelasoft.pso.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.facelets.FaceletContext;
import javax.imageio.ImageIO;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.icesoft.faces.component.paneltabset.TabChangeEvent;
import com.itelasoft.pso.beans.EnumItemCategory;
import com.itelasoft.pso.beans.GroupedStaff;
import com.itelasoft.pso.beans.GroupedStaffWeekday;
import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.Location;
import com.itelasoft.pso.beans.NdisCommittedEvent;
import com.itelasoft.pso.beans.NdisPrice;
import com.itelasoft.pso.beans.NdisSupportItem;
import com.itelasoft.pso.beans.Program;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.beans.ReferenceItem;
import com.itelasoft.pso.beans.StaffEvent;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.StaffSkill;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentEvent;
import com.itelasoft.pso.beans.StudentFundingSource;
import com.itelasoft.pso.beans.StudentGroup;
import com.itelasoft.pso.beans.WeekDay;
import com.itelasoft.util.SortObjectByName;
import com.itelasoft.util.SortSelectItemsByLabel;

@ManagedBean(name = "programManagerModel")
@SessionScoped
public class ProgramManagerModel extends UIModel {
	private Program program, tmpProgram;
	private Location location;
	private ProgramEvent proEvent;
	private List<Program> programList;
	private Long selectedCoordinatorId;
	private Long selectedGroupId;
	private Long selectedProEventId;
	private Long selectedLocationId;
	private Long selectedNDISItemId;
	private Double selectedPrice, chargeAmount;
	private List<SelectItem> coordinatorSelectItems;
	private List<SelectItem> proEventSelectItems;
	private List<SelectItem> locationSelectItems;
	private List<SelectItem> NDISSelectItems;
	private List<SelectItem> groupSelectItems;
	private List<Student> availableStudents;
	private List<StaffMember> availableStaff, staffList;
	private List<ProgramEvent> availableEvents;
	private HashMap<Long, Student> enrolledStudents;
	private HashMap<Long, ProgramEvent> enrolledEvents;
	private boolean visibleEnrollStd, visibleEnrollStaff, visibleProEvents, visibleEditStd;
	private boolean selectAllStd, showAllStudents, showAllStudentsBtn;
	// private boolean selectAllStaff;
	private boolean visibleCheckStudentEvents;
	private boolean selectAllEvents;
	private String searchText, flag;
	private String searchProgramText;
	private String searchValue;
	private TimeBean startTime = new TimeBean();
	private TimeBean endTime = new TimeBean();
	private TimeBean actualStartTime = new TimeBean();;
	private TimeBean actualEndTime = new TimeBean();;
	private int selectedTabIndex, groupTabIndex;
	private List<String> selectedWeekDayIds, selectedStaffDays;
	private List<GroupedStaffWeekday> groupedStaffWeekdayLs;
	private String recurrenceType;
	private Date startDate;
	private Date endDate;
	private StudentGroup studentGroup, tmpStdGroup;
	private boolean setActualEventTimes;
	private String eventStatus;
	private boolean visibleStatusConfirmation, holidayWarning, inactiveSleepOverWaning;
	private String confirmationStatusMessage;
	private ProgramType programType;
	private List<ProgramType> proTypes;
	private GroupedStudent groupedStudent;
	private int photoH, photoW;
	private double eventChargeAmount;
	private StaffMember staffMember;
	private GroupedStaff groupedStaff;
	private boolean includeBreak;
	private SimpleDateFormat mydateFormat = new SimpleDateFormat("EEEE");
	private List<SelectItem> weekDays;
	// private NdisSupportItem ndisCluster;
	// private List<ReferenceItem> gst_CodeList;
	// private List<SelectItem> gstCodeList;
	// private String selectedGstCode;
	// private Long selectedGSTCodeId;
	// private static final String Active = "Active";
	// private InternalOrganization internalOrganization;
	// private List<InternalOrganization> intOrgList;
	private boolean visibleSkills = false;
	private List<ReferenceItem> availableSkills;
	private Map<Long, Boolean> skillsChecked = new HashMap<Long, Boolean>();
	// Assigned Staff skills flag (Staff member have the required skills or not)
	private Map<String, Boolean> staffFlag = new HashMap<String, Boolean>();
	private Map<String, String> staffMissingSkills = new HashMap<String, String>();
	// private Long groupid;
	private Double clusterPrice;
	private boolean showCommittedEvent, ndisChange;
	private List<NdisCommittedEvent> committedEvents;
	private boolean showExcludeStdPopup;
	private Long selectedProType;

	public ProgramManagerModel() {
		initStaffPrograms();
		initStudentPrograms();
		showExcludeStdPopup = ndisChange = false;
	}

	public void initStudentPrograms() {
		programType = serviceLocator.getProgramTypeService().retrieveByName("Student");
		ProgramType individual = serviceLocator.getProgramTypeService().retrieveByName("Individual");
		proTypes = new ArrayList<ProgramType>();
		if (individual != null)
			proTypes.add(individual);
		if (programType != null) {
			proTypes.add(programType);
			programList = serviceLocator.getProgramService().listByCriteria(null, proTypes, null, false);
		} else {
			programList = null;
			showError("There is an error with the program types. Please contact the system addministrator.");
			return;
		}
		selectedProType = new Long(0);
		showCommittedEvent = false;
		holidayWarning = inactiveSleepOverWaning = true;
		init();
	}

	public void initStaffPrograms() {
		List<ProgramType> types = new ArrayList<ProgramType>();
		programType = serviceLocator.getProgramTypeService().retrieveByName("Staff");
		if (programType != null) {
			types.add(programType);
			programList = serviceLocator.getProgramService().listByCriteria(null, types, null, false);
		} else {
			programList = null;
			showError("There is an error with the program types. Please contact the system addministrator.");
			return;
		}
		init();
	}

	private void init() {
		program = tmpProgram = null;
		proEvent = null;
		NDISSelectItems = new ArrayList<SelectItem>();
		selectedGroupId = selectedProEventId = null;
		visibleEnrollStd = visibleProEvents = visibleCheckStudentEvents = visibleEnrollStaff = visibleStatusConfirmation = visibleProEvents = false;
		selectedTabIndex = 0;
		searchProgramText = "";
		searchValue = "name";
		selectedPrice = 0.00;
		studentGroup = null;
		availableStaff = serviceLocator.getStaffMemberService().listActiveStaffMembers();
		locationSelectItems = new ArrayList<SelectItem>();
		List<Location> locations = serviceLocator.getLocationService().findAll();
		if (locations != null && !locations.isEmpty()) {
			for (Location loc : locations) {
				if (loc.getName().equals("Home"))
					continue;
				locationSelectItems.add(new SelectItem(loc.getId(), loc.getName()));
			}
			Collections.sort(locationSelectItems, new SortSelectItemsByLabel());
			locationSelectItems.add(0, new SelectItem(0, "Not Assigned"));
		} else {
			locationSelectItems.add(new SelectItem(0, "Not Available"));
		}
	}

	public void tabChangeListner(TabChangeEvent event) {
		if (program != null) {
			if (event.getOldTabIndex() == 0) {
				if (validateProgramFields())
					selectedTabIndex = event.getNewTabIndex();
				else
					selectedTabIndex = 0;
			} else {
				selectedTabIndex = event.getNewTabIndex();
			}
			if (selectedTabIndex == 0) {
				loadCoordinators();
				if (program.getCoordinator() != null)
					selectedCoordinatorId = program.getCoordinator().getId();
				else
					selectedCoordinatorId = new Long(0);
			} else if (selectedTabIndex == 1) {
				if (selectedGroupId != null && selectedGroupId != 0)
					selectStudentGroup(selectedGroupId);
			} else if (selectedTabIndex == 2) {
				refreshProEventsSelectItems();
				if (proEvent != null) {
					if (selectedGroupId != null && proEvent.getGroup().getId().equals(selectedGroupId)) {
						loadProgramEvent();
					} else {
						selectedProEventId = null;
						proEvent = null;
					}
				}
			}
		} else
			showError("Please select a cost center.");
	}

	public void searchPrograms() {
		List<ProgramType> types = new ArrayList<ProgramType>();
		if (selectedProType != 0) {
			ProgramType type = new ProgramType(selectedProType);
			types.add(type);
		} else {
			types.addAll(proTypes);
		}
		// System.out.println(selectedType);
		try {
			// See if the user has entered an ID instead name
			Long id = Long.parseLong(searchProgramText);
			Program pro = serviceLocator.getProgramService().retrieveById(id, types);
			if (pro == null)
				showError("No result available for this Id.");
			else {
				programList.clear();
				programList.add(pro);
				tmpProgram = program = null;
				selectedTabIndex = 0;
			}
		} catch (NumberFormatException nFE) {
			// User has entered a name
			List<Program> programs = serviceLocator.getProgramService().listByCriteria(searchProgramText, types, null,
					false);
			if (programs == null || programs.isEmpty())
				showError("No results for the given search text.");
			else {
				programList = programs;
				tmpProgram = program = null;
				selectedTabIndex = 0;
			}
		} catch (Exception exception) {
			showExceptionAsError(exception);
		}
	}

	public void newProgram(ActionEvent ae) {
		String type = (String) ae.getComponent().getAttributes().get("programType");
		program = new Program();
		if (type != null && type.equals("individual")) {
			ProgramType tmpType = serviceLocator.getProgramTypeService().retrieveByName("Individual");
			if (tmpType == null) {
				showError("There is a problem with individual program type. Please contact system administrator.");
				return;
			}
			program.setType(tmpType);
		} else
			program.setType(programType);
		if (tmpProgram != null)
			tmpProgram.setUi_selected(false);
		selectedTabIndex = 0;
		selectedCoordinatorId = new Long(0);
		// refreshStudentGroupsSelectItems();
		proEvent = null;
		studentGroup = null;
		selectedProEventId = selectedGroupId = null;
		setActiveProgram();
	}

	public void selectProgram(ClickActionEvent re) {
		clearInputs();
		if (tmpProgram != null)
			tmpProgram.setUi_selected(false);
		tmpProgram = (Program) re.getComponent().getAttributes().get("program");
		tmpProgram.setUi_selected(true);
		program = serviceLocator.getProgramService().retrieve(tmpProgram.getId());
		if (program.getCoordinator() != null)
			selectedCoordinatorId = program.getCoordinator().getId();
		else
			selectedCoordinatorId = Long.valueOf(0);
		proEvent = null;
		studentGroup = null;
		selectedProEventId = selectedGroupId = null;
		groupTabIndex = 0;
		setActiveProgram();
		ndisChange = false;
	}

	public void saveProgram(ActionEvent event) {
		if (validateProgramFields()) {
			StaffMember staff = null;
			if (selectedCoordinatorId > 0)
				staff = serviceLocator.getStaffMemberService().retrieve(selectedCoordinatorId);
			if (staff != null && !staff.getStatus().equals("Current")) {
				confirmationStatusMessage = "Cordinator of this Cost Center is not a current staff member. Are you sure you want to continue?";
				visibleStatusConfirmation = true;
			} else {
				program.setCoordinator(staff);
				saveProgram();
			}
		}
	}

	public void saveProgram() {
		if (program.getId() == null) {
			program = serviceLocator.getProgramService().create(program);
			tmpProgram = program;
			// for not to update table data if user have done any changes to
			// program
			program = serviceLocator.getProgramService().retrieve(program.getId());
			tmpProgram.setUi_selected(true);
			programList.add(tmpProgram);
			showInfo("New Cost Center added successfully.");
		} else {
			program = serviceLocator.getProgramService().update(program);
			program.setUi_selected(true);
			programList.set(programList.indexOf(tmpProgram), program);
			tmpProgram = program;
			// for not to update table data if user have done any changes to
			// program
			program = serviceLocator.getProgramService().retrieve(program.getId());
			showInfo("Cost Center saved successfully.");
		}
		setActiveProgram();
	}

	public void deleteProgram() {
		try {
			serviceLocator.getProgramService().delete(program.getId());
			programList.remove(tmpProgram);
			showInfo("Cost Center deleted successfully.");
			program = tmpProgram = null;
			setActiveProgram();
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public void purgeProgram() {
		try {
			serviceLocator.getProgramService().deleteWithDependencies(program.getId());
			programList.remove(tmpProgram);
			showInfo("Cost Center and all it's dependencies are purged successfully.");
			program = tmpProgram = null;
			setActiveProgram();
		} catch (Exception d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	private void setActiveProgram() {
		if (program != null) {
			if (program.getType().getName().equals("Student")) {
				createNdisList("DayProgram");
			}
			if (program.getType().getName().equals("Individual")) {
				createNdisList("Individual");
			}
			if (program.getId() != null) {
				sessionContext.setActiveString(program.getName());
				refreshStudentGroupsSelectItems();
			} else
				sessionContext.setActiveString("New-Program");
			loadCoordinators();
		} else
			sessionContext.setActiveString("");
	}

	private void createNdisList(String programType) {
		NDISSelectItems = new ArrayList<SelectItem>();
		List<NdisSupportItem> supportitems = serviceLocator.getNdisSupportItemService().listByType(programType);
		if (supportitems != null && !supportitems.isEmpty()) {
			for (NdisSupportItem item : supportitems) {
				NDISSelectItems.add(new SelectItem(item.getId(), item.getItemName()));
			}
			Collections.sort(NDISSelectItems, new SortSelectItemsByLabel());
			NDISSelectItems.add(0, new SelectItem(0, "Not Assigned"));
		} else {
			NDISSelectItems.add(new SelectItem(0, "Not Available"));
		}
	}

	private boolean validateProgramFields() {
		List<String> componentIds = new ArrayList<String>();
		clearInputs();
		if (program.getName() == null || program.getName().isEmpty()) {
			componentIds.add("input_ProgramName");
			highlightInputs(componentIds);
			showError("Cost Center name can not be empty.");
			return false;
		}/*
		if (!program.getType().getName().equals("Staff")) {
			if (program.getChargeAmount() < 0) {
				showError("Invalid charge amount.");
				componentIds.add("input_ProgramAmount");
				highlightInputs(componentIds);
				return false;
			}
		}*/
		if (program.getStartDate() == null) {
			showError("Start date is required");
			componentIds.add("input_ProgramStartDate");
			highlightInputs(componentIds);
			return false;
		}
		if (program.getEndDate() == null) {
			showError("End date is required");
			componentIds.add("input_ProgramEndDate");
			highlightInputs(componentIds);
			return false;
		}
		if (!validateDates(program.getStartDate(), program.getEndDate())) {
			componentIds.add("input_ProgramStartDate");
			componentIds.add("input_ProgramEndDate");
			highlightInputs(componentIds);
			return false;
		}
		return true;
	}

	private boolean validateDates(Date startDate, Date endDate) {
		if (startDate != null && endDate != null) {
			if (endDate.compareTo(startDate) < 0) {
				showError("Start date should be before the end date.");
				return false;
			} else {
				return true;
			}
		}
		return true;
	}

	private void loadCoordinators() {
		coordinatorSelectItems = new ArrayList<SelectItem>();
		List<StaffMember> tmpList = new ArrayList<StaffMember>();
		tmpList.addAll(availableStaff);
		if (selectedTabIndex == 0 && program.getCoordinator() != null
				&& !program.getCoordinator().getStatus().equals("Current")) {
			tmpList.add(program.getCoordinator());
		} else if (selectedTabIndex == 2) {
			if (proEvent != null && proEvent.getCoordinator() != null) {
				boolean found = false;
				for (StaffMember staff : tmpList) {
					if (staff.getId().equals(proEvent.getCoordinator().getId())) {
						found = true;
						break;
					}
				}
				if (!found)
					tmpList.add(proEvent.getCoordinator());
			}
		}
		if (!tmpList.isEmpty()) {
			for (StaffMember member : tmpList) {
				coordinatorSelectItems.add(new SelectItem(member.getId(), member.getContact().getName()));
			}
			Collections.sort(coordinatorSelectItems, new SortSelectItemsByLabel());
			coordinatorSelectItems.add(0, new SelectItem(0, "Not Assigned"));
		} else {
			coordinatorSelectItems.add(new SelectItem(0, "Not Available"));
		}
	}

	public void confirmationStatusAction(ActionEvent ae) {
		visibleStatusConfirmation = false;
		String action = (String) ae.getComponent().getAttributes().get("confirmation");
		if (action.equals("Yes")) {
			if (proEvent != null)
				saveProgramEvent();
			else
				saveProgram();
		}
	}

	/*
	 * Group Functions
	 */

	public void addNewGroup() {
		clearInputs();
		if (program.getStatus().equals("Inactive"))
			showError("Operation not allowed. Cost Center status marked as inactive..");
		else {
			selectedGroupId = null;
			groupTabIndex = 0;
			selectedLocationId = new Long(0);
			selectedNDISItemId = new Long(0);
			studentGroup = new StudentGroup();
			startTime = new TimeBean();
			endTime = new TimeBean();
			setActualEventTimes = false;
			studentGroup.setStartDate(program.getStartDate());
			studentGroup.setEndDate(program.getEndDate());
			studentGroup.setProgram(program);
			studentGroup.setcAmount(null);
			//studentGroup.setChargeAmount(studentGroup.getProgram().getChargeAmount());
			selectedWeekDayIds = new ArrayList<String>();
			studentGroup.setStaffMembers(new ArrayList<GroupedStaff>());
			recurrenceType = "Weekly";
			ndisChange = false;
		}
	}

	public void selectStudentGroup(ValueChangeEvent ve) {
		clearInputs();
		Long id = (Long) ve.getNewValue();
		selectedGroupId = id;
		if (id != null && id != 0) {
			selectStudentGroup(id);
		}
	}

	private void selectStudentGroup(Long groupId) {
		showAllStudents = setActualEventTimes = showAllStudentsBtn = false;
		tmpStdGroup = serviceLocator.getStudentGroupService().retrieveWithData(groupId,true);
		studentGroup = serviceLocator.getStudentGroupService().retrieveWithData(groupId, true);
		ndisChange = false;
		// duplicating excluded students from list
		studentGroup.setAvailableStudents(new ArrayList<GroupedStudent>());
		for (GroupedStudent groupedStudent : studentGroup.getStudents()) {
			if (groupedStudent.getStatus().equals("Excluded")) {
				groupedStudent.setUi_selected(true);
			} else {
				studentGroup.getAvailableStudents().add(groupedStudent);
			}
		}
		// studentGroup.getStudents().removeAll(studentGroup.getExcludedStudents());
		// setting Staff Member skills flag
		this.checkStaffSkills();
		if (studentGroup.getStartTime() != null)
			startTime = new TimeBean(studentGroup.getStartTime());
		else
			startTime = new TimeBean();
		if (studentGroup.getEndTime() != null)
			endTime = new TimeBean(studentGroup.getEndTime());
		else
			endTime = new TimeBean();
		if (studentGroup.getLocation() != null)
			selectedLocationId = studentGroup.getLocation().getId();
		else
			selectedLocationId = new Long(0);
		if (studentGroup.getNdis() != null) {
			selectedNDISItemId = studentGroup.getNdis().getId();
		} else {
			selectedNDISItemId = new Long(0);
		}
		selectedWeekDayIds = new ArrayList<String>();
		if (!studentGroup.getWeekDays().isEmpty()) {
			recurrenceType = "Weekly";
			for (WeekDay day : studentGroup.getWeekDays()) {
				selectedWeekDayIds.add(day.getId().toString());
			}
		} else {
			recurrenceType = "Daily";
		}
		studentGroup.setcAmount(String.valueOf(studentGroup.getChargeAmount()));
		initPhotoImage();
	}

	public void editStudent(ActionEvent ae) {
		groupedStudent = (GroupedStudent) ae.getComponent().getAttributes().get("student");
		chargeAmount = groupedStudent.getChargeAmount();
		editStudentPopup();
	}

	public void changeAmount() {
		if (chargeAmount < 0)
			showError("Invalid Charge Amount");
		groupedStudent.setChargeAmount(chargeAmount);
		serviceLocator.getGroupedStudentService().update(groupedStudent);
		editStudentPopup();

	}

	// selectStudentGroup to view Excluded students
	public void showHideExcluded() {
		if (showAllStudentsBtn || showAllStudents) {
			studentGroup.setAvailableStudents(new ArrayList<GroupedStudent>());
			for (GroupedStudent gs : studentGroup.getStudents()) {
				if (!gs.isUi_selected())
					studentGroup.getAvailableStudents().add(gs);
			}
			showAllStudentsBtn = showAllStudents = false;
		} else
			showAllStudentsBtn = showAllStudents = !showAllStudents;
	}

	public void saveStudentGroup() {
		if (validateStudentGroup()) {
			if (studentGroup.getId() != null) {
				committedEvents = serviceLocator.getNdisCommittedEventService()
						.ndisCommittedEventsListByGroup(studentGroup.getId());
				if (!committedEvents.isEmpty() && compareStdGroupValues() || ndisChange == true) {
					showCommittedEventPopUp();
				} else {
					if (!studentGroup.getWeekDays().isEmpty()) {
						List<WeekDay> notAssignedList = listNotAssignedWeekDays(selectedWeekDayIds);
						List<WeekDay> removeList = new ArrayList<WeekDay>();
						for (GroupedStaff stf : studentGroup.getStaffMembers()) {
							for (GroupedStaffWeekday groupedday : stf.getAssignedDays()) {
								WeekDay day = groupedday.getWeekday();
								if (notAssignedList.contains(day)) {
									removeList.add(day);
								}
							}
							if (!removeList.isEmpty())
								stf.getAssignedDays().removeAll(removeList);
						}
					}
					studentGroup = serviceLocator.getStudentGroupService().update(studentGroup);
					if (programType.getName().equals("Staff")) {
						showInfo("Staff program saved successfully.");
					} else {
						showInfo(Util.getMessage("student_label") + " program saved successfully..");
					}
					showInfo(
							"The changes doesn't affect to the existing events. Please goto event manager and update them if neccessary..");
					refreshStudentGroupsSelectItems();
				}
			} else {
				studentGroup = serviceLocator.getStudentGroupService().create(studentGroup);
				groupSelectItems.add(new SelectItem(studentGroup.getId(), studentGroup.getName()));
				Collections.sort(groupSelectItems, new SortSelectItemsByLabel());
				if (programType.getName().equals("Staff"))
					showInfo("New staff group created successfully..");
				else
					showInfo("New " + Util.getMessage("student_label").toLowerCase()
							+ " program created successfully..");
				selectedGroupId = studentGroup.getId();
			}
		}
	}

	/*
	 * public boolean checkStdGroupDays() { Set<String> noDays = new
	 * TreeSet<String>(); List<WeekDay> days = studentGroup.getWeekDays();
	 * 
	 * boolean check = true; NdisSupportItem item =
	 * serviceLocator.getNdisSupportItemService().retrieve(selectedNDISItemId);
	 * List<NdisPrice> prices = item.getNdisPrice(); for (WeekDay weekDay :
	 * days) {
	 * 
	 * if (!prices.isEmpty()) { for (NdisPrice price : prices) { if
	 * (weekDay.getName().equals("Saturday")) { if
	 * (price.getNdisTime().equals("Saturday")) { noDays.remove("Saturday");
	 * break;
	 * 
	 * } else { studentGroup.getWeekDays().remove(weekDay.getId());
	 * noDays.add("Saturday"); continue; }
	 * 
	 * } else if (weekDay.getName().equals("Sunday")) { if
	 * (price.getNdisTime().equals("Sunday")) { noDays.remove("Sunday"); break;
	 * } else { studentGroup.getWeekDays().remove(weekDay.getId());
	 * noDays.add("Sunday"); continue; }
	 * 
	 * } else { if (price.getNdisTime().equals("Weekday Evening") ||
	 * price.getNdisTime().equals("Weekday Daytime") ||
	 * price.getNdisTime().equals("Weekday Overnight Active")) {
	 * noDays.remove(weekDay.getName()); break;
	 * 
	 * } else { studentGroup.getWeekDays().remove(weekDay.getId());
	 * noDays.add(weekDay.getName()); continue; } } }
	 * 
	 * } else { showError("Selected Ndis Support Cluster does not have prices.."
	 * ); return false; } } if (noDays.isEmpty()) { check = true; } else { for
	 * (String day : noDays) { showError("There is no ndis price for " + day +
	 * " for selected Ndis cluster"); } check = false; noDays.clear(); }
	 * 
	 * return check; }
	 */

	public void deleteStudentGroup() {
		try {
			serviceLocator.getStudentGroupService().delete(studentGroup.getId());
			if (programType.getName().equals("Staff"))
				showInfo("Staff program deleted successfully.");
			else
				showInfo(Util.getMessage("student_label") + " program deleted successfully.");
			for (SelectItem item : groupSelectItems) {
				if (item.getValue() == selectedGroupId) {
					groupSelectItems.remove(item);
					break;
				}
			}
			selectedGroupId = null;
			studentGroup = null;
			// refreshStudentGroupsSelectItems();
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
		refreshStudentGroupsSelectItems();
	}

	private void refreshStudentGroupsSelectItems() {
		groupSelectItems = new ArrayList<SelectItem>();
		List<StudentGroup> groups = serviceLocator.getStudentGroupService().listByProgram(program.getId());
		if (groups != null && !groups.isEmpty()) {
			for (StudentGroup group : groups) {
				groupSelectItems.add(new SelectItem(group.getId(), group.getName()));
			}
			Collections.sort(groupSelectItems, new SortSelectItemsByLabel());
		}
	}

	public void initPhotoImage() {
		byte[] tmpData = null;
		if (studentGroup.getPhoto() != null) {
			tmpData = studentGroup.getPhoto().getBlobFileData().getData();
			if (tmpData != null) {
				BufferedImage loadImg;
				try {
					loadImg = ImageIO.read(new ByteArrayInputStream(tmpData));
					int w = loadImg.getWidth();
					int h = loadImg.getHeight();
					if (w > h) {
						photoW = 150;
						photoH = photoW * h / w;
					} else {
						photoH = 100;
						photoW = photoH * w / h;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean validateStudentGroup() {
		List<String> componentIds = new ArrayList<String>();
		clearInputs();
		if (studentGroup.getName() == null || studentGroup.getName().isEmpty()) {
			showError(Util.getMessage("student_label") + " group name shouldn't be empty.");
			if (programType.getName().equals("Student")) {
				componentIds.add("input_textStudentGroupName");
			} else {
				componentIds.add("input_StaffGroupName");
			}
			highlightInputs(componentIds);
			return false;
		} else if (!serviceLocator.getStudentGroupService().validateGroupName(studentGroup)) {
			showError(Util.getMessage("student_label") + " group name is already exists.");
			if (programType.getName().equals("Student")) {
				componentIds.add("input_textStudentGroupName");
			} else {
				componentIds.add("input_StaffGroupName");
			}
			highlightInputs(componentIds);
			return false;
		}
		if (!programType.getName().equals("Staff")) {
			if (studentGroup.getcAmount() == null || studentGroup.getcAmount().isEmpty()) {
				showError("Charge amount can't be empty.");
				return false;
			}
			if (studentGroup.getcAmount() != null && !studentGroup.getcAmount().isEmpty()) {
				try {
					studentGroup.setChargeAmount(Double.parseDouble(studentGroup.getcAmount()));
				} catch (NumberFormatException e) {
					showError("Invalide charge amount.");
					return false;
				}
			}
			if (studentGroup.getChargeAmount() < 0) {
				showError("Charge amount can't be negative value.");
				return false;
			} 
		}
		if (studentGroup.getStartDate() == null) {
			if (programType.getName().equals("Student")) {
				componentIds.add("input_groupStartDate");
			} else {
				componentIds.add("input_staffGroupStartDate");
			}
			showError("Start Date can't be empty.");
			highlightInputs(componentIds);
			return false;
		}
		if (studentGroup.getEndDate() == null) {
			if (programType.getName().equals("Student")) {
				componentIds.add("input_groupEndDate");
			} else {
				componentIds.add("input_staffGroupEndDate");
			}
			showError("End Date can't be empty.");
			highlightInputs(componentIds);
			return false;
		}
		if (studentGroup.getStartDate().compareTo(program.getStartDate()) < 0
				|| studentGroup.getStartDate().compareTo(program.getEndDate()) > 0) {
			showError(
					"Invalid start date! Program Start Date does not match with the Cost center start date/end date.");
			return false;
		}
		if (studentGroup.getEndDate().compareTo(program.getEndDate()) > 0) {
			showError("Invalid End date! Program End Date does not match with the Cost center start end date.");
			return false;
		}
		if (!validateDates(studentGroup.getStartDate(), studentGroup.getEndDate())) {
			return false;
		}
		if (selectedLocationId != 0) {
			studentGroup.setLocation(serviceLocator.getLocationService().retrieve(selectedLocationId));
		} else {
			showError("Please select a Location.");
			componentIds.add("select_GroupLocation");
			highlightInputs(componentIds);
			return false;
		}

		if (startTime.getHours() < 0) {
			showError("Start time can not be empty.");
			if (programType.getName().equals("Student")) {
				componentIds.add("select_grpStartTime_hrs");
				componentIds.add("select_grpStartTime_hrs1");
				componentIds.add("select_grpStartTime_mins");
				componentIds.add("select_grpStartTime_session");
			} else {
				componentIds.add("input_StaffStartTime");
			}
			highlightInputs(componentIds);
			return false;
		}
		if (endTime.getHours() < 0) {
			showError("End time can not be empty.");
			if (programType.getName().equals("Student")) {
				componentIds.add("select_grpEndTime_hrs");
				componentIds.add("select_grpEndTime_hrs1");
				componentIds.add("select_grpEndTime_mins");
				componentIds.add("select_grpEndTime_session");
			} else {
				componentIds.add("input_StaffEndTime");
			}
			highlightInputs(componentIds);
			return false;
		}
		if (!validateAndSetTime()) {
			return false;
		}
		if (programType.getName().equals("Staff") && studentGroup.isLunchIncluded()) {
			if (studentGroup.getLunchTime() == null || studentGroup.getLunchTime().isEmpty()) {
				showError("Lunch Break Cannot be empty");
				return false;
			} else {
				try {
					Integer time = Integer.parseInt(studentGroup.getLunchTime());
					if (time == 0) {
						showError("Lunch Break Time Cannot be zero");
						return false;
					}
				} catch (Exception e) {
					showError("Lunch Break Time must be a numeric value");
					return false;
				}
			}
		}
		studentGroup.setWeekDays(new ArrayList<WeekDay>());
		List<String> tmpWeekIds = new ArrayList<String>();
		if (recurrenceType.equals("Weekly")) {
			if (!selectedWeekDayIds.isEmpty()) {
				for (String id : selectedWeekDayIds) {
					tmpWeekIds.add(id);
					// studentGroup.getWeekDays().add(new
					// WeekDay(Long.valueOf(id)));
					studentGroup.getWeekDays().add(serviceLocator.getWeekDayService().retrieve(new Long(id)));
				}
			} else {
				showError("At least one week day should be selected.");
				return false;
			}
		} else {
			for (int i = 1; i < 8; i++) {
				tmpWeekIds.add(String.valueOf(i));
			}
		}
		if ((studentGroup.getProgram().getType().getName().equals("Student")
				|| studentGroup.getProgram().getType().getName().equals("Individual")) && selectedNDISItemId != 0) {
			return validateGroupWithCluster(tmpWeekIds);
		}

		if ((studentGroup.getProgram().getType().getName().equals("Individual")
				&& studentGroup.isAllowProgramtorunonaholiday()) && selectedNDISItemId != 0) {
			return checkHoliday();
		}
		if (studentGroup.getProgram().getType().getName().equals("Individual") && studentGroup.isInactiveovernight()
				&& selectedNDISItemId != 0) {
			return checkInactiveSleepOver();
		}
		return true;

	}

	private boolean validateAndSetTime() {
		if (studentGroup.getProgram().getType().getName().equals("Individual")) {
			studentGroup.setStartTime(startTime.getDateTime(new Date()));
			studentGroup.setEndTime(endTime.getDateTime(new Date()));
			return true;
		}
		GregorianCalendar calEndTime = new GregorianCalendar();
		calEndTime.setTime(endTime.getDateTime(new Date()));
		GregorianCalendar calStartTime = new GregorianCalendar();
		calStartTime.setTime(startTime.getDateTime(new Date()));
		if (calEndTime.getTimeInMillis() > calStartTime.getTimeInMillis()) {
			studentGroup.setStartTime(startTime.getDateTime(new Date()));
			studentGroup.setEndTime(endTime.getDateTime(new Date()));
			return true;
		}
		showError("Invalid time. End time should be after the start time");
		return false;
	}

	/*
	 * GroupedStudents Functions
	 */

	public void enrollNewStudents() {
		if (program.getStatus().equals("Inactive")) {
			showError("Operation not allowed. Program status marked as inactive..");
			return;
		}
		if (program.getType().getName().equals("Individual") && studentGroup.getStudents() != null
				&& studentGroup.getStudents().size() >= 1) {
			showError("Operation not allowed. The program is an indivudual program.");
			showError("Only one " + Util.getMessage("student_label").toLowerCase()
					+ " can be assigned with individual programs.");
			return;
		}
		searchText = "";
		availableStudents = serviceLocator.getStudentService().listAvailableByGroup(studentGroup.getId(), searchText);
		if (availableStudents == null || availableStudents.isEmpty()) {
			showError("There are no new " + Util.getMessage("students_label").toLowerCase()
					+ " available for this program.");
		} else {
			selectAllStd = false;
			enrolledStudents = new HashMap<Long, Student>();
			enrollStudentPopup();
			// showInfo("Student(s) without a active funding source will not be
			// listed..");
		}
	}

	public void searchStudents() {
		availableStudents = new ArrayList<Student>();
		enrolledStudents.clear();
		selectAllStd = false;
		try {
			// See if the user has entered an ID instead name
			Long id = Long.parseLong(searchText);
			Student student = serviceLocator.getStudentService().retrieveAvailableByGroup(studentGroup.getId(), id);
			if (student == null || student.getStatus().equals("Inactive"))
				showError("No " + Util.getMessage("student_label").toLowerCase() + " available for this student id.");
			else
				availableStudents.add(student);
		} catch (NumberFormatException nFE) {
			// User has entered a name
			availableStudents = serviceLocator.getStudentService().listAvailableByGroup(studentGroup.getId(),
					searchText);
			if (availableStudents == null || availableStudents.isEmpty())
				showError("No results found for the given search text.");
		} catch (Exception exception) {
			showExceptionAsError(exception);
		}
	}

	public void selectStudent(ValueChangeEvent ve) {
		Student student = (Student) ve.getComponent().getAttributes().get("student");
		Boolean selected = (Boolean) ve.getNewValue();
		student.setUi_selected(selected);
		if (selected) {
			student.setChargeAmount(studentGroup.getChargeAmount());
			if (program.getType().getName().equals("Individual")) {
				for (Student std : enrolledStudents.values()) {
					std.setUi_selected(false);
				}
				enrolledStudents.clear();
			}
			enrolledStudents.put(student.getId(), student);
			if (enrolledStudents.values().containsAll(availableStudents))
				selectAllStd = true;
		} else {
			selectAllStd = false;
			enrolledStudents.remove(student.getId());
		}
	}

	public void selectAllStudents(ValueChangeEvent ve) {
		Boolean selected = (Boolean) ve.getNewValue();
		for (Student std : availableStudents) {
			std.setUi_selected(selected);
			if (selected) {
				std.setChargeAmount(studentGroup.getChargeAmount());
				enrolledStudents.put(std.getId(), std);
			} else
				enrolledStudents.remove(std.getId());
		}
	}

	public void saveEnrolledStudents() {
		if (!enrolledStudents.isEmpty()) {
			if (studentGroup.getStudents() == null)
				studentGroup.setStudents(new ArrayList<GroupedStudent>());
			if (studentGroup.getAvailableStudents() == null)
				studentGroup.setAvailableStudents(new ArrayList<GroupedStudent>());
			for (Student std : enrolledStudents.values()) {
				GroupedStudent grostu = new GroupedStudent();
				grostu.setStudent(std);
				grostu.setGroup(studentGroup);
				grostu.setStatus("Active");
				grostu.setChargeAmount(std.getChargeAmount());
				studentGroup.getStudents().add(grostu);
				studentGroup.getAvailableStudents().add(grostu);
			}
			studentGroup = serviceLocator.getStudentGroupService().update(studentGroup);
			Collections.sort(studentGroup.getStudents(), new SortObjectByName());
			Collections.sort(studentGroup.getAvailableStudents(), new SortObjectByName());
			showInfo("Selected " + Util.getMessage("student_label").toLowerCase()
					+ "(s) enrolled with the group successfully.");
		}
		enrollStudentPopup();
	}

	public void removeStudent(ActionEvent ae) {
		groupedStudent = (GroupedStudent) ae.getComponent().getAttributes().get("student");
		if (groupedStudent != null) {
			groupedStudent.setTmpString("delete");
			if (committedEventsExist(groupedStudent, new Date())) {
				showExcludeStdPopup = true;
			} else {
				int tmpIndex = 0, index = studentGroup.getStudents().indexOf(groupedStudent);
				if (!showAllStudents) {
					tmpIndex = studentGroup.getAvailableStudents().indexOf(groupedStudent);
				}
				studentGroup.getStudents().remove(groupedStudent);
				studentGroup.getAvailableStudents().remove(groupedStudent);
				try {
					studentGroup = serviceLocator.getStudentGroupService().update(studentGroup);
					showInfo("Selected " + Util.getMessage("student_label").toLowerCase()
							+ " has been removed from the program successfully.");
				} catch (DataIntegrityViolationException d) {
					showError(Util.getMessage("error.integrity"));
					studentGroup.getStudents().add(index, groupedStudent);
					if (!showAllStudents) {
						studentGroup.getAvailableStudents().add(tmpIndex, groupedStudent);
					}
					// studentGroup.setStudents(serviceLocator.getGroupedStudentService()
					// .listByGroup(studentGroup.getId()));
				}
				if (showAllStudentsBtn) {
					for (GroupedStudent gs : studentGroup.getAvailableStudents()) {
						if (gs.isUi_selected())
							return;
					}
					showAllStudentsBtn = false;
				}
			}
		}
	}

	public void excludeStudent(ValueChangeEvent vce) {
		Boolean exclude = (Boolean) vce.getNewValue();
		groupedStudent = (GroupedStudent) vce.getComponent().getAttributes().get("student");
		if (exclude != null && groupedStudent != null) {
			int index = 0;
			for (GroupedStudent gs : studentGroup.getStudents()) {
				if (groupedStudent.getId() == gs.getId())
					index = studentGroup.getStudents().indexOf(gs);
			}
			if (exclude) {
				groupedStudent.setTmpString("exclude");
				if (committedEventsExist(groupedStudent, new Date())) {
					showExcludeStdPopup = true;
				} else {
					groupedStudent.setUi_selected(true);
					groupedStudent.setStatus("Excluded");
					serviceLocator.getGroupedStudentService().update(groupedStudent);
					showAllStudentsBtn = true;
					// studentGroup.getAvailableStudents().remove(groupedStudent);
					showInfo("Selected " + Util.getMessage("student_label").toLowerCase()
							+ " excluded from the program successfully.");
					// studentGroup.getStudents().remove(groupedStudent);
				}
			} else {
				groupedStudent.setTmpString("include");
				groupedStudent.setUi_selected(false);
				groupedStudent.setStatus("Active");
				serviceLocator.getGroupedStudentService().update(groupedStudent);
				showInfo("Selected " + Util.getMessage("student_label").toLowerCase()
						+ " included to the profile successfully.");
				// studentGroup.getStudents().add(groupedStudent);
				// studentGroup.getAvailableStudents().add(groupedStudent);
			}
			studentGroup.getStudents().set(index, groupedStudent);
		}
	}

	private boolean committedEventsExist(GroupedStudent student, Date date) {
		List<NdisCommittedEvent> committedEvents = serviceLocator.getNdisCommittedEventService()
				.SelectedStudentEvents(student.getStudent().getId(), date, student.getGroup().getId());
		return !committedEvents.isEmpty();
	}

	public void unCommitEvents(ActionEvent ae) {
		String action = (String) ae.getComponent().getAttributes().get("act");
		if (action != null) {
			if (action.equals("yes")) {
				serviceLocator.getNdisCommittedEventService().deleteSelectedStudentEvents(
						groupedStudent.getStudent().getId(), new Date(), studentGroup.getId());
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				showInfo("NDIS Commitment have been successfully removed for the selected "
						+ Util.getMessage("student_label").toLowerCase() + " from " + formatter.format(new Date())
						+ " onwards.");
			}
			if (action.equals("cancel")) {
				groupedStudent.setUi_selected(false);
				groupedStudent.setStatus("Active");
				showExcludeStdPopup = false;
				return;
			}
			if (groupedStudent.getTmpString().equals("exclude")) {
				int index = 0;
				for (GroupedStudent gs : studentGroup.getStudents()) {
					if (groupedStudent.getId() == gs.getId())
						index = studentGroup.getStudents().indexOf(gs);
				}
				groupedStudent.setUi_selected(true);
				groupedStudent.setStatus("Excluded");
				serviceLocator.getGroupedStudentService().update(groupedStudent);
				showInfo("Selected " + Util.getMessage("student_label").toLowerCase()
						+ " excluded from the program successfully.");
				showAllStudentsBtn = true;
				studentGroup.getStudents().set(index, groupedStudent);
				// studentGroup.getAvailableStudents().remove(groupedStudent);
				// studentGroup.getStudents().remove(groupedStudent);
			} else if (groupedStudent.getTmpString().equals("delete")) {
				int tmpIndex = 0, index = studentGroup.getStudents().indexOf(groupedStudent);
				if (!showAllStudents) {
					tmpIndex = studentGroup.getAvailableStudents().indexOf(groupedStudent);
				}
				studentGroup.getStudents().remove(groupedStudent);
				studentGroup.getAvailableStudents().remove(groupedStudent);
				try {
					studentGroup = serviceLocator.getStudentGroupService().update(studentGroup);
					showInfo("Selected " + Util.getMessage("student_label").toLowerCase()
							+ " has been removed from the program successfully.");
				} catch (DataIntegrityViolationException d) {
					showError(Util.getMessage("error.integrity"));
					studentGroup.getStudents().add(index, groupedStudent);
					if (!showAllStudents) {
						studentGroup.getAvailableStudents().add(tmpIndex, groupedStudent);
					}
					// studentGroup.setStudents(serviceLocator.getGroupedStudentService()
					// .listByGroup(studentGroup.getId()));
				}
			}
		}
		showExcludeStdPopup = false;
	}

	public void enrollStudentPopup() {
		if (visibleEnrollStd)
			visibleEnrollStd = false;
		else
			visibleEnrollStd = true;
	}

	public void editStudentPopup() {
		if (visibleEditStd)
			visibleEditStd = false;
		else
			visibleEditStd = true;
	}

	public void addEditGroups() {
		selectedTabIndex = 1;
		groupTabIndex = 0;
		studentGroup = null;
	}

	/*
	 * GroupedStaff Functions
	 */

	public void enrollNewStaff() {
		if (program.getStatus().equals("Inactive"))
			showError("Operation not allowed. Program status marked as inactive..");
		else {
			flag = "new";
			searchText = "";
			staffMember = null;
			initWeekdays();
			// staffList = serviceLocator
			// .getStaffMemberService()
			// .listAvailableByNameNGroup(studentGroup.getId(), searchText);
			staffList = serviceLocator.getStaffMemberService().listActiveStaffMembers();
			if (staffList == null || staffList.isEmpty()) {
				showError("There are no active staff members available.");
			} else {
				// selectAllStaff = false;
				// enrolledStaff = new HashMap<Long, StaffMember>();
				if (studentGroup.getStaffMembers() == null)
					studentGroup.setStaffMembers(new ArrayList<GroupedStaff>());
				for (GroupedStaff s : studentGroup.getStaffMembers()) {
					for (int i = 0; i < staffList.size(); i++) {
						StaffMember staff = staffList.get(i);
						if (s.getStaffMember().getId() == staff.getId()) {
							staffList.remove(i);
							break;
						}
					}
				}
				if (studentGroup.getNdis() != null && studentGroup.getNdis().getNumerator() != 0
						&& studentGroup.getNdis().getDenominator() != 0) {

					if (studentGroup.getProgram().getType().getName().equals("Student")) {
					}
				}
				enrollStaffPopup();
			}
		}
	}

	/*
	 * public void validateLos(){
	 * 
	 * actStaffs = studentGroup.getStaffMembers().size(); actStudents =
	 * studentGroup.getStudents().size();
	 * 
	 * int currentStaffs = 1; double currentStudents = actStudents / actStaffs;
	 * 
	 * expStaffs = studentGroup.getNdis().getNumerator(); expStudents =
	 * studentGroup.getNdis().getDenominator();
	 * 
	 * int currentExpStaffs = 1; double currentExpStudents =
	 * expStudents/expStaffs;
	 * 
	 * if (currentStaffs == currentExpStaffs && currentStudents ==
	 * currentExpStudents) { displayIcon = true; } else { displayIcon = false; }
	 * //double studentsForStaff = expStudents / expStaffs; double
	 * actStaffsNeeded = actStudents / currentExpStudents; double remainStaffs =
	 * actStaffsNeeded - actStaffs;
	 * 
	 * int iPart = (int) remainStaffs; double fPart = remainStaffs - iPart;
	 * double fPartPos = 0;
	 * 
	 * if (fPart >= 0) { fPartPos = fPart; } else if (fPart < 0) { fPartPos =
	 * -(fPart); }
	 * 
	 * if (remainStaffs > 0) { if (fPartPos > 0) {
	 * 
	 * noOfStaffs = iPart + 1; } else if (fPartPos == 0) {
	 * 
	 * noOfStaffs = iPart; } commandText = "Add"; displayMessage = true;
	 * displayIcon = false; } else if (remainStaffs < 0) {
	 * 
	 * if (fPartPos > 0) {
	 * 
	 * noOfStaffs = iPart + 1; } else if (fPartPos == 0) {
	 * 
	 * noOfStaffs = iPart; } commandText = "Remove"; displayMessage = true;
	 * displayIcon = false; } else { displayMessage = false; displayIcon = true;
	 * }
	 * 
	 * }
	 */
	public void searchStaff() {
		// enrolledStaff.clear();
		// selectAllStaff = false;
		staffList.clear();
		if (searchValue.equals("id") && !searchText.equals("")) {
			// StaffMember staff = serviceLocator.getStaffMemberService()
			// .listAvailableByStaffIdNGroup(studentGroup.getId(),
			// searchText);
			staffList = serviceLocator.getStaffMemberService().listAvailableByStaffIdNGroup(selectedGroupId,
					searchText);// searchByStaffId(searchText);
//			if (list.isEmpty()) {
//				showError("No result available for this Id.");
//			} else {
//				boolean available = false;
//				for (GroupedStaff groupedstaff : studentGroup.getStaffMembers()) {
//					if (groupedstaff.getStaffMember().getId() == staff.getId()) {
//						available = true;
//					}
//				}
//				if (!available)
//					staffList.add(staff);
//			}
		} else {
			// List<StaffMember> staffMembers = serviceLocator
			// .getStaffMemberService().listAvailableByNameNGroup(
			// studentGroup.getId(), searchText);
			staffList = serviceLocator.getStaffMemberService().listAvailableByNameNGroup(selectedGroupId, searchText); // listAvailableByName(searchText);
		}
		if (staffList == null || staffList.isEmpty()) {
			showError("No results for the given " + (searchValue.equals("id") ? "staff id." : "search text."));
		} else if (studentGroup.getStaffMembers() != null) {
			for (GroupedStaff gs : studentGroup.getStaffMembers()) {
				for (int i = 0; i < staffList.size(); i++) {
					StaffMember staff = staffList.get(i);
					if (gs.getStaffMember().getId() == staff.getId()) {
						staffList.remove(i);
						break;
					}
				}
			}
			if (staffList.isEmpty()) {
				showError("No results for the given " + (searchValue.equals("id") ? "staff id." : "search text."));
			}
		}
	}

	public void selectStaff(ClickActionEvent re) {
		staffMember = (StaffMember) re.getComponent().getAttributes().get("staff");
		staffMember.setUi_selected(true);
		// groupedStaff = null;
		// if (studentGroup.getStaffMembers() != null) {
		// for (GroupedStaff stf : studentGroup.getStaffMembers()) {
		// if (stf.getStaffMember().getId().equals(staffMember.getId())) {
		// groupedStaff = stf;
		// break;
		// }
		// }
		// }
		// if (groupedStaff != null) {
		// setStaffInfo();
		// } else {
		groupedStaff = new GroupedStaff();
		groupedStaff.setStaffMember(staffMember);
		groupedStaff.setGroup(studentGroup);
		groupedStaff.setStatus("Active");
		// selectedStaffDays = new ArrayList<String>();
		groupedStaffWeekdayLs = new ArrayList<GroupedStaffWeekday>();

		if (studentGroup.getWeekDays().isEmpty()) {
			// selectedStaffDays.addAll(listAssignedWeekDayIds(new
			// ArrayList<WeekDay>()));
			for (WeekDay day : ((LookupListProvider) Util.getManagedBean("lookupListProvider")).getWeekDays()) {
				GroupedStaffWeekday gsw = new GroupedStaffWeekday();
				gsw.setGroupedstaff(groupedStaff);
				gsw.setWeekday(day);
				gsw.setStartTime(studentGroup.getStartTime());
				gsw.setEndTime(studentGroup.getEndTime());
				if (!staffMember.getUnAvailableDays().contains(day))
					gsw.setUi_selected(true);
				if (studentGroup.isLunchIncluded()
						&& !(studentGroup.getLunchTime() == null || studentGroup.getLunchTime().isEmpty())) {
					gsw.setLunch(Integer.parseInt(studentGroup.getLunchTime()));
				}
				groupedStaffWeekdayLs.add(gsw);

			}
		} else {
			for (WeekDay day : studentGroup.getWeekDays()) {
				// selectedStaffDays.add(day.getId().toString());
				// groupedStaffWeekdayLs.
				GroupedStaffWeekday wd = new GroupedStaffWeekday();
				wd.setGroupedstaff(groupedStaff);
				wd.setWeekday(day);
				wd.setStartTime(studentGroup.getStartTime());
				wd.setEndTime(studentGroup.getEndTime());
				if (!staffMember.getUnAvailableDays().contains(day))
					wd.setUi_selected(true);
				if (studentGroup.isLunchIncluded()
						&& !(studentGroup.getLunchTime() == null || studentGroup.getLunchTime().isEmpty())) {
					wd.setLunch(Integer.parseInt(studentGroup.getLunchTime()));
				}
				groupedStaffWeekdayLs.add(wd);
			}
		}
		// groupedStaff.set
		// }

		// set start and end time to hash map.
		initStartEndmaps();
	}

	public void selectStaffWeekday(ValueChangeEvent e) {
		GroupedStaffWeekday gsw = (GroupedStaffWeekday) e.getComponent().getAttributes().get("groupedstaffweekday");
		if (!gsw.isUi_selected() && staffMember.getUnAvailableDays().contains(gsw.getWeekday()))
			showError("Staff Member is not available on " + gsw.getWeekday().getName());
	}

	public boolean enrollStaff() {
		if (validateGroupedStaff()) {
			studentGroup = serviceLocator.getStudentGroupService().update(studentGroup);
			Collections.sort(studentGroup.getStaffMembers(), new SortObjectByName());
			// validateLos();
			showInfo("Staff Member enrolled successfully.");
			staffMember.setUi_selected(false);
			staffMember = null;
			groupedStaff = null;
			this.checkStaffSkills();
			return true;
		}
		return false;
	}

	public void enrollStaffNExit() {
		if (enrollStaff())
			visibleEnrollStaff = false;
	}

	public void editStaff(ActionEvent ae) {
		groupedStaff = (GroupedStaff) ae.getComponent().getAttributes().get("staff");
		// initWeekdays();
		flag = "edit";
		staffMember = groupedStaff.getStaffMember();
		setStaffInfo();
		initStartEndmaps();
		enrollStaffPopup();
	}

	public void removeStaff(ActionEvent ae) {
		GroupedStaff mainStaff = (GroupedStaff) ae.getComponent().getAttributes().get("staff");
		int index = studentGroup.getStaffMembers().indexOf(mainStaff);
		studentGroup.getStaffMembers().remove(mainStaff);
		try {
			studentGroup = serviceLocator.getStudentGroupService().update(studentGroup);
			showInfo("Staff Member deleted successfully.");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
			studentGroup.getStaffMembers().add(index, mainStaff);
		}
	}

	private boolean validateGroupedStaff() {
		List<GroupedStaffWeekday> assigneddays = new ArrayList<GroupedStaffWeekday>();
		GregorianCalendar calStartTime = new GregorianCalendar();
		GregorianCalendar calEndTime = new GregorianCalendar();
		String invalidDays = "";
		for (GroupedStaffWeekday day : groupedStaffWeekdayLs) {
			if (day.isUi_selected()) {
				// check if time is matching
				calStartTime.setTime(day.getGroupStaffStartTime().getDateTime(new Date()));
				calEndTime.setTime(day.getGroupStaffEndTime().getDateTime(new Date()));
				if (calEndTime.getTimeInMillis() < calStartTime.getTimeInMillis()
						&& !studentGroup.getProgram().getType().getName().equals("Individual")) {
					if(invalidDays == ""){
						invalidDays = invalidDays + day.getWeekday().getName();
					}else{
						invalidDays = invalidDays+","+day.getWeekday().getName();
					}
				/*	showError("Start And End times are not matching on " + day.getWeekday().getName());
					return false;*/
				}
				day.setStartTime(day.getGroupStaffStartTime().getDateTime(new Date()));
				day.setEndTime(day.getGroupStaffEndTime().getDateTime(new Date()));
				assigneddays.add(day);
			}
		}
		
		if(!invalidDays.equals("")){
			showError("Start time should be before the end time on " + invalidDays + ".");
			return false;
		}
		
		if (assigneddays.isEmpty()) {
			showError("Staff Member should be assign at least to a single day.");
			return false;
		}
		groupedStaff.setAssignedDays(assigneddays);
		if (groupedStaff.getId() == null) {
			studentGroup.getStaffMembers().add(groupedStaff);
		}
		return true;
	}

	private void setStaffInfo() {
		boolean found = false;
		groupedStaffWeekdayLs = new ArrayList<GroupedStaffWeekday>();
		List<WeekDay> itrL = new ArrayList<WeekDay>();
		if (!studentGroup.getWeekDays().isEmpty()) {
			itrL = studentGroup.getWeekDays();
		} else {
			itrL = ((LookupListProvider) Util.getManagedBean("lookupListProvider")).getWeekDays();
		}
		for (WeekDay wday : itrL) {
			found = false;
			for (GroupedStaffWeekday groupedday : groupedStaff.getAssignedDays()) {
				if (wday.getId().equals(groupedday.getWeekday().getId())) {
					groupedday.setUi_selected(true);
					groupedStaffWeekdayLs.add(groupedday);
					found = true;
					break;
				}
			}
			if (!found) {
				GroupedStaffWeekday wd = new GroupedStaffWeekday();
				wd.setGroupedstaff(groupedStaff);
				wd.setWeekday(wday);
				wd.setStartTime(studentGroup.getStartTime());
				wd.setEndTime(studentGroup.getEndTime());
				if (studentGroup.isLunchIncluded()
						&& !(studentGroup.getLunchTime() == null || studentGroup.getLunchTime().isEmpty())) {
					wd.setLunch(Integer.parseInt(studentGroup.getLunchTime()));
				}
				groupedStaffWeekdayLs.add(wd);
			}
		}
	}

	// private List<String> listAssignedWeekDayIds(List<WeekDay> assignList) {
	// List<String> returnList = new ArrayList<String>();
	// if (allWeekDayIds == null) {
	// allWeekDayIds = new ArrayList<String>();
	// for (WeekDay day : ((LookupListProvider)
	// Util.getManagedBean("lookupListProvider")).getWeekDays()) {
	// allWeekDayIds.add(day.getId().toString());
	// }
	// }
	// for (String id : allWeekDayIds) {
	// for (WeekDay day : assignList) {
	// if (id.equals(day.getId().toString())) {
	// returnList.add(id);
	// }
	// }
	// }
	// return returnList;
	// }
	//
	// private List<GroupedStaffWeekday> listAssignedWeekDays(List<String>
	// assignedWeekDayIds) {
	// List<GroupedStaffWeekday> returnList = new
	// ArrayList<GroupedStaffWeekday>();
	// for (WeekDay day : ((LookupListProvider)
	// Util.getManagedBean("lookupListProvider")).getWeekDays()) {
	// for (String id : assignedWeekDayIds) {
	// if (id.equals(day.getId().toString())) {
	// GroupedStaffWeekday groupedSweekDay = new GroupedStaffWeekday();
	// groupedSweekDay.setWeekday(day);
	// returnList.add(groupedSweekDay);
	// }
	// }
	// }
	// return returnList;
	// }

	private List<WeekDay> listNotAssignedWeekDays(List<String> assignedWeekDayIds) {
		List<WeekDay> returnList = new ArrayList<WeekDay>();
		for (WeekDay day : ((LookupListProvider) Util.getManagedBean("lookupListProvider")).getWeekDays()) {
			boolean found = false;
			for (String id : assignedWeekDayIds) {
				if (id.equals(day.getId().toString())) {
					found = true;
					break;
				}
			}
			if (!found) {
				returnList.add(day);
			}
		}
		return returnList;
	}

	private void initWeekdays() {
		weekDays = new ArrayList<SelectItem>();
		for (WeekDay day : ((LookupListProvider) Util.getManagedBean("lookupListProvider")).getWeekDays()) {
			if (studentGroup.getWeekDays().isEmpty()) {
				weekDays.add(new SelectItem(day.getId(), day.getName()));
				continue;
			}
			if (studentGroup.getWeekDays().contains(day))
				weekDays.add(new SelectItem(day.getId(), day.getName()));
			else
				weekDays.add(new SelectItem(day.getId(), day.getName(), "", true));
		}
	}

	private void initStartEndmaps() {

		for (GroupedStaffWeekday day : groupedStaffWeekdayLs) {
			TimeBean start = new TimeBean(day.getStartTime());
			TimeBean end = new TimeBean(day.getEndTime());
			day.setGroupStaffStartTime(start);
			day.setGroupStaffEndTime(end);
		}
	}

	public void enrollStaffPopup() {
		visibleEnrollStaff = !visibleEnrollStaff;
	}

	/*
	 * Student Extra Event Functions
	 */

	public void addRemoveFromEvents(ActionEvent event) {
		groupedStudent = (GroupedStudent) event.getComponent().getAttributes().get("student");
		populateEditableEvents(groupedStudent);
		if (availableEvents == null || availableEvents.isEmpty()) {
			showError("There are no editable events found for this student.");
		} else
			enrollStudentEventsPopup();
	}

	private void populateEditableEvents(GroupedStudent groStu) {
		selectAllEvents = false;
		enrolledEvents = new HashMap<Long, ProgramEvent>();
		availableEvents = serviceLocator.getProgramEventService().listEditableEventsByGroupedStudent(groStu, null);
	}

	public void enrollStudentEventsPopup() {
		if (visibleCheckStudentEvents)
			visibleCheckStudentEvents = false;
		else
			visibleCheckStudentEvents = true;
	}

	public void selectEvent(ValueChangeEvent ve) {
		ProgramEvent event = (ProgramEvent) ve.getComponent().getAttributes().get("event");
		Boolean selected = (Boolean) ve.getNewValue();
		event.setUi_selected(selected);
		if (selected) {
			enrolledEvents.put(event.getId(), event);
			if (enrolledEvents.values().containsAll(availableEvents))
				selectAllEvents = true;
		} else {
			selectAllEvents = false;
			enrolledEvents.remove(event.getId());
		}
	}

	public void selectAllEvents(ValueChangeEvent ve) {
		Boolean selected = (Boolean) ve.getNewValue();
		for (ProgramEvent event : availableEvents) {
			event.setUi_selected(selected);
			if (selected)
				enrolledEvents.put(event.getId(), event);
			else
				enrolledEvents.remove(event.getId());
		}
	}

	public void saveEnrolledEvents() {
		boolean error = false, success = false;
		String days="";
		SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
		if (!enrolledEvents.isEmpty()) {
			for (ProgramEvent event : enrolledEvents.values()) {
				StudentFundingSource sfs = serviceLocator.getStudentFundingSourceService()
						.getRelatedStudentFundingSource(groupedStudent.getStudent().getId(), event.getEventDate());
				if (sfs == null) {
					/*showError(Util.getMessage("student_label")
							+ "(s) found without a matching funding source with the event date.");*/
					if(days.equals("")){
						days=days.concat(format.format(event.getEventDate()));	
					}else{
						days=days.concat("," + format.format(event.getEventDate()));
					}
					error = true;
					continue;
				}
				StudentEvent stuEvent = new StudentEvent();
				stuEvent.setGroupedStudent(groupedStudent);
				stuEvent.setProEvent(event);
				stuEvent.setStdFundingSrc(sfs);
				stuEvent = serviceLocator.getStudentEventService().create(stuEvent);
				success = true;
			}
			populateEditableEvents(groupedStudent);
			if (error && success) {
				showInfo(Util.getMessage("student_label")
						+ " enrolled with some of the selected event(s) successfully.");
				showError("No matching funding source found with rest of the event's dates.");
			} else if (error) {
				showError("No matching funding source found with the event's dates (" + days + ")" + ".");
			} else if (success) {
				showInfo(Util.getMessage("student_label") + " enrolled to the selected event(s) successfully.");
				enrollStudentEventsPopup();
			}
		}
	}

	public void deleteEnrolledEvents() {
		if (!enrolledEvents.isEmpty()) {
			List<ProgramEvent> events = new ArrayList<ProgramEvent>(enrolledEvents.values());
			try {
				serviceLocator.getStudentEventService().deleteByProEventsAndStudent(events,
						groupedStudent.getStudent().getId());
				showInfo(Util.getMessage("student_label") + " removed from the selected events successfully.");
				enrollStudentEventsPopup();
			} catch (Exception e) {
				showError("Operation not allowed. Records are currently in use.");
			}
		}
		// populateEditableEvents(groStu);
	}

	/*
	 * Program Event Functions
	 */

	public void selectEventGroup(ValueChangeEvent event) {
		selectedGroupId = (Long) event.getNewValue();
		studentGroup = serviceLocator.getStudentGroupService().retrieveWithData(selectedGroupId, false);
		refreshProEventsSelectItems();
		selectedProEventId = null;
		proEvent = null;
	}

	public void createProgramEvent() {
		if (program.getStatus().equals("Inactive"))
			showError("Operation not allowed. Program status marked as inactive..");
		else {
			StudentGroup group = serviceLocator.getStudentGroupService().retrieveWithStaffMembers(selectedGroupId);
			if (group.getStartTime() != null)
				startTime = new TimeBean(group.getStartTime());
			else
				startTime = new TimeBean();
			if (group.getEndTime() != null)
				endTime = new TimeBean(group.getEndTime());
			else
				endTime = new TimeBean();
			proEvent = new ProgramEvent();
			if (group.getStartTime() != null)
				startTime = new TimeBean(group.getStartTime());
			else
				startTime = new TimeBean();
			if (group.getEndTime() != null)
				endTime = new TimeBean(group.getEndTime());
			else
				endTime = new TimeBean();
			proEvent.setStatus("pending");
			proEvent.setLunchIncluded(group.isLunchIncluded());
			actualStartTime = new TimeBean();
			actualEndTime = new TimeBean();
			setActualEventTimes = false;
			selectedProEventId = null;
			proEvent.setGroup(group);
			proEvent.setChargeAmount(group.getChargeAmount());
			if (group.getLocation() != null)
				selectedLocationId = group.getLocation().getId();
			else
				selectedLocationId = new Long(0);
			loadCoordinators();
			if (program.getCoordinator() != null)
				selectedCoordinatorId = program.getCoordinator().getId();
			else
				selectedCoordinatorId = new Long(0);
			eventStatus = "pending";
		}
	}

	public void selectProgramEvent(ValueChangeEvent ve) {
		Long id = (Long) ve.getNewValue();
		if (id != null && id > 0) {
			selectedProEventId = id;
			proEvent = serviceLocator.getProgramEventService().retrieve(selectedProEventId);
			eventStatus = proEvent.getStatus();
			loadProgramEvent();
		}
	}

	public void loadProgramEvent() {
		startTime = new TimeBean();
		endTime = new TimeBean();
		actualStartTime = new TimeBean();
		actualEndTime = new TimeBean();
		if (proEvent.getLocation() != null)
			selectedLocationId = proEvent.getLocation().getId();
		else
			selectedLocationId = new Long(0);
		loadCoordinators();
		if (proEvent.getCoordinator() != null)
			selectedCoordinatorId = proEvent.getCoordinator().getId();
		else
			selectedCoordinatorId = new Long(0);
		startTime.setDateTime(proEvent.getStartTime());
		endTime.setDateTime(proEvent.getEndTime());
		if (proEvent.getActualStartTime() == null || proEvent.getActualEndTime() == null) {
			setActualEventTimes = false;
		} else {
			setActualEventTimes = true;
			actualStartTime.setDateTime(proEvent.getActualStartTime());
			actualEndTime.setDateTime(proEvent.getActualEndTime());
		}
	}

	public void saveProgramEvent(ActionEvent event) {
		if (validateProEventFields()) {
			String status = proEvent.getStatus();
			if ((eventStatus.equals("pending") && (status.equals("finished") || status.equals("banked")))
					|| (eventStatus.equals("finished") && status.equals("banked"))) {
				if (proEvent.getGeneratedDate() == null) {
					showError("Status connot be changed.");
					showError("There is no attendence sheet generated.");
					return;
				} else {
					proEvent.setCompletedDate(GregorianCalendar.getInstance().getTime());
					if (status.equals("banked"))
						proEvent.setBankedDate(GregorianCalendar.getInstance().getTime());
					confirmationStatusMessage = "This will mark the event as "
							+ (status.equals("finished") ? "completed" : status)
							+ " and cannot be undone. Do you want to continue?";
					visibleStatusConfirmation = true;
					return;
				}
			} else if (status.equals("pending"))
				saveProgramEvent();
		}
	}

	private void saveProgramEvent() {
		Location locPro = serviceLocator.getLocationService().retrieve(selectedLocationId);
		proEvent.setLocation(locPro);
		if (selectedCoordinatorId > 0) {
			if (proEvent.getCoordinator() == null || !proEvent.getCoordinator().getId().equals(selectedCoordinatorId)) {
				StaffMember coordinator = serviceLocator.getStaffMemberService().retrieve(selectedCoordinatorId);
				proEvent.setCoordinator(coordinator);
			}
		} else {
			proEvent.setCoordinator(null);
		}
		proEvent.setStartTime(startTime.getDateTime(proEvent.getEventDate()));
		proEvent.setEndTime(endTime.getDateTime(proEvent.getEventDate()));
		if (setActualEventTimes) {
			proEvent.setActualStartTime(actualStartTime.getDateTime(proEvent.getEventDate()));
			proEvent.setActualEndTime(actualEndTime.getDateTime(proEvent.getEventDate()));
		} else {
			proEvent.setActualStartTime(null);
			proEvent.setActualEndTime(null);
		}
		if (proEvent.getId() == null) {
			proEvent.setProgram(program);
			// proEvent.setCoordinator(program.getCoordinator());
			proEvent = setStaffEvents(proEvent);
			proEvent = serviceLocator.getProgramEventService().create(proEvent);
			selectedProEventId = proEvent.getId();
			showInfo("New program event created successfully..");
		} else {
			proEvent = serviceLocator.getProgramEventService().update(proEvent);
			eventStatus = proEvent.getStatus();
			showInfo("Program event saved successfully..");
		}
		refreshProEventsSelectItems();
	}

	public void deleteProgramEvent() {
		try {
			serviceLocator.getProgramEventService().delete(proEvent.getId());
			refreshProEventsSelectItems();
			selectedProEventId = null;
			proEvent = null;
			showInfo("Program event deleted successfully..");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public void createProEvents() {
		if (program.getStatus().equals("Inactive"))
			showError("Operation not allowed. Program status marked as inactive..");
		else {
			proEvent = null;
			StudentGroup group = serviceLocator.getStudentGroupService().retrieve(selectedGroupId);
			startDate = endDate = null;
			includeBreak = group.isLunchIncluded();
			startTime = new TimeBean();
			endTime = new TimeBean();
			if (group.getStartDate() != null)
				startDate = group.getStartDate();
			if (group.getEndDate() != null)
				endDate = group.getEndDate();
			if (group.getStartTime() != null)
				startTime = new TimeBean(group.getStartTime());
			if (group.getEndTime() != null)
				endTime = new TimeBean(group.getEndTime());
			if (group.getLocation() != null)
				selectedLocationId = group.getLocation().getId();
			else
				selectedLocationId = new Long(0);
			loadCoordinators();
			if (program.getCoordinator() != null)
				selectedCoordinatorId = program.getCoordinator().getId();
			else
				selectedCoordinatorId = new Long(0);
			eventChargeAmount = group.getChargeAmount();
			selectedWeekDayIds = new ArrayList<String>();
			if (group.getWeekDays().isEmpty()) {
				recurrenceType = "Daily";
			} else {
				recurrenceType = "Weekly";
				for (WeekDay day : group.getWeekDays()) {
					selectedWeekDayIds.add(day.getId().toString());
				}
			}
			showInfo("This will avoid creating events for holidays within the given date range..");
			proEventsPopup();
		}
	}

	public void generateProgramEvents() {
		if (validateProEventsFields()) {
			int createdEvents = 0;
			SimpleDateFormat formatDay = new SimpleDateFormat("EEEE");
			Calendar calDate = Calendar.getInstance();
			calDate.setTime(startDate);
			calDate.add(Calendar.DATE, -1);
			Location location = serviceLocator.getLocationService().retrieve(selectedLocationId);
			StaffMember coordinator = null;
			if (selectedCoordinatorId > 0)
				coordinator = serviceLocator.getStaffMemberService().retrieve(selectedCoordinatorId);
			StudentGroup group = serviceLocator.getStudentGroupService().retrieveWithStaffMembers(selectedGroupId);
			HashMap<String, WeekDay> weekDays = null;
			if (recurrenceType.equals("Weekly")) {
				weekDays = new HashMap<String, WeekDay>();
				for (String id : selectedWeekDayIds) {
					WeekDay day = serviceLocator.getWeekDayService().retrieve(Long.parseLong(id));
					weekDays.put(day.getName(), day);
				}
			}
			do {
				calDate.add(Calendar.DATE, 1);
				if (recurrenceType.equals("Weekly") && !weekDays.containsKey(formatDay.format(calDate.getTime())))
					continue;
				if (serviceLocator.getHolidayService().isHoliday(new Long(1), calDate.getTime()))
					continue;
				if (serviceLocator.getProgramEventService().isEventExist(selectedGroupId, calDate.getTime(),
						startTime.getDateTime(calDate.getTime()), endTime.getDateTime(calDate.getTime()))) {
					continue;
				}
				proEvent = new ProgramEvent();
				proEvent.setProgram(program);
				proEvent.setEventDate(calDate.getTime());
				proEvent.setStartTime(startTime.getDateTime(calDate.getTime()));
				proEvent.setEndTime(endTime.getDateTime(calDate.getTime()));
				// proEvent.setCoordinator(program.getCoordinator());
				proEvent.setGroup(group);
				proEvent.setStatus("pending");
				proEvent.setLocation(location);
				proEvent.setCoordinator(coordinator);
				proEvent.setChargeAmount(eventChargeAmount);
				proEvent.setLunchIncluded(includeBreak);
				proEvent = setStaffEvents(proEvent);
				proEvent = serviceLocator.getProgramEventService().create(proEvent);
				createdEvents++;
			} while (calDate.getTime().compareTo(endDate) != 0);
			if (createdEvents > 0) {
				showInfo(String.valueOf(createdEvents) + " Event(s) created successfully..");
				refreshProEventsSelectItems();
				proEventsPopup();
			} else
				showError("There are no available dates to create events within the given date range or events exist.");
		}
	}

	private ProgramEvent setStaffEvents(ProgramEvent event) {
		event.setStaffEvents(new ArrayList<StaffEvent>());
		if (event.getGroup().getStaffMembers() != null && !event.getGroup().getStaffMembers().isEmpty()) {
			for (GroupedStaff staff : event.getGroup().getStaffMembers()) {
				boolean found = false;
				for (WeekDay day : staff.getStaffMember().getUnAvailableDays()) {
					if (day.getName().equals(getDateString(event.getEventDate()))) {
						found = true;
						break;
					}
				}
				if (!found) {
					event.getStaffEvents().add(createStaffEvent(staff.getStaffMember(), event));
				}
			}
		}
		return event;
	}

	private StaffEvent createStaffEvent(StaffMember staffMember, ProgramEvent programEvent) {
		StaffEvent event = new StaffEvent();
		event.setStaffMember(staffMember);
		event.setProgramEvent(programEvent);
		event.setRosterStartTime(programEvent.getStartTime());
		event.setRosterEndTime(programEvent.getEndTime());
		return event;
	}

	public void proEventsPopup() {
		if (visibleProEvents) {
			visibleProEvents = false;
			proEvent = null;
			selectedProEventId = null;
			selectedLocationId = new Long(0);
		} else
			visibleProEvents = true;
	}

	private boolean validateProEventTime(Date eventDate) {
		if (studentGroup.getProgram().getType().getName().equals("Individual")) {
			return true;
		} else if (startTime.getDateTime(eventDate).before(endTime.getDateTime(eventDate))) {
			if (setActualEventTimes) {
				if (actualStartTime.getDateTime(eventDate).before(actualEndTime.getDateTime(eventDate))) {
					return true;
				}
			} else {
				return true;
			}
		}
		showError("Invalid time. Start time should be before the end time");
		return false;
	}

	private boolean validateProEventFields() {
		Date startDate = program.getStartDate();
		Date endDate = program.getEndDate();
		Date eventDate = proEvent.getEventDate();
		if (eventDate != null) {
			if (serviceLocator.getHolidayService().isHoliday(new Long(1), eventDate)) {
				showError("Selected date is a holiday..");
				return false;
			} else {
				if (eventDate.compareTo(startDate) < 0 || endDate.compareTo(eventDate) < 0) {
					showError("Invalid date! Event date does not match with the program start/end dates..");
					return false;
				} else {
					if (validateProEventTime(proEvent.getEventDate())) {
						if (selectedLocationId != 0)
							return true;
						else {
							showError("Please select a location..");
							return false;
						}
					} else
						return false;
				}
			}
		} else {
			showError("Event date shold not be empty..");
			return false;
		}
	}

	private boolean validateProEventsFields() {
		Date proStartDate = program.getStartDate();
		Date proEndDate = program.getEndDate();
		if (startDate != null && endDate != null) {
			if (startDate.compareTo(proStartDate) < 0 || proEndDate.compareTo(startDate) < 0
					|| endDate.compareTo(proStartDate) < 0 || proEndDate.compareTo(endDate) < 0) {
				showError("Events start/end dates do not match with the program start/end dates..");
				return false;
			} else {
				if (endDate.compareTo(startDate) < 0) {
					showError("Events start date should be before the events end date..");
					return false;
				} else {
					if (validateProEventTime(null)) {
						if (selectedLocationId != 0) {
							if (recurrenceType.equals("Weekly") && selectedWeekDayIds.isEmpty()) {
								showError("Please select weekday(s)..");
								return false;
							} else
								return true;
						} else {
							showError("Please select a location..");
							return false;
						}
					} else
						return false;
				}
			}
		} else {
			showError("Events start/end dates shold not be empty..");
			return false;
		}
	}

	public void eventDateChanged(ValueChangeEvent vce) {
		Date newDate = (Date) vce.getNewValue();
		setActualEventTimes = false;
		if (newDate != null && newDate.after(new Date())) {
			proEvent.setActualStartTime(null);
			proEvent.setActualEndTime(null);
			actualStartTime = new TimeBean();
			actualEndTime = new TimeBean();
		}
	}

	public boolean isExpired(Date date) {
		if (date != null && date.before(new Date()))
			return true;
		else
			return false;
	}

	public void setActualEventTimes(ValueChangeEvent vce) {
		boolean checked = (Boolean) vce.getNewValue();
		if (checked && proEvent.getStartTime() != null && proEvent.getEndTime() != null) {
			actualStartTime.setDateTime(proEvent.getStartTime());
			actualEndTime.setDateTime(proEvent.getEndTime());
		}
	}

	private void refreshProEventsSelectItems() {
		proEventSelectItems = new ArrayList<SelectItem>();
		List<ProgramEvent> programEvents = serviceLocator.getProgramEventService().listByStudentGroup(selectedGroupId);
		if (programEvents != null && !programEvents.isEmpty()) {
			for (ProgramEvent pro : programEvents)
				proEventSelectItems.add(new SelectItem(pro.getId(), pro.getName()));
		}
	}

	// Program Skills related functions

	public void addSkillPopup() {
		// toggle
		if (visibleSkills) {
			visibleSkills = false;
		} else {
			try {
				this.skillsChecked = new HashMap<Long, Boolean>();
				this.availableSkills = serviceLocator.getReferenceItemService()
						.findItemsByCategory(EnumItemCategory.STAFF_SKILL, "Active");
			} catch (DataIntegrityViolationException d) {
				showError(Util.getMessage("error.integrity"));
			}
			List<ReferenceItem> existingSkills = this.studentGroup.getStaffSkills();
			// for concurrent list operation
			List<ReferenceItem> list = new CopyOnWriteArrayList<ReferenceItem>();
			for (ReferenceItem avSkill : this.availableSkills) {
				list.add(avSkill);
			}
			for (ReferenceItem avSkill : list) {
				for (ReferenceItem exSkill : existingSkills) {
					if (exSkill.getId() == avSkill.getId()) {
						this.availableSkills.remove(avSkill);
					}
				}
			}
			if (this.availableSkills.size() != 0) {
				visibleSkills = true;
			} else {
				showError("All the available skills were assigned.");
			}
		}
	}

	public void assignSkill() {
		this.addSkillPopup();
	}

	public void addSkills() {
		List<ReferenceItem> newStaffSkills = new ArrayList<ReferenceItem>();
		for (ReferenceItem avalSkill : this.availableSkills) {
			if (this.skillsChecked.get(avalSkill.getId())) {
				newStaffSkills.add(avalSkill);
			}
		}
		try {
			this.studentGroup.getStaffSkills().addAll(newStaffSkills);
			serviceLocator.getStudentGroupService().update(this.studentGroup);
			showInfo("Selected skill(s) have assigned");
			this.addSkillPopup();
			this.checkStaffSkills();
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public void deleteSkill(ActionEvent ae) {
		try {
			this.studentGroup.getStaffSkills().remove(ae.getComponent().getAttributes().get("skill"));
			serviceLocator.getStudentGroupService().update(this.studentGroup);
			showInfo("Selected skill deleted successfully..");
			this.checkStaffSkills();
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	private void checkStaffSkills() {
		// check staff member have the required group skills and assign the flag
		this.staffFlag = new HashMap<String, Boolean>();
		this.staffMissingSkills = new HashMap<String, String>();
		for (GroupedStaff staff : this.studentGroup.getStaffMembers()) {
			StaffMember member = staff.getStaffMember();
			Boolean haveAll = false;
			Boolean missingSkill = false;
			String missingSkills = "";
			for (ReferenceItem reqSkill : this.studentGroup.getStaffSkills()) {
				Boolean haveOne = false;
				for (StaffSkill memberSkill : member.getSkills()) {
					ReferenceItem skill = memberSkill.getSkill();
					if (reqSkill.getId() == skill.getId()) {
						haveOne = true;
						missingSkill = true;
					} else {
						if (!missingSkill)
							missingSkill = false;
					}
				}
				if (haveOne) {
					haveAll = true;
				} else {
					haveAll = false;
				}
				if (!missingSkill) {
					missingSkills += reqSkill.getItemValue() + ", ";
				}
				missingSkill = false;
			}
			this.staffMissingSkills.put(member.getStaffId(), missingSkills);
			this.staffFlag.put(member.getStaffId(), haveAll);
		}

	}

	public void showCommittedEventPopUp() {
		showCommittedEvent = !showCommittedEvent;
	}

	public void UpdateCommittedEvents() {
		List<NdisCommittedEvent> committedEvnts = new ArrayList<NdisCommittedEvent>();
		for (NdisCommittedEvent cEvent : committedEvents) {
			if (!startTime.getTimeAsString().equals(new TimeBean(tmpStdGroup.getStartTime()).getTimeAsString())
					|| !endTime.getTimeAsString().equals(new TimeBean(tmpStdGroup.getEndTime()).getTimeAsString())) {
				cEvent.setStartTime(startTime.getDateTime(new Date()));
				cEvent.setEndTime(endTime.getDateTime(new Date()));
				setTimeDifferance(cEvent);
			}
			cEvent.setNdisSupportCluster(studentGroup.getNdis());
			if (cEvent.getClusterOverride() != null) {
				cEvent.setClusterOverride(studentGroup.getNdis());
			}
			committedEvnts.add(cEvent);
			// serviceLocator.getNdisCommittedEventService().update(cEvent);
		}
		serviceLocator.getNdisCommittedEventService().calculateCommittedEventPrice(committedEvnts, true);
		saveGroup();
		showCommittedEventPopUp();
		showInfo("Committed Events Updated..");
	}

	public void saveGroup() {
		if (studentGroup.getId() != null) {
			if (!studentGroup.getWeekDays().isEmpty()) {
				List<WeekDay> notAssignedList = listNotAssignedWeekDays(selectedWeekDayIds);
				List<WeekDay> removeList = new ArrayList<WeekDay>();
				for (GroupedStaff stf : studentGroup.getStaffMembers()) {
					for (GroupedStaffWeekday groupedday : stf.getAssignedDays()) {
						WeekDay day = groupedday.getWeekday();
						if (notAssignedList.contains(day)) {
							removeList.add(day);
						}
					}
					if (!removeList.isEmpty())
						stf.getAssignedDays().removeAll(removeList);
				}
			}
			studentGroup = serviceLocator.getStudentGroupService().update(studentGroup);
			if (programType.getName().equals("Staff"))
				showInfo("Staff group saved successfully..");
			else
				showInfo(Util.getMessage("student_label") + " group saved successfully..");
			showInfo(
					"The changes doesn't affect to the existing events. Please goto event schedule and update them if neccessary..");
			refreshStudentGroupsSelectItems();
		}
	}

	private void setTimeDifferance(NdisCommittedEvent cEvent) {
		Date groupSTime = studentGroup.getStartTime();
		Date groupETime = studentGroup.getEndTime();
		try {
			long diff = groupETime.getTime() - groupSTime.getTime();
			long diffHours = diff / 3600000;
			double hours = Double.valueOf(diffHours);
			cEvent.setHours(hours);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cancelUpdateCEvent() {
		showCommittedEventPopUp();
		saveGroup();
	}

	private boolean compareStdGroupValues() {
		String oldNdis = "", newNdis = "";
		if (tmpStdGroup.getNdis() != null) {
			oldNdis = tmpStdGroup.getNdis().getItemName();
		}
		if (studentGroup.getNdis() != null) {
			newNdis = studentGroup.getNdis().getItemName();
		}
		if (!oldNdis.equals(newNdis)) {
			return true;
		}
		if (!startTime.getTimeAsString().equals(new TimeBean(tmpStdGroup.getStartTime()).getTimeAsString())) {
			return true;
		}
		if (!endTime.getTimeAsString().equals(new TimeBean(tmpStdGroup.getEndTime()).getTimeAsString())) {
			return true;
		}
		return false;

	}

	public void selectSupportCluster(ValueChangeEvent event) {
		Long id = (Long) event.getNewValue();
		Long oldId = (Long) event.getOldValue();
		if (id != oldId) {
			ndisChange = true;
		} else {
			ndisChange = false;
		}
		selectedNDISItemId = id;
		// :TODO Fix lazy false
		if (selectedNDISItemId == null || selectedNDISItemId == 0) {
			studentGroup.setNdis(null);
		} else {
			studentGroup.setNdis(serviceLocator.getNdisSupportItemService().retrieve(selectedNDISItemId));
		}
		if (studentGroup.getNdis() != null && studentGroup.getNdis().getNdisPrice().isEmpty()) {
			showError("Selected NDIS Support Cluster doesn't have NDIS prices.");
		}
		/*
		 * startTime = new TimeBean(); endTime = new TimeBean(); recurrenceType
		 * = "Weekly"; selectedWeekDayIds.clear();
		 * studentGroup.setAllowProgramtorunonaholiday(false);
		 * studentGroup.setInactiveovernight(false);
		 */
	}

	private boolean checkHoliday() {
		boolean found = false;
		if (studentGroup.getNdis().getNdisPrice() != null && !studentGroup.getNdis().getNdisPrice().isEmpty()) {
			for (NdisPrice ndisPrice : studentGroup.getNdis().getNdisPrice()) {
				if (ndisPrice.getNdisTime().equals("Public Holiday")) {
					found = true;
					return true;
				}
			}
			if (!found) {
				showError("There is no public holiday price available in the selected NDIS Support Cluster");
				return false;
			}
		}
		showError("There are no prices available in the selected NDIS Support Cluster");
		return false;
	}

	public boolean checkInactiveSleepOver() {
		boolean found = false;
		if (studentGroup.getNdis().getNdisPrice() != null || !studentGroup.getNdis().getNdisPrice().isEmpty()) {
			for (NdisPrice ndisPrice : studentGroup.getNdis().getNdisPrice()) {
				if (ndisPrice.getNdisTime().equals("Overnight Inactive")) {
					found = true;
					return true;
				}
			}
			if (!found) {
				showError("There is no inactive overnight prices available in the selected NDIS Support Cluster");
				return false;
			}
		} else
			showError("There are no prices available in the selected NDIS Support Cluster");
		return false;

	}

	// Check days according to ndis cluster
	/*
	 * public void checkDays(ValueChangeEvent event) {
	 * 
	 * @SuppressWarnings("unchecked") ArrayList<String> ids =
	 * (ArrayList<String>) event.getNewValue();
	 * 
	 * @SuppressWarnings("unchecked") ArrayList<String> oldIds =
	 * (ArrayList<String>) event.getOldValue(); if (ids != null && oldIds !=
	 * null && oldIds.size() > ids.size()) { return; } boolean errorFound =
	 * false; if (validateAndSetTime()) { if (ids != null && selectedWeekDayIds
	 * != null) { for (String id : ids) { if (!selectedWeekDayIds.contains(id))
	 * { if (id.equals("6") && !checkInPrices("Saturday")) { showError(
	 * "There is no matching NDIS price found for Saturday within the selected NDIS support cluster."
	 * ); errorFound = true; } else if (id.equals("7") &&
	 * !checkInPrices("Sunday")) { showError(
	 * "There is no matching NDIS price found for Sunday within the selected NDIS support cluster."
	 * ); errorFound = true; } else if (!id.equals("6") && !id.equals("7") &&
	 * !checkInPrices("Others")) { showError(
	 * "There is no matching NDIS price(s) found for the weekday(s) within the selected NDIS Support Cluster."
	 * ); errorFound = true; } if (errorFound) { ids.remove(id);
	 * selectedWeekDayIds.remove(id); return; } } } } } }
	 */
	private boolean validateGroupWithCluster(List<String> weekDayIds) {
		if (weekDayIds != null && !weekDayIds.isEmpty()) {
			boolean errorFound = false;
			for (String id : weekDayIds) {
				if (id.equals("6") && !checkInPrices("Saturday")) {
					showError(
							"There is no matching NDIS price found for Saturday within the selected NDIS support cluster.");
					errorFound = true;
				} else if (id.equals("7") && !checkInPrices("Sunday")) {
					showError(
							"There is no matching NDIS price found for Sunday within the selected NDIS support cluster.");
					errorFound = true;
				} else if (!id.equals("6") && !id.equals("7") && !checkInPrices("Others")) {
					showError(
							"There is no matching NDIS price(s) found for the weekday(s) matching selected time period within the selected NDIS Support Cluster.");
					errorFound = true;
				}
				if (errorFound) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private boolean checkInPrices(String day) {
		boolean found = false;
		Long currentEndTime, eTime1, eTime2, eTime3, eTime4;
		currentEndTime = eTime1 = eTime2 = eTime3 = eTime4 = null;
		Date endTime = this.endTime.getDateTime(new Date());
		DateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss a");
		String endTimeStr = timeFormatter.format(endTime);
		String ndisTime = null;

		try {
			currentEndTime = timeFormatter.parse(endTimeStr).getTime();
			Date time1Dt = timeFormatter.parse("08:00:00 PM");
			eTime1 = time1Dt.getTime();
			Date time2Dt = timeFormatter.parse("11:59:00 PM");
			eTime2 = time2Dt.getTime();
			Date time3Dt = timeFormatter.parse("12:00:00 AM");
			eTime3 = time3Dt.getTime();
			Date time4Dt = timeFormatter.parse("06:00:00 AM");
			eTime4 = time4Dt.getTime();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (eTime3 <= currentEndTime && currentEndTime <= eTime4) {
			ndisTime = "Weekday Overnight Active";
		} else if (eTime4 <= currentEndTime && currentEndTime <= eTime1) {
			ndisTime = "Weekday Daytime";
		} else if (eTime1 < currentEndTime && currentEndTime <= eTime2 && studentGroup.isInactiveovernight()) {
			ndisTime = "Overnight Inactive";
		} else if (eTime1 < currentEndTime && currentEndTime <= eTime2 && !studentGroup.isInactiveovernight()) {
			ndisTime = "Weekday Evening";
		}
		for (NdisPrice ndisPrice : studentGroup.getNdis().getNdisPrice()) {
			if (day.equals("Others")) {
				if (ndisPrice.getNdisTime().equals(ndisTime)) {
					found = true;
				}
			} else if (ndisPrice.getNdisTime().equals(day)) {
				found = true;
			}
		}
		return found;
	}

	public void hideHolidayWarning() {
		holidayWarning = true;
		studentGroup.setAllowProgramtorunonaholiday(false);
	}

	public void hideInactiveSleepOverWarning() {
		inactiveSleepOverWaning = true;
		studentGroup.setInactiveovernight(false);
	}

	/*
	 * getters and setters
	 */

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public List<Program> getProgramList() {
		return programList;
	}

	public List<SelectItem> getCoordinatorSelectItems() {
		return coordinatorSelectItems;
	}

	public List<SelectItem> getProEventSelectItems() {
		return proEventSelectItems;
	}

	public boolean isVisibleEnrollStd() {
		return visibleEnrollStd;
	}

	public List<Student> getAvailableStudents() {
		return availableStudents;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSelectedLocationId(Long selectedLocationId) {
		this.selectedLocationId = selectedLocationId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<SelectItem> getLocationSelectItems() {
		return locationSelectItems;
	}

	public List<SelectItem> getNDISSelectItems() {
		return NDISSelectItems;
	}

	public void setNDISSelectItems(List<SelectItem> NDISSelectItems) {
		this.NDISSelectItems = NDISSelectItems;
	}

	public Long getSelectedLocationId() {
		return selectedLocationId;
	}

	public Long getselectedNDISItemId() {
		return selectedNDISItemId;
	}

	public void setselectedNDISItemId(Long selectedNDISItemId) {
		this.selectedNDISItemId = selectedNDISItemId;
	}

	public void setSelectedProEventId(Long selectedProEventId) {
		this.selectedProEventId = selectedProEventId;
	}

	public Long getSelectedProEventId() {
		return selectedProEventId;
	}

	public void setProEvent(ProgramEvent proEvent) {
		this.proEvent = proEvent;
	}

	public ProgramEvent getProEvent() {
		return proEvent;
	}

	public void setSelectedCoordinatorId(Long selectedCoordinatorId) {
		this.selectedCoordinatorId = selectedCoordinatorId;
	}

	public Long getSelectedCoordinatorId() {
		return selectedCoordinatorId;
	}

	public boolean isSelectAllStd() {
		return selectAllStd;
	}

	public void setSelectAllStd(boolean selectAllStd) {
		this.selectAllStd = selectAllStd;
	}

	public HashMap<Long, Student> getEnrolledStudents() {
		return enrolledStudents;
	}

	public void setStartTime(TimeBean startTime) {
		this.startTime = startTime;
	}

	public TimeBean getStartTime() {
		if (!studentGroup.getProgram().getType().getName().equals("Individual")) {
			if (startTime.getSession() == Calendar.PM || startTime.getHours() == 12) {
				endTime.setSession(Calendar.PM);
				endTime.setSessionFixedToPM(true);
			} else if (endTime.isSessionFixedToPM()) {
				// endTime.setSession(Calendar.AM);
				endTime.setSessionFixedToPM(false);
			}
		}
		return startTime;
	}

	public void setEndTime(TimeBean endTime) {
		this.endTime = endTime;
	}

	public TimeBean getEndTime() {
		if (!studentGroup.getProgram().getType().getName().equals("Individual")) {
			if (endTime.getHours() != -1 && endTime.getHours() < startTime.getHours()) {
				endTime.setSession(Calendar.PM);
				endTime.setSessionFixedToPM(true);
			}
		}
		return endTime;
	}

	public void setSearchProgramText(String searchProgramText) {
		this.searchProgramText = searchProgramText;
	}

	public String getSearchProgramText() {
		return searchProgramText;
	}

	public void setSelectedTabIndex(int selectedTabIndex) {
		this.selectedTabIndex = selectedTabIndex;
	}

	public int getSelectedTabIndex() {
		return selectedTabIndex;
	}

	public String getRecurrenceType() {
		return recurrenceType;
	}

	public void setRecurrenceType(String recurrenceType) {
		this.recurrenceType = recurrenceType;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public boolean isVisibleProEvents() {
		return visibleProEvents;
	}

	public void setStudentGroup(StudentGroup studentGroup) {
		this.studentGroup = studentGroup;
	}

	public StudentGroup getStudentGroup() {
		return studentGroup;
	}

	public List<SelectItem> getGroupSelectItems() {
		return groupSelectItems;
	}

	public void setSelectedGroupId(Long selectedGroupId) {
		this.selectedGroupId = selectedGroupId;
	}

	public Long getSelectedGroupId() {
		return selectedGroupId;
	}

	public TimeBean getActualStartTime() {
		if (actualStartTime.getSession() == Calendar.PM || actualStartTime.getHours() == 12) {
			actualEndTime.setSession(Calendar.PM);
			actualEndTime.setSessionFixedToPM(true);
		} else if (actualEndTime.isSessionFixedToPM()) {
			actualEndTime.setSession(Calendar.AM);
			actualEndTime.setSessionFixedToPM(false);
		}
		return actualStartTime;
	}

	public List<String> getSelectedWeekDayIds() {
		return selectedWeekDayIds;
	}

	public void setSelectedWeekDayIds(List<String> selectedWeekDayIds) {
		this.selectedWeekDayIds = selectedWeekDayIds;
	}

	public void setActualStartTime(TimeBean actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public TimeBean getActualEndTime() {
		if (actualEndTime.getHours() != -1 && actualEndTime.getHours() < actualStartTime.getHours()) {
			actualEndTime.setSession(Calendar.PM);
			actualEndTime.setSessionFixedToPM(true);
		}
		return actualEndTime;
	}

	public void setActualEndTime(TimeBean actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	public boolean isSetActualEventTimes() {
		return setActualEventTimes;
	}

	public void setSetActualEventTimes(boolean setActualEventTimes) {
		this.setActualEventTimes = setActualEventTimes;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setVisibleStatusConfirmation(boolean visibleStatusConfirmation) {
		this.visibleStatusConfirmation = visibleStatusConfirmation;
	}

	public boolean isVisibleStatusConfirmation() {
		return visibleStatusConfirmation;
	}

	public void setConfirmationStatusMessage(String confirmationStatusMessage) {
		this.confirmationStatusMessage = confirmationStatusMessage;
	}

	public String getConfirmationStatusMessage() {
		return confirmationStatusMessage;
	}

	public ProgramType getProgramType() {
		return programType;
	}

	public void setVisibleCheckStudentEvents(boolean visibleCheckStudentEvents) {
		this.visibleCheckStudentEvents = visibleCheckStudentEvents;
	}

	public boolean isVisibleCheckStudentEvents() {
		return visibleCheckStudentEvents;
	}

	public void setEnrolledEvents(HashMap<Long, ProgramEvent> enrolledEvents) {
		this.enrolledEvents = enrolledEvents;
	}

	public HashMap<Long, ProgramEvent> getEnrolledEvents() {
		return enrolledEvents;
	}

	public void setAvailableEvents(List<ProgramEvent> availableEvents) {
		this.availableEvents = availableEvents;
	}

	public List<ProgramEvent> getAvailableEvents() {
		return availableEvents;
	}

	public void setSelectAllEvents(boolean selectAllEvents) {
		this.selectAllEvents = selectAllEvents;
	}

	public boolean isSelectAllEvents() {
		return selectAllEvents;
	}

	public void setVisibleEnrollStaff(boolean visibleEnrollStaff) {
		this.visibleEnrollStaff = visibleEnrollStaff;
	}

	public boolean isVisibleEnrollStaff() {
		return visibleEnrollStaff;
	}

	public void setAvailableStaff(List<StaffMember> availableStaff) {
		this.availableStaff = availableStaff;
	}

	public List<StaffMember> getAvailableStaff() {
		return availableStaff;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public int getPhotoH() {
		return photoH;
	}

	public int getPhotoW() {
		return photoW;
	}

	public void setGroupTabIndex(int groupTabIndex) {
		this.groupTabIndex = groupTabIndex;
	}

	public int getGroupTabIndex() {
		return groupTabIndex;
	}

	public void setEventChargeAmount(double eventChargeAmount) {
		this.eventChargeAmount = eventChargeAmount;
	}

	public double getEventChargeAmount() {
		return eventChargeAmount;
	}

	public void setIncludeBreak(boolean includeBreak) {
		this.includeBreak = includeBreak;
	}

	public boolean isIncludeBreak() {
		return includeBreak;
	}

	public String getFlag() {
		return flag;
	}

	public StaffMember getStaffMember() {
		return staffMember;
	}

	public GroupedStaff getGroupedStaff() {
		return groupedStaff;
	}

	public void setGroupedStaff(GroupedStaff groupedStaff) {
		this.groupedStaff = groupedStaff;
	}

	public List<String> getSelectedStaffDays() {
		return selectedStaffDays;
	}

	public void setSelectedStaffDays(List<String> selectedStaffDays) {
		this.selectedStaffDays = selectedStaffDays;
	}

	public List<SelectItem> getWeekDays() {
		return weekDays;
	}

	private String getDateString(Date date) {
		if (date != null) {
			return mydateFormat.format(date);
		}
		return "";
	}

	public void setStaffList(List<StaffMember> staffList) {
		this.staffList = staffList;
	}

	public List<StaffMember> getStaffList() {
		return staffList;
	}

	public void setVisibleSkills(boolean visibleSkills) {
		this.visibleSkills = visibleSkills;
	}

	public boolean isVisibleSkills() {
		return visibleSkills;
	}

	public void setAvailableSkills(List<ReferenceItem> availableSkills) {
		this.availableSkills = availableSkills;
	}

	public List<ReferenceItem> getAvailableSkills() {
		return availableSkills;
	}

	public void setSkillsChecked(Map<Long, Boolean> skillsChecked) {
		this.skillsChecked = skillsChecked;
	}

	public Map<Long, Boolean> getSkillsChecked() {
		return skillsChecked;
	}

	public void setStaffFlag(Map<String, Boolean> staffFlag) {
		this.staffFlag = staffFlag;
	}

	public Map<String, Boolean> getStaffFlag() {
		return staffFlag;
	}

	public void setStaffMissingSkills(Map<String, String> staffMissingSkills) {
		this.staffMissingSkills = staffMissingSkills;
	}

	public Map<String, String> getStaffMissingSkills() {
		return staffMissingSkills;
	}

	public Double getSelectedPrice() {
		return selectedPrice;
	}

	public void setSelectedPrice(Double selectedPrice) {
		this.selectedPrice = selectedPrice;
	}

	public Double getClusterPrice() {
		return clusterPrice;
	}

	public void setClusterPrice(Double clusterPrice) {
		this.clusterPrice = clusterPrice;
	}

	public boolean isShowCommittedEvent() {
		return showCommittedEvent;
	}

	public void setShowCommittedEvent(boolean showCommittedEvent) {
		this.showCommittedEvent = showCommittedEvent;
	}

	public List<NdisCommittedEvent> getCommittedEvents() {
		return committedEvents;
	}

	public void setCommittedEvents(List<NdisCommittedEvent> committedEvents) {
		this.committedEvents = committedEvents;
	}

	public boolean isHolidayWarning() {
		return holidayWarning;
	}

	public void setHolidayWarning(boolean holidayWarning) {
		this.holidayWarning = holidayWarning;
	}

	public boolean isInactiveSleepOverWaning() {
		return inactiveSleepOverWaning;
	}

	public void setInactiveSleepOverWaning(boolean inactiveSleepOverWaning) {
		this.inactiveSleepOverWaning = inactiveSleepOverWaning;
	}

	public boolean isShowExcludeStdPopup() {
		return showExcludeStdPopup;
	}

	public void setShowExcludeStdPopup(boolean showExcludeStdPopup) {
		this.showExcludeStdPopup = showExcludeStdPopup;
	}

	public boolean isShowAllStudents() {
		return showAllStudents;
	}

	public GroupedStudent getGroupedStudent() {
		return groupedStudent;
	}

	public void setGroupedStudent(GroupedStudent groupedStudent) {
		this.groupedStudent = groupedStudent;
	}

	public List<GroupedStaffWeekday> getGroupedStaffWeekdayLs() {
		return groupedStaffWeekdayLs;
	}

	public void setGroupedStaffWeekdayLs(List<GroupedStaffWeekday> groupedStaffWeekdayLs) {
		this.groupedStaffWeekdayLs = groupedStaffWeekdayLs;
	}

	public List<ProgramType> getProTypes() {
		return proTypes;
	}

	public void setSelectedProType(Long selectedProType) {
		this.selectedProType = selectedProType;
	}

	public Long getSelectedProType() {
		return selectedProType;
	}

	public boolean isShowAllStudentsBtn() {
		return showAllStudentsBtn;
	}

	public boolean isNdisChange() {
		return ndisChange;
	}

	public void setNdisChange(boolean ndisChange) {
		this.ndisChange = ndisChange;
	}

	public boolean isVisibleEditStd() {
		return visibleEditStd;
	}

	public void setVisibleEditStd(boolean visibleEditStd) {
		this.visibleEditStd = visibleEditStd;
	}

	public Double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

}
