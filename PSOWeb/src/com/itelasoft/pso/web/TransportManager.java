package com.itelasoft.pso.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.icesoft.faces.component.paneltabset.TabChangeEvent;
import com.itelasoft.pso.beans.GroupedStaff;
import com.itelasoft.pso.beans.GroupedStaffWeekday;
import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.GroupedStudentReturn;//import newly created bean class 
import com.itelasoft.pso.beans.Location;
import com.itelasoft.pso.beans.NdisCommittedEvent;
import com.itelasoft.pso.beans.NdisSupportItem;
import com.itelasoft.pso.beans.Program;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.beans.StaffEvent;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentEvent;
import com.itelasoft.pso.beans.StudentFundingSource;
import com.itelasoft.pso.beans.StudentGroup;
import com.itelasoft.pso.beans.Vehicle;
import com.itelasoft.pso.beans.WeekDay;
import com.itelasoft.util.SortGroupedStudentBySequence;
import com.itelasoft.util.SortObjectByName;

@ManagedBean(name = "transportManagerModel")
@SessionScoped
public class TransportManager extends UIModel {

	private List<Program> transportPrograms;
	private Program transportPro, tmpProgram;
	private Long selectedVehicleId;
	private List<SelectItem> vehicleSelectItems;
	private String searchProgramText;
	private String searchText, flag;
	private List<Student> availableStudents;
	private List<ProgramEvent> availableEvents;
	private boolean visibleEnrollStd;
	private TimeBean startTime = new TimeBean();;
	private TimeBean endTime = new TimeBean();;
	private TimeBean actualStartTime = new TimeBean();
	private TimeBean actualEndTime = new TimeBean();
	private TimeBean returnStartTime = new TimeBean();
	private TimeBean returnEndTime = new TimeBean();
	private ProgramEvent proEvent;
	private List<SelectItem> coordinatorSelectItems;
	private Long selectedCoordinatorId;
	private Long selectedProEventId;
	private List<SelectItem> proEventSelectItems;
	private List<SelectItem> locationSelectItems;
	private Student student;
	private StaffMember staffMember;
	private List<GroupedStudentReturn> studentList;
	private GroupedStudent returnStudent, groupedStudent, tmpGS, tmpReturnGS, deleteStudent;
	private GroupedStudent groStu;
	private GroupedStaff groupedStaff, returnStaff;
	private Long mainPickupId, mainDropoffId, returnPickupId, returnDropoffId;
	private List<String> selectedWeekDayIds, selectedStaffDays, selectedReturnDays, allWeekDayIds;
	private Date firstDate;
	private Date secondDate;
	private boolean visibleGenerateEvent;
	private StudentGroup studentGroup, returnGroup, tmpStdGroup;
	// private TransportRet returnGroup;
	private List<StudentGroup> allGroups;
	private List<SelectItem> groupSelectItems;
	private Long selectedGroupId;
	private int selectedTabIndex, groupTabIndex;
	private String recurrenceType;
	private String proEventStatus;
	private Vehicle vehicle;
	private int photoW, photoH;
	private boolean setActualEventTimes;
	private String eventStatus;
	private boolean visibleStatusConfirmation;
	private String confirmationStatusMessage;
	private ProgramType programType;
	private boolean visibleCheckStudentEvents;
	private boolean selectAllEvents;
	private HashMap<Long, ProgramEvent> enrolledEvents;
	private List<StaffMember> availableStaff, staffList;
	// private boolean selectAllStaff;
	// private HashMap<Long, StaffMember> enrolledStaff;
	private String searchValue;
	private boolean visibleEnrollStaff;
	private double eventChargeAmount;
	private SimpleDateFormat mydateFormat = new SimpleDateFormat("EEEE");
	private List<SelectItem> weekDays = new ArrayList<SelectItem>();
	private List<ProgramType> types;
	private List<SelectItem> NDISSelectItems;
	private Long selectedNdisItemId;
	private NdisSupportItem supportItem;
	private Boolean excluded;
	private boolean showExcludeStdPopup, exclude, delete;
	private GroupedStudent excludedStd;
	private List<NdisCommittedEvent> committedEvents;
	private boolean showCommittedEvent, changeNdisCluster;

	public TransportManager() {
		init();
	}

	public void init() {
		types = new ArrayList<ProgramType>();
		programType = serviceLocator.getProgramTypeService().retrieveByName("Transport");
		if (programType != null) {
			types.add(programType);
			transportPrograms = serviceLocator.getProgramService().listByCriteria(null, types, null, false);
		} else {
			transportPrograms = null;
			showError("There is an error with the program types. Please contact the system addministrator.");
			return;
		}
		selectedTabIndex = 0;
		transportPro = tmpProgram = null;
		proEvent = null;
		selectedGroupId = selectedProEventId = null;
		visibleEnrollStd = visibleGenerateEvent = visibleEnrollStaff = false;
		searchValue = "name";
		visibleStatusConfirmation = showCommittedEvent = changeNdisCluster = false;
		searchProgramText = "";
		confirmationStatusMessage = "";
		selectedNdisItemId = new Long(0);
		vehicleSelectItems = new ArrayList<SelectItem>();
		locationSelectItems = new ArrayList<SelectItem>();
		committedEvents = new ArrayList<NdisCommittedEvent>();
		availableStaff = serviceLocator.getStaffMemberService().listActiveStaffMembers();
		List<Location> locations = serviceLocator.getLocationService().findAll();
		if (!locations.isEmpty()) {
			locationSelectItems.add(new SelectItem(0, "Please Select"));
			for (Location loc : locations) {
				locationSelectItems.add(new SelectItem(loc.getId(), loc.getName()));
			}
		} else {
			locationSelectItems.add(new SelectItem(0, "Not Available"));
		}
		NDISSelectItems = new ArrayList<SelectItem>();
		List<NdisSupportItem> supportitems = serviceLocator.getNdisSupportItemService().listByType("Transport");

		if (supportitems != null && !supportitems.isEmpty()) {

			// NDISSelectItems.add(new SelectItem(0, "Not Assigned"));
			for (NdisSupportItem item : supportitems) {

				if (item.getItemName().equals("gggg"))
					continue;
				NDISSelectItems.add(new SelectItem(item.getId(), item.getItemName()));
			}
			Collections.sort(NDISSelectItems, new Comparator<SelectItem>() {

				@Override
				public int compare(SelectItem item1, SelectItem item2) {
					String lb1 = item1.getLabel();
					String lb2 = item2.getLabel();
					return lb1.toLowerCase().compareTo(lb2.toLowerCase());
				}
			});
			NDISSelectItems.add(0, new SelectItem(0, "Not Assigned"));

		} else {
			NDISSelectItems.add(new SelectItem(0, "Not Available"));
		}
	}

	public void tabChangeListner(TabChangeEvent event) {
		if (transportPro != null) {
			clearInputs();
			if (event.getOldTabIndex() == 0) {
				if (validateTransportProgram()) {
					selectedTabIndex = event.getNewTabIndex();
				} else
					selectedTabIndex = 0;
			} else {
				selectedTabIndex = event.getNewTabIndex();
			}
			if (selectedTabIndex == 0) {
				if (transportPro != null)
					vehicle = transportPro.getVehicle();
				if (vehicle != null)
					selectedVehicleId = vehicle.getId();
				else
					selectedVehicleId = new Long(0);
				// initVehicleImage(vehicle);
				loadCoordinators();
				if (transportPro.getCoordinator() != null)
					selectedCoordinatorId = transportPro.getCoordinator().getId();
				else
					selectedCoordinatorId = new Long(0);
				loadVehicles();
			}
			if (selectedTabIndex == 1) {
				if (selectedGroupId != null && selectedGroupId != 0)
					selectStudentGroup(selectedGroupId);
			}
			if (selectedTabIndex == 2) {
				refreshProEventsSelectItems();
				if (proEvent != null) {
					if (selectedGroupId != null && proEvent.getGroup().getId().equals(selectedGroupId)) {
						loadProgramEvent();
					} else {
						selectedProEventId = null;
						proEvent = null;
						selectedVehicleId = new Long(0);
					}
					/*
					 * if (proEvent.getCoordinator() != null) {
					 * selectedCoordinatorId =
					 * proEvent.getCoordinator().getId(); } else {
					 * selectedCoordinatorId = new Long(0); }
					 */
				}
			}
		} else
			showError("Please select a cost center.");
	}

	public void addNewTransport() {
		clearInputs();
		if (tmpProgram != null)
			tmpProgram.setUi_selected(false);
		transportPro = new Program();
		selectedTabIndex = 0;
		selectedCoordinatorId = new Long(0);
		selectedVehicleId = new Long(0);
		vehicle = null;
		selectedProEventId = selectedGroupId = null;
		proEvent = null;
		studentGroup = null;
		selectedNdisItemId = new Long(0);
		setActiveProgram();
	}

	public void selectTransport(ClickActionEvent re) {
		clearInputs();
		if (tmpProgram != null)
			tmpProgram.setUi_selected(false);
		tmpProgram = (Program) re.getComponent().getAttributes().get("transport");
		tmpProgram.setUi_selected(true);
		transportPro = serviceLocator.getProgramService().retrieve(tmpProgram.getId());
		if (transportPro.getCoordinator() != null)
			selectedCoordinatorId = transportPro.getCoordinator().getId();
		else
			selectedCoordinatorId = Long.valueOf(0);
		if (transportPro.getVehicle() != null) {
			selectedVehicleId = transportPro.getVehicle().getId();
			vehicle = transportPro.getVehicle();
			// initVehicleImage(vehicle);
		} else {
			selectedVehicleId = new Long(0);
		}
		selectedProEventId = selectedGroupId = null;
		groupTabIndex = 0;
		proEvent = null;
		studentGroup = null;
		setActiveProgram();
	}

	private void loadVehicles() {
		vehicleSelectItems.clear();
		List<Vehicle> vehicles = serviceLocator.getVehicleService().listActiveVehicles();
		if (proEvent != null) {
			if (proEvent.getVehicle() != null && !proEvent.getVehicle().getStatus().equals("Active")) {
				vehicles.add(proEvent.getVehicle());
			}
		} else {
			if (vehicle != null && !vehicle.getStatus().equals("Active")) {
				vehicles.add(transportPro.getVehicle());
			}
		}
		if (!vehicles.isEmpty()) {
			vehicleSelectItems.add(new SelectItem(0, "Not Assigned"));
			for (Vehicle vehi : vehicles) {
				vehicleSelectItems.add(new SelectItem(vehi.getId(), vehi.getName()));
			}
		} else {
			vehicleSelectItems.add(new SelectItem(0, "Not Available"));
		}
	}

	private void loadCoordinators() {
		coordinatorSelectItems = new ArrayList<SelectItem>();
		List<StaffMember> tmpStaff = new ArrayList<StaffMember>();
		tmpStaff.addAll(availableStaff);
		// List<StaffMember> staffMembers =
		// serviceLocator.getStaffMemberService()
		// .listActiveStaffMembers();
		if (transportPro.getCoordinator() != null && !transportPro.getCoordinator().getStatus().equals("Current")) {
			tmpStaff.add(transportPro.getCoordinator());
		}
		if (selectedTabIndex == 2) {
			if (proEvent != null && proEvent.getCoordinator() != null) {
				boolean found = false;
				for (StaffMember sm : tmpStaff) {
					if (sm.getId().equals(proEvent.getCoordinator().getId())) {
						found = true;
						break;
					}
				}
				if (!found)
					tmpStaff.add(proEvent.getCoordinator());
			}
		}
		if (tmpStaff != null && !tmpStaff.isEmpty()) {
			coordinatorSelectItems.add(new SelectItem(0, "Not Assigned"));
			for (StaffMember member : tmpStaff) {
				coordinatorSelectItems.add(new SelectItem(member.getId(), member.getContact().getName()));
			}
		} else {
			coordinatorSelectItems.add(new SelectItem(0, "Not Available"));
		}
	}

	private void setActiveProgram() {
		if (transportPro != null) {
			if (transportPro.getId() != null) {
				transportPro = serviceLocator.getProgramService().retrieve(transportPro.getId());
				sessionContext.setActiveString(transportPro.getName());
			} else
				sessionContext.setActiveString("New-Program");
			refreshStudentGroupsSelectItems();
			loadVehicles();
			loadCoordinators();
		} else
			sessionContext.setActiveString("");
	}

	public void searchPrograms() {
		try {
			// See if the user has entered an ID instead name
			Long id = Long.parseLong(searchProgramText);
			Program pro = serviceLocator.getProgramService().retrieveById(id, types);
			if (pro == null) {
				showError("No transport program available for this id");
			} else {
				transportPrograms.clear();
				transportPrograms.add(pro);
				transportPro = tmpProgram = null;
				selectedTabIndex = 0;
			}
		} catch (NumberFormatException nFE) {
			// User has entered a name
			List<Program> transportPrograms = serviceLocator.getProgramService().listByCriteria(searchProgramText,
					types, null, false);
			if (transportPrograms == null || transportPrograms.isEmpty())
				showError("No results found for the given search text.");
			else {
				this.transportPrograms = transportPrograms;
				transportPro = tmpProgram = null;
				selectedTabIndex = 0;
			}
		} catch (Exception exception) {
			showExceptionAsError(exception);
		}
	}

	public void saveTransport(ActionEvent event) {
		if (validateTransportProgram()) {
			confirmationStatusMessage = "";
			StaffMember staff;
			if (selectedCoordinatorId > 0)
				staff = serviceLocator.getStaffMemberService().retrieve(selectedCoordinatorId);
			else
				staff = null;
			transportPro.setCoordinator(staff);
			if (vehicle != null && vehicle.getStatus().equals("Inactive") && staff != null
					&& !staff.getStatus().equals("Current")) {
				confirmationStatusMessage = "Vehicle of this program is inactive and also The cordinator of this program is not a current staff member. Are you sure you want to continue?";
				visibleStatusConfirmation = true;
			} else if (vehicle != null && vehicle.getStatus().equals("Inactive")) {
				confirmationStatusMessage = "Vehicle of this program is inactive. Are you sure you want to continue?";
				visibleStatusConfirmation = true;
			} else if (staff != null && !staff.getStatus().equals("Current")) {
				confirmationStatusMessage = "Cordinator of this program is not a current staff member. Are you sure you want to continue?";
				visibleStatusConfirmation = true;
			} else {
				saveTransport();
			}
		}
	}

	public void saveTransport() {
		if (transportPro.getId() == null) {
			transportPro.setType(programType);
			transportPro = serviceLocator.getProgramService().create(transportPro);
			tmpProgram = transportPro;
			tmpProgram.setUi_selected(true);
			transportPrograms.add(tmpProgram);
			transportPro = serviceLocator.getProgramService().retrieve(tmpProgram.getId());
			showInfo("Transport created successfully..");
		} else {
			transportPro = serviceLocator.getProgramService().update(transportPro);
			transportPro.setUi_selected(true);
			transportPrograms.set(transportPrograms.indexOf(tmpProgram), transportPro);
			tmpProgram = transportPro;
			transportPro = serviceLocator.getProgramService().retrieve(tmpProgram.getId());
			showInfo("Transport updated successfully..");
		}
		setActiveProgram();
	}

	public void deleteTransport() {
		try {
			serviceLocator.getProgramService().delete(transportPro.getId());
			transportPrograms.remove(tmpProgram);
			transportPro = tmpProgram = null;
			showInfo("Transport deleted succesfully.");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public void purgeTransport() {
		try {
			serviceLocator.getProgramService().deleteWithDependencies(transportPro.getId());
			transportPrograms.remove(tmpProgram);
			transportPro = tmpProgram = null;
			showInfo("Transport and all it's dependencies are purged successfully.");
		} catch (Exception d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public void selectVehicle(ValueChangeEvent ve) {
		selectedVehicleId = (Long) ve.getNewValue();
		String reference = (String) ve.getComponent().getAttributes().get("ref");
		if (selectedVehicleId != null && selectedVehicleId > 0) {
			vehicle = serviceLocator.getVehicleService().retrieve(selectedVehicleId);
			// initVehicleImage(vehicle);
		} else
			vehicle = null;
		if (reference.equals("program"))
			transportPro.setVehicle(vehicle);
		if (reference.equals("group"))
			studentGroup.setVehicle(vehicle);
		if (reference.equals("event"))
			proEvent.setVehicle(vehicle);
	}

	@SuppressWarnings("unused")
	private void initVehicleImage(Vehicle vehicle) {
		byte[] tmpData = null;
		if (vehicle != null && vehicle.getPhoto() != null) {
			tmpData = vehicle.getPhoto().getBlobFileData().getData();
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

	public boolean validateTransportProgram() {
		List<String> componentIds = new ArrayList<String>();
		clearInputs();
		if (validateString(transportPro.getName())) {
			if (validateDates(transportPro.getStartDate(), transportPro.getEndDate()))
				return true;
			else
				return false;
		} else {
			componentIds.add("input_TrosportProgramName");
			highlightInputs(componentIds);
			showError("Cost center name can not be empty..");
			return false;
		}
	}

	private boolean validateDates(Date startDate, Date endDate) {
		List<String> componentIds = new ArrayList<String>();
		clearInputs();
		if (startDate != null && endDate != null)
			if (endDate.compareTo(startDate) < 0) {
				showError("Start date should be before the end date.");
				componentIds.add("input_ProgramStartDate");
				componentIds.add("input_ProgramEndDate");
				highlightInputs(componentIds);
				return false;
			} else
				return true;
		else {
			if (startDate == null) {
				showError("Start Date fields can not be empty.");
				componentIds.add("input_ProgramStartDate");
				highlightInputs(componentIds);
				return false;
			}
			if (endDate == null) {
			}
			showError("End Date fields can not be empty.");
			componentIds.add("input_ProgramEndDate");
			highlightInputs(componentIds);
			return false;
		}
	}

	private boolean validateString(String string) {
		if (string != null && !string.isEmpty())
			return true;
		else
			return false;
	}

	public void confirmationStatusAction(ActionEvent ae) {
		visibleStatusConfirmation = false;
		String action = (String) ae.getComponent().getAttributes().get("confirmation");
		if (action.equals("Yes")) {
			if (proEvent != null) {
				Vehicle vehicle = proEvent.getVehicle();
				if (vehicle != null && vehicle.getStatus().equals("Inactive")) {
					confirmationStatusMessage = "Vehicle of this event is Inactive. Are you sure you want to continue?";
					visibleStatusConfirmation = true;
				} else {
					saveProgramEvent();
				}
			} else
				saveTransport();
		}
	}

	/*
	 * Student Extra Event Functions
	 */

	public void addRemoveFromEvents(ActionEvent event) {
		groStu = (GroupedStudent) event.getComponent().getAttributes().get("student");
		returnStudent = null;
		if (returnGroup != null && studentGroup.isReturnAvailable() && returnGroup.getStudents() != null) {
			for (GroupedStudent std : returnGroup.getStudents()) {
				if (groStu.getStudent().getId().equals(std.getStudent().getId())) {
					returnStudent = std;
					break;
				}
			}
		}
		availableEvents = serviceLocator.getProgramEventService().listEditableEventsByGroupedStudent(groStu, null);
		if (returnStudent != null) {
			if (availableEvents == null)
				availableEvents = new ArrayList<ProgramEvent>();
			List<ProgramEvent> returnEvents = serviceLocator.getProgramEventService()
					.listEditableEventsByGroupedStudent(returnStudent, null);
			if (returnEvents != null)
				for (ProgramEvent returnEvent : returnEvents) {
					returnEvent.setEventColor("return");
					availableEvents.add(returnEvent);
				}
		}
		if (availableEvents == null || availableEvents.isEmpty()) {
			showError("There are no editable present or future events found.");
		} else {
			selectAllEvents = false;
			enrolledEvents = new HashMap<Long, ProgramEvent>();
		}
		enrollStudentEventsPopup();
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
		if (!enrolledEvents.isEmpty()) {
			List<ProgramEvent> deleteMainList = new ArrayList<ProgramEvent>();
			List<ProgramEvent> deleteReturnList = new ArrayList<ProgramEvent>();
			int notAdded = 0;
			int added = 0;
			int deleted = 0;
			for (ProgramEvent event : enrolledEvents.values()) {
				if (event.getStatus().equals("Add")) {
					StudentFundingSource sfs = null;
					StudentEvent stuEvent = new StudentEvent();
					if (event.getEventColor() != null && event.getEventColor().equals("return")) {
						stuEvent.setGroupedStudent(returnStudent);
						sfs = serviceLocator.getStudentFundingSourceService().getRelatedStudentFundingSource(
								returnStudent.getStudent().getId(), event.getEventDate());
					} else {
						stuEvent.setGroupedStudent(groStu);
						sfs = serviceLocator.getStudentFundingSourceService()
								.getRelatedStudentFundingSource(groStu.getStudent().getId(), event.getEventDate());
					}
					if (sfs == null) {
						notAdded++;
						continue;
					}
					stuEvent.setStdFundingSrc(sfs);
					stuEvent.setProEvent(event);
					stuEvent = serviceLocator.getStudentEventService().create(stuEvent);
					added++;
				} else {
					if (event.getEventColor() != null && event.getEventColor().equals("return"))
						deleteReturnList.add(event);
					else
						deleteMainList.add(event);
					deleted++;
				}
			}
			if (!deleteMainList.isEmpty()) {
				serviceLocator.getStudentEventService().deleteByProEventsAndStudent(deleteMainList,
						groStu.getStudent().getId());

			}
			if (!deleteReturnList.isEmpty()) {
				serviceLocator.getStudentEventService().deleteByProEventsAndStudent(deleteReturnList,
						returnStudent.getStudent().getId());
			}
			if (deleted != 0)
				showInfo(deleted + " event(s) removed successfully.");
			if (added != 0)
				showInfo(added + " event(s) added successfully.");
			if (notAdded != 0)
				showInfo(notAdded + " event(s) not deleted. No active funding source found.");
		}
		enrollStudentEventsPopup();
	}

	/*
	 * Group Functions
	 */

	public void addNewGroup() {
		clearInputs();
		changeNdisCluster = false;
		if (transportPro.getStatus().equals("Inactive"))
			showError("Operation not allowed. Program status marked as inactive..");
		else {
			selectedGroupId = null;
			groupTabIndex = 0;
			studentGroup = new StudentGroup();
			returnGroup = null;
			studentGroup.setProgram(transportPro);
			//studentGroup.setChargeAmount(studentGroup.getProgram().getChargeAmount());
			studentGroup.setcAmount(null);
			recurrenceType = "Daily";
			studentGroup.setStartDate(studentGroup.getProgram().getStartDate());
			studentGroup.setEndDate(studentGroup.getProgram().getEndDate());
			selectedWeekDayIds = new ArrayList<String>();
			if (transportPro.getVehicle() != null) {
				// initVehicleImage(transportPro.getVehicle());
				studentGroup.setVehicle(transportPro.getVehicle());
				selectedVehicleId = transportPro.getVehicle().getId();
			} else {
				studentGroup.setVehicle(null);
				selectedVehicleId = new Long(0);
			}
			startTime = new TimeBean();
			endTime = new TimeBean();
			returnStartTime = new TimeBean();
			returnEndTime = new TimeBean();
		}
	}

	public void selectStudentGroup(ValueChangeEvent ve) {
		clearInputs();
		Long id = (Long) ve.getNewValue();
		if (id != null && id != 0) {
			selectStudentGroup(id);
		}
	}

	public void selectNdisItem(ValueChangeEvent ve) {
		selectedNdisItemId = (Long) ve.getNewValue();
		if (studentGroup.getNdis() != null) {
			if (studentGroup.getNdis().getId() != selectedNdisItemId) {
				changeNdisCluster = true;
			} else {
				changeNdisCluster = false;
			}

		} else {
			changeNdisCluster = false;
		}
		if (selectedNdisItemId != null && selectedNdisItemId != 0) {
			supportItem = serviceLocator.getNdisSupportItemService().retrieve(selectedNdisItemId);
		}
	}

	private void selectStudentGroup(Long groupId) {
		setActualEventTimes = false;
		clearInputs();
		studentGroup = serviceLocator.getStudentGroupService().retrieveWithData(groupId, false);
		tmpStdGroup = new StudentGroup();
		tmpStdGroup = serviceLocator.getStudentGroupService().retrieveWithData(groupId, false);
		// System.out.println(tmpStdGroup.getNdis().getItemName());

		if (studentGroup != null) {
			// studentGroup.setStudents(serviceLocator.getGroupedStudentService()
			// .listByGroup(studentGroup.getId()));
			if (studentGroup.getStartTime() != null)
				startTime = new TimeBean(studentGroup.getStartTime());
			else
				startTime = new TimeBean();
			if (studentGroup.getEndTime() != null)
				endTime = new TimeBean(studentGroup.getEndTime());
			else
				endTime = new TimeBean();
			if (studentGroup.getVehicle() != null) {
				selectedVehicleId = studentGroup.getVehicle().getId();
				// initVehicleImage(studentGroup.getVehicle());
			} else
				selectedVehicleId = new Long(0);
			selectedWeekDayIds = new ArrayList<String>();
			if (!studentGroup.getWeekDays().isEmpty()) {
				recurrenceType = "Weekly";
				for (WeekDay day : studentGroup.getWeekDays()) {
					selectedWeekDayIds.add(day.getId().toString());
				}
			} else {
				recurrenceType = "Daily";
			}
			returnGroup = serviceLocator.getStudentGroupService().getReturnGroup(studentGroup.getId(), true);
			if (returnGroup != null) {
				studentGroup.setReturnAvailable(true);
				returnStartTime = new TimeBean(returnGroup.getStartTime());
				returnEndTime = new TimeBean(returnGroup.getEndTime());
			} else {
				studentGroup.setReturnAvailable(false);
				returnStartTime = new TimeBean();
				returnEndTime = new TimeBean();
			}
			if (studentGroup.getStudents() != null) {
				Collections.sort(studentGroup.getStudents(), new SortGroupedStudentBySequence());
				updateReturnSequence();
				returnExclude();// add table data to array list
			}
			if (studentGroup.getNdis() != null)
				selectedNdisItemId = studentGroup.getNdis().getId();
			else
				selectedNdisItemId = new Long(0);
			
			studentGroup.setcAmount(String.valueOf(studentGroup.getChargeAmount()));
		}
	}

	public void saveStudentGroup() {
		if (validateStudentGroup()) {
			boolean found = false;
			if (changeNdisCluster) {
				committedEvents = serviceLocator.getNdisCommittedEventService()
						.ndisCommittedEventsListByGroup(studentGroup.getId());
				if (studentGroup.isReturnAvailable())
					committedEvents.addAll(serviceLocator.getNdisCommittedEventService()
							.ndisCommittedEventsListByGroup(returnGroup.getId()));
				if (!committedEvents.isEmpty()) {
					showCommittedEventPopUp();
					found = true;
				}
			}
			if (!found) {
				saveGroup();
			}
		}
	}

	public void showCommittedEventPopUp() {
		showCommittedEvent = !showCommittedEvent;
	}

	public void cancelUpdateCEvent() {
		showCommittedEventPopUp();
		changeNdisCluster = false;
		saveGroup();
	}

	public void UpdateCommittedEvents() {
		List<NdisCommittedEvent> committedEvnts = new ArrayList<NdisCommittedEvent>();
		for (NdisCommittedEvent cEvent : committedEvents) {
			cEvent.setNdisSupportCluster(studentGroup.getNdis());
			if (cEvent.getClusterOverride() != null) {
				cEvent.setClusterOverride(studentGroup.getNdis());
			}
			committedEvnts.add(cEvent);
			// serviceLocator.getNdisCommittedEventService().update(cEvent);
		}
		serviceLocator.getNdisCommittedEventService().calculateCommittedEventPrice(committedEvnts, true);
		changeNdisCluster = false;
		showCommittedEventPopUp();
		saveGroup();
		showInfo("Committed Events Updated.");
	}

	public void saveGroup() {
		if (studentGroup.getId() != null) {
			if (selectedNdisItemId != null && selectedNdisItemId != 0) {
				studentGroup.setNdis(supportItem);
			}
			updateGroupedStaffs(studentGroup);
			studentGroup = serviceLocator.getStudentGroupService().update(studentGroup);
			showInfo("Transport program saved successfully");
			showInfo(
					"The changes doesn't affect to the existing events. Please go to event schedule and update them if neccessary..");
			refreshStudentGroupsSelectItems();

		} else {
			if (selectedNdisItemId != null && selectedNdisItemId != 0) {
				studentGroup.setNdis(supportItem);
			}
			studentGroup = serviceLocator.getStudentGroupService().create(studentGroup);
			groupSelectItems.add(new SelectItem(studentGroup.getId(), studentGroup.getName()));
			showInfo("New Transport program created successfully..");
			selectedGroupId = studentGroup.getId();
		}
		if (studentGroup.isReturnAvailable()) {
			if (returnGroup == null) {
				returnGroup = new StudentGroup();
				returnGroup.setProgram(studentGroup.getProgram());
			}
			returnGroup.setName(studentGroup.getName() + " (Return)");
			returnGroup.setStartDate(studentGroup.getStartDate());
			returnGroup.setEndDate(studentGroup.getEndDate());
			returnGroup.setAllowProgramtorunonaholiday(studentGroup.isAllowProgramtorunonaholiday());
			returnGroup.setStartTime(returnStartTime.getDateTime(new Date()));
			returnGroup.setEndTime(returnEndTime.getDateTime(new Date()));
			returnGroup.setChargeAmount(studentGroup.getChargeAmount());
			returnGroup.setNdis(studentGroup.getNdis());
			// returnGroup.setStatus(studentGroup.getStatus());
			// returnGroup.setLocation(studentGroup.getLocation());//RIDMA
			returnGroup.setVehicle(studentGroup.getVehicle());
			// returnGroup.setPhoto(studentGroup.getPhoto());
			returnGroup.setWeekDays(new ArrayList<WeekDay>());
			if (recurrenceType.equals("Weekly")) {
				returnGroup.getWeekDays().addAll(studentGroup.getWeekDays());
			}
			if (returnGroup.getId() == null) {
				returnGroup.setParentGroup(studentGroup);
				if (studentGroup.getStudents() != null) {
					returnGroup.setStudents(new ArrayList<GroupedStudent>());
					for (GroupedStudent student : studentGroup.getStudents()) {
						returnGroup.getStudents().add(createReturnStudent(student, returnGroup));
					}
				}
				if (studentGroup.getStaffMembers() != null && !studentGroup.getStaffMembers().isEmpty()) {
					returnGroup.setStaffMembers(new ArrayList<GroupedStaff>());
					for (GroupedStaff staff : studentGroup.getStaffMembers()) {
						returnGroup.getStaffMembers().add(createReturnStaff(staff, returnGroup));
					}
				}
				serviceLocator.getStudentGroupService().create(returnGroup);
			} else {
				updateGroupedStaffs(returnGroup);
				serviceLocator.getStudentGroupService().update(returnGroup);
			}
		} else if (returnGroup != null && returnGroup.getId() != null) {
			try {
				serviceLocator.getStudentGroupService().delete(returnGroup.getId());
				returnGroup = null;
			} catch (DataIntegrityViolationException e) {
				showError("Return group can not be deleted. The record is currently in use.");
				studentGroup.setReturnAvailable(true);
			}
		}
	}

	public void deleteStudentGroup() {
		try {
			if (returnGroup != null) {
				serviceLocator.getStudentGroupService().delete(returnGroup.getId());
				returnGroup = null;
			}
			serviceLocator.getStudentGroupService().delete(studentGroup.getId());
			selectedGroupId = null;
			studentGroup = null;
			showInfo("Transport program deleted successfully..");
			refreshStudentGroupsSelectItems();
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	private void refreshStudentGroupsSelectItems() {
		groupSelectItems = new ArrayList<SelectItem>();
		List<StudentGroup> groups = serviceLocator.getStudentGroupService()
				.listMainTranportGroups(transportPro.getId());
		if (groups != null && !groups.isEmpty()) {
			for (StudentGroup group : groups) {
				groupSelectItems.add(new SelectItem(group.getId(), group.getName()));
			}
		}
	}

	private boolean validateStudentGroup() {
		List<String> componentIds = new ArrayList<String>();
		clearInputs();
		if (!validateString(studentGroup.getName())) {
			showError("Transport program name should not be empty..");
			componentIds.add("input_textStudentGroupName");
			highlightInputs(componentIds);
			return false;
		}
		if (!serviceLocator.getStudentGroupService().validateGroupName(studentGroup)) {
			showError("Transport program name is already exists..");
			componentIds.add("input_textStudentGroupName");
			highlightInputs(componentIds);
			return false;
		}
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
		if (studentGroup.getStartDate() == null) {
			showError("Start Date can not be empty..");
			componentIds.add("input_groupStartDate");
			highlightInputs(componentIds);
			return false;
		}
		if (studentGroup.getEndDate() == null) {
			showError("End Date can not be empty..");
			componentIds.add("input_groupEndDate");
			highlightInputs(componentIds);
			return false;
		}
		if (studentGroup.getStartDate().compareTo(transportPro.getStartDate()) < 0
				|| studentGroup.getStartDate().compareTo(transportPro.getEndDate()) > 0) {
			showError(
					"Invalid start date! Program Start Date does not match with the Cost center start date/end date.");
			return false;
		}
		if (studentGroup.getEndDate().compareTo(transportPro.getEndDate()) > 0) {
			showError("Invalid End date! Program End Date does not match with the Cost center end date.");
			return false;
		}
		if (!validateDates(studentGroup.getStartDate(), studentGroup.getEndDate())) {
			componentIds.add("input_groupStartDate");
			highlightInputs(componentIds);
			return false;
		}
		if (validateProEventTime(startTime, endTime, new Date(), false)) {
			studentGroup.setStartTime(startTime.getDateTime(new Date()));
			studentGroup.setEndTime(endTime.getDateTime(new Date()));
		} else {
			return false;
		}
		if (studentGroup.isReturnAvailable()) {
			if (!validateProEventTime(returnStartTime, returnEndTime, new Date(), true)) {
				return false;
			}

			if (endTime.getDateTime(new Date()).after(returnStartTime.getDateTime(new Date()))) {
				showError("Return event time can not be earlier than main event time.");
				return false;
			}
		}
		studentGroup.setWeekDays(new ArrayList<WeekDay>());
		if (recurrenceType.equals("Weekly")) {
			if (!selectedWeekDayIds.isEmpty()) {
				for (String id : selectedWeekDayIds) {
					studentGroup.getWeekDays().add(serviceLocator.getWeekDayService().retrieve(new Long(id)));
				}
			} else {
				showError("At least one week day should be selected.");
				return false;
			}
		}
		if (selectedVehicleId != null && selectedVehicleId != 0) {
			studentGroup.setVehicle(serviceLocator.getVehicleService().retrieve(selectedVehicleId));
		} else {
			showError("Please select a Vehicle.");
			componentIds.add("select_TransportGrpVehicle");
			highlightInputs(componentIds);
			return false;
		}
		return true;
	}

	public void addEditGroups() {
		selectedTabIndex = 1;
		groupTabIndex = 0;
		studentGroup = null;
	}

	private void updateGroupedStaffs(StudentGroup group) {
		if (group.getStaffMembers() != null) {
			if (!group.getWeekDays().isEmpty()) {
				List<WeekDay> notAssignedList = listNotAssignedWeekDays(selectedWeekDayIds);
				List<WeekDay> removeList = new ArrayList<WeekDay>();
				for (GroupedStaff stf : group.getStaffMembers()) {
					for (GroupedStaffWeekday day : stf.getAssignedDays()) {
						if (notAssignedList.contains(day.getWeekday())) {
							removeList.add(day.getWeekday());
						}
					}
					if (!removeList.isEmpty())
						stf.getAssignedDays().removeAll(removeList);
				}
			}
		}
	}

	/*
	 * GroupedStudent Functions
	 */

	public void enrollNewStudents() {
		if (transportPro.getStatus().equals("Inactive"))
			showError("Operation not allowed. Program status marked as inactive..");
		else {
			flag = "new";
			searchText = "";
			if (returnGroup != null) {
				availableStudents = serviceLocator.getStudentService()
						.retrieveAvailableByTwoGroups(studentGroup.getId(), returnGroup.getId());
			} else {
				availableStudents = serviceLocator.getStudentService()
						.retrieveAvailableByTwoGroups(studentGroup.getId(), null);
			}
			if (availableStudents == null || availableStudents.isEmpty()) {
				showError("There are no new " + Util.getMessage("students_label").toLowerCase()
						+ " available for this program.");
			} else {
				student = null;
				groupedStudent = null;
				if (studentGroup.getStudents() == null)
					studentGroup.setStudents(new ArrayList<GroupedStudent>());
				if (returnGroup != null && returnGroup.getStudents() == null)
					returnGroup.setStudents(new ArrayList<GroupedStudent>());
				enrollStudentPopup();
			}
		}
	}

	public void searchStudents() {
		try {
			// See if the user has entered an ID instead name
			Long id = Long.parseLong(searchText);
			Student student = new Student();
			for (Student students : availableStudents) {
				if (students.getId().equals(id)) {
					student = students;
				}
			}
			if (student.getId() != null) {
				availableStudents.clear();
				availableStudents.add(student);
			} else {
				showError("No results for the given search text.");
			}
		} catch (NumberFormatException nFE) {
			// User has entered a name
			if (searchText.equals("")) {
				if (returnGroup != null) {
					availableStudents = serviceLocator.getStudentService()
							.retrieveAvailableByTwoGroups(studentGroup.getId(), returnGroup.getId());
				} else {
					availableStudents = serviceLocator.getStudentService()
							.retrieveAvailableByTwoGroups(studentGroup.getId(), null);
				}
			} else {
				Pattern name = Pattern.compile(searchText.toLowerCase());
				List<Student> temp = new ArrayList<Student>();
				for (Student std : availableStudents) {
					Matcher match = name.matcher(std.getContact().getName().toLowerCase());
					if (match.find()) {
						temp.add(std);
					}
				}
				if (temp.size() > 0) {
					availableStudents.clear();
					availableStudents.addAll(temp);
				} else {
					showError("No results for the given search text.");
				}
			}
		} catch (Exception exception) {
			showExceptionAsError(exception);
		}
	}

	public void selectStudentTransport(ClickActionEvent re) {
		student = (Student) re.getComponent().getAttributes().get("transtu");
		student.setUi_selected(true);
		groupedStudent = null;
		if (studentGroup.getStudents() != null) {
			for (GroupedStudent std : studentGroup.getStudents()) {
				if (std.getStudent().getId().equals(student.getId())) {
					groupedStudent = std;
					break;
				}
			}
		}
		if (groupedStudent != null) {
			setStudentInfo();
		} else {
			// TODO: Please check the following. I have commented my code which
			// was there earlier and left your code.
			// if you look <GroupedStudent tmpGroupedStudent = groupedStudent;>
			// here tmpGroupedStudent will always be NULL because of the above
			// condition

			/*
			 * groupedStudent = new GroupedStudent();
			 * groupedStudent.setStudent(student);
			 * groupedStudent.setGroup(studentGroup);
			 * groupedStudent.setStatus("Active"); mainPickupId = mainDropoffId
			 * = returnPickupId = returnDropoffId = new Long( 0);
			 */
			GroupedStudent tmpGroupedStudent = groupedStudent;
			groupedStudent = new GroupedStudent();
			groupedStudent.setSequence(getNextSequence(studentGroup));
			groupedStudent.setStudent(student);
			groupedStudent.setGroup(studentGroup);
			groupedStudent.setStatus("Active");
			groupedStudent.setChargeAmount(studentGroup.getChargeAmount());
			if (tmpGroupedStudent != null) {
				groupedStudent.setDropoff(tmpGroupedStudent.getDropoff());
				groupedStudent.setPickup(tmpGroupedStudent.getPickup());
				mainPickupId = tmpGroupedStudent.getPickup().getId();
				mainDropoffId = tmpGroupedStudent.getDropoff().getId();
				returnPickupId = tmpGroupedStudent.getDropoff().getId();
				returnDropoffId = tmpGroupedStudent.getPickup().getId();
			}
			if (returnGroup != null) {
				returnStudent = new GroupedStudent();
				returnStudent.setChargeAmount(returnGroup.getChargeAmount());
				returnGroup.setReturnAvailable(false);
			}
		}
	}

	public boolean enrollStudent() {
		if (validateGroupedStudent()) {
			boolean newGS = (groupedStudent.getId() == null);
			studentGroup = serviceLocator.getStudentGroupService().update(studentGroup);
			if (returnGroup != null) {
				if (!returnGroup.isReturnAvailable() && returnStudent != null) {
					// returnGroup.getStudents().remove(returnStudent);
					updateSequence(returnStudent, returnGroup, true);
				}
				returnGroup = serviceLocator.getStudentGroupService().update(returnGroup);
			}
			if (newGS)
				showInfo(Util.getMessage("student_label") + " enrolled successfully.");
			else
				showInfo(Util.getMessage("student_label") + " updated successfully.");
			student.setUi_selected(false);
			student = null;
			groupedStudent = null;
			returnStudent = null;
			if (returnGroup != null)
				returnGroup.setReturnAvailable(false);
			Collections.sort(studentGroup.getStudents(), new SortGroupedStudentBySequence());
			updateReturnSequence();
			if (returnGroup != null) {
				availableStudents = serviceLocator.getStudentService()
						.retrieveAvailableByTwoGroups(studentGroup.getId(), returnGroup.getId());
			} else {
				availableStudents = serviceLocator.getStudentService()
						.retrieveAvailableByTwoGroups(studentGroup.getId(), null);
			}
			return true;
		}
		return false;
	}

	public void enrollStudentNExit() {
		if (enrollStudent())
			visibleEnrollStd = false;
	}

	public void editStudent(ActionEvent ae) {
		tmpGS = (GroupedStudent) ae.getComponent().getAttributes().get("student");
		groupedStudent = serviceLocator.getGroupedStudentService().retrieve(tmpGS.getId());
		flag = "edit";
		student = groupedStudent.getStudent();
		setStudentInfo();
		enrollStudentPopup();
	}

	public void removeStudent(ActionEvent ae) {
		GroupedStudent mainStd = (GroupedStudent) ae.getComponent().getAttributes().get("student");
		deleteStudent = mainStd;
		delete = true;
		exclude = excluded = false;
		GroupedStudent returnStd = null;
		if (returnGroup != null && returnGroup.getStudents() != null) {
			for (GroupedStudent std : returnGroup.getStudents()) {
				if (std.getStudent().getId().equals(mainStd.getStudent().getId())) {
					returnStd = std;
					break;
				}
			}
		}
		// int index = studentGroup.getStudents().indexOf(mainStd);
		updateSequence(mainStd, studentGroup, true);
		// int returnIndex = -1;
		if (returnStd != null) {
			// returnIndex = returnGroup.getStudents().indexOf(returnStd);
			updateSequence(returnStd, returnGroup, true);
		}
		try {
			serviceLocator.getStudentGroupService().updateGroups(studentGroup, returnGroup);
			updateReturnSequence();
		} catch (Exception e) {
			// studentGroup.getStudents().add(index, mainStd);
			studentGroup.setStudents(serviceLocator.getGroupedStudentService().listByGroup(studentGroup.getId()));
			updateSequence(mainStd, studentGroup, false);
			if (returnStd != null) {
				// returnGroup.getStudents().add(returnIndex, returnStd);
				returnGroup.setStudents(serviceLocator.getGroupedStudentService().listByGroup(returnGroup.getId()));
				// updateSequence(returnStd, returnGroup, false);
				updateReturnSequence();
			}
			Collections.sort(studentGroup.getStudents(), new SortGroupedStudentBySequence());
			showError(Util.getMessage("error.integrity"));
		}
		returnExclude();
		/*
		 * GroupedStudent returnStd = null; if (returnGroup != null &&
		 * returnGroup.getStudents() != null) { for (GroupedStudent std :
		 * returnGroup.getStudents()) { if (std.getStudent().getId()
		 * .equals(mainStd.getStudent().getId())) { returnStd = std; break; } }
		 * } int index = studentGroup.getStudents().indexOf(mainStd); int
		 * returnIndex = -1; if (returnStd != null) { List<StudentEvent> events
		 * = serviceLocator.getStudentEventService()
		 * .listByGroupedStudent(returnStd.getId()); if (events != null &&
		 * !events.isEmpty()) { showError(Util.getMessage("error.integrity"));
		 * return; } returnIndex = returnGroup.getStudents().indexOf(returnStd);
		 * updateSequence(returnStd, returnGroup, true); }
		 * updateSequence(mainStd, studentGroup, true); try { studentGroup =
		 * serviceLocator.getStudentGroupService().update( studentGroup); if
		 * (returnGroup != null) returnGroup =
		 * serviceLocator.getStudentGroupService().update( returnGroup);
		 * showInfo("Student deleted successfully."); } catch
		 * (DataIntegrityViolationException d) {
		 * showError(Util.getMessage("error.integrity"));
		 * studentGroup.getStudents().add(index, mainStd); if (returnGroup !=
		 * null && returnStd != null) returnGroup.getStudents().add(returnIndex,
		 * returnStd); //
		 * studentGroup.setStudents(serviceLocator.getGroupedStudentService() //
		 * .listByGroup(studentGroup.getId())); }
		 */
	}

	private boolean validateGroupedStudent() {
		Location mainPickup, mainDropoff, returnPickup = null, returnDropoff = null;
		int ms = groupedStudent.getSequence();
		if (ms == 0 || ms < 0
				|| (!studentGroup.getStudents().isEmpty()
						&& (groupedStudent.getId() == null && ms > studentGroup.getStudents().size() + 1)
						|| (groupedStudent.getId() != null && ms > studentGroup.getStudents().size()))) {
			showError("Main sequence is not valid.");
			return false;
		}
		mainPickup = serviceLocator.getLocationService().retrieve(mainPickupId);
		mainDropoff = serviceLocator.getLocationService().retrieve(mainDropoffId);
		if (mainPickup == null || mainDropoff == null) {
			showError("Please select pick-up and drop-off locations..");
			return false;
		}
		if (mainPickup.getName().equals(mainDropoff.getName())) {
			showError("Pick-up and Drop-off locations can not be same..");
			return false;
		}
		int rs = 0;
		if (returnGroup != null && returnGroup.isReturnAvailable()) {
			rs = returnStudent.getSequence();
			if (rs == 0 || rs < 0
					|| (!returnGroup.getStudents().isEmpty()
							&& (returnStudent.getId() == null && rs > returnGroup.getStudents().size() + 1)
							|| (returnStudent.getId() != null && rs > returnGroup.getStudents().size()))) {
				showError("Return sequence is not valid.");
				return false;
			}
			returnPickup = serviceLocator.getLocationService().retrieve(returnPickupId);
			returnDropoff = serviceLocator.getLocationService().retrieve(returnDropoffId);
			if (returnPickup == null || returnDropoff == null) {
				showError("Please select return pick-up and drop-off locations..");
				return false;
			}
			if (returnPickup.getName().equals(returnDropoff.getName())) {
				showError("Return Pick-up and Drop-off locations can not be same..");
				return false;
			}
		}
		groupedStudent.setPickup(mainPickup);
		groupedStudent.setDropoff(mainDropoff);
		if (groupedStudent.isUi_selected())
			groupedStudent.setStatus("Excluded");
		else
			groupedStudent.setStatus("Active");
		if (groupedStudent.getId() == null) {
			studentGroup.getStudents().add(groupedStudent);
		} else {
			studentGroup.getStudents().set(studentGroup.getStudents().indexOf(tmpGS), groupedStudent);
		}
		updateSequence(groupedStudent, studentGroup, false);
		if (returnGroup != null && returnGroup.isReturnAvailable()) {
			if (returnStudent.isUi_selected())
				returnStudent.setStatus("Excluded");
			else
				returnStudent.setStatus("Active");
			returnStudent.setPickup(returnPickup);
			returnStudent.setDropoff(returnDropoff);
			if (returnStudent.getId() == null) {
				returnGroup.getStudents().add(returnStudent);
			} else {
				returnGroup.getStudents().set(returnGroup.getStudents().indexOf(tmpReturnGS), returnStudent);
			}
			updateSequence(returnStudent, returnGroup, false);
		}
		return true;
	}

	private GroupedStudent createReturnStudent(GroupedStudent groupedStudent, StudentGroup returnGroup) {
		GroupedStudent returnStudent = new GroupedStudent();
		returnStudent.setStudent(groupedStudent.getStudent());
		returnStudent.setStatus(groupedStudent.getStatus());
		returnStudent.setGroup(returnGroup);
		returnStudent.setPickup(groupedStudent.getDropoff());
		returnStudent.setDropoff(groupedStudent.getPickup());
		return returnStudent;
	}

	public void toggleReturnStudent(ValueChangeEvent event) {
		Boolean value = (Boolean) event.getNewValue();
		if (value != null && value && studentGroup.isReturnAvailable()) {
			returnStudent = null;
			if (returnGroup.getStudents() != null) {
				for (GroupedStudent std : returnGroup.getStudents()) {
					if (std.getStudent().getId().equals(student.getId())) {
						returnStudent = std;
						returnDropoffId = returnStudent.getPickup().getId();
						returnPickupId = returnStudent.getDropoff().getId();
						returnStudent.setChargeAmount(returnGroup.getChargeAmount());
						break;
					}
				}
			}
			if (returnStudent == null) {
				returnStudent = createReturnStudent(groupedStudent, returnGroup);
				returnStudent.setSequence(getNextSequence(returnGroup));
				returnStudent.setChargeAmount(returnGroup.getChargeAmount());
				if (mainPickupId != null)
					returnDropoffId = new Long(mainPickupId);
				if (mainDropoffId != null)
					returnDropoffId = new Long(mainPickupId);
			}
		}
	}

	private int getNextSequence(StudentGroup studentGroup) {
		int sequence = 0;
		if (studentGroup != null && studentGroup.getStudents() != null) {
			sequence = studentGroup.getStudents().size() + 1;
		}
		return sequence;
	}

	public void updateSequence(GroupedStudent groupedStudent, StudentGroup studentGroup, boolean remove) {
		int sequence = groupedStudent.getSequence();
		if (remove) {
			studentGroup.getStudents().remove(groupedStudent);
			if (studentGroup.getStudents().size() + 1 > sequence) {
				for (GroupedStudent gs : studentGroup.getStudents()) {
					if (gs.getSequence() > sequence)
						gs.setSequence(gs.getSequence() - 1);

				}
			}
			return;
		}
		if (groupedStudent.getId() == null) {
			for (GroupedStudent gs : studentGroup.getStudents()) {
				if (gs.getId() != null && gs.getSequence() >= sequence)
					gs.setSequence(gs.getSequence() + 1);
			}
		} else {
			GroupedStudent similar = null;
			for (GroupedStudent s : studentGroup.getStudents()) {
				if (s.getId() != null && !s.getId().equals(groupedStudent.getId()) && s.getSequence() == sequence) {
					similar = s;
					break;
				}
			}
			if (similar != null) {
				for (int count = 1; count <= studentGroup.getStudents().size(); count++) {
					boolean found = false;
					for (GroupedStudent s : studentGroup.getStudents()) {
						if (s.getSequence() == count) {
							found = true;
							break;
						}
					}
					if (!found) {
						similar.setSequence(count);
						break;
					}
				}
			}
		}
	}

	private void updateReturnSequence() {
		if (studentGroup.getStudents() != null) {
			if (returnGroup != null && returnGroup.getStudents() != null) {
				for (GroupedStudent gs : studentGroup.getStudents()) {
					boolean found = false;
					for (GroupedStudent rs : returnGroup.getStudents()) {
						if (gs.getStudent().getId().equals(rs.getStudent().getId())) {
							found = true;
							gs.setTmpString(String.valueOf(rs.getSequence()));
							break;
						}
					}
					if (!found)
						gs.setTmpString("N/A");
				}
			} else {
				for (GroupedStudent gs : studentGroup.getStudents()) {
					gs.setTmpString("N/A");
				}
			}
		}
		returnExclude();
	}

	private void returnExclude() {

		studentList = new ArrayList<GroupedStudentReturn>();
		if (studentGroup.getStudents() != null) {
			if (returnGroup != null && returnGroup.getStudents() != null) {

				for (GroupedStudent std : studentGroup.getStudents()) {
					if (std.getStatus().equals("Excluded"))
						std.setUi_selected(true);
					else
						std.setUi_selected(false);

					GroupedStudentReturn sr = new GroupedStudentReturn();
					sr.setMainStd(std);
					studentList.add(sr);
					for (GroupedStudent rtn : returnGroup.getStudents()) {
						if (std.getStudent().getId().equals(rtn.getStudent().getId())) {
							if (rtn.getStatus().equals("Excluded"))
								rtn.setUi_selected(true);
							else
								rtn.setUi_selected(false);
							sr.setReturnStd(rtn);
							break;
						}
					}
				}
			} else {
				for (GroupedStudent std : studentGroup.getStudents()) {
					std.setTmpString("N/A");
					if (std.getStatus().equals("Excluded"))
						std.setUi_selected(true);
					else
						std.setUi_selected(false);
					GroupedStudentReturn sr = new GroupedStudentReturn();
					sr.setMainStd(std);
					studentList.add(sr);
				}

			}
		}
		Collections.sort(studentList, new SortObjectByName());
	}

	public List<GroupedStudentReturn> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<GroupedStudentReturn> studentList) {
		this.studentList = studentList;
	}

	public void excludeStudent(ValueChangeEvent vce) {
		excluded = (Boolean) vce.getNewValue();
		// String type = (String)
		// vce.getComponent().getAttributes().get("type");
		GroupedStudent student = (GroupedStudent) vce.getComponent().getAttributes().get("student");
		excludedStd = student;
		if (!checkStudentCommittedEvents(student, new Date())) {
			if (excluded != null && excluded) {
				// if (type.equals("main")){
				student.setStatus("Excluded");
				serviceLocator.getGroupedStudentService().update(student);
				showInfo("Selected " + Util.getMessage("student_label").toLowerCase()
						+ " excluded from the group successfully..");
				// if(type.equals("return")){
				// student.setStatus("Excluded");
				// serviceLocator.getGroupedStudentService().update(student);
				// showInfo("Selected " +
				// Util.getMessage("student_label").toLowerCase() + " excluded
				// from the group successfully..");}

			} else {
				student.setStatus("Active");
				serviceLocator.getGroupedStudentService().update(student);
				showInfo("Selected " + Util.getMessage("student_label").toLowerCase()
						+ " included to the group successfully..");

			}
		}

	}

	public boolean checkStudentCommittedEvents(GroupedStudent student, Date date) {
		List<NdisCommittedEvent> committedEvents = new ArrayList<NdisCommittedEvent>();
		committedEvents = serviceLocator.getNdisCommittedEventService()
				.SelectedStudentEvents(student.getStudent().getId(), date, student.getGroup().getId());
		if (!committedEvents.isEmpty() && excluded) {
			showExcludeStdPopup = true;
			return true;
		}
		return false;
	}

	public void updateCEventsWithExcludedStudents() {
		if (excluded != null && excluded) {
			// if (type.equals("main")){
			excludedStd.setStatus("Excluded");
			serviceLocator.getGroupedStudentService().update(excludedStd);
			showInfo("Selected " + Util.getMessage("student_label").toLowerCase()
					+ " excluded from the group successfully..");
			// if(type.equals("return")){
			// student.setStatus("Excluded");
			// serviceLocator.getGroupedStudentService().update(student);
			// showInfo("Selected " +
			// Util.getMessage("student_label").toLowerCase() + " excluded from
			// the group successfully..");}

		} else {
			excludedStd.setStatus("Active");
			serviceLocator.getGroupedStudentService().update(excludedStd);
			showInfo("Selected " + Util.getMessage("student_label").toLowerCase()
					+ " included to the group successfully..");

		}
		serviceLocator.getNdisCommittedEventService().deleteSelectedStudentEvents(excludedStd.getStudent().getId(),
				new Date(), excludedStd.getGroup().getId());
		showInfo("Deleted Commited Events successfully");
		showExcludeStdPopup = false;
	}

	public void hideExcludeStdPopup() {
		showExcludeStdPopup = false;
		excludedStd.setUi_selected(false);

	}

	private void setStudentInfo() {
		mainPickupId = groupedStudent.getPickup().getId();
		mainDropoffId = groupedStudent.getDropoff().getId();
		if (groupedStudent.getStatus().equals("Excluded"))
			groupedStudent.setUi_selected(true);
		else
			groupedStudent.setUi_selected(false);
		returnStudent = tmpReturnGS = null;
		if (returnGroup != null && studentGroup.isReturnAvailable() && returnGroup.getStudents() != null) {
			for (GroupedStudent std : returnGroup.getStudents()) {
				if (std.getStudent().getId().equals(student.getId())) {
					tmpReturnGS = std;
					returnStudent = serviceLocator.getGroupedStudentService().retrieve(tmpReturnGS.getId());
					break;
				}
			}
		}
		if (returnStudent != null) {
			if (returnStudent.getStatus().equals("Excluded"))
				returnStudent.setUi_selected(true);
			else
				returnStudent.setUi_selected(false);
			returnGroup.setReturnAvailable(true);
			returnPickupId = returnStudent.getPickup() != null ? returnStudent.getPickup().getId() : null;
			returnDropoffId = returnStudent.getDropoff() != null ? returnStudent.getDropoff().getId() : null;
		} else {
			returnPickupId = returnDropoffId = new Long(0);
			if (returnGroup != null)
				returnGroup.setReturnAvailable(false);
		}
	}

	public void enrollStudentPopup() {
		if (visibleEnrollStd) {
			visibleEnrollStd = false;

		} else
			visibleEnrollStd = true;
	}

	/*
	 * GroupedStaff Functions
	 */

	public void enrollNewStaff() {
		if (transportPro.getStatus().equals("Inactive"))
			showError("Operation not allowed. Program status marked as inactive..");
		else {
			staffMember = null;
			searchText = "";
			flag = "new";
			initWeekdays();
			// availableStaff = serviceLocator
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
				if (returnGroup != null && returnGroup.getStaffMembers() == null)
					returnGroup.setStaffMembers(new ArrayList<GroupedStaff>());
				for (GroupedStaff s : studentGroup.getStaffMembers()) {
					for (int i = 0; i < staffList.size(); i++) {
						StaffMember staff = staffList.get(i);
						if (s.getStaffMember().getId() == staff.getId()) {
							staffList.remove(i);
							break;
						}
					}
				}
				enrollStaffPopup();
			}
		}
	}

	/*
	 * public void searchStaff() { // enrolledStaff.clear(); // selectAllStaff =
	 * false; staffList.clear(); if (searchValue.equals("id") &&
	 * !searchText.equals("")) { // StaffMember staff =
	 * serviceLocator.getStaffMemberService() //
	 * .listAvailableByStaffIdNGroup(studentGroup.getId(), // searchText);
	 * StaffMember staff =
	 * serviceLocator.getStaffMemberService().searchByStaffId(searchText); if
	 * (staff == null || !staff.getStatus().equals("Current")) showError(
	 * "No result available for this Id."); else { boolean available = false;
	 * for (GroupedStaff groupedstaff : studentGroup.getStaffMembers()) { if
	 * (groupedstaff.getStaffMember().getId() == staff.getId()) { available =
	 * true; } } if (!available) { staffList.add(staff); } } } else { //
	 * List<StaffMember> staffMembers = serviceLocator //
	 * .getStaffMemberService().listAvailableByNameNGroup( //
	 * studentGroup.getId(), searchText); staffList =
	 * serviceLocator.getStaffMemberService().listAvailableByName(searchText);
	 * if (staffList == null || staffList.isEmpty()) { showError(
	 * "No results for the given search text."); } else if
	 * (studentGroup.getStaffMembers() != null) { for (GroupedStaff gs :
	 * studentGroup.getStaffMembers()) { for (int i = 0; i < staffList.size();
	 * i++) { StaffMember staff = staffList.get(i); if
	 * (gs.getStaffMember().getId() == staff.getId()) { staffList.remove(i);
	 * break; } } } } } }
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
			// if (list.isEmpty()) {
			// showError("No result available for this Id.");
			// } else {
			// boolean available = false;
			// for (GroupedStaff groupedstaff : studentGroup.getStaffMembers())
			// {
			// if (groupedstaff.getStaffMember().getId() == staff.getId()) {
			// available = true;
			// }
			// }
			// if (!available)
			// staffList.add(staff);
			// }
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
		groupedStaff = null;
		if (studentGroup.getStaffMembers() != null) {
			for (GroupedStaff stf : studentGroup.getStaffMembers()) {
				if (stf.getStaffMember().getId().equals(staffMember.getId())) {
					groupedStaff = stf;
					break;
				}
			}
		}
		if (groupedStaff != null) {
			setStaffInfo();
		} else {
			groupedStaff = new GroupedStaff();
			groupedStaff.setStaffMember(staffMember);
			groupedStaff.setGroup(studentGroup);
			groupedStaff.setStatus("Active");
			selectedStaffDays = new ArrayList<String>();
			if (studentGroup.getWeekDays().isEmpty())
				selectedStaffDays.addAll(listAssignedWeekDayIds(new ArrayList<GroupedStaffWeekday>()));
			else {
				for (WeekDay day : studentGroup.getWeekDays())
					if (!staffMember.getUnAvailableDays().contains(day))
						selectedStaffDays.add(day.getId().toString());
			}
			if (returnGroup != null)
				returnGroup.setReturnAvailable(false);
		}
		// Boolean selected = (Boolean) ve.getNewValue();
		// staff.setUi_selected(selected);
		// if (selected) {
		// enrolledStaff.put(staff.getId(), staff);
		// if (enrolledStaff.values().containsAll(availableStaff))
		// selectAllStaff = true;
		// } else {
		// selectAllStaff = false;
		// enrolledStaff.remove(staff.getId());
		// }
	}

	public void selectStaffWeekday(ValueChangeEvent e) {
		List<String> old = (List<String>) e.getOldValue();
		List<String> newl = (List<String>) e.getNewValue();
		boolean found = false;
		if (newl.size() > old.size()) {
			for (String newid : newl) {
				found = false;
				for (String oldid : old) {
					if (oldid.equals(newid)) {
						found = true;
						break;
					}
				}

				if (!found) {
					for (WeekDay unavailable : staffMember.getUnAvailableDays()) {
						if (newid.equals(unavailable.getId().toString())) {
							showError("Staff Member is not available on " + unavailable.getName());
						}
					}
				}
			}
		}

	}

	// public void selectAllStaff(ValueChangeEvent ve) {
	// Boolean selected = (Boolean) ve.getNewValue();
	// for (StaffMember staff : availableStaff) {
	// staff.setUi_selected(selected);
	// if (selected)
	// enrolledStaff.put(staff.getId(), staff);
	// else
	// enrolledStaff.remove(staff.getId());
	// }
	// }

	public boolean enrollStaff() {
		if (validateGroupedStaff()) {
			studentGroup = serviceLocator.getStudentGroupService().update(studentGroup);
			if (returnGroup != null) {
				if (!returnGroup.isReturnAvailable() && returnStaff != null)
					returnGroup.getStaffMembers().remove(returnStaff);
				returnGroup = serviceLocator.getStudentGroupService().update(returnGroup);
			}
			showInfo("Staff Member enrolled successfully.");
			staffMember.setUi_selected(false);
			staffMember = null;
			groupedStaff = null;
			returnStaff = null;
			if (returnGroup != null)
				returnGroup.setReturnAvailable(false);

			if (studentGroup.getStaffMembers() != null) {
				Collections.sort(studentGroup.getStaffMembers(), new SortObjectByName());
			}
			return true;
		}
		return false;
		// if (!enrolledStaff.isEmpty()) {
		// if (studentGroup.getStaffMembers() == null)
		// studentGroup.setStaffMembers(new ArrayList<StaffMember>());
		// if (studentGroup.isReturnAvailable()
		// && returnGroup.getStaffMembers() == null)
		// returnGroup.setStaffMembers(new ArrayList<StaffMember>());
		// for (StaffMember staff : enrolledStaff.values()) {
		// studentGroup.getStaffMembers().add(staff);
		// if (studentGroup.isReturnAvailable())
		// returnGroup.getStaffMembers().add(staff);
		// }
		// studentGroup = serviceLocator.getStudentGroupService().update(
		// studentGroup);
		// if (studentGroup.isReturnAvailable())
		// returnGroup = serviceLocator.getStudentGroupService().update(
		// returnGroup);
		// showInfo("Selected staff members(s) enrolled with the group
		// successfully.");
		// }
		// enrollStaffPopup();
	}

	public void enrollStaffNExit() {
		if (enrollStaff())
			visibleEnrollStaff = false;
	}

	public void editStaff(ActionEvent ae) {
		groupedStaff = (GroupedStaff) ae.getComponent().getAttributes().get("staff");
		initWeekdays();
		flag = "edit";
		staffMember = groupedStaff.getStaffMember();
		setStaffInfo();
		enrollStaffPopup();
	}

	public void removeStaff(ActionEvent ae) {
		GroupedStaff mainStaff = (GroupedStaff) ae.getComponent().getAttributes().get("staff");
		int index = studentGroup.getStaffMembers().indexOf(mainStaff);
		studentGroup.getStaffMembers().remove(mainStaff);
		int index1 = -1;
		GroupedStaff returnStaff = null;
		if (returnGroup != null && returnGroup.getStudents() != null) {
			for (GroupedStaff stf : returnGroup.getStaffMembers()) {
				if (stf.getStaffMember().getId().equals(mainStaff.getStaffMember().getId())) {
					returnStaff = stf;
					index1 = returnGroup.getStaffMembers().indexOf(returnStaff);
					break;
				}
			}
		}
		if (returnStaff != null) {
			returnGroup.getStaffMembers().remove(returnStaff);
		}
		try {
			studentGroup = serviceLocator.getStudentGroupService().update(studentGroup);
			if (returnGroup != null)
				returnGroup = serviceLocator.getStudentGroupService().update(returnGroup);
			showInfo("Staff Member deleted successfully.");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
			studentGroup.getStaffMembers().add(index, mainStaff);
			returnGroup.getStaffMembers().add(index1, returnStaff);
		}
		// try {
		// StaffMember staff = (StaffMember) ae.getComponent().getAttributes()
		// .get("staff");
		// studentGroup.getStaffMembers().remove(staff);
		// studentGroup = serviceLocator.getStudentGroupService().update(
		// studentGroup);
		// showInfo("Staff member deleted successfully..");
		// } catch (DataIntegrityViolationException d) {
		// showError(Util.getMessage("error.integrity"));
		// studentGroup = serviceLocator.getStudentGroupService()
		// .retrieveWithData(studentGroup.getId());
		// }
	}

	private boolean validateGroupedStaff() {
		if ((selectedStaffDays == null || selectedStaffDays.isEmpty())
				&& (selectedReturnDays == null || selectedReturnDays.isEmpty())) {
			showError("Staff Member should be assign at least to a single day.");
			return false;
		}
		if (returnGroup != null && returnGroup.isReturnAvailable()) {
			if (selectedReturnDays == null || selectedReturnDays.isEmpty()) {
				showError("Staff Member should be assign at least to a single day.");
				return false;
			}
			returnStaff.setAssignedDays(listAssignedWeekDays(selectedReturnDays, returnStaff));
			if (returnStaff.getId() == null)
				returnGroup.getStaffMembers().add(returnStaff);
		}
		if (!selectedStaffDays.isEmpty())
			groupedStaff.setAssignedDays(listAssignedWeekDays(selectedStaffDays, groupedStaff));
		if (groupedStaff.getId() == null) {
			studentGroup.getStaffMembers().add(groupedStaff);
		}
		return true;
	}

	private GroupedStaff createReturnStaff(GroupedStaff groupedStaff, StudentGroup returnGroup) {
		GroupedStaff returnStaff = new GroupedStaff();
		returnStaff.setStaffMember(groupedStaff.getStaffMember());
		returnStaff.setStatus(groupedStaff.getStatus());
		returnStaff.setGroup(returnGroup);
		for (GroupedStaffWeekday day : groupedStaff.getAssignedDays())
			returnStaff.getAssignedDays().add(day);
		return returnStaff;
	}

	public void toggleReturnStaff(ValueChangeEvent event) {
		Boolean value = (Boolean) event.getNewValue();
		if (value != null && value && returnGroup != null) {
			returnStaff = createReturnStaff(groupedStaff, returnGroup);
			selectedReturnDays = new ArrayList<String>(selectedStaffDays);
		}
	}

	private void setStaffInfo() {
		selectedStaffDays = new ArrayList<String>();
		selectedReturnDays = new ArrayList<String>();
		selectedStaffDays.addAll(listAssignedWeekDayIds(groupedStaff.getAssignedDays()));
		returnStaff = null;
		if (returnGroup != null && studentGroup.isReturnAvailable() && returnGroup.getStaffMembers() != null) {
			for (GroupedStaff stf : returnGroup.getStaffMembers()) {
				if (stf.getStaffMember().getId().equals(staffMember.getId())) {
					returnStaff = stf;
					break;
				}
			}
		}
		if (returnStaff != null) {
			returnGroup.setReturnAvailable(true);
			selectedReturnDays.addAll(listAssignedWeekDayIds(returnStaff.getAssignedDays()));
		} else if (returnGroup != null)
			returnGroup.setReturnAvailable(false);
	}

	private List<String> listAssignedWeekDayIds(List<GroupedStaffWeekday> assignList) {
		List<String> returnList = new ArrayList<String>();
		if (allWeekDayIds == null) {
			allWeekDayIds = new ArrayList<String>();
			for (WeekDay day : ((LookupListProvider) Util.getManagedBean("lookupListProvider")).getWeekDays()) {
				if (!staffMember.getUnAvailableDays().contains(day))
					allWeekDayIds.add(day.getId().toString());
			}
		}
		for (String id : allWeekDayIds) {
			// boolean found = false;
			for (GroupedStaffWeekday day : assignList) {
				if (id.equals(day.getWeekday().getId().toString())) {
					returnList.add(id);
					// found = true;
					// break;
				}
			}
			// if (!found) {
			// returnList.add(id);
			// }
		}
		return returnList.size() == 0 ? allWeekDayIds : returnList;
	}

	private List<GroupedStaffWeekday> listAssignedWeekDays(List<String> assignedWeekDayIds, GroupedStaff groupstaff) {
		List<GroupedStaffWeekday> returnList = new ArrayList<GroupedStaffWeekday>();
		for (WeekDay day : ((LookupListProvider) Util.getManagedBean("lookupListProvider")).getWeekDays()) {
			// boolean found = false;
			for (String id : assignedWeekDayIds) {
				if (id.equals(day.getId().toString())) {
					GroupedStaffWeekday gwd = new GroupedStaffWeekday();
					gwd.setGroupedstaff(groupstaff);
					gwd.setWeekday(day);
					returnList.add(gwd);
					// found = true;
					// break;
				}
			}
			// if (!found) {
			// returnList.add(day);
			// }
		}
		return returnList;
	}

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

	public void enrollStaffPopup() {
		if (visibleEnrollStaff)
			visibleEnrollStaff = false;
		else
			visibleEnrollStaff = true;
	}

	/*
	 * Transport Event Functions
	 */

	public void selectEventGroup(ValueChangeEvent event) {
		selectedGroupId = (Long) event.getNewValue();
		refreshProEventsSelectItems();
		selectedProEventId = null;
		proEvent = null;
	}

	public void createProgramEvent() {
		if (transportPro.getStatus().equals("Inactive"))
			showError("Operation not allowed. Program status marked as inactive..");
		else {
			proEvent = new ProgramEvent();
			// startTime = new TimeBean();
			// endTime = new TimeBean();
			actualStartTime = new TimeBean();
			actualEndTime = new TimeBean();
			setActualEventTimes = false;
			selectedProEventId = null;
			StudentGroup group = serviceLocator.getStudentGroupService().retrieveWithStaffMembers(selectedGroupId);
			if (group.getStartTime() != null)
				startTime = new TimeBean(group.getStartTime());
			else
				startTime = new TimeBean();
			if (group.getEndTime() != null)
				endTime = new TimeBean(group.getEndTime());
			else
				endTime = new TimeBean();
			proEvent.setChargeAmount(group.getChargeAmount());
			proEvent.setGroup(group);
			proEvent.setStatus("pending");
			eventStatus = "pending";
			loadCoordinators();
			if (transportPro.getCoordinator() != null)
				selectedCoordinatorId = transportPro.getCoordinator().getId();
			else
				selectedCoordinatorId = new Long(0);
			if (transportPro.getVehicle() != null && transportPro.getVehicle().getStatus().equals("Active")) {
				proEvent.setVehicle(transportPro.getVehicle());
				selectedVehicleId = transportPro.getVehicle().getId();
				// initVehicleImage(proEvent.getVehicle());
			} else {
				selectedVehicleId = new Long(0);
			}
			loadVehicles();
		}
	}

	public void selectProgramEvent(ValueChangeEvent ve) {
		Long id = (Long) ve.getNewValue();
		if (id != null && id > 0) {
			selectedProEventId = id;
			proEvent = serviceLocator.getProgramEventService().retrieve(selectedProEventId);
			loadProgramEvent();
			eventStatus = proEvent.getStatus();
		}
	}

	public void loadProgramEvent() {
		startTime = new TimeBean();
		endTime = new TimeBean();
		actualStartTime = new TimeBean();
		actualEndTime = new TimeBean();
		loadVehicles();
		if (proEvent.getVehicle() == null) {
			selectedVehicleId = new Long(0);
		} else {
			// initVehicleImage(proEvent.getVehicle());
			selectedVehicleId = proEvent.getVehicle().getId();
		}
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
			} else if (status.equals("pending")) {
				Vehicle vehicle = proEvent.getVehicle();
				if (vehicle != null && vehicle.getStatus().equals("Inactive")) {
					confirmationStatusMessage = "Vehicle of this event is Inactive. Are you sure you want to continue?";
					visibleStatusConfirmation = true;
				} else {
					saveProgramEvent();
				}
			}
		}
	}

	public void saveProgramEvent() {
		proEvent.setStartTime(startTime.getDateTime(proEvent.getEventDate()));
		proEvent.setEndTime(endTime.getDateTime(proEvent.getEventDate()));
		if (selectedCoordinatorId > 0) {
			if (proEvent.getCoordinator() == null || !proEvent.getCoordinator().getId().equals(selectedCoordinatorId)) {
				StaffMember coordinator = serviceLocator.getStaffMemberService().retrieve(selectedCoordinatorId);
				proEvent.setCoordinator(coordinator);
			}
		} else {
			proEvent.setCoordinator(null);
		}
		if (setActualEventTimes) {
			proEvent.setActualStartTime(actualStartTime.getDateTime(proEvent.getEventDate()));
			proEvent.setActualEndTime(actualEndTime.getDateTime(proEvent.getEventDate()));
		} else {
			proEvent.setActualStartTime(null);
			proEvent.setActualEndTime(null);
		}
		if (proEvent.getId() == null) {
			proEvent.setProgram(transportPro);
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

	private boolean validateProEventTime(TimeBean startTime, TimeBean endTime, Date eventDate, boolean isReturn) {
		List<String> componentIds = new ArrayList<String>();
		if (startTime.getHours() >= 0 && endTime.getHours() >= 0) {
			if (startTime.getDateTime(eventDate).before(endTime.getDateTime(eventDate))) {
				if (setActualEventTimes) {
					if (actualStartTime.getDateTime(eventDate).before(actualEndTime.getDateTime(eventDate))) {
						return true;
					}
				} else {
					return true;
				}
			}
			showError("Invalid time. Start time should be earlier than the end time");
			if (isReturn) {
				componentIds.add("select_grpReturnStartTime_hrs");
				componentIds.add("select_grpReturnStartTime_hrs1");
				componentIds.add("select_grpReturnStartTime_mins");
				componentIds.add("select_grpReturnStartTime_session");
				componentIds.add("select_grpReturnEndTime_hrs");
				componentIds.add("select_grpReturnEndTime_hrs1");
				componentIds.add("select_grpReturnEndTime_mins");
				componentIds.add("select_grpReturnEndTime_session");
			} else {
				componentIds.add("select_grpStartTime_hrs");
				componentIds.add("select_grpStartTime_hrs1");
				componentIds.add("select_grpStartTime_mins");
				componentIds.add("select_grpStartTime_session");
				componentIds.add("select_grpEndTime_hrs");
				componentIds.add("select_grpEndTime_hrs1");
				componentIds.add("select_grpEndTime_mins");
				componentIds.add("select_grpEndTime_session");
			}
			highlightInputs(componentIds);
			return false;
		} else {
			if (startTime.getHours() < 0) {
				if (isReturn) {
					showError("Return Start time can not be empty");
					componentIds.add("select_grpReturnStartTime_hrs");
					componentIds.add("select_grpReturnStartTime_hrs1");
					componentIds.add("select_grpReturnStartTime_mins");
					componentIds.add("select_grpReturnStartTime_session");
				} else {
					showError("Start time can not be empty");
					componentIds.add("select_grpStartTime_hrs");
					componentIds.add("select_grpStartTime_hrs1");
					componentIds.add("select_grpStartTime_mins");
					componentIds.add("select_grpStartTime_session");
				}
			}
			if (endTime.getHours() < 0) {
				if (isReturn) {
					showError("Return End time can not be empty");
					componentIds.add("select_grpReturnEndTime_hrs");
					componentIds.add("select_grpReturnEndTime_hrs1");
					componentIds.add("select_grpReturnEndTime_mins");
					componentIds.add("select_grpReturnEndTime_session");
				} else {
					showError("End time can not be empty");
					componentIds.add("select_grpEndTime_hrs");
					componentIds.add("select_grpEndTime_hrs1");
					componentIds.add("select_grpEndTime_mins");
					componentIds.add("select_grpEndTime_session");
				}
			}
			highlightInputs(componentIds);
			return false;
		}
	}

	private boolean validateProEventFields() {
		Date startDate = transportPro.getStartDate();
		Date endDate = transportPro.getEndDate();
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
					if (validateProEventTime(startTime, endTime, proEvent.getEventDate(), false)) {
						return true;
					} else {
						return false;
					}
				}
			}
		} else {
			showError("Event date can not be empty..");
			return false;
		}
	}

	public void createEvents() {
		if (transportPro.getStatus().equals("Inactive"))
			showError("Operation not allowed. Program status marked as inactive..");
		else {
			proEvent = null;
			startTime = new TimeBean();
			endTime = new TimeBean();
			firstDate = secondDate = null;
			StudentGroup group = serviceLocator.getStudentGroupService().retrieveWithStaffMembers(selectedGroupId);
			eventChargeAmount = group.getChargeAmount();
			if (group.getStartDate() != null)
				firstDate = group.getStartDate();
			if (group.getEndDate() != null)
				secondDate = group.getEndDate();
			if (group.getStartTime() != null)
				startTime = new TimeBean(group.getStartTime());
			if (group.getEndTime() != null)
				endTime = new TimeBean(group.getEndTime());
			loadCoordinators();
			if (transportPro.getCoordinator() != null)
				selectedCoordinatorId = transportPro.getCoordinator().getId();
			else
				selectedCoordinatorId = new Long(0);
			if (group.getVehicle() != null && group.getVehicle().getStatus().equals("Active"))
				selectedVehicleId = group.getVehicle().getId();
			else
				selectedVehicleId = new Long(0);
			selectedWeekDayIds = new ArrayList<String>();
			if (group.getWeekDays().isEmpty()) {
				recurrenceType = "Daily";
			} else {
				recurrenceType = "Weekly";
				for (WeekDay day : group.getWeekDays()) {
					selectedWeekDayIds.add(day.getId().toString());
				}
			}
			loadVehicles();
			showInfo("This will avoid creating events for holidays within the given date range..");
			genereteEventPopup();
		}
	}

	public void generateProgramEvent() {
		if (firstDate != null && secondDate != null) {
			if (validateProEventsFields()) {
				int createdEvents = 0;
				SimpleDateFormat formatDay = new SimpleDateFormat("EEEE");
				Calendar calDate = Calendar.getInstance();
				calDate.setTime(firstDate);
				calDate.add(Calendar.DATE, -1);
				StudentGroup group = serviceLocator.getStudentGroupService().retrieveWithStaffMembers(selectedGroupId);
				Vehicle vehicle = serviceLocator.getVehicleService().retrieve(selectedVehicleId);
				HashMap<String, WeekDay> weekDays = null;
				StaffMember coordinator = null;
				if (selectedCoordinatorId > 0)
					coordinator = serviceLocator.getStaffMemberService().retrieve(selectedCoordinatorId);
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
					proEvent.setProgram(transportPro);
					proEvent.setEventDate(calDate.getTime());
					proEvent.setStartTime(startTime.getDateTime(calDate.getTime()));
					proEvent.setEndTime(endTime.getDateTime(calDate.getTime()));
					proEvent.setCoordinator(coordinator);
					proEvent.setGroup(group);
					proEvent = setStaffEvents(proEvent);
					proEvent.setStatus("pending");
					proEvent.setVehicle(vehicle);
					proEvent.setChargeAmount(eventChargeAmount);
					proEvent = serviceLocator.getProgramEventService().create(proEvent);
					createdEvents++;
				} while (calDate.getTime().compareTo(secondDate) != 0);
				if (createdEvents > 0) {
					showInfo(String.valueOf(createdEvents) + " Event(s) created successfully..");
					refreshProEventsSelectItems();
					genereteEventPopup();
				} else
					showError(
							"There are no available dates to create events within the given date range or events exist.");
			}
		} else {
			showError("Dates should not be empty..");
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

	private boolean validateProEventsFields() {
		Date proStartDate = transportPro.getStartDate();
		Date proEndDate = transportPro.getEndDate();
		if (firstDate != null && secondDate != null) {
			if (firstDate.compareTo(proStartDate) < 0 || proEndDate.compareTo(firstDate) < 0
					|| secondDate.compareTo(proStartDate) < 0 || proEndDate.compareTo(secondDate) < 0) {
				showError("Events start/end dates do not match with the program start/end dates.");
				return false;
			} else {
				if (secondDate.compareTo(firstDate) < 0) {
					showError("Events start date should be before the events end date.");
					return false;
				} else {
					if (validateProEventTime(startTime, endTime, null, false)) {
						if (recurrenceType.equals("Weekly") && selectedWeekDayIds.isEmpty()) {
							showError("Please select weekday(s).");
							return false;
						} else
							return true;
					} else
						return false;
				}
			}
		} else {
			showError("Events start/end dates shold not be empty..");
			return false;
		}
	}

	public boolean isExpired(Date date) {
		if (date != null && date.before(new Date()))
			return true;
		else
			return false;
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

	public void genereteEventPopup() {
		if (visibleGenerateEvent) {
			visibleGenerateEvent = false;
			proEvent = null;
			selectedProEventId = null;
			selectedVehicleId = null;
		} else
			visibleGenerateEvent = true;
	}

	/*
	 * getters and setters
	 */

	public TimeZone getTimeZone() {
		return java.util.TimeZone.getDefault();
	}

	public void setTransportPrograms(List<Program> transportPrograms) {
		this.transportPrograms = transportPrograms;
	}

	public List<Program> getTransportPrograms() {
		return transportPrograms;
	}

	public void setTransportPro(Program transportPro) {
		this.transportPro = transportPro;
	}

	public Program getTransportPro() {
		return transportPro;
	}

	public void setSelectedVehicleId(Long selectedVehicleId) {
		this.selectedVehicleId = selectedVehicleId;
	}

	public Long getSelectedVehicleId() {
		return selectedVehicleId;
	}

	public void setVehicleSelectItems(List<SelectItem> vehicleSelectItems) {
		this.vehicleSelectItems = vehicleSelectItems;
	}

	public List<SelectItem> getVehicleSelectItems() {
		return vehicleSelectItems;
	}

	public String getSearchProgramText() {
		return searchProgramText;
	}

	public void setSearchProgramText(String searchProgramText) {
		this.searchProgramText = searchProgramText;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public List<Student> getAvailableStudents() {
		return availableStudents;
	}

	public void setAvailableStudents(List<Student> availableStudents) {
		this.availableStudents = availableStudents;
	}

	public boolean isVisibleEnrollStd() {
		return visibleEnrollStd;
	}

	public void setVisibleEnrollStd(boolean visibleEnrollStd) {
		this.visibleEnrollStd = visibleEnrollStd;
	}

	public void setStartTime(TimeBean startTime) {
		this.startTime = startTime;
	}

	public TimeBean getStartTime() {
		if (startTime.getSession() == Calendar.PM || startTime.getHours() == 12) {
			endTime.setSession(Calendar.PM);
			endTime.setSessionFixedToPM(true);
		} else if (endTime.isSessionFixedToPM()) {
			endTime.setSession(Calendar.AM);
			endTime.setSessionFixedToPM(false);
		}
		return startTime;
	}

	public TimeBean getEndTime() {
		if (endTime.getHours() != -1 && endTime.getHours() < startTime.getHours()) {
			endTime.setSession(Calendar.PM);
			endTime.setSessionFixedToPM(true);
		}
		return endTime;
	}

	public void setEndTime(TimeBean endTime) {
		this.endTime = endTime;
	}

	public ProgramEvent getProEvent() {
		return proEvent;
	}

	public void setProEvent(ProgramEvent proEvent) {
		this.proEvent = proEvent;
	}

	public List<SelectItem> getCoordinatorSelectItems() {
		return coordinatorSelectItems;
	}

	public void setCoordinatorSelectItems(List<SelectItem> coordinatorSelectItems) {
		this.coordinatorSelectItems = coordinatorSelectItems;
	}

	public Long getSelectedCoordinatorId() {
		return selectedCoordinatorId;
	}

	public void setSelectedCoordinatorId(Long selectedCoordinatorId) {
		this.selectedCoordinatorId = selectedCoordinatorId;
	}

	public Long getSelectedProEventId() {
		return selectedProEventId;
	}

	public void setSelectedProEventId(Long selectedProEventId) {
		this.selectedProEventId = selectedProEventId;
	}

	public List<SelectItem> getProEventSelectItems() {
		return proEventSelectItems;
	}

	public void setProEventSelectItems(List<SelectItem> proEventSelectItems) {
		this.proEventSelectItems = proEventSelectItems;
	}

	public List<SelectItem> getLocationSelectItems() {
		return locationSelectItems;
	}

	public void setLocationSelectItems(List<SelectItem> locationSelectItems) {
		this.locationSelectItems = locationSelectItems;
	}

	public Student getStudent() {
		return student;
	}

	public StaffMember getStaffMember() {
		return staffMember;
	}

	public Long getMainPickupId() {
		return mainPickupId;
	}

	public void setMainPickupId(Long mainPickupId) {
		this.mainPickupId = mainPickupId;
	}

	public Long getMainDropoffId() {
		return mainDropoffId;
	}

	public void setMainDropoffId(Long mainDropoffId) {
		this.mainDropoffId = mainDropoffId;
	}

	public Long getReturnPickupId() {
		return returnPickupId;
	}

	public void setReturnPickupId(Long returnPickupId) {
		this.returnPickupId = returnPickupId;
	}

	public Long getReturnDropoffId() {
		return returnDropoffId;
	}

	public void setReturnDropoffId(Long returnDropoffId) {
		this.returnDropoffId = returnDropoffId;
	}

	public List<String> getSelectedWeekDayIds() {
		return selectedWeekDayIds;
	}

	public void setSelectedWeekDayIds(List<String> selectedWeekDayIds) {
		this.selectedWeekDayIds = selectedWeekDayIds;
	}

	public List<String> getSelectedStaffDays() {
		return selectedStaffDays;
	}

	public void setSelectedStaffDays(List<String> selectedStaffDays) {
		this.selectedStaffDays = selectedStaffDays;
	}

	public List<String> getSelectedReturnDays() {
		return selectedReturnDays;
	}

	public void setSelectedReturnDays(List<String> selectedReturnDays) {
		this.selectedReturnDays = selectedReturnDays;
	}

	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

	public Date getFirstDate() {
		return firstDate;
	}

	public void setSecondDate(Date secondDate) {
		this.secondDate = secondDate;
	}

	public Date getSecondDate() {
		return secondDate;
	}

	public boolean isVisibleGenerateEvent() {
		return visibleGenerateEvent;
	}

	public void setVisibleGenerateEvent(boolean visibleGenerateEvent) {
		this.visibleGenerateEvent = visibleGenerateEvent;
	}

	public StudentGroup getStudentGroup() {
		return studentGroup;
	}

	public void setStudentGroup(StudentGroup studentGroup) {
		this.studentGroup = studentGroup;
	}

	public List<SelectItem> getGroupSelectItems() {
		return groupSelectItems;
	}

	public void setGroupSelectItems(List<SelectItem> groupSelectItems) {
		this.groupSelectItems = groupSelectItems;
	}

	public Long getSelectedGroupId() {
		return selectedGroupId;
	}

	public void setSelectedGroupId(Long selectedGroupId) {
		this.selectedGroupId = selectedGroupId;
	}

	public void setAllGroups(List<StudentGroup> allGroups) {
		this.allGroups = allGroups;
	}

	public List<StudentGroup> getAllGroups() {
		return allGroups;
	}

	public int getSelectedTabIndex() {
		return selectedTabIndex;
	}

	public void setSelectedTabIndex(int selectedTabIndex) {
		this.selectedTabIndex = selectedTabIndex;
	}

	public String getRecurrenceType() {
		return recurrenceType;
	}

	public void setRecurrenceType(String recurrenceType) {
		this.recurrenceType = recurrenceType;
	}

	public String getProEventStatus() {
		return proEventStatus;
	}

	public void setProEventStatus(String proEventStatus) {
		this.proEventStatus = proEventStatus;
	}

	public GroupedStudent getReturnStudent() {
		return returnStudent;
	}

	public void setReturnStudent(GroupedStudent returnStudent) {
		this.returnStudent = returnStudent;
	}

	public GroupedStudent getGroupedStudent() {
		return groupedStudent;
	}

	public void setGroupedStudent(GroupedStudent groupedStudent) {
		this.groupedStudent = groupedStudent;
	}

	public int getPhotoW() {
		return photoW;
	}

	public int getPhotoH() {
		return photoH;
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

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
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

	public String getConfirmationStatusMessage() {
		return confirmationStatusMessage;
	}

	public void setConfirmationStatusMessage(String confirmationStatusMessage) {
		this.confirmationStatusMessage = confirmationStatusMessage;
	}

	public ProgramType getProgramType() {
		return programType;
	}

	public void setAvailableEvents(List<ProgramEvent> availableEvents) {
		this.availableEvents = availableEvents;
	}

	public List<ProgramEvent> getAvailableEvents() {
		return availableEvents;
	}

	public void setVisibleCheckStudentEvents(boolean visibleCheckStudentEvents) {
		this.visibleCheckStudentEvents = visibleCheckStudentEvents;
	}

	public boolean isVisibleCheckStudentEvents() {
		return visibleCheckStudentEvents;
	}

	public void setSelectAllEvents(boolean selectAllEvents) {
		this.selectAllEvents = selectAllEvents;
	}

	public boolean isSelectAllEvents() {
		return selectAllEvents;
	}

	public void setGroStu(GroupedStudent groStu) {
		this.groStu = groStu;
	}

	public GroupedStudent getGroStu() {
		return groStu;
	}

	public void setEnrolledEvents(HashMap<Long, ProgramEvent> enrolledEvents) {
		this.enrolledEvents = enrolledEvents;
	}

	public HashMap<Long, ProgramEvent> getEnrolledEvents() {
		return enrolledEvents;
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

	public void setVisibleEnrollStaff(boolean visibleEnrollStaff) {
		this.visibleEnrollStaff = visibleEnrollStaff;
	}

	public boolean isVisibleEnrollStaff() {
		return visibleEnrollStaff;
	}

	public void setEventChargeAmount(double eventChargeAmount) {
		this.eventChargeAmount = eventChargeAmount;
	}

	public double getEventChargeAmount() {
		return eventChargeAmount;
	}

	public void setGroupTabIndex(int groupTabIndex) {
		this.groupTabIndex = groupTabIndex;
	}

	public int getGroupTabIndex() {
		return groupTabIndex;
	}

	private String getDateString(Date date) {
		if (date != null) {
			return mydateFormat.format(date);
		}
		return "";
	}

	public StudentGroup getReturnGroup() {
		return returnGroup;
	}

	public TimeBean getReturnStartTime() {
		if (returnStartTime.getSession() == Calendar.PM || returnStartTime.getHours() == 12) {
			returnEndTime.setSession(Calendar.PM);
			returnEndTime.setSessionFixedToPM(true);
		} else if (returnEndTime.isSessionFixedToPM()) {
			returnEndTime.setSession(Calendar.AM);
			returnEndTime.setSessionFixedToPM(false);
		}
		return returnStartTime;
	}

	public void setReturnStartTime(TimeBean returnStartTime) {
		this.returnStartTime = returnStartTime;
	}

	public TimeBean getReturnEndTime() {
		if (returnEndTime.getHours() != -1 && returnEndTime.getHours() < returnStartTime.getHours()) {
			returnEndTime.setSession(Calendar.PM);
			returnEndTime.setSessionFixedToPM(true);
		}
		return returnEndTime;
	}

	public void setReturnEndTime(TimeBean returnEndTime) {
		this.returnEndTime = returnEndTime;
	}

	public String getFlag() {
		return flag;
	}

	public void setReturnStaff(GroupedStaff returnStaff) {
		this.returnStaff = returnStaff;
	}

	public GroupedStaff getReturnStaff() {
		return returnStaff;
	}

	public void setGroupedStaff(GroupedStaff groupedStaff) {
		this.groupedStaff = groupedStaff;
	}

	public GroupedStaff getGroupedStaff() {
		return groupedStaff;
	}

	public List<SelectItem> getWeekDays() {
		return weekDays;
	}

	public void setStaffList(List<StaffMember> staffList) {
		this.staffList = staffList;
	}

	public List<StaffMember> getStaffList() {
		return staffList;
	}

	public List<SelectItem> getNDISSelectItems() {
		return NDISSelectItems;
	}

	public void setNDISSelectItems(List<SelectItem> nDISSelectItems) {
		NDISSelectItems = nDISSelectItems;
	}

	public Long getSelectedNdisItemId() {
		return selectedNdisItemId;
	}

	public void setSelectedNdisItemId(Long selectedNdisItemId) {
		this.selectedNdisItemId = selectedNdisItemId;
	}

	public NdisSupportItem getSupportItem() {
		return supportItem;
	}

	public void setSupportItem(NdisSupportItem supportItem) {
		this.supportItem = supportItem;
	}

	public Boolean isExcluded() {
		return excluded;
	}

	public void setExcluded(Boolean excluded) {
		this.excluded = excluded;
	}

	public boolean isShowExcludeStdPopup() {
		return showExcludeStdPopup;
	}

	public void setShowExcludeStdPopup(boolean showExcludeStdPopup) {
		this.showExcludeStdPopup = showExcludeStdPopup;
	}

	public GroupedStudent getExcludedStd() {
		return excludedStd;
	}

	public void setExcludedStd(GroupedStudent excludedStd) {
		this.excludedStd = excludedStd;
	}

	public boolean isExclude() {
		return exclude;
	}

	public void setExclude(boolean exclude) {
		this.exclude = exclude;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public GroupedStudent getDeleteStudent() {
		return deleteStudent;
	}

	public void setDeleteStudent(GroupedStudent deleteStudent) {
		this.deleteStudent = deleteStudent;
	}

	public List<NdisCommittedEvent> getCommittedEvents() {
		return committedEvents;
	}

	public void setCommittedEvents(List<NdisCommittedEvent> committedEvents) {
		this.committedEvents = committedEvents;
	}

	public boolean isShowCommittedEvent() {
		return showCommittedEvent;
	}

	public void setShowCommittedEvent(boolean showCommittedEvent) {
		this.showCommittedEvent = showCommittedEvent;
	}

	public StudentGroup getTmpStdGroup() {
		return tmpStdGroup;
	}

	public void setTmpStdGroup(StudentGroup tmpStdGroup) {
		this.tmpStdGroup = tmpStdGroup;
	}

	public boolean isChangeNdisCluster() {
		return changeNdisCluster;
	}

	public void setChangeNdisCluster(boolean changeNdisCluster) {
		this.changeNdisCluster = changeNdisCluster;
	}

}
