package com.itelasoft.pso.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.HtmlDataTable;
import com.icesoft.faces.component.paneltabset.TabChangeEvent;
import com.itelasoft.pso.beans.Collection;
import com.itelasoft.pso.beans.EnumPaymentType;
import com.itelasoft.pso.beans.GroupedStaff;
import com.itelasoft.pso.beans.GroupedStaffWeekday;
import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.Location;
import com.itelasoft.pso.beans.NdisPrice;
import com.itelasoft.pso.beans.Program;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.beans.StaffEvent;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.StudentEvent;
import com.itelasoft.pso.beans.StudentFundingSource;
import com.itelasoft.pso.beans.StudentGroup;
import com.itelasoft.pso.beans.Transaction;
import com.itelasoft.pso.beans.Transaction.EnumChargeType;
import com.itelasoft.pso.beans.Transaction.EnumTransactionType;
import com.itelasoft.pso.beans.TransportEventTableRow;
import com.itelasoft.pso.beans.TransportStaffEventTableRow;
import com.itelasoft.pso.beans.Vehicle;
import com.itelasoft.pso.beans.WeekDay;
import com.itelasoft.util.SortEventsBySequence;
import com.itelasoft.util.SortObjectByName;
import com.itelasoft.util.SortProgramEventsByDate;
import com.itelasoft.util.SortSelectItemsByLabel;

import net.sf.json.JSONObject;

@ManagedBean(name = "eventManagerModel")
@SessionScoped
public class EventManagerModel extends UIModel {
	private Date toDate;
	private HashMap<String, ProgramEvent> proEvents;
	private List<ProgramEvent> events;
	private List<ProgramEvent> tabs;
	private ProgramEvent programEvent;
	private HashMap<String, ProgramEvent> selectedEventsMap;
	private HashMap<Long, StudentEvent> eventsAttended, returnsAttended;
	private StudentEvent studentEvent;
	private boolean selectAllEvents, selectAllAtd, selectAllReturnAtd, visibleAttendance, selectAllStfAtd,
			selectAllStfReturnAtd;
	private int tabIndex = -1;
	private boolean visiblePayment, visibleCharges, visibleCharge, individual;
	private Transaction paymentTx;
	private Transaction chargeTx;
	private String eventStatus, returnStatus;
	private TimeBean rosterStartTime = new TimeBean();
	private TimeBean rosterEndTime = new TimeBean();
	private TimeBean actualStartTime = new TimeBean();
	private TimeBean actualEndTime = new TimeBean();
	// Transport actual return start time and end time
	private TimeBean actualReturnStartTime = new TimeBean();
	private TimeBean actualReturnEndTime = new TimeBean();
	private boolean setActualReturnEventTimes;
	private TimeBean returnStartTime = new TimeBean();
	private TimeBean returnEndTime = new TimeBean();
	private Long selectedVehicleId, returnVehicleId, selectedLocationId, selectedCoordinatorId,
			selectedReturnCoordinatorId, selectedProgramId, selectedGroupId;
	private String timePeriod;
	private List<SelectItem> programSelectItems;
	private List<SelectItem> groupSelectItems;
	private List<SelectItem> vehicleSelectItems;
	private List<SelectItem> locationSelectItems;
	private List<SelectItem> coordinatorSelectItems, returnCoordinatorSelectItems, tempCoordinators;
	private boolean setActualEventTimes, gstIncluded;
	private int photoW, photoH;
	private boolean visibleConfirmation;
	private String confirmationMessage;
	private List<String> selectedStatuses;
	private String jsonString;
	private List<ProgramType> programTypes, selectedProTypes;
	private double chargeAmount;
	private String txRemarks;
	private EnumPaymentType payType;
	private Long collectionId;
	private List<Collection> collection;
	private boolean selectAll;
	private List<StudentEvent> txStudentEvents;
	private List<SelectItem> paymentTypes;
	private String printStatus;
	private HashMap<Long, Program> programMap;
	private HtmlDataTable dataTable;
	private SimpleDateFormat mydateFormat = new SimpleDateFormat("EEEE");
	private List<TransportEventTableRow> transportStdEvents;
	private List<TransportStaffEventTableRow> transportStfEvents;
	private String stdEventType, totalMoneyCollected, mainEventInfo, returnEventInfo;
	private boolean filterNew, filterGenerated, filterCompleted, filterBanked;
	private Long selectedProTypeId;
	private boolean setOverridePrice, setReturnOverridePrice;

	public EventManagerModel() {
		init();
	}

	public void init() {
		programTypes = serviceLocator.getProgramTypeService().findAll();
		if (programTypes == null || programTypes.isEmpty()) {
			showError("There is an error with the program types. Please contact the system addministrator.");
		} else {
			for (ProgramType type : programTypes) {
				if (type.getName().equals("Student")) {
					type.setName("Day Programs");
					break;
				}
			}
			Collections.sort(programTypes, new SortObjectByName());
			toDate = new Date();
			timePeriod = "1W";
			// selectedStatus = "all";
			filterNew = filterGenerated = filterCompleted = filterBanked = true;
			selectedProTypeId = selectedProgramId = selectedGroupId = new Long(0);
			programEvent = null;
			proEvents = null;
			events = null;
			selectedEventsMap = new HashMap<String, ProgramEvent>();
			tabs = new ArrayList<ProgramEvent>();
			selectAllEvents = false;
			visibleAttendance = visibleConfirmation = visiblePayment = false;
			confirmationMessage = "";
			groupSelectItems = new ArrayList<SelectItem>();
			groupSelectItems.add(new SelectItem(0, "All Programs"));
			programMap = new HashMap<Long, Program>();
			createCostCenterList();
			vehicleSelectItems = new ArrayList<SelectItem>();
			List<Vehicle> vehicles = serviceLocator.getVehicleService().listActiveVehicles();
			if (!vehicles.isEmpty()) {
				for (Vehicle vehi : vehicles) {
					vehicleSelectItems.add(new SelectItem(vehi.getId(), vehi.getName()));
				}
				Collections.sort(vehicleSelectItems, new SortSelectItemsByLabel());
			}
			finalizeSelectItems(vehicleSelectItems, "Not Assigned");
			locationSelectItems = new ArrayList<SelectItem>();
			List<Location> locations = serviceLocator.getLocationService().findAll();
			if (locations != null && !locations.isEmpty()) {
				for (Location loc : locations) {
					if (loc.getName().equals("Home"))
						continue;
					locationSelectItems.add(new SelectItem(loc.getId(), loc.getName()));
				}
				Collections.sort(locationSelectItems, new SortSelectItemsByLabel());
			}
			finalizeSelectItems(locationSelectItems, "Select One");
			tempCoordinators = new ArrayList<SelectItem>();
			List<StaffMember> staffMembers = serviceLocator.getStaffMemberService().listActiveStaffMembers();
			if (staffMembers != null && !staffMembers.isEmpty()) {
				for (StaffMember sm : staffMembers) {
					tempCoordinators.add(new SelectItem(sm.getId(), sm.getContact().getName()));
				}
			}
		}
	}

	public void selectProgramType(ValueChangeEvent event) {
		Long id = (Long) event.getNewValue();
		selectedProTypes = new ArrayList<ProgramType>();
		if (id != null && id != 0) {
			selectedProTypes.add(new ProgramType(id));
		} else {
			selectedProTypes.addAll(programTypes);
		}
		createCostCenterList();
	}

	private void createCostCenterList() {
		selectedProgramId = new Long(0);
		programMap = new HashMap<Long, Program>();
		for (Program program : serviceLocator.getProgramService().listByCriteria(null, selectedProTypes, "Active",
				true)) {
			programMap.put(program.getId(), program);
		}
		if (!programMap.isEmpty()) {
			programSelectItems = new ArrayList<SelectItem>();
			for (Program program : programMap.values()) {
				programSelectItems.add(new SelectItem(program.getId(), program.getName()));
			}
			Collections.sort(programSelectItems, new SortSelectItemsByLabel());
			programSelectItems.add(0, new SelectItem(0, "All Cost Centers"));
		} else {
			programSelectItems.add(new SelectItem(-1, "No Programs Available"));
		}
	}

	public void refreshStudentGroups(ValueChangeEvent vce) {
		selectedProgramId = (Long) vce.getNewValue();
		groupSelectItems.clear();
		List<StudentGroup> groups = new ArrayList<StudentGroup>();
		if (programMap.containsKey(selectedProgramId)) {
			groups = programMap.get(selectedProgramId).getStudentGroups();
		}
		if (groups != null && !groups.isEmpty()) {
			selectedGroupId = new Long(0);
			// groupSelectItems.add(new SelectItem(0, "All Groups"));
			for (StudentGroup group : groups) {
				groupSelectItems.add(new SelectItem(group.getId(), group.getName()));
			}
			Collections.sort(groupSelectItems, new SortSelectItemsByLabel());
			groupSelectItems.add(0, new SelectItem(0, "All Programs"));
		} else {
			if (selectedProgramId != 0) {
				selectedGroupId = new Long(-1);
				groupSelectItems.add(new SelectItem(-1, "No Programs Available"));
			} else {
				selectedGroupId = new Long(0);
				groupSelectItems.add(new SelectItem(0, "All Programs"));
			}
		}
	}

	public void searchEvents() {
		if (!filterNew && !filterGenerated && !filterCompleted && !filterBanked) {
			showError("Please select an event status.");
			return;
		}
		if (toDate == null) {
			showError("Date can not be empty.");
			return;
		}
		individual = false;
		proEvents = new HashMap<String, ProgramEvent>();
		StudentGroup group = null;
		Program program = null;
		String errorDetail = "";
		String errorSummary = null;
		if (selectedProgramId == 0) {
			for (Program prg : programMap.values()) {
				for (StudentGroup grp : prg.getStudentGroups()) {
					if (!validateGroupFields(grp)) {
						if (errorSummary == null)
							errorSummary = Util.getMessage("error.custom.eventincomplete");
						errorDetail += prg.getName() + "-" + grp.getName() + "\n";
					}
				}
			}
		} else if (selectedProgramId > 0) {
			if (selectedGroupId == 0) {
				program = programMap.get(selectedProgramId);
				if (program.getType().getName().equals("Individual"))
					individual = true;
				// program.setStudentGroups(serviceLocator.getStudentGroupService().listByProgram(selectedProgramId));
				boolean validated = true;
				for (StudentGroup grp : program.getStudentGroups()) {
					if (!validateGroupFields(grp)) {
						validated = false;
						break;
					}
				}
				if (!validated) {
					if (errorSummary == null)
						errorSummary = Util.getMessage("error.custom.eventincomplete");
				}
			} else if (selectedGroupId > 0) {
				group = serviceLocator.getStudentGroupService().retrieveWithData(selectedGroupId, false);
				if (group.getProgram().getType().getName().equals("Individual") && group.getStudents() != null
						&& group.getStudents().size() == 1)
					individual = true;
				if (!validateGroupFields(group)) {
					showError(
							"All necessary information of the selected program isn't available. Please complete the program profile first.. ");
				}
			}
		}
		if (errorSummary != null) {
			showError(errorDetail, Util.getMessage("error.custom.eventincomplete"));
		}
		List<ProgramEvent> events = null;
		selectedStatuses = new ArrayList<String>();
		if (filterNew)
			selectedStatuses.add("new");
		if (filterGenerated)
			selectedStatuses.add("pending");
		if (filterCompleted)
			selectedStatuses.add("finished");
		if (filterBanked)
			selectedStatuses.add("banked");
		events = serviceLocator.getProgramEventService().listByCriteria(selectedProgramId, selectedProTypes,
				selectedGroupId, getStartDate(), toDate, null, null);
		if (events != null) {
			for (ProgramEvent event : events) {
				proEvents.put(event.getEventInfo(), event);
			}
		}
		if (selectedProgramId == 0) {
			for (Program p : programMap.values()) {
				for (StudentGroup grp : p.getStudentGroups()) {
					addNotExistingEvents(grp, proEvents);
				}
			}
		} else {
			if (selectedGroupId == 0) {
				for (StudentGroup grp : program.getStudentGroups()) {
					addNotExistingEvents(grp, proEvents);
				}
			} else if (selectedGroupId > 0) {
				addNotExistingEvents(group, proEvents);
			}
		}
		HashMap<String, ProgramEvent> newList = new HashMap<String, ProgramEvent>();
		for (ProgramEvent event : proEvents.values()) {
			if (event.getId() != null && selectedStatuses.contains(event.getStatus())
					|| (selectedStatuses.contains("new") && event.getId() == null))
				newList.put(event.getEventInfo(), event);
		}
		proEvents = new HashMap<String, ProgramEvent>();
		proEvents.putAll(newList);
		if (proEvents.isEmpty()) {
			this.events = null;
			showError("No events found..");
		} else {
			// if (programEvent != null)
			// updateRecords();
			sortList();
			selectedEventsMap = new HashMap<String, ProgramEvent>();
			selectAllEvents = false;
		}
	}

	private void addNotExistingEvents(StudentGroup group, HashMap<String, ProgramEvent> existing) {
		if (validateGroupFields(group)) {
			StudentGroup returnGroup = null;
			if (group.getProgram().getType().getName().equals("Transport"))
				returnGroup = serviceLocator.getStudentGroupService().getReturnGroup(group.getId(), false);
			SimpleDateFormat formatDay = new SimpleDateFormat("EEEE");
			Calendar calDate = Calendar.getInstance();
			calDate.setTime(getStartDate());
			// calDate.add(Calendar.DATE, -1);
			HashMap<String, WeekDay> weekDays = null;
			if (!group.getWeekDays().isEmpty()) {
				weekDays = new HashMap<String, WeekDay>();
				for (WeekDay day : group.getWeekDays()) {
					weekDays.put(day.getName(), day);
				}
			}
			HashMap<String, ProgramEvent> tmpMap = new HashMap<String, ProgramEvent>();
			ProgramEvent proEvent = null;
			do {
				calDate.add(Calendar.DATE, 1);
				if (calDate.getTime().compareTo(group.getEndDate()) > 0) {
					break;
				}
				if (calDate.getTime().compareTo(group.getStartDate()) < 0) {
					continue;
				}
				boolean found = false;
				for (ProgramEvent event : existing.values()) {
					if (event.getEventInfoAsNew().equals(group.getEventInfo(calDate.getTime()))) {
						found = true;
						break;
					}
				}
				if (found)
					continue;
				if (!group.getWeekDays().isEmpty() && !weekDays.containsKey(formatDay.format(calDate.getTime())))
					continue;
				if (!group.isAllowProgramtorunonaholiday()
						&& serviceLocator.getHolidayService().isHoliday(new Long(1), calDate.getTime())) {
					continue;
				}
				proEvent = createProgramEvent(group, calDate.getTime());
				if (returnGroup != null) {
					proEvent.setReturnEvent(createProgramEvent(returnGroup, calDate.getTime()));
					proEvent.getGroup().setReturnAvailable(true);
				}
				tmpMap.put(proEvent.getEventInfo(), proEvent);
				proEvent = null;
			} while (calDate.getTime().compareTo(toDate) != 0);
			proEvents.putAll(tmpMap);
		}
	}

	private ProgramEvent createProgramEvent(StudentGroup group, Date eventDate) {
		ProgramEvent proEvent = new ProgramEvent();
		proEvent.setProgram(group.getProgram());
		proEvent.setEventDate(eventDate);
		proEvent.setStartTime(setDateTime(eventDate, group.getStartTime()));
		proEvent.setEndTime(setDateTime(eventDate, group.getEndTime()));
		Calendar start = new GregorianCalendar();
		start.setTime(proEvent.getStartTime());
		Calendar end = new GregorianCalendar();
		end.setTime(proEvent.getEndTime());
		if (group.getProgram().getType().getName().equals("Individual")) {
			if (start.get(Calendar.AM_PM) > end.get(Calendar.AM_PM)) {
				end.add(Calendar.DATE, 1);
				proEvent.setEndTime(end.getTime());
			}
		}
		proEvent.setCoordinator(group.getProgram().getCoordinator());
		proEvent.setGroup(group);
		proEvent.setStatus("pending");
		if (group.getProgram().getType().getName().equals("Transport"))
			proEvent.setVehicle(group.getVehicle());
		else
			proEvent.setLocation(group.getLocation());
		proEvent.setChargeAmount(group.getChargeAmount());
		proEvent.setLunchIncluded(group.isLunchIncluded());
		return proEvent;
	}

	private Date setDateTime(Date date, Date time) {
		Calendar dateCal = new GregorianCalendar();
		dateCal.setTime(date);
		Calendar timeCal = new GregorianCalendar();
		timeCal.setTime(time);
		dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
		dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
		dateCal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
		return dateCal.getTime();
	}

	private StaffEvent createStaffEvent(StaffMember staffMember, ProgramEvent programEvent) {
		StaffEvent event = new StaffEvent();
		event.setStaffMember(staffMember);
		event.setProgramEvent(programEvent);
		for (GroupedStaff gs : programEvent.getGroup().getStaffMembers()) {
			if (staffMember.getId() == gs.getStaffMember().getId()) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(programEvent.getEventDate());
				int eventWeekDayId = cal.get(Calendar.DAY_OF_WEEK) - 1;
				if (eventWeekDayId == 0)
					eventWeekDayId = 7;
				if (gs.getAssignedDays() != null) {
					for (GroupedStaffWeekday gsw : gs.getAssignedDays()) {
						if (gsw.getWeekday().getId() == eventWeekDayId) {
							event.setLunchMinutes(gsw.getLunch() == null ? 0 : gsw.getLunch());
							if (gsw.getStartTime() != null && gsw.getEndTime() != null) {
								// change date of the Date obj to event date
								Date startdate = combineDateTime(programEvent.getStartTime(), gsw.getStartTime());
								Date endDate = combineDateTime(programEvent.getEndTime(), gsw.getEndTime());
								TimeBean start = new TimeBean(startdate);
								TimeBean end = new TimeBean(endDate);
								event.setRosterStartTime(startdate);
								event.setRosterEndTime(endDate);
								event.setRosterStatTimeBean(start);
								event.setRosterEndTimeBean(end);
							} else {
								event.setRosterStartTime(programEvent.getStartTime());
								event.setRosterEndTime(programEvent.getEndTime());
							}
							break;
						}
					}
				}
				break;
			}
		}
		/*
		 * if (holidayOverride) { event.setAttended(true);
		 * event.setStartTime(programEvent.getStartTime());
		 * event.setEndTime(programEvent.getEndTime()); }
		 */
		return event;
	}

	private Date combineDateTime(Date date, Date time) {
		Calendar calTime = Calendar.getInstance();
		Calendar calDate = Calendar.getInstance();

		calTime.setTime(time);
		calDate.setTime(date);

		calTime.set(Calendar.YEAR, calDate.get(Calendar.YEAR));
		calTime.set(Calendar.MONTH, calDate.get(Calendar.MONTH));
		calTime.set(Calendar.DATE, calDate.get(Calendar.DATE));

		return calTime.getTime();
	}

	private boolean validateGroupFields(StudentGroup group) {
		return !(group.getStartDate() == null || group.getEndDate() == null || group.getStartTime() == null
				|| group.getEndTime() == null || !(group.getVehicle() != null || group.getLocation() != null));
	}

	private Date getStartDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate);
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
		return calendar.getTime();
	}

	public void selectAllEvents(ValueChangeEvent ve) {
		selectAllEvents = (Boolean) ve.getNewValue();
		int start = Integer.valueOf(dataTable.getFirst());
		int end = start + 19;
		if (events.size() - 1 < end)
			end = events.size() - 1;
		List<ProgramEvent> tmpList = new ArrayList<ProgramEvent>();
		tmpList.addAll(events.subList(start, end));
		tmpList.add(events.get(end));
		for (ProgramEvent event : tmpList) {
			event.setUi_selected(selectAllEvents);
			if (selectAllEvents)
				selectedEventsMap.put(event.getEventInfo(), event);
			else
				selectedEventsMap.remove(event.getEventInfo());
		}
	}

	public void selectEvent(ValueChangeEvent ve) {
		ProgramEvent event = (ProgramEvent) ve.getComponent().getAttributes().get("event");
		int start = Integer.valueOf(dataTable.getFirst());
		int end = start + 19;
		if (events.size() - 1 < end)
			end = events.size() - 1;
		Boolean selected = (Boolean) ve.getNewValue();
		event.setUi_selected(selected);
		if (selected) {
			List<ProgramEvent> tmpList = new ArrayList<ProgramEvent>();
			tmpList.addAll(events.subList(start, end));
			tmpList.add(events.get(end));
			selectedEventsMap.put(event.getEventInfo(), event);
			if (selectedEventsMap.values().containsAll(tmpList))
				selectAllEvents = true;
		} else {
			selectAllEvents = false;
			selectedEventsMap.remove(event.getEventInfo());
		}
	}

	private void calculateTotalMoneyCollected() {
		totalMoneyCollected = "";
		double total = 0;
		if (events != null) {
			for (ProgramEvent event : events) {
				total += event.getTotalMoneyCollected() + event.getTotalEFTCollected();
			}
		}
		if (total != 0) {
			totalMoneyCollected = "$" + BigDecimal.valueOf(total).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		} else {
			totalMoneyCollected = "$0.00";
		}
	}

	public void paginatorClicked(ActionEvent ae) {
		int start = Integer.valueOf(dataTable.getFirst());
		int end = start + 19;
		if (events.size() - 1 < end)
			end = events.size() - 1;
		List<ProgramEvent> tmpList = new ArrayList<ProgramEvent>();
		tmpList.addAll(events.subList(start, end));
		tmpList.add(events.get(end));
		if (selectedEventsMap.values().containsAll(tmpList))
			selectAllEvents = true;
		else
			selectAllEvents = false;
	}

	public void deleteEvents() {
		int notDeleted = 0, notGenerated = 0, bankedOrCompleted = 0, invoiced = 0;
		int oldTabIndex = tabIndex;
		List<ProgramEvent> deletedEvents = new ArrayList<ProgramEvent>();
		HashMap<Long, StudentGroup> deletedGroups = new HashMap<Long, StudentGroup>();
		for (ProgramEvent event : selectedEventsMap.values()) {
			try {
				if (event.getId() != null) {
					if (isUserInRole("ROLE_SUPERUSER") || event.getStatus().equals("pending")) {
						if (!event.isInvoiced()) {
							serviceLocator.getProgramEventService().delete(event.getId());
							if (!deletedGroups.containsKey(event.getGroup().getId())) {
								deletedGroups.put(event.getGroup().getId(), event.getGroup());
							}
							// ProgramEvent pe = initializeEvent(event);
							// proEvents.put(pe.getEventInfo(), pe);
							// deletedEvents.add(pe);
							proEvents.remove(event.getEventInfo());
							deletedEvents.add(event);
							for (ProgramEvent tab : tabs) {
								if (tab.getEventInfo().equals(event.getEventInfo())) {
									tabs.remove(tab);
									tabIndex = tabs.size() - 1;
									break;
									// initTab();
								}
							}
						} else {
							invoiced++;
						}
					} else {
						bankedOrCompleted++;
					}
				} else {
					notGenerated++;
				}
			} catch (DataIntegrityViolationException d) {
				notDeleted++;
			}
		}
		if (!deletedEvents.isEmpty()) {
			// for (String key : proEvents.keySet()) {
			// if (key != null && key.contains("|0")) {
			if (filterNew) {
				for (StudentGroup grp : deletedGroups.values()) {
					addNotExistingEvents(grp, proEvents);
				}
			}
			// break;
			// }
			// }
			// selectAllEvents = false;
			for (ProgramEvent pe : deletedEvents)
				selectedEventsMap.remove(pe.getEventInfo());
			// selectedEventsMap.put(pe.getEventInfo(), pe);
			showInfo(deletedEvents.size() + " Event(s) deleted successfully..");
			sortList();
			if (oldTabIndex != tabIndex)
				initTab();
			// if (programEvent != null)
			// updateRecords();
			selectAllEvents = false;
		}
		if (bankedOrCompleted > 0)
			showError(bankedOrCompleted + " Event(s) cannot be deleted as they are marked as 'Completed' or 'Banked'.");
		if (notDeleted > 0)
			showError(notDeleted + " Event(s) cannot be deleted. Operation was not allowed.");
		if (notGenerated > 0)
			showError(notGenerated + " Event(s) does not exist to delete.");
		if (invoiced > 0)
			showError(invoiced
					+ " Event(s) are not deleted as the activity statements are already generated for these events.");
		if (selectedEventsMap.isEmpty())
			selectAllEvents = false;
	}

	public void markEventsAsBanked() {
		int banked = 0;
		int notBanked = 0;
		int notCompleted = 0;
		List<ProgramEvent> list = new ArrayList<ProgramEvent>();
		for (ProgramEvent event : selectedEventsMap.values()) {
			if (event.getStatus().equals("pending")) {
				notCompleted++;
				// showError("Only completed events can be marked as banked");
				// return;
			} else if (event.getStatus().equals("banked")) {
				notBanked++;
			} else {
				event.setStatus("banked");
				serviceLocator.getProgramEventService().update(event);
				list.add(event);
				banked++;
			}
		}
		for (ProgramEvent event : list) {
			if (selectedEventsMap.containsKey(event.getEventInfo())) {
				event.setUi_selected(true);
				selectedEventsMap.put(event.getEventInfo(), dumpData(event));
			}
			proEvents.put(event.getEventInfo(), dumpData(event));
		}
		if (banked > 0) {
			assignToTabs(list);
			showInfo(banked + " Event(s) marked as banked..");
		}
		if (notBanked > 0)
			showInfo(notBanked + " Selected event(s) are already banked..");
		if (notCompleted > 0)
			showError(notCompleted + " Selected event(s) are not completed..");
	}

	public void initEventReport() {
		printStatus = "There is no existing events within the given period..";
		for (ProgramEvent event : proEvents.values()) {
			if (event.getId() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				JSONObject json = new JSONObject();
				Date fromDate = getStartDate();
				json.put("selectedProgramId", selectedProgramId);
				json.put("selectedGroupId", selectedGroupId);
				json.put("selectedStatuses", selectedStatuses);
				if (fromDate != null)
					json.put("fromDate", dateFormat.format(fromDate));
				else
					json.put("fromDate", null);
				if (toDate != null)
					json.put("toDate", dateFormat.format(toDate));
				else
					json.put("toDate", null);
				jsonString = json.toString();
				printStatus = "success";
				break;
			}
		}
	}

	public void generateAttendanceSheets() {
		int generated = 0;
		int notGenerated = 0;
		int alreadyGenerated = 0;
		// boolean holidayOverride = false;
		List<ProgramEvent> createdList = new ArrayList<ProgramEvent>();
		for (ProgramEvent event : selectedEventsMap.values()) {
			if (event.getGeneratedDate() == null) {
				/*
				 * holidayOverride =
				 * event.getGroup().isAllowProgramtorunonaholiday() &&
				 * serviceLocator.getHolidayService().isHoliday(new Long(1),
				 * event.getEventDate());
				 */

				// For the staff programs
				if (event.getGroup().getProgram().getType().getName().equals("Staff")) {
					// :TODO group staffmembers are set to lazy = false. Need to
					// fix
					// event.getGroup().setStaffMembers(
					// serviceLocator.getGroupedStaffService().listByGroup(event.getGroup().getId()));
					if (event.getGroup().getStaffMembers() == null || event.getGroup().getStaffMembers().isEmpty()) {
						if (selectedEventsMap.size() == 1)
							showError("There are no staff members enrolled with the program of the selected event.");
						notGenerated++;
					} else {
						// createStaffEvents(event, holidayOverride);
						createStaffEvents(event);
						if (event.getStaffEvents().isEmpty()) {
							if (selectedEventsMap.size() == 1)
								showError(
										"There are no available staff members found within the program of the selected event.");
							notGenerated++;
						} else {
							if (event.getId() == null)
								serviceLocator.getProgramEventService().create(event);
							else
								serviceLocator.getProgramEventService().update(event);
							createdList.add(event);
							generated++;
						}
					}
				} else {
					// For Student/Individual/Transport programs
					List<GroupedStudent> studentList = serviceLocator.getGroupedStudentService()
							.listByGroup(event.getGroup().getId());
					if (studentList == null || studentList.isEmpty()) {
						if (selectedEventsMap.size() == 1)
							showError("There are no " + Util.getMessage("students_label").toLowerCase()
									+ " enrolled with the program of the selected event.");
						notGenerated++;
					} else {
						event.getGroup().setStudents(studentList);
						// if
						// (event.getProgram().getType().getName().equals("Individual"))
						createStudentEvents(event);
						// By chance if all students are excluded, then
						// studentEvents would be null
						if ((event.getProgram().getType().getName().equals("Individual"))
								&& event.getStudentEvents() == null) {
							if (selectedEventsMap.size() == 1)
								showError("There are no active " + Util.getMessage("students_label").toLowerCase()
										+ " available within the program of the selected event.");
							notGenerated++;
						} else {
							createStaffEvents(event);
							/*
							 * if (holidayOverride) {
							 * event.setStatus("finished"); }
							 */
							if (event.getId() == null)
								serviceLocator.getProgramEventService().create(event);
							else
								serviceLocator.getProgramEventService().update(event);
							createdList.add(event);
							generated++;
						}
					}
				}
			} else {
				alreadyGenerated++;
			}
		}
		for (ProgramEvent event : createdList) {
			if (selectedEventsMap.containsKey(event.getEventInfoAsNew())
					|| selectedEventsMap.containsKey(event.getEventInfo())) {
				selectedEventsMap.remove(event.getEventInfoAsNew());
				selectedEventsMap.remove(event.getEventInfo());
				event.setUi_selected(true);
				selectedEventsMap.put(event.getEventInfo(), dumpData(event));
			}
			proEvents.remove(event.getEventInfoAsNew());
			proEvents.put(event.getEventInfo(), dumpData(event));
		}
		if (alreadyGenerated > 0) {
			showInfo(alreadyGenerated + " Attendance sheet(s) already generated.");
		}
		if (generated > 0) {
			showInfo(generated + " Attendance sheet(s) successfully generated.");
			sortList();
			assignToTabs(createdList);
		}
		if (notGenerated > 0)
			showError(notGenerated + " Attendance sheet(s) not generated.");
	}

	private void createStudentEvents(ProgramEvent event) {
		if (event.getGroup().getStudents() != null) {
			List<StudentEvent> stuEvents = new ArrayList<StudentEvent>();
			for (GroupedStudent student : event.getGroup().getStudents()) {
				if (event.getEventDate().compareTo(student.getStudent().getStartFrom()) < 0) {
					continue;
				}
				if (student.getStudent().getStatus().equals("Inactive")
						&& (event.getEventDate().after(student.getStudent().getServiceEndDate())
								|| event.getEventDate().equals(student.getStudent().getServiceEndDate()))) {
					continue;
				}
				if (student.getStatus() != null && student.getStatus().equals("Excluded")) {
					continue;
				}
				StudentFundingSource sfs = serviceLocator.getStudentFundingSourceService()
						.getRelatedStudentFundingSource(student.getStudent().getId(), event.getEventDate());
				StudentEvent stuEvent = new StudentEvent();
				stuEvent.setGroupedStudent(student);
				stuEvent.setStdFundingSrc(sfs);
				stuEvent.setProEvent(event);
				stuEvent.setRemarks(sfs == null ? "No Funds" : "");
				stuEvents.add(stuEvent);
			}
			if (stuEvents.isEmpty())
				event.setStudentEvents(null);
			else {
				event.setGeneratedDate(new Date());
				event.setStudentEvents(stuEvents);
			}
		}
	}

	private void createStaffEvents(ProgramEvent event) {
		// :TODO group staffmembers are set to lazy = false. Need to
		// fix
		// StudentGroup group = serviceLocator.getStudentGroupService()
		// .retrieveWithStaffMembers(event.getGroup().getId());
		event.setStaffEvents(new ArrayList<StaffEvent>());
		if (event.getGroup().getStaffMembers() != null) {
			for (GroupedStaff gs : event.getGroup().getStaffMembers()) {
				/*
				 * if (holidayOverride &&
				 * gs.getStaffMember().getEmploymentType().equals(
				 * EnumEmploymentType.CASUAL)) { continue; }
				 */
				boolean assigned = false;
				boolean available = true;
				for (GroupedStaffWeekday day : gs.getAssignedDays()) {
					if (day.getWeekday().getName().equals(getDateString(event.getEventDate()))) {
						assigned = true;
						break;
					}
				}
				for (WeekDay day : gs.getStaffMember().getUnAvailableDays()) {
					if (day.getName().equals(getDateString(event.getEventDate()))) {
						available = false;
						break;
					}
				}
				if (assigned && available) {
					event.setGeneratedDate(new Date());
					event.getStaffEvents().add(createStaffEvent(gs.getStaffMember(), event));
				}
			}
		}
	}

	private void initTab() {
		if (tabs.size() > 0) {
			loadProgramEvent(tabs.get(tabIndex));
			initAttendance();
		}
	}

	public void openTab(ActionEvent ae) {
		setOverridePrice = false;
		setReturnOverridePrice = false;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (tabs.size() < 5) {
			ProgramEvent tmpEvent = (ProgramEvent) ae.getComponent().getAttributes().get("event");
			setOverridePrice = tmpEvent.isOverridePrice();

			for (ProgramEvent tab : tabs) {
				if (tmpEvent.getGroup().getParentGroup() != null) {
					if (formatter.format(tab.getEventDate()).equals(formatter.format(tmpEvent.getEventDate()))
							&& tmpEvent.getGroup().getParentGroup().getName().equals(tab.getGroup().getName())) {
						showInfo("Event is already open..");
						tabIndex = tabs.indexOf(tab);
						initTab();
						return;
					}
				} else if (tmpEvent.getEventInfo().equals(tab.getEventInfo())) {
					showInfo("Event is already open..");
					tabIndex = tabs.indexOf(tab);
					initTab();
					return;
				}
			}

			if (tmpEvent.getId() != null) {
				tmpEvent = serviceLocator.getProgramEventService().retrieveWithData(tmpEvent.getId(), true, true);
			}
			if (tmpEvent.getProgram().getType().getName().equals("Transport")) {
				if (tmpEvent.getGroup().getParentGroup() != null) {
					ProgramEvent mainEvent = serviceLocator.getProgramEventService().getMainEventWithData(tmpEvent,
							true, true);
					if (mainEvent == null) {
						mainEvent = createProgramEvent(tmpEvent.getGroup().getParentGroup(), tmpEvent.getEventDate());
					}
					mainEvent.setReturnEvent(tmpEvent);
					tmpEvent = mainEvent;
				} else {
					ProgramEvent returnEvent = serviceLocator.getProgramEventService().getReturnEventWithData(tmpEvent,
							true, true);
					if (returnEvent != null) {
						setReturnOverridePrice = returnEvent.isOverridePrice();
						tmpEvent.getGroup().setReturnAvailable(true);
						tmpEvent.setReturnEvent(returnEvent);
					}
				}
			}
			tabs.add(tmpEvent);
			tabIndex = tabs.size() - 1;
			initTab();
		} else {
			showError("Maximum number of opened tabs reached. Please close a tab and try again..");
		}
	}

	public void selectTab(TabChangeEvent event) {
		tabIndex = event.getNewTabIndex();
		initTab();
	}

	public void closeTab(ActionEvent event) {
		ProgramEvent pe = (ProgramEvent) event.getComponent().getAttributes().get("event");
		if (tabIndex >= tabs.indexOf(pe) && tabIndex != 0)
			tabIndex--;
		tabs.remove(pe);
		initTab();
	}

	private void assignToTabs(List<ProgramEvent> createdList) {
		int index;
		for (ProgramEvent event : createdList) {
			for (ProgramEvent tab : tabs) {
				if (event.getGroup().getParentGroup() != null) {
					if (tab.getEventDate().equals(event.getEventDate())
							&& event.getGroup().getParentGroup().getName().equals(tab.getGroup().getName())) {
						tab.getGroup().setReturnAvailable(true);
						event = serviceLocator.getProgramEventService().retrieveWithData(event.getId(), true, true);
						tab.setReturnEvent(event);
						if (programEvent != null) {
							if (programEvent.getEventInfo().equals(tab.getEventInfo())
									|| programEvent.getEventInfoAsNew().equals(tab.getEventInfoAsNew())) {
								initTab();
							}
						}
						break;
					}
				} else if (event.getEventInfo().equals(tab.getEventInfo())
						|| event.getEventInfoAsNew().equals(tab.getEventInfoAsNew())) {
					index = tabs.indexOf(tab);
					event = serviceLocator.getProgramEventService().retrieveWithData(event.getId(), true, true);
					event.setReturnEvent(tab.getReturnEvent());
					if (event.getReturnEvent() != null) {
						event.getGroup().setReturnAvailable(true);
					}
					tabs.set(index, event);
					if (programEvent != null) {
						if (programEvent.getEventInfo().equals(tab.getEventInfo())
								|| programEvent.getEventInfoAsNew().equals(tab.getEventInfoAsNew())) {
							initTab();
						}
					}
					break;
				}
			}
		}
	}

	private void loadProgramEvent(ProgramEvent tmpEvent) {
		if (tmpEvent != null) {
			if (tmpEvent.getId() != null) {
				programEvent = serviceLocator.getProgramEventService().retrieveWithData(tmpEvent.getId(), true, true);
				if (programEvent.getStudentEvents() != null) {
					Collections.sort(programEvent.getStudentEvents(), new SortEventsBySequence());
				}
			} else {
				programEvent = tmpEvent;
			}
			mainEventInfo = programEvent.getEventInfo();
			if (programEvent.getProgram().getType().getName().equals("Transport")) {
				ProgramEvent returnEvent = serviceLocator.getProgramEventService().getReturnEventWithData(tmpEvent,
						true, true);
				if (returnEvent != null) {
					programEvent.getGroup().setReturnAvailable(true);
					programEvent.setReturnEvent(returnEvent);
					returnEventInfo = returnEvent.getEventInfo();
				}
			} else {
				if (programEvent.getStudentEvents() != null) {
					Collections.sort(programEvent.getStudentEvents(), new SortObjectByName());
				}
				if (programEvent.getStaffEvents() != null) {
					Collections.sort(programEvent.getStaffEvents(), new SortObjectByName());
				}
			}
		}
		// if (tmpEvent != null)
		// programEvent = tmpEvent;
		eventStatus = programEvent.getStatus();
		rosterStartTime.setDateTime(programEvent.getStartTime());
		rosterEndTime.setDateTime(programEvent.getEndTime());

		// Code to set actual startTime, endTime if needed
		if (programEvent.getActualStartTime() == null || programEvent.getActualEndTime() == null) {
			setActualEventTimes = false;
		} else {
			setActualEventTimes = true;
			actualStartTime.setDateTime(programEvent.getActualStartTime());
			actualEndTime.setDateTime(programEvent.getActualEndTime());

		}
		if (programEvent.getLocation() != null) {
			selectedLocationId = programEvent.getLocation().getId();
		} else {
			selectedLocationId = new Long(0);
		}
		if (programEvent.getVehicle() == null) {
			selectedVehicleId = new Long(0);
		} else {
			// initVehicleImage(programEvent.getVehicle());
			selectedVehicleId = programEvent.getVehicle().getId();
		}
		coordinatorSelectItems = new ArrayList<SelectItem>();
		coordinatorSelectItems.addAll(tempCoordinators);
		if (programEvent.getCoordinator() != null) {
			boolean found = false;
			for (SelectItem si : coordinatorSelectItems) {
				if (si.getValue().equals(programEvent.getCoordinator().getId())) {
					found = true;
					break;
				}
			}
			if (!found)
				coordinatorSelectItems.add(new SelectItem(programEvent.getCoordinator().getId(),
						programEvent.getCoordinator().getContact().getName()));
			selectedCoordinatorId = programEvent.getCoordinator().getId();
		} else
			selectedCoordinatorId = new Long(0);
		Collections.sort(coordinatorSelectItems, new SortSelectItemsByLabel());
		finalizeSelectItems(coordinatorSelectItems, "Not Assigned");
		loadReturnEvent();
	}

	private void loadReturnEvent() {
		returnStatus = "pending";
		if (programEvent.getReturnEvent() != null) {
			programEvent.getGroup().setReturnAvailable(true);
			returnStatus = programEvent.getReturnEvent().getStatus();
			returnEventInfo = programEvent.getReturnEvent().getEventInfo();
			if (programEvent.getReturnEvent().getActualStartTime() == null
					|| programEvent.getReturnEvent().getActualEndTime() == null) {
				setActualReturnEventTimes = false;
			} else {
				setActualReturnEventTimes = true;
				actualReturnStartTime.setDateTime(programEvent.getReturnEvent().getActualStartTime());
				actualReturnEndTime.setDateTime(programEvent.getReturnEvent().getActualEndTime());
			}

			returnStartTime.setDateTime(programEvent.getReturnEvent().getStartTime());
			returnEndTime.setDateTime(programEvent.getReturnEvent().getEndTime());
			if (programEvent.getReturnEvent().getVehicle() == null) {
				returnVehicleId = new Long(0);
			} else {
				returnVehicleId = programEvent.getReturnEvent().getVehicle().getId();
			}
			returnCoordinatorSelectItems = new ArrayList<SelectItem>();
			returnCoordinatorSelectItems.addAll(tempCoordinators);
			if (programEvent.getReturnEvent().getCoordinator() != null) {
				boolean found = false;
				for (SelectItem si : returnCoordinatorSelectItems) {
					if (si.getValue().equals(programEvent.getReturnEvent().getCoordinator().getId())) {
						found = true;
						break;
					}
				}
				if (!found)
					returnCoordinatorSelectItems
							.add(new SelectItem(programEvent.getReturnEvent().getCoordinator().getId(),
									programEvent.getReturnEvent().getCoordinator().getContact().getName()));
				selectedReturnCoordinatorId = programEvent.getReturnEvent().getCoordinator().getId();
			} else
				selectedReturnCoordinatorId = new Long(0);
			Collections.sort(returnCoordinatorSelectItems, new SortSelectItemsByLabel());
			finalizeSelectItems(returnCoordinatorSelectItems, "Not Assigned");
		}
	}

	public void deleteProgramEvent(ActionEvent ae) {
		String type = (String) ae.getComponent().getAttributes().get("event");
		ProgramEvent returnEvent = programEvent.getReturnEvent();
		StudentGroup group = serviceLocator.getStudentGroupService().retrieve(selectedGroupId);
		boolean returnTried = false;
		try {
			if (returnEvent != null && returnEvent.getId() != null) {
				if (isUserInRole("ROLE_SUPERUSER") || returnEvent.getStatus().equals("pending")) {
					returnTried = true;
					if (!returnEvent.isInvoiced()) {
						serviceLocator.getProgramEventService().delete(returnEvent.getId());
						proEvents.remove(returnEvent.getEventInfo());
						if (returnEvent.getEventInfoAsNew()
								.equals(returnEvent.getGroup().getEventInfo(returnEvent.getEventDate()))) {
							if (group == null || group.getParentGroup() != null) {
								addNotExistingEvents(returnEvent.getGroup(), proEvents);
							}
							if (type == null || !type.equals("returnOnly")) {
								programEvent.getGroup().setReturnAvailable(false);
								programEvent.setReturnEvent(null);
								sortList();
								initTab();
							}
						}
						if (selectedEventsMap.containsKey(returnEvent.getEventInfo())) {
							selectedEventsMap.remove(returnEvent.getEventInfo());
							if (returnEvent.getEventInfoAsNew()
									.equals(returnEvent.getGroup().getEventInfo(returnEvent.getEventDate()))) {
								ProgramEvent re = proEvents.get(returnEvent.getEventInfoAsNew());
								if (re != null) {
									re.setUi_selected(true);
									selectedEventsMap.put(re.getEventInfo(), re);
								}
							}
						}
					} else {
						showError("Return event is not deleted as the activity statement is already generated.");
					}
				} else {
					showError("Return event is not deleted as they are marked as 'Completed' or 'Banked'.");
				}
			}
		} catch (DataIntegrityViolationException e) {
			showInfo("Return event can not be deleted. The record is currently in use.");
			return;
		}
		if (type != null && type.equals("returnOnly")) {
			showInfo("Return event deleted successfully.");
			programEvent.getGroup().setReturnAvailable(false);
			programEvent.setReturnEvent(null);
			sortList();
			initTab();
			return;
		}
		try {
			if (programEvent.getId() != null) {
				if (isUserInRole("ROLE_SUPERUSER") || programEvent.getStatus().equals("pending")) {
					if (!programEvent.isInvoiced()) {
						serviceLocator.getProgramEventService().delete(programEvent.getId());
						showInfo("Event deleted successfully..");
						proEvents.remove(programEvent.getEventInfo());
						if (programEvent.getEventInfoAsNew()
								.equals(programEvent.getGroup().getEventInfo(programEvent.getEventDate()))
								&& (group == null || group.getParentGroup() == null)) {
							addNotExistingEvents(programEvent.getGroup(), proEvents);
						}
						if (selectedEventsMap.containsKey(programEvent.getEventInfo())) {
							selectedEventsMap.remove(programEvent.getEventInfo());
							if (programEvent.getEventInfoAsNew()
									.equals(programEvent.getGroup().getEventInfo(programEvent.getEventDate()))) {
								ProgramEvent re = proEvents.get(programEvent.getEventInfoAsNew());
								if (re != null) {
									re.setUi_selected(true);
									selectedEventsMap.put(re.getEventInfo(), re);
								}
							}
						}
						for (ProgramEvent tabEvent : tabs) {
							if (tabEvent.getEventInfo().equals(programEvent.getEventInfo())) {
								if (tabIndex >= tabs.indexOf(tabEvent) && tabIndex != 0)
									tabIndex--;
								tabs.remove(tabEvent);
								initTab();
								break;
							}
						}
						sortList();
					} else {
						showError((returnTried ? "Main " : "")
								+ "Event is not deleted as the activity statement is already generated");
					}
				} else {
					showError((returnTried ? "Main " : "")
							+ "Event is not deleted as they are marked as 'Completed' or 'Banked'.");
				}
			}
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	private ProgramEvent dumpData(ProgramEvent event) {
		event.setStudentEvents(null);
		event.setStaffEvents(null);
		return event;
	}

	public void saveAll(ActionEvent event) {
		if (validateProEventFields()) {
			confirmationMessage = "";
			String status = programEvent.getStatus();
			programEvent.setOverridePrice(setOverridePrice);
			if ((eventStatus.equals("pending") && (status.equals("finished") || status.equals("banked")))
					|| (eventStatus.equals("finished") && status.equals("banked"))) {
				if (programEvent.getGeneratedDate() == null) {
					if (programEvent.getGroup().isReturnAvailable())
						showError("Main event status connot be changed. There is no attendence sheet generated.");
					else
						showError("Status connot be changed. There is no attendence sheet generated.");
					return;
				}
				programEvent.setCompletedDate(GregorianCalendar.getInstance().getTime());
				if (status.equals("banked"))
					programEvent.setBankedDate(GregorianCalendar.getInstance().getTime());
				confirmationMessage = "This will mark the "
						+ (programEvent.getGroup().isReturnAvailable() ? "main " : "") + "event as "
						+ (status.equals("finished") ? "completed" : status)
						+ " and cannot be undone. Do you want to continue?";
			}
			ProgramEvent returnEvent = programEvent.getReturnEvent();
			if (returnEvent != null) {
				returnEvent.setOverridePrice(setReturnOverridePrice);
			}
			if (programEvent.getGroup().isReturnAvailable()) {
				String status1 = returnEvent.getStatus();
				if ((returnStatus.equals("pending") && (status1.equals("finished") || status1.equals("banked")))
						|| (returnStatus.equals("finished") && status1.equals("banked"))) {
					if (returnEvent.getGeneratedDate() == null) {
						showError("Return event status connot be completed. There is no attendence sheet generated.");
						return;
					}
					returnEvent.setCompletedDate(GregorianCalendar.getInstance().getTime());
					if (status1.equals("banked"))
						returnEvent.setBankedDate(GregorianCalendar.getInstance().getTime());
					if (confirmationMessage.isEmpty()) {
						confirmationMessage = "This will mark the return event as "
								+ (status1.equals("finished") ? "completed" : status1)
								+ " and cannot be undone. Do you want to continue?";
					} else {
						if (status.equals(status1)) {
							confirmationMessage = "This will mark both the main and return event as "
									+ (status.equals("finished") ? "completed" : status)
									+ " and cannot be undone. Do you want to continue?";
						} else {
							confirmationMessage = "This will mark the main event as "
									+ (status.equals("finished") ? "completed" : status) + ", return event as "
									+ (status1.equals("finished") ? "completed" : status1)
									+ " and cannot be undone. Do you want to continue?";
						}
					}
				}
			}
			if (!confirmationMessage.isEmpty()) {
				visibleConfirmation = true;
				// sessionContext.getRejectConfirmation().requestFocus();
				return;
			}
			saveAll();
		}
	}

	private void saveAll() {
		ProgramEvent returnEvent = programEvent.getReturnEvent();
		// boolean mainEventHolidayOverride = false, returnEventHolidayOverride
		// = false;
		// if (programEvent.getGroup().isAllowProgramtorunonaholiday()
		// && serviceLocator.getHolidayService().isHoliday(new Long(1),
		// programEvent.getEventDate())) {
		// mainEventHolidayOverride = true;
		// }
		// if (returnEvent != null &&
		// returnEvent.getGroup().isAllowProgramtorunonaholiday()
		// && serviceLocator.getHolidayService().isHoliday(new Long(1),
		// returnEvent.getEventDate())) {
		// returnEventHolidayOverride = true;
		// }
		if (programEvent.getStaffEvents() != null) {
			for (StaffEvent se : programEvent.getStaffEvents()) {
				// set roster start/end time
				se.setRosterStartTime(se.getRosterStatTimeBean().getDateTime(programEvent.getEventDate()));
				if (programEvent.getGroup().getProgram().getType().getName().equals("Individual")) {
					if (se.getRosterStatTimeBean().getTimeAsString().substring(6).equals("PM")
							&& se.getRosterEndTimeBean().getTimeAsString().substring(6).equals("AM")) {
						Calendar c = Calendar.getInstance();
						c.setTime(programEvent.getEventDate());
						c.add(Calendar.DATE, 1);
						se.setRosterEndTime(se.getRosterEndTimeBean().getDateTime(c.getTime()));
					} else {
						se.setRosterEndTime(se.getRosterEndTimeBean().getDateTime(programEvent.getEventDate()));
					}
				} else {
					se.setRosterEndTime(se.getRosterEndTimeBean().getDateTime(programEvent.getEventDate()));
				}
				if (!programEvent.getGroup().getProgram().getType().getName().equals("Transport")
						&& !validateStaffTimes(se.getRosterStartTime(), se.getRosterEndTime())) {
					showError("Invalid staff roster times.");
					showError("Start time should be before the end time");
					return;
				}
				if (se.isAttended()) {
					se.setStartTime(se.getStartTimeBean().getDateTime(programEvent.getEventDate()));
					if (programEvent.getGroup().getProgram().getType().getName().equals("Individual")) {
						if (se.getStartTimeBean().getTimeAsString().substring(6).equals("PM")
								&& se.getEndTimeBean().getTimeAsString().substring(6).equals("AM")) {
							Calendar c = Calendar.getInstance();
							c.setTime(programEvent.getEventDate());
							c.add(Calendar.DATE, 1);
							se.setEndTime(se.getEndTimeBean().getDateTime(c.getTime()));
						} else {
							se.setEndTime(se.getEndTimeBean().getDateTime(programEvent.getEventDate()));
						}
					} else {
						se.setEndTime(se.getEndTimeBean().getDateTime(programEvent.getEventDate()));
					}
					if (!validateStaffTimes(se.getStartTime(), se.getEndTime())) {
						showError("Invalid staff actual times.");
						showError("Start time should be before the end time");
						return;
					}
				} else {
					se.setStartTime(null);
					se.setEndTime(null);
				}
			}
		}
		if (returnEvent != null && returnEvent.getStaffEvents() != null) {
			for (StaffEvent se : returnEvent.getStaffEvents()) {
				if (se.isAttended()) {
					se.setStartTime(se.getStartTimeBean().getDateTime(returnEvent.getEventDate()));
					se.setEndTime(se.getEndTimeBean().getDateTime(returnEvent.getEventDate()));
					if (!validateStaffTimes(se.getStartTime(), se.getEndTime())) {
						showError("Invalid staff actual times.");
						showError("Start time should be before the end time");
						return;
					}
				} else {
					se.setStartTime(null);
					se.setEndTime(null);
				}
			}
		}
		if (programEvent.getProgram().getType().getName().equals("Transport")) {
			if (!programEvent.getGroup().isReturnAvailable()) {
				if (returnEvent != null && returnEvent.getId() != null) {
					try {
						serviceLocator.getProgramEventService().delete(returnEvent.getId());
						if (returnEvent.getEventInfoAsNew()
								.equals(returnEvent.getGroup().getEventInfo(returnEvent.getEventDate()))) {
							addNotExistingEvents(returnEvent.getGroup(), proEvents);
						}
						if (selectedEventsMap.containsKey(returnEvent.getEventInfo())) {
							selectedEventsMap.remove(returnEvent.getEventInfo());
							if (returnEvent.getEventInfoAsNew()
									.equals(returnEvent.getGroup().getEventInfo(returnEvent.getEventDate()))) {
								ProgramEvent re = proEvents.get(returnEvent.getEventInfoAsNew());
								if (re != null) {
									re.setUi_selected(true);
									selectedEventsMap.put(re.getEventInfo(), re);
								}
							}
						}
						returnEvent = null;
						programEvent.setReturnEvent(returnEvent);
					} catch (Exception e) {
						showError("Return event can not be removed. The record is currently in use.");
						programEvent.getGroup().setReturnAvailable(true);
						return;
					}
				}
			} else {
				if (selectedReturnCoordinatorId == 0)
					returnEvent.setCoordinator(null);
				else {
					if (returnEvent.getCoordinator() == null
							|| !returnEvent.getCoordinator().getId().equals(selectedReturnCoordinatorId)) {
						StaffMember coordinator = serviceLocator.getStaffMemberService()
								.retrieve(selectedReturnCoordinatorId);
						returnEvent.setCoordinator(coordinator);
					}
				}

				if (setActualReturnEventTimes) {
					returnEvent.setActualStartTime(actualReturnStartTime.getDateTime(programEvent.getEventDate()));
					returnEvent.setActualEndTime(actualReturnEndTime.getDateTime(programEvent.getEventDate()));
				} else {
					returnEvent.setActualStartTime(null);
					returnEvent.setActualEndTime(null);
				}

				updateAttendanceSheet(returnEvent);
				setTotalMoneyCollected(returnEvent);
				if (returnEvent.getId() == null) {
					// createStaffEvents(returnEvent,
					// returnEventHolidayOverride);
					returnEvent = serviceLocator.getProgramEventService().create(returnEvent);
				} else {
					returnEvent = serviceLocator.getProgramEventService().update(returnEvent);
				}
				returnStatus = returnEvent.getStatus();
				programEvent.setReturnEvent(returnEvent);
			}
		} else {
			if (programEvent.getStudentEvents() != null)
				Collections.sort(programEvent.getStudentEvents(), new SortEventsBySequence());
		}
		if (selectedCoordinatorId == 0)
			programEvent.setCoordinator(null);
		else {
			if (programEvent.getCoordinator() == null
					|| !programEvent.getCoordinator().getId().equals(selectedCoordinatorId)) {
				StaffMember coordinator = serviceLocator.getStaffMemberService().retrieve(selectedCoordinatorId);
				programEvent.setCoordinator(coordinator);
			}
		}
		if (setActualEventTimes) {
			programEvent.setActualStartTime(actualStartTime.getDateTime(programEvent.getEventDate()));
			if (programEvent.getGroup().getProgram().getType().getName().equals("Individual")) {
				if (actualStartTime.getTimeAsString().substring(6).equals("PM")
						&& actualEndTime.getTimeAsString().substring(6).equals("AM")) {
					Calendar c = Calendar.getInstance();
					c.setTime(programEvent.getEventDate());
					c.add(Calendar.DATE, 1);
					programEvent.setActualEndTime(actualEndTime.getDateTime(c.getTime()));
				} else {
					programEvent.setActualEndTime(actualEndTime.getDateTime(programEvent.getEventDate()));
				}
			} else {
				programEvent.setActualEndTime(actualEndTime.getDateTime(programEvent.getEventDate()));
			}
		} else {
			programEvent.setActualStartTime(null);
			programEvent.setActualEndTime(null);
		}
		updateAttendanceSheet(programEvent);
		setTotalMoneyCollected(programEvent);
		if (programEvent.getId() == null) {
			// createStaffEvents(programEvent, mainEventHolidayOverride);
			programEvent = serviceLocator.getProgramEventService().create(programEvent);
		} else {
			programEvent = serviceLocator.getProgramEventService().update(programEvent);
		}
		if (programEvent.getStaffEvents() != null) {
			Collections.sort(programEvent.getStaffEvents(), new SortObjectByName());
		}
		if (programEvent.getProgram().getType().getName().equals("Transport")) {
			if (returnEvent.getStaffEvents() != null) {
				Collections.sort(returnEvent.getStaffEvents(), new SortObjectByName());
			}
			programEvent.setReturnEvent(returnEvent);
			loadTransportAttendance();
		} else {
			if (programEvent.getStudentEvents() != null) {
				Collections.sort(programEvent.getStudentEvents(), new SortObjectByName());
			}
		}
		eventStatus = programEvent.getStatus();
		showInfo("Event saved successfully..");
		showInfo("Attendance details saved successfully.");
		updateRecords();
	}

	public void returnEventChanged(ValueChangeEvent event) {
		boolean available = (Boolean) event.getNewValue();
		if (available) {
			if (programEvent.getReturnEvent() == null) {
				StudentGroup returnGroup = serviceLocator.getStudentGroupService()
						.getReturnGroup(programEvent.getGroup().getId(), false);
				if (returnGroup != null) {
					programEvent.setReturnEvent(createProgramEvent(returnGroup, programEvent.getEventDate()));
					loadReturnEvent();
				} else {
					showError(Util.getMessage("student_label") + " Group isn't configured with return schedule.");
					programEvent.getGroup().setReturnAvailable(false);
				}
			}
		}
		if (programEvent.getProgram().getType().getName().equals("Transport"))
			loadTransportAttendance();
	}

	private boolean validateTimeWithCluster() {
		Calendar date = new GregorianCalendar();
		date.setTime(programEvent.getEventDate());
		if (date.get(Calendar.DAY_OF_WEEK) != 1 && date.get(Calendar.DAY_OF_WEEK) != 7) {
			if (!checkInPrices()) {
				showError(
						"There is no matching NDIS price(s) found for the selected time period within the NDIS Support Cluster of the event.");
				return false;
			}
		}
		return true;
	}

	private boolean checkInPrices() {
		Long currentEndTime, eTime1, eTime2, eTime3, eTime4;
		currentEndTime = eTime1 = eTime2 = eTime3 = eTime4 = null;
		Date endTime = programEvent.getEndTime();
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
		} else if (eTime1 < currentEndTime && currentEndTime <= eTime2
				&& programEvent.getGroup().isInactiveovernight()) {
			ndisTime = "Overnight Inactive";
		} else if (eTime1 < currentEndTime && currentEndTime <= eTime2
				&& !programEvent.getGroup().isInactiveovernight()) {
			ndisTime = "Weekday Evening";
		}
		for (NdisPrice ndisPrice : programEvent.getGroup().getNdis().getNdisPrice()) {
			if (ndisPrice.getNdisTime().equals(ndisTime)) {
				return true;
			}
		}
		return false;
	}

	private void updateRecords() {
		ProgramEvent mainEvent = programEvent.getId() != null
				? serviceLocator.getProgramEventService().retrieve(programEvent.getId()) : programEvent;
		ProgramEvent returnEvent = (programEvent.getReturnEvent() != null
				&& programEvent.getReturnEvent().getId() != null)
						? serviceLocator.getProgramEventService().retrieve(programEvent.getReturnEvent().getId())
						: programEvent.getReturnEvent();
		if (returnEvent != null) {
			if (mainEvent.getId() != null) {
				mainEvent.setReturnEvent(returnEvent);
				mainEvent.getGroup().setReturnAvailable(true);
			}
			if (selectedEventsMap.containsKey(returnEventInfo)) {
				selectedEventsMap.remove(returnEventInfo);
				returnEvent.setUi_selected(true);
				selectedEventsMap.put(returnEvent.getEventInfo(), dumpData(returnEvent));
			}
			if (returnEventInfo == null || returnEventInfo.isEmpty())
				returnEventInfo = returnEvent.getEventInfoAsNew();
			if (proEvents.containsKey(returnEventInfo)) {
				proEvents.remove(returnEventInfo);
				proEvents.put(returnEvent.getEventInfo(), dumpData(returnEvent));
				returnEventInfo = returnEvent.getEventInfo();
			}
			if (returnEventInfo.contains("|0") && !returnEventInfo.equals(returnEvent.getEventInfoAsNew())) {
				addNotExistingEvents(returnEvent.getGroup(), proEvents);
			}
		}
		if (selectedEventsMap.containsKey(mainEventInfo)) {
			selectedEventsMap.remove(mainEventInfo);
			mainEvent.setUi_selected(true);
			selectedEventsMap.put(mainEvent.getEventInfo(), dumpData(mainEvent));
		}
		if (proEvents.containsKey(mainEventInfo)) {
			proEvents.remove(mainEventInfo);
			if (mainEvent != null) {
				proEvents.put(mainEvent.getEventInfo(), dumpData(mainEvent));
				mainEventInfo = mainEvent.getEventInfo();
			}
		}
		if (mainEventInfo.contains("|0") && !mainEventInfo.equals(mainEvent.getEventInfoAsNew())) {
			addNotExistingEvents(mainEvent.getGroup(), proEvents);
		}
		// programEvent = mainEvent;
		/*
		 * for (ProgramEvent tab : tabs) { if
		 * (tab.getEventInfo().equals(programEvent.getEventInfo())) {
		 * tabs.set(tabs.indexOf(tab), programEvent); initTab(); break; } }
		 */
		sortList();
	}

	private void sortList() {
		events = null;
		if (proEvents != null) {
			events = new ArrayList<ProgramEvent>();
			for (ProgramEvent event : proEvents.values()) {
				events.add(event);
			}
			Collections.sort(events, new SortProgramEventsByDate());
			calculateTotalMoneyCollected();
			initEventReport();
		}
	}

	public void confirmationAction(ActionEvent ae) {
		visibleConfirmation = false;
		String action = (String) ae.getComponent().getAttributes().get("confirmation");
		if (programEvent != null) {
			if (action.equals("Yes")) {
				saveAll();
			} else {
				programEvent.setStatus(eventStatus);
				if (programEvent.getReturnEvent() != null)
					programEvent.getReturnEvent().setStatus(returnStatus);
			}
		}
	}

	private void initAttendance() {
		eventsAttended = new HashMap<Long, StudentEvent>();
		returnsAttended = new HashMap<Long, StudentEvent>();
		loadAttendance(programEvent, false);
		setTotalMoneyCollected(programEvent);
		if (programEvent.getReturnEvent() != null) {
			loadAttendance(programEvent.getReturnEvent(), true);
			setTotalMoneyCollected(programEvent.getReturnEvent());
		}
		if (programEvent.getProgram().getType().getName().equals("Transport")) {
			loadTransportAttendance();
		}
	}

	private void loadAttendance(ProgramEvent event, boolean isReturn) {
		List<StudentEvent> events = event.getStudentEvents();
		List<StaffEvent> staffEvents = event.getStaffEvents();
		if (events != null && !events.isEmpty()) {
			boolean noFunding = false;
			for (StudentEvent stdEvent : events) {
				if (stdEvent.isAttended())
					if (isReturn)
						returnsAttended.put(stdEvent.getId(), stdEvent);
					else
						eventsAttended.put(stdEvent.getId(), stdEvent);
				if (stdEvent.getStdFundingSrc() == null) {
					StudentFundingSource sfs = serviceLocator.getStudentFundingSourceService()
							.getRelatedStudentFundingSource(stdEvent.getGroupedStudent().getStudent().getId(),
									programEvent.getEventDate());
					if (sfs != null) {
						stdEvent.setStdFundingSrc(sfs);
						serviceLocator.getStudentEventService().update(stdEvent);
					} else {
						noFunding = true;
						// showError("Funding source not found of "
						// +
						// stdEvent.getGroupedStudent().getStudent().getContact().getName());
						// continue;
					}
				}
				if (!programEvent.getProgram().getType().getName().equals("Transport")) {
					TimeTrackerModel model = new TimeTrackerModel();
					Date fromDate = model.getFortnightStartDate(programEvent.getEventDate());
					Date toDate = model.getFortnightEnd();
					setRemainingHours(stdEvent, fromDate, toDate);
				}
			}
			if (isReturn) {
				selectAllReturnAtd = false;
				if (events.size() == returnsAttended.size())
					selectAllReturnAtd = true;
			} else {
				selectAllAtd = false;
				if (events.size() == eventsAttended.size())
					selectAllAtd = true;
			}
			if (noFunding)
				showError("One or more participants are missing Funding sources");
		}
		if (staffEvents != null) {
			if (isReturn)
				selectAllStfReturnAtd = true;
			else
				selectAllStfAtd = true;
			for (StaffEvent se : staffEvents) {
				if (isReturn) {
					if (selectAllStfReturnAtd && !se.isAttended())
						selectAllStfReturnAtd = false;
				} else {
					if (selectAllStfAtd && !se.isAttended())
						selectAllStfAtd = false;
				}
				// se.setStartTimeBean(new TimeBean(se.getStartTime()));
				// se.setEndTimeBean(new TimeBean(se.getEndTime()));
				// null pointer is handeled within the time bean
				// se.setRosterStatTimeBean(new
				// TimeBean(se.getRosterStartTime()));
				// se.setRosterEndTimeBean(new TimeBean(se.getRosterEndTime()));
			}
		}
	}

	public void createAttendanceSheet(ActionEvent ae) {
		selectAllAtd = selectAllReturnAtd = selectAllStfAtd = selectAllStfReturnAtd = false;
		programEvent.setOverridePrice(setOverridePrice);
		if (validateProEventFields()) {
			String type = (String) ae.getComponent().getAttributes().get("return");
			if (type == null || type.equals("mainOnly")) {
				/*
				 * boolean holidayOverride =
				 * programEvent.getGroup().isAllowProgramtorunonaholiday() &&
				 * serviceLocator.getHolidayService().isHoliday(new Long(1),
				 * programEvent.getEventDate());
				 */
				if (programEvent.getGroup().getProgram().getType().getName().equals("Staff")) {
					// :TODO loading eargerly by default
					// programEvent.getGroup().setStaffMembers(
					// serviceLocator.getGroupedStaffService().listByGroup(programEvent.getGroup().getId()));
					if (programEvent.getGroup().getStaffMembers() == null
							|| programEvent.getGroup().getStaffMembers().isEmpty()) {
						showError("There are no staff members enrolled with the program of the selected event.");
						return;
					}
				} else {
					List<GroupedStudent> studentList = serviceLocator.getGroupedStudentService()
							.listByGroup(programEvent.getGroup().getId());
					programEvent.getGroup().setStudents(studentList);
					if (programEvent.getGroup().getStudents() == null
							|| programEvent.getGroup().getStudents().isEmpty()) {
						if (programEvent.getGroup().isReturnAvailable())
							showError("There are no " + Util.getMessage("students_label").toLowerCase()
									+ " enrolled with the program of the selected main event.");
						else
							showError("There are no " + Util.getMessage("students_label").toLowerCase()
									+ " enrolled with the program of the selected event.");
						return;
					}
					// if (!holidayOverride ||
					// programEvent.getProgram().getType().getName().equals("Individual"))
					// {
					createStudentEvents(programEvent);
					// }
				}
				if (!programEvent.getGroup().getProgram().getType().getName().equals("Staff")
						&& programEvent.getStudentEvents() == null) {
					showError("There are no active " + Util.getMessage("students_label").toLowerCase()
							+ " available within the program of the selected event.");
					return;
				}
				createStaffEvents(programEvent);
				if (programEvent.getStaffEvents().isEmpty()) {
					showError("There are no available staff members found within the program of the selected event.");
					if (programEvent.getGroup().getProgram().getType().getName().equals("Staff"))
						return;
				}
				/*
				 * if (holidayOverride) { programEvent.setStatus("finished"); }
				 */
				if (programEvent.getId() == null) {
					serviceLocator.getProgramEventService().create(programEvent);
				} else
					serviceLocator.getProgramEventService().update(programEvent);
				if (programEvent.getProgram().getType().getName().equals("Transport"))
					showInfo("Main attendance sheet created successfully.");
				else
					showInfo("Attendance sheet created successfully.");
			}
			if ((type == null || !type.equals("mainOnly")) && programEvent.getGroup().isReturnAvailable()) {

				ProgramEvent returnEvent = programEvent.getReturnEvent();
				returnEvent.setOverridePrice(setReturnOverridePrice);
				List<GroupedStudent> studentList = serviceLocator.getGroupedStudentService()
						.listByGroup(returnEvent.getGroup().getId());
				returnEvent.getGroup().setStudents(studentList);
				if (returnEvent.getGroup().getStudents() == null || returnEvent.getGroup().getStudents().isEmpty()) {
					showError("There are no " + Util.getMessage("students_label").toLowerCase()
							+ " enrolled with the program of the selected return event.");
					loadTransportAttendance();
					return;
				}
				/*
				 * boolean holidayOverride =
				 * returnEvent.getGroup().isAllowProgramtorunonaholiday() &&
				 * serviceLocator.getHolidayService().isHoliday(new Long(1),
				 * returnEvent.getEventDate());
				 */
				// if (!holidayOverride) {
				createStudentEvents(returnEvent);
				// }
				if (returnEvent.getStudentEvents() == null) {
					showError("There are no active " + Util.getMessage("students_label").toLowerCase()
							+ " available within the program of the selected return event.");
					return;
				}
				createStaffEvents(returnEvent);
				if (returnEvent.getStaffEvents().isEmpty()) {
					showError(
							"There are no available staff members found within the program of the selected return event.");
				}
				/*
				 * if (holidayOverride) { returnEvent.setStatus("finished"); }
				 */
				if (returnEvent.getId() == null) {
					serviceLocator.getProgramEventService().create(returnEvent);
				} else
					serviceLocator.getProgramEventService().update(returnEvent);
				showInfo("Return attendance sheet created successfully.");
			}
			if (programEvent.getProgram().getType().getName().equals("Transport")) {
				loadTransportAttendance();
			} else {
				if (programEvent.getStudentEvents() != null) {
					Collections.sort(programEvent.getStudentEvents(), new SortObjectByName());
				}
				if (programEvent.getStaffEvents() != null) {
					Collections.sort(programEvent.getStaffEvents(), new SortObjectByName());
				}
			}
			updateRecords();
		}
	}

	private void loadTransportAttendance() {
		TimeTrackerModel model = new TimeTrackerModel();
		Date fromDate = model.getFortnightStartDate(programEvent.getEventDate());
		Date toDate = model.getFortnightEnd();
		transportStdEvents = new ArrayList<TransportEventTableRow>();
		if (programEvent.getStudentEvents() != null) {
			Collections.sort(programEvent.getStudentEvents(), new SortEventsBySequence());
			for (StudentEvent event : programEvent.getStudentEvents()) {
				setRemainingHours(event, fromDate, toDate);
				transportStdEvents.add(new TransportEventTableRow(event, true));
			}
		}
		transportStfEvents = new ArrayList<TransportStaffEventTableRow>();
		if (programEvent.getStaffEvents() != null) {
			for (StaffEvent se : programEvent.getStaffEvents()) {
				transportStfEvents.add(new TransportStaffEventTableRow(se, true));
			}
		}
		ProgramEvent returnEvent = programEvent.getReturnEvent();
		if (programEvent.getGroup().isReturnAvailable() && returnEvent != null) {
			if (returnEvent.getStudentEvents() != null) {
				List<TransportEventTableRow> returns = new ArrayList<TransportEventTableRow>();
				Collections.sort(returnEvent.getStudentEvents(), new SortEventsBySequence());
				for (StudentEvent event : returnEvent.getStudentEvents()) {
					boolean found = false;
					for (TransportEventTableRow te : transportStdEvents) {
						if (te.getMainEvent() != null && te.getMainEvent().getGroupedStudent().getStudent().getId()
								.equals(event.getGroupedStudent().getStudent().getId())) {
							te.setReturnEvent(event);
							found = true;
							break;
						}
					}
					if (!found) {
						setRemainingHours(event, fromDate, toDate);
						returns.add(new TransportEventTableRow(event, false));
					}
				}
				transportStdEvents.addAll(returns);
			}
			if (returnEvent.getStaffEvents() != null) {
				List<TransportStaffEventTableRow> returns = new ArrayList<TransportStaffEventTableRow>();
				for (StaffEvent se : returnEvent.getStaffEvents()) {
					boolean found = false;
					for (TransportStaffEventTableRow te : transportStfEvents) {
						if (te.getMainEvent() != null
								&& te.getMainEvent().getStaffMember().getId().equals(se.getStaffMember().getId())) {
							te.setReturnEvent(se);
							found = true;
							break;
						}
					}
					if (!found) {
						returns.add(new TransportStaffEventTableRow(se, false));
					}
				}
				transportStfEvents.addAll(returns);
			}
		}
	}

	private void setRemainingHours(StudentEvent stdEvent, Date fromDate, Date toDate) {
		if (stdEvent.getStdFundingSrc() != null) {
			double fundingMins = stdEvent.getStdFundingSrc().getFundingHours() * 60;
			double remainingMins = fundingMins - serviceLocator.getStudentFundingSourceService()
					.getUsedHoursForTheFortnight(stdEvent.getGroupedStudent().getStudent().getId(), fromDate, toDate);
			stdEvent.setRemainingHours(remainingMins / 60);
			if (stdEvent.getRemainingHours() == -1)
				stdEvent.setFundingStatus("1Hr elapsed..");
			else if (stdEvent.getRemainingHours() < -1)
				stdEvent.setFundingStatus(stdEvent.getRemainingHours() + "Hrs elapsed..");
			else if (stdEvent.getRemainingHours() == 0)
				stdEvent.setFundingStatus("No contracted hours remaining..");
			else if (stdEvent.getRemainingHours() == 1)
				stdEvent.setFundingStatus("Only 1Hr remaining..");
			else // if (stdEvent.getRemainingHours() > 0 &&
					// stdEvent.getRemainingHours() <= 10)
				stdEvent.setFundingStatus("Only " + stdEvent.getRemainingHours() + "Hrs remaining..");
		} else {
			stdEvent.setRemainingHours(0);
			stdEvent.setFundingStatus("No funding source assigned!");
		}
	}

	public void markStdEvent(ValueChangeEvent ve) {
		StudentEvent event = (StudentEvent) ve.getComponent().getAttributes().get("event");
		String type = (String) ve.getComponent().getAttributes().get("mark");
		String tmp = (String) ve.getComponent().getAttributes().get("return");

		programEvent.setOverridePrice(setOverridePrice);
		boolean isReturn = false;
		if (tmp != null && tmp.equals("return")) {
			isReturn = true;
			programEvent.setOverridePrice(setReturnOverridePrice);
		}

		Boolean attended = (Boolean) ve.getNewValue();
		if (event != null && attended != null) {
			event.setAttended(attended);
			if (attended) {
				double chargeAmount;
				if (programEvent.isOverridePrice()) {
					if (tmp != null) {
						if (tmp.equals("return")) {
							chargeAmount = programEvent.getReturnEvent().getChargeAmount();
						} else {
							chargeAmount = programEvent.getChargeAmount();
						}
					} else {
						chargeAmount = programEvent.getChargeAmount();
					}
				} else {
					chargeAmount = event.getGroupedStudent().getChargeAmount() != null
							? event.getGroupedStudent().getChargeAmount() : 0;
				}
				if (event.getEventChargeTx() == null && chargeAmount != 0) {
					Transaction chargeTx = new Transaction();
					chargeTx.setTransactionType(EnumTransactionType.CREDIT);
					chargeTx.setStudentId(event.getGroupedStudent().getStudent().getId());
					chargeTx.setAmount(chargeAmount);
					chargeTx.setProgramEvent(event.getProEvent());
					chargeTx.setTransactionDate(event.getProEvent().getEventDate());
					chargeTx.setCreatedUserId(sessionContext.getUser().getId());
					chargeTx.setChargeType(EnumChargeType.EVENT);
					event.setEventChargeTx(chargeTx);
				}
				if (type != null && type.equals("popup")) {
					event.setTmpAmount(chargeAmount);
					event.setPaymentMethod("Cash");
					event.setTmpRemarks("");
				}
				if (event.getEventChargeTx() != null && type != null && !type.equals("popup")) {
					event.getEventChargeTx().setAmount(chargeAmount);
					event.setTmpAmount(chargeAmount);
				}
				event.setOtherChargeTxs(new ArrayList<Transaction>());
				if (isReturn) {
					returnsAttended.put(event.getId(), event);
					if (returnsAttended.values().containsAll(programEvent.getReturnEvent().getStudentEvents()))
						selectAllReturnAtd = true;
				} else {
					eventsAttended.put(event.getId(), event);
					if (eventsAttended.values().containsAll(programEvent.getStudentEvents()))
						selectAllAtd = true;
				}
			} else {
				if (isReturn) {
					selectAllReturnAtd = false;
					returnsAttended.remove(event.getId());
				} else {
					selectAllAtd = false;
					eventsAttended.remove(event.getId());
				}
			}
		}
	}

	public void markAllStdEvents(ValueChangeEvent ve) {
		Boolean attended = (Boolean) ve.getNewValue();
		String type = (String) ve.getComponent().getAttributes().get("mark");
		String isReturn = (String) ve.getComponent().getAttributes().get("return");
		if (isReturn != null && isReturn.equals("return"))
			markAllStdEvents(programEvent.getReturnEvent(), type, attended, true);
		else
			markAllStdEvents(programEvent, type, attended, false);
	}

	private void markAllStdEvents(ProgramEvent programEvent, String type, boolean attended, boolean isReturn) {
		for (StudentEvent event : programEvent.getStudentEvents()) {
			if (isReturn) {
				programEvent.setOverridePrice(setReturnOverridePrice);

			} else {
				programEvent.setOverridePrice(setOverridePrice);
			}
			event.setAttended(attended);
			if (attended) {
				double chargeAmount;
				if (programEvent.isOverridePrice()) {
					chargeAmount = programEvent.getChargeAmount();
				} else {
					chargeAmount = event.getGroupedStudent().getChargeAmount() != null
							? event.getGroupedStudent().getChargeAmount() : 0;
				}
				if (event.getEventChargeTx() == null && chargeAmount != 0) {
					Transaction chargeTx = new Transaction();
					chargeTx.setTransactionType(EnumTransactionType.CREDIT);
					chargeTx.setStudentId(event.getGroupedStudent().getStudent().getId());
					chargeTx.setProgramEvent(programEvent);
					chargeTx.setAmount(chargeAmount);
					chargeTx.setCreatedUserId(sessionContext.getUser().getId());
					chargeTx.setTransactionDate(programEvent.getEventDate());
					chargeTx.setChargeType(EnumChargeType.EVENT);
					event.setEventChargeTx(chargeTx);
				}
				event.setOtherChargeTxs(new ArrayList<Transaction>());
				if (type != null && type.equals("popup")) {
					event.setTmpAmount(chargeAmount);
					event.setPaymentMethod("Cash");
					event.setTmpRemarks("");
				}
				if (event.getEventChargeTx() != null && type != null && !type.equals("popup")) {
					event.getEventChargeTx().setAmount(chargeAmount);
					event.setTmpAmount(chargeAmount);
				}
				if (isReturn) {
					returnsAttended.put(event.getId(), event);
				} else {
					eventsAttended.put(event.getId(), event);
				}
			} else {
				if (isReturn) {
					returnsAttended.remove(event.getId());
				} else {
					eventsAttended.remove(event.getId());
				}
			}
		}
	}

	public void markAttendance() {
		boolean check = false;
		if (programEvent.getStudentEvents() != null) {
			for (StudentEvent studentEvent : programEvent.getStudentEvents()) {
				if (studentEvent.isAttended()) {
					check = true;
					if (studentEvent.getTmpAmount() > 0) {
						// if (studentEvent.getTmpAmount() <= programEvent
						// .getChargeAmount()) {
						Transaction paymentTx = new Transaction();
						paymentTx.setTransactionType(EnumTransactionType.DEBIT);
						paymentTx.setPaymentType(EnumPaymentType.EVENT);
						paymentTx.setPaymentMethod(studentEvent.getPaymentMethod());
						paymentTx.setStudentEvent(studentEvent);
						paymentTx.setStudentId(studentEvent.getGroupedStudent().getStudent().getId());
						paymentTx.setProgramEvent(programEvent);
						paymentTx.setAmount(studentEvent.getTmpAmount());
						paymentTx.setRemarks(studentEvent.getTmpRemarks());
						paymentTx.setCreatedUserId(sessionContext.getUser().getId());
						paymentTx.setTransactionDate(programEvent.getEventDate());
						studentEvent.getPaymentTxs().clear();
						studentEvent.getPaymentTxs().add(paymentTx);
						// } else {
						// showError("[Student ID: "
						// + studentEvent.getGroupedStudent().getStudent()
						// .getId()
						// + "] Amount cannot exceed the event charge amount");
						// return;
						// }
					} else if (studentEvent.getTmpAmount() < 0) {
						showError("[" + Util.getMessage("student_label") + " ID: "
								+ studentEvent.getGroupedStudent().getStudent().getId()
								+ "] Initial payment can not be a negative value.");
						return;
					}
					studentEvent.setAmountPaid(studentEvent.getTmpAmount());
				}
			}
		}
		setTotalMoneyCollected(programEvent);
		ProgramEvent returnEvent = programEvent.getReturnEvent();
		if (programEvent.getGroup().isReturnAvailable() && returnEvent.getStudentEvents() != null) {
			for (StudentEvent studentEvent : returnEvent.getStudentEvents()) {
				if (studentEvent.isAttended()) {
					check = true;
					studentEvent.setPaymentTxs(new ArrayList<Transaction>());
					if (studentEvent.getTmpAmount() > 0) {
						// if (studentEvent.getTmpAmount() <= returnEvent
						// .getChargeAmount()) {
						Transaction paymentTx = new Transaction();
						paymentTx.setTransactionType(EnumTransactionType.DEBIT);
						paymentTx.setPaymentType(EnumPaymentType.EVENT);
						paymentTx.setPaymentMethod(studentEvent.getPaymentMethod());
						paymentTx.setStudentEvent(studentEvent);
						paymentTx.setStudentId(studentEvent.getGroupedStudent().getStudent().getId());
						paymentTx.setProgramEvent(returnEvent);
						paymentTx.setAmount(studentEvent.getTmpAmount());
						paymentTx.setRemarks(studentEvent.getTmpRemarks());
						paymentTx.setCreatedUserId(sessionContext.getUser().getId());
						paymentTx.setTransactionDate(returnEvent.getEventDate());
						studentEvent.getPaymentTxs().clear();
						studentEvent.getPaymentTxs().add(paymentTx);
						// } else {
						// showError("[Student ID: "
						// + studentEvent.getGroupedStudent()
						// .getStudent().getId()
						// + "] Amount cannot exceed the event charge amount");
						// return;
						// }
					} else if (studentEvent.getTmpAmount() < 0) {
						showError("[" + Util.getMessage("student_label") + " ID: "
								+ studentEvent.getGroupedStudent().getStudent().getId()
								+ "] Initial payment can not be a negative value.");
						return;
					}
					studentEvent.setAmountPaid(studentEvent.getTmpAmount());
				}
			}
			setTotalMoneyCollected(returnEvent);
		}
		if (!check) {
			showError("There are no " + Util.getMessage("students_label").toLowerCase() + " marked as attended..");
			return;
		} else {
			// for (StudentEvent event : programEvent.getStudentEvents())
			// serviceLocator.getStudentEventService().update(event);
			if (programEvent.getStudentEvents() != null) {
				serviceLocator.getStudentEventService().updateList(programEvent.getStudentEvents());
			}
			serviceLocator.getProgramEventService().updateMoneyCollected(programEvent.getId(),
					programEvent.getTotalMoneyCollected(), programEvent.getTotalEFTCollected());
			// serviceLocator.getProgramEventService().update(programEvent);
			if (programEvent.getGroup().isReturnAvailable() && returnEvent.getStudentEvents() != null) {
				// for (StudentEvent event : returnEvent.getStudentEvents())
				// serviceLocator.getStudentEventService().update(event);
				serviceLocator.getStudentEventService().updateList(returnEvent.getStudentEvents());
				serviceLocator.getProgramEventService().updateMoneyCollected(returnEvent.getId(),
						returnEvent.getTotalMoneyCollected(), returnEvent.getTotalEFTCollected());
				// serviceLocator.getProgramEventService().update(returnEvent);
			}
			showInfo(Util.getMessage("student_label") + " Events updated successfully..");
		}
		visibleAttendance = false;
	}

	public void attendancePopup() {
		if (visibleAttendance) {
			if (programEvent.getStudentEvents() != null) {
				for (StudentEvent se : programEvent.getStudentEvents()) {
					se.setAttended(false);
					se.setAmountPaid(0);
					se.setTmpRemarks("");
					se.setEventChargeTx(null);
				}
			}
			eventsAttended = new HashMap<Long, StudentEvent>();
			selectAllAtd = false;
			if (programEvent.getGroup().isReturnAvailable()
					&& programEvent.getReturnEvent().getStudentEvents() != null) {
				for (StudentEvent se : programEvent.getReturnEvent().getStudentEvents()) {
					se.setAttended(false);
					se.setAmountPaid(0);
					se.setTmpRemarks("");
					se.setEventChargeTx(null);
				}
				returnsAttended = new HashMap<Long, StudentEvent>();
				selectAllReturnAtd = false;
			}
			visibleAttendance = false;
		} else
			visibleAttendance = true;
	}

	public void deleteAttendanceSheet(ActionEvent ae) {
		String type = (String) ae.getComponent().getAttributes().get("return");
		if (type == null || !type.equals("return")) {
			deleteAttendanceSheet(programEvent, true, false);
			eventsAttended.clear();
		} else if (programEvent.getReturnEvent() != null) {
			deleteAttendanceSheet(programEvent.getReturnEvent(), true, true);
			returnsAttended.clear();
		}
		if (programEvent.getProgram().getType().getName().equals("Transport"))
			loadTransportAttendance();
	}

	private void deleteAttendanceSheet(ProgramEvent programEvent, boolean includingStaffEvents, boolean isReturn) {
		if (programEvent.isInvoiced()) {
			showError("Operation was not allowed. Activity statements are already generated for this event.");
			return;
		}
		if (!isUserInRole("ROLE_SUPERUSER")) {
			if (isReturn) {
				if (returnStatus.equals("finished") || returnStatus.equals("banked")) {
					showError("Operation was not allowed. Event status is marked as "
							+ (returnStatus.equals("finished") ? "completed" : "banked"));
					return;
				}
			} else {
				if (eventStatus.equals("finished") || eventStatus.equals("banked")) {
					showError("Operation was not allowed. Event status is marked as "
							+ (eventStatus.equals("finished") ? "completed" : "banked"));
					return;
				}
			}
		}
		if (isReturn) {
			returnStatus = "pending";
			programEvent.setStatus(returnStatus);
		} else {
			eventStatus = "pending";
			programEvent.setStatus(eventStatus);
		}
		programEvent.setGeneratedDate(null);
		programEvent.setCompletedDate(null);
		programEvent.setPrintedDate(null);
		programEvent.setTotalMoneyCollected(0);
		programEvent.setTotalEFTCollected(0);
		serviceLocator.getStudentEventService().deleteList(programEvent.getStudentEvents());
		serviceLocator.getStaffEventService().deleteList(programEvent.getStaffEvents());
		serviceLocator.getProgramEventService().updateMoneyCollected(programEvent.getId(), 0, 0);
		programEvent.getStudentEvents().clear();
		programEvent.setStaffEvents(null);
		serviceLocator.getProgramEventService().update(programEvent);
		showInfo("Attendance sheets deleted successfully.");
		updateRecords();
	}

	private void updateAttendanceSheet(ProgramEvent event) {
		if (event.getStudentEvents() != null) {
			for (StudentEvent studentEvent : event.getStudentEvents()) {
				Transaction chargeTx = null;
				if (!studentEvent.isAttended()) {
					studentEvent.setAmountPaid(0);
					studentEvent.setRemarks("");
					if (studentEvent.getEventChargeTx() != null) {
						chargeTx = studentEvent.getEventChargeTx();
						studentEvent.setEventChargeTx(null);
					}
					if (!studentEvent.getPaymentTxs().isEmpty()) {
						studentEvent.getPaymentTxs().clear();
					}
					if (!studentEvent.getOtherChargeTxs().isEmpty()) {
						studentEvent.getOtherChargeTxs().clear();
					}
				}
				serviceLocator.getStudentEventService().update(studentEvent);
				// :TODO check if deleting this separately is necessary
				if (chargeTx != null && chargeTx.getId() != null)
					serviceLocator.getTransactionService().delete(chargeTx.getId());
			}
		}
	}

	public void initPayment() {
		double paid = 0;
		collectionId = new Long(0);
		paymentTx = new Transaction();
		for (Transaction tx : studentEvent.getPaymentTxs()) {
			if (tx.getPaymentType().equals(EnumPaymentType.EVENT)) {
				paid = paid + tx.getAmount();
			}
		}
		if (studentEvent.getEventChargeTx() != null) {
			studentEvent.setTmpAmount(studentEvent.getEventChargeTx().getAmount() - paid);
			paymentTx.setAmount(studentEvent.getEventChargeTx().getAmount() - paid);
		} else {
			studentEvent.setTmpAmount(0);
			paymentTx.setAmount(0);
		}
		paymentTx.setTransactionType(EnumTransactionType.DEBIT);
		paymentTx.setPaymentType(EnumPaymentType.EVENT);
		paymentTx.setStudentEvent(studentEvent);
		paymentTx.setStudentId(studentEvent.getGroupedStudent().getStudent().getId());
		paymentTx.setProgramEvent(studentEvent.getProEvent());
		paymentTx.setCreatedUserId(sessionContext.getUser().getId());
		paymentTx.setTransactionDate(studentEvent.getProEvent().getEventDate());
	}

	public void createNewPayment(ActionEvent ae) {
		studentEvent = (StudentEvent) ae.getComponent().getAttributes().get("stdEvent");
		stdEventType = (String) ae.getComponent().getAttributes().get("eventType");
		paymentTypes = new ArrayList<SelectItem>();
		paymentTypes.add(new SelectItem(EnumPaymentType.EVENT, EnumPaymentType.EVENT.getId()));
		if (!studentEvent.getOtherChargeTxs().isEmpty()) {
			for (Transaction tx : studentEvent.getOtherChargeTxs()) {
				if (tx.getChargeType().equals(EnumChargeType.PERSONAL)) {
					paymentTypes.add(new SelectItem(EnumPaymentType.PERSONAL, EnumPaymentType.PERSONAL.getId()));
					break;
				}
			}
		}
		paymentTypes.add(new SelectItem(EnumPaymentType.COLLECTION, EnumPaymentType.COLLECTION.getId()));
		initPayment();
		paymentPopup();
	}

	public void changePaymentType(ValueChangeEvent ve) {
		EnumPaymentType type = (EnumPaymentType) ve.getNewValue();
		if (type != EnumPaymentType.COLLECTION) {
			double paid = 0;
			for (Transaction tx : studentEvent.getPaymentTxs()) {
				if (tx.getPaymentType().equals(type)) {
					paid = paid + tx.getAmount();
				}
			}
			double chargeAmount = studentEvent.getGroupedStudent().getChargeAmount() != null
					? studentEvent.getGroupedStudent().getChargeAmount() : 0;
			if (chargeAmount == 0) {
				// gets the instance variable (PE)'s charge amount, because
				// they could have modified it on the UI prior to mark the
				// SE
				chargeAmount = studentEvent.getProEvent().getChargeAmount();
			}
			if (type.equals(EnumPaymentType.EVENT)) {
				studentEvent.setTmpAmount(chargeAmount - paid);
			}
			if (type.equals(EnumPaymentType.PERSONAL)) {
				double charge = 0;
				for (Transaction tx : studentEvent.getOtherChargeTxs()) {
					if (tx.getChargeType().equals(EnumChargeType.PERSONAL)) {
						charge = charge + tx.getAmount();
					}
				}
				studentEvent.setTmpAmount(charge - paid);
			}
		} else if (collectionId != 0) {
			double paid = 0;
			for (Transaction tx : studentEvent.getPaymentTxs()) {
				if (tx.getPaymentType().equals(EnumPaymentType.COLLECTION)
						&& tx.getCollection().getId().equals(collectionId))
					paid = paid + tx.getAmount();
			}
			studentEvent.setTmpAmount(paid);
		} else {
			studentEvent.setTmpAmount(0);
		}
		paymentTx.setPaymentType(type);
		if (!type.equals(EnumPaymentType.COLLECTION)) {
			paymentTx.setAmount(studentEvent.getTmpAmount());
		} else {
			paymentTx.setAmount(0);
		}
		if (type != null && type.equals(EnumPaymentType.COLLECTION)) {
			collection = serviceLocator.getCollectionService().listActiveCollections();
		}
	}

	public void addPayment() {
		EnumPaymentType payType = paymentTx.getPaymentType();
		if (paymentTx.getAmount() == 0) {
			showError("You have added amount 0..");
			return;
		}
		if (payType == EnumPaymentType.COLLECTION && collectionId == 0) {
			showError("Please select a collection..");
			return;
		}
		// if (!payType.equals(EnumPaymentType.COLLECTION))
		if (payType.equals(EnumPaymentType.PERSONAL)) {
			if (paymentTx.getAmount() > 0 && (paymentTx.getAmount() > (studentEvent.getTmpAmount()))) {
				showError("New amount should not exceed the amount payable..");
				return;
			}
		}
		if (paymentTx.getAmount() < 0 && paymentTx.getRemarks().isEmpty()) {
			showError("Please add a remark..");
			return;

		}
		if (payType == EnumPaymentType.EVENT) {
			if (paymentTx.getAmount() < -(studentEvent.getProEvent().getChargeAmount() - studentEvent.getTmpAmount())) {
				showError("Amount can not exceeds the total amount paid for event charges..");
				return;
			}
		}
		if (payType == EnumPaymentType.PERSONAL) {
			double total = 0;
			for (Transaction tx : studentEvent.getOtherChargeTxs()) {
				total = total + tx.getAmount();
			}
			if (paymentTx.getAmount() < -(total - studentEvent.getTmpAmount())) {
				showError("Amount can not exceeds the total amount paid for additional charges..");
				return;
			}
		}
		if (payType == EnumPaymentType.COLLECTION) {
			if (paymentTx.getAmount() < -(studentEvent.getTmpAmount())) {
				showError("Amount can not exceeds the total amount paid for the collection.");
				return;
			}
			Transaction t = new Transaction();
			t.setTransactionType(EnumTransactionType.CREDIT);
			t.setStudentEvent(studentEvent);
			t.setStudentId(studentEvent.getGroupedStudent().getStudent().getId());
			t.setProgramEvent(studentEvent.getProEvent());
			t.setCollection(paymentTx.getCollection());
			t.setChargeType(EnumChargeType.COLLECTION);
			t.setAmount(paymentTx.getAmount());
			t.setCreatedUserId(sessionContext.getUser().getId());
			t.setTransactionDate(studentEvent.getProEvent().getEventDate());
			t.setRemarks("Charge for " + " '" + paymentTx.getCollection().getName() + "'");
			studentEvent.getOtherChargeTxs().add(t);
		}
		studentEvent.getPaymentTxs().add(paymentTx);
		initPayment();
	}

	public void savePayment() {
		double tmp = 0;
		for (Transaction tx : studentEvent.getPaymentTxs()) {
			tmp = tmp + tx.getAmount();
		}
		studentEvent.setAmountPaid(tmp);
		serviceLocator.getStudentEventService().update(studentEvent);
		if (stdEventType != null && stdEventType.equals("return")) {
			setTotalMoneyCollected(programEvent.getReturnEvent());
			serviceLocator.getProgramEventService().updateMoneyCollected(programEvent.getReturnEvent().getId(),
					programEvent.getReturnEvent().getTotalMoneyCollected(),
					programEvent.getReturnEvent().getTotalEFTCollected());
		} else {
			setTotalMoneyCollected(programEvent);
			serviceLocator.getProgramEventService().updateMoneyCollected(programEvent.getId(),
					programEvent.getTotalMoneyCollected(), programEvent.getTotalEFTCollected());
		}
		// if (programEvent.getReturnEvent() != null
		// && programEvent.getReturnEvent().getStudentEvents() != null) {
		// setTotalMoneyCollected(programEvent.getReturnEvent());
		// serviceLocator.getProgramEventService().update(
		// programEvent.getReturnEvent());
		// }
		studentEvent.setTmpTxList(new ArrayList<Transaction>());
		updateRecords();
		// loadProgramEvent(programEvent);
		visiblePayment = false;
	}

	public void deletePayment(ActionEvent ae) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Transaction tx = (Transaction) ae.getComponent().getAttributes().get("tx");
		if (tx.getCollection() != null) {
			for (Transaction charge : studentEvent.getOtherChargeTxs()) {
				if (charge.getCollection() != null && tx.getCollection().equals(charge.getCollection())
						&& tx.getAmount() == charge.getAmount() && dateFormat.format(tx.getTransactionDate())
								.equals(dateFormat.format(charge.getTransactionDate()))) {
					studentEvent.getTmpTxList().add(charge);
					studentEvent.getOtherChargeTxs().remove(charge);
					break;
				}
			}
		}
		if (tx.getId() != null) {
			studentEvent.getTmpTxList().add(tx);
		}
		studentEvent.getPaymentTxs().remove(tx);
		double paid = 0;
		for (Transaction tx1 : studentEvent.getPaymentTxs()) {
			if (tx1.getPaymentType().equals(paymentTx.getPaymentType())) {
				paid = paid + tx1.getAmount();
			}
		}
		double chargeAmount = studentEvent.getGroupedStudent().getChargeAmount() != null
				? studentEvent.getGroupedStudent().getChargeAmount() : 0;
		if (chargeAmount == 0) {
			// gets the instance variable (PE)'s charge amount, because
			// they could have modified it on the UI prior to mark the
			// SE
			chargeAmount = studentEvent.getProEvent().getChargeAmount();
		}
		if (paymentTx.getPaymentType().equals(EnumPaymentType.EVENT)) {
			studentEvent.setTmpAmount(chargeAmount - paid);
			paymentTx.setAmount(chargeAmount - paid);
		}
		if (paymentTx.getPaymentType().equals(EnumPaymentType.PERSONAL)) {
			chargeAmount = 0;
			for (Transaction tx1 : studentEvent.getOtherChargeTxs()) {
				if (tx1.getChargeType().equals(EnumChargeType.PERSONAL)) {
					chargeAmount = chargeAmount + tx1.getAmount();
				}
			}
			studentEvent.setTmpAmount(chargeAmount - paid);
			paymentTx.setAmount(chargeAmount - paid);
		}
	}

	private void setTotalMoneyCollected(ProgramEvent programEvent) {
		if (programEvent.getStudentEvents() != null) {
			double totalMoney = 0;
			double totalEFT = 0;
			for (StudentEvent studentEvent : programEvent.getStudentEvents()) {
				for (Transaction tx : studentEvent.getPaymentTxs()) {
					if (tx.getPaymentMethod().equals("EFT"))
						totalEFT = totalEFT + tx.getAmount();
					else
						totalMoney = totalMoney + tx.getAmount();
				}
			}
			programEvent.setTotalMoneyCollected(totalMoney);
			programEvent.setTotalEFTCollected(totalEFT);
		}
	}

	public void collectionChanged(ValueChangeEvent ve) {
		collectionId = (Long) ve.getNewValue();
		double paid = 0;
		for (Transaction tx : studentEvent.getPaymentTxs()) {
			if (tx.getPaymentType().equals(EnumPaymentType.COLLECTION) && tx.getCollection() != null
					&& tx.getCollection().getId().equals(collectionId))
				paid = paid + tx.getAmount();
		}
		if (collectionId != null && collectionId != 0)
			paymentTx.setCollection(serviceLocator.getCollectionService().retrieve(collectionId));
		studentEvent.setTmpAmount(paid);
		paymentTx.setAmount(0);
	}

	public void paymentPopup() {
		if (visiblePayment) {
			List<Transaction> paymentTxs = new ArrayList<Transaction>();
			List<Transaction> otherChrgTxs = new ArrayList<Transaction>();
			for (Transaction tx : studentEvent.getPaymentTxs()) {
				if (tx.getId() == null)
					paymentTxs.add(tx);
			}
			for (Transaction tx : studentEvent.getOtherChargeTxs()) {
				if (tx.getId() == null)
					otherChrgTxs.add(tx);
			}
			if (!paymentTxs.isEmpty())
				studentEvent.getPaymentTxs().removeAll(paymentTxs);
			if (!otherChrgTxs.isEmpty())
				studentEvent.getOtherChargeTxs().removeAll(otherChrgTxs);
			if (!studentEvent.getTmpTxList().isEmpty()) {
				for (Transaction tx : studentEvent.getTmpTxList()) {
					if (tx.getTransactionType().equals(EnumTransactionType.CREDIT)) {
						studentEvent.getOtherChargeTxs().add(tx);
					} else {
						studentEvent.getPaymentTxs().add(tx);
					}
				}
				studentEvent.setTmpTxList(new ArrayList<Transaction>());
			}
			visiblePayment = false;
		} else {
			visiblePayment = true;
		}
	}

	public void createNewCharge(ActionEvent ae) {
		studentEvent = (StudentEvent) ae.getComponent().getAttributes().get("stdEvent");
		stdEventType = (String) ae.getComponent().getAttributes().get("eventType");
		initChargeTx();
		chargePopup();

	}

	private void initChargeTx() {
		chargeTx = new Transaction();
		chargeTx.setChargeType(EnumChargeType.PERSONAL);
		chargeTx.setTransactionType(EnumTransactionType.CREDIT);
		chargeTx.setStudentEvent(studentEvent);
		chargeTx.setStudentId(studentEvent.getGroupedStudent().getStudent().getId());
		chargeTx.setProgramEvent(studentEvent.getProEvent());
		chargeTx.setCreatedUserId(sessionContext.getUser().getId());
		chargeTx.setTransactionDate(studentEvent.getProEvent().getEventDate());
	}

	public void addCharge() {
		if (chargeTx.getAmount() == 0) {
			showError("Amount can not be zero(0)..");
			return;
		}
		if (chargeTx.getRemarks().isEmpty()) {
			showError("Please add a remark..");
			return;
		}
		if (chargeTx.getAmount() < 0) {
			double total = 0;
			for (Transaction tx : studentEvent.getOtherChargeTxs()) {
				total = total + tx.getAmount();
			}
			if (chargeTx.getAmount() < -total) {
				showError("Amount can not exceeds the total amount charged as additional charges..");
				return;
			}
		}
		studentEvent.getOtherChargeTxs().add(chargeTx);
		initChargeTx();
	}

	public void saveCharges() {
		serviceLocator.getStudentEventService().update(studentEvent);
		visibleCharge = false;
	}

	public void chargePopup() {
		if (visibleCharge) {
			List<Transaction> otherChrgTxs = new ArrayList<Transaction>();
			for (Transaction tx : studentEvent.getOtherChargeTxs()) {
				if (tx.getId() == null)
					otherChrgTxs.add(tx);
			}
			if (!otherChrgTxs.isEmpty())
				studentEvent.getOtherChargeTxs().removeAll(otherChrgTxs);
			visibleCharge = false;
		} else {
			visibleCharge = true;
		}
	}

	public void additionalChargesPopup(ActionEvent ae) {
		stdEventType = (String) ae.getComponent().getAttributes().get("return");
		if (visibleCharges) {
			visibleCharges = false;
		} else {
			chargeAmount = 0;
			txRemarks = "";
			gstIncluded = false;
			selectAll = false;
			txStudentEvents = new ArrayList<StudentEvent>();
			if (stdEventType == null || !stdEventType.equals("return")) {
				for (StudentEvent se : programEvent.getStudentEvents()) {
					if (se.isAttended()) {
						se.setUi_selected(false);
						se.setTmpAmount(0);
						se.setTmpRemarks("");
						se.setTmpGST(false);
						txStudentEvents.add(se);
					}
				}
			}
			if (stdEventType != null && stdEventType.equals("return")) {
				for (StudentEvent se : programEvent.getReturnEvent().getStudentEvents()) {
					if (se.isAttended()) {
						se.setUi_selected(false);
						se.setTmpAmount(0);
						se.setTmpRemarks("");
						se.setTmpGST(false);
						txStudentEvents.add(se);
					}
				}
			}
			if (txStudentEvents.isEmpty()) {
				showError("There are no attended " + Util.getMessage("students_label").toLowerCase()
						+ " to add charges..");
				return;
			}
			visibleCharges = true;
		}
	}

	public void markStudent(ValueChangeEvent ve) {
		Boolean selected = (Boolean) ve.getNewValue();
		StudentEvent std = (StudentEvent) ve.getComponent().getAttributes().get("se");
		std.setUi_selected(selected);
		if (selected) {
			std.setTmpAmount(chargeAmount);
			std.setTmpRemarks(txRemarks);
			std.setTmpGST(gstIncluded);
			selectAll = true;
			for (StudentEvent se : txStudentEvents) {
				if (!se.isUi_selected()) {
					selectAll = false;
					break;
				}
			}
		} else {
			std.setTmpAmount(0);
			std.setTmpRemarks("");
			std.setTmpGST(false);
			selectAll = false;
		}
	}

	public void markAllStudents(ValueChangeEvent ve) {
		selectAll = (Boolean) ve.getNewValue();
		for (StudentEvent se : txStudentEvents) {
			se.setUi_selected(selectAll);
			if (selectAll) {
				se.setTmpAmount(chargeAmount);
				se.setTmpRemarks(txRemarks);
				se.setTmpGST(gstIncluded);
			} else {
				se.setTmpAmount(0);
				se.setTmpRemarks("");
				se.setTmpGST(false);
			}
		}
	}

	public void addCharges() {
		boolean selected = false;
		boolean amount = true;
		boolean remarks = true;
		for (StudentEvent sd : txStudentEvents) {
			if (sd.isUi_selected()) {
				selected = true;
				if (sd.getTmpAmount() == 0)
					amount = false;
				if (sd.getTmpRemarks() == null || sd.getTmpRemarks().isEmpty())
					remarks = false;
			}
		}
		if (!selected) {
			showError("Please select at least one " + Util.getMessage("student_label").toLowerCase()
					+ " to add charges.");
			return;
		}
		if (!amount) {
			showError("Zero(0) amounts can not be added. Please deselect or provide a valid amount.");
			return;
		}
		if (!remarks) {
			showError("Remarks can not be empty. Please deselect or provide a remark.");
			return;
		}
		for (StudentEvent sd : txStudentEvents) {
			if (sd.isUi_selected()) {
				Transaction t = new Transaction();
				if (stdEventType == null || !stdEventType.equals("return")) {
					t.setProgramEvent(programEvent);
					t.setTransactionDate(programEvent.getEventDate());
				}
				if (stdEventType != null && stdEventType.equals("return")) {
					t.setProgramEvent(programEvent.getReturnEvent());
					t.setTransactionDate(programEvent.getReturnEvent().getEventDate());
				}
				t.setStudentEvent(sd);
				t.setStudentId(sd.getGroupedStudent().getStudent().getId());
				t.setTransactionType(EnumTransactionType.CREDIT);
				t.setAmount(sd.getTmpAmount());
				t.setRemarks(sd.getTmpRemarks());
				t.setGstIncluded(sd.isTmpGST());
				t.setCreatedUserId(sessionContext.getUser().getId());
				t.setChargeType(EnumChargeType.PERSONAL);
				sd.getOtherChargeTxs().add(t);
				serviceLocator.getStudentEventService().update(sd);
			}
		}
		visibleCharges = false;
	}

	public void markStfEvent(ValueChangeEvent ve) {
		StaffEvent event = (StaffEvent) ve.getComponent().getAttributes().get("event");
		String tmp = (String) ve.getComponent().getAttributes().get("return");
		boolean isReturn = false;
		if (tmp != null && tmp.equals("return"))
			isReturn = true;
		Boolean attended = (Boolean) ve.getNewValue();
		if (event != null && attended != null) {
			event.setAttended(attended);
			if (attended) {
				if (event.getStartTimeBean() == null || !event.getStartTimeBean().isValid()) {
					event.setStartTimeBean(new TimeBean(event.getRosterStartTime()));
					event.setEndTimeBean(new TimeBean(event.getRosterEndTime()));
					event.setRosterStatTimeBean(new TimeBean(event.getRosterStartTime()));
					event.setRosterEndTimeBean(new TimeBean(event.getRosterEndTime()));
				}
				if (isReturn) {
					selectAllStfReturnAtd = true;
					for (StaffEvent se : programEvent.getReturnEvent().getStaffEvents()) {
						if (!se.isAttended()) {
							selectAllStfReturnAtd = false;
							break;
						}
					}
				} else {
					selectAllStfAtd = true;
					for (StaffEvent se : programEvent.getStaffEvents()) {
						if (!se.isAttended()) {
							selectAllStfAtd = false;
							break;
						}
					}
				}
			} else {
				if (isReturn) {
					selectAllStfReturnAtd = false;
				} else {
					selectAllStfAtd = false;
				}
			}
		}
	}

	public void markAllStfEvents(ValueChangeEvent ve) {
		Boolean attended = (Boolean) ve.getNewValue();
		String isReturn = (String) ve.getComponent().getAttributes().get("return");
		if (attended != null) {
			List<StaffEvent> events;
			if (isReturn != null && isReturn.equals("return")) {
				events = programEvent.getReturnEvent().getStaffEvents();
			} else {
				events = programEvent.getStaffEvents();
			}
			for (StaffEvent se : events) {
				se.setAttended(attended);
				if (attended && (se.getStartTimeBean() == null || !se.getStartTimeBean().isValid())) {
					se.setStartTimeBean(new TimeBean(se.getRosterStartTime()));
					se.setEndTimeBean(new TimeBean(se.getRosterEndTime()));
				}
			}
		}
	}

	public void eventDateChanged(ValueChangeEvent vce) {
		Date newDate = (Date) vce.getNewValue();
		setActualEventTimes = false;
		setActualReturnEventTimes = false;
		if (newDate != null && newDate.after(new Date())) {
			programEvent.setActualStartTime(null);
			programEvent.setActualEndTime(null);
			actualStartTime = new TimeBean();
			actualEndTime = new TimeBean();
			actualReturnStartTime = new TimeBean();
			actualReturnEndTime = new TimeBean();
		}
	}

	public boolean isExpired(Date date) {
		if (date.before(new Date()))
			return true;
		else
			return false;
	}

	private boolean validateProEventFields() {
		Date startDate = programEvent.getProgram().getStartDate();
		Date endDate = programEvent.getProgram().getEndDate();
		Date eventDate = programEvent.getEventDate();
		if (eventDate == null) {
			showError("Event date can not be empty..");
			return false;
		}
		if (!programEvent.getGroup().isAllowProgramtorunonaholiday()
				&& serviceLocator.getHolidayService().isHoliday(new Long(1), eventDate)) {
			showError("Selected date is a holiday. The program is not allowed to run on holidays.");
			return false;
		}
		if (eventDate.compareTo(startDate) < 0 || endDate.compareTo(eventDate) < 0) {
			showError("Invalid date! Event date does not match with the cost center start/end dates..");
			return false;
		}
		if (!validateProEventTimes()) {
			return false;
		}
		programEvent.setStartTime(rosterStartTime.getDateTime(programEvent.getEventDate()));
		if (programEvent.getGroup().getProgram().getType().getName().equals("Individual")) {
			if (rosterStartTime.getTimeAsString().substring(6).equals("PM")
					&& rosterEndTime.getTimeAsString().substring(6).equals("AM")) {
				Calendar c = Calendar.getInstance();
				c.setTime(programEvent.getEventDate());
				c.add(Calendar.DATE, 1);
				programEvent.setEndTime(rosterEndTime.getDateTime(c.getTime()));
			} else {
				programEvent.setEndTime(rosterEndTime.getDateTime(programEvent.getEventDate()));
			}
		} else {
			programEvent.setEndTime(rosterEndTime.getDateTime(programEvent.getEventDate()));
		}
		if (programEvent.getProgram().getType().getName().equals("Transport")) {
			if (selectedVehicleId == 0) {
				if (programEvent.getGroup().isReturnAvailable())
					showError("Please select a vehicle to the main event.");
				else
					showError("Please select a vehicle.");
				return false;
			}
			Vehicle mainVehicle = serviceLocator.getVehicleService().retrieve(selectedVehicleId);
			programEvent.setVehicle(mainVehicle);
			if (programEvent.getGroup().isReturnAvailable()) {
				if (!validateReturnEventTime(programEvent.getEventDate())) {
					return false;
				}
				programEvent.getReturnEvent()
						.setStartTime(returnStartTime.getDateTime(programEvent.getReturnEvent().getEventDate()));
				programEvent.getReturnEvent()
						.setEndTime(returnEndTime.getDateTime(programEvent.getReturnEvent().getEventDate()));
				if (returnVehicleId == 0) {
					showError("Please select a vehicle to the return event.");
					return false;
				} else if (selectedVehicleId == returnVehicleId) {
					programEvent.getReturnEvent().setVehicle(mainVehicle);
				} else {
					programEvent.getReturnEvent()
							.setVehicle(serviceLocator.getVehicleService().retrieve(returnVehicleId));
				}
				programEvent.getReturnEvent().setEventDate(programEvent.getEventDate());
			}
		} else {
			if (selectedLocationId == 0) {
				showError("Please select a location..");
				return false;
			}
			programEvent.setLocation(serviceLocator.getLocationService().retrieve(selectedLocationId));
		}
		if (programEvent.getProgram().getType().equals("Individual")
				|| programEvent.getProgram().getType().equals("Student")) {
			return validateTimeWithCluster();
		}
		return true;
	}

	private boolean validateProEventTimes() {
		// if
		// (!programEvent.getGroup().getProgram().getType().getName().equals("Individual"))
		// {
		if (!rosterStartTime.getDateTime(programEvent.getStartTime())
				.before(rosterEndTime.getDateTime(programEvent.getEndTime()))) {
			showError("Invalid roster times. Start time should be before the end time");
			return false;
		}
		if (setActualEventTimes && !actualStartTime.getDateTime(programEvent.getActualStartTime())
				.before(actualEndTime.getDateTime(programEvent.getActualEndTime()))) {
			showError("Invalid actual times. Start time should be before the end time");
			return false;
		}
		if (setActualReturnEventTimes
				&& !actualReturnStartTime.getDateTime(programEvent.getReturnEvent().getActualStartTime())
						.before(actualReturnEndTime.getDateTime(programEvent.getReturnEvent().getActualEndTime()))) {
			showError("Invalid actual return times. Start time should be before the end time");
			return false;
		}
		// }
		return true;
	}

	private boolean validateStaffTimes(Date startTime, Date endTime) {
		if (startTime.before(endTime)) {
			return true;
		}
		return false;
	}

	private boolean validateReturnEventTime(Date eventDate) {
		if (returnStartTime.getDateTime(eventDate).before(returnEndTime.getDateTime(eventDate))) {
			return true;
		}
		programEvent.getReturnEvent().setStatus("pending");
		showError("Invalid return time. Start time should be before the end time");
		return false;
	}

	public void setActualEventTimes(ValueChangeEvent vce) {
		boolean checked = (Boolean) vce.getNewValue();
		if (checked) {
			actualStartTime.setDateTime(programEvent.getStartTime());
			actualEndTime.setDateTime(programEvent.getEndTime());
		}
	}

	public void setActualReturnEventTimes(ValueChangeEvent vce) {
		setActualReturnEventTimes = (Boolean) vce.getNewValue();
		if (setActualReturnEventTimes) {
			actualReturnStartTime.setDateTime(programEvent.getReturnEvent().getStartTime());
			actualReturnEndTime.setDateTime(programEvent.getReturnEvent().getEndTime());
		}
	}

	public void setReturnOverridePrice(ValueChangeEvent vce) {
		boolean override = (Boolean) vce.getNewValue();
		setReturnOverridePrice = override;
		programEvent.getReturnEvent().setOverridePrice(override);
	}

	public void setOverridePrice(ValueChangeEvent vce) {
		boolean override = (Boolean) vce.getNewValue();
		setOverridePrice = override;
		programEvent.setOverridePrice(override);
	}

	public void selectEventVehicle(ValueChangeEvent ve) {
		selectedVehicleId = (Long) ve.getNewValue();
		if (selectedVehicleId != null && selectedVehicleId > 0) {
			Vehicle vehi = serviceLocator.getVehicleService().retrieve(selectedVehicleId);
			programEvent.setVehicle(vehi);
			initVehicleImage(vehi);
		}
	}

	private void initVehicleImage(Vehicle vehicle) {
		byte[] tmpData = null;
		if (vehicle.getPhoto() != null) {
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

	// methods calculating the total money collected when creating
	// attendance

	public double getMainEventCollection() {
		double tempCollected1 = 0.0;
		for (StudentEvent studentEvent : programEvent.getStudentEvents()) {
			if (studentEvent.isAttended()) {
				if (studentEvent.getTmpAmount() > 0) {
					tempCollected1 += studentEvent.getTmpAmount();
				}
			}
		}
		return tempCollected1;
	}

	public double getReturnEventCollection() {
		double tempCollected2 = 0.0;
		if (programEvent.getReturnEvent() != null)
			for (StudentEvent returnEvent : programEvent.getReturnEvent().getStudentEvents()) {
				if (returnEvent.isAttended()) {
					if (returnEvent.getTmpAmount() > 0) {
						tempCollected2 += returnEvent.getTmpAmount();
					}
				}
			}
		return tempCollected2;
	}

	private void finalizeSelectItems(List<SelectItem> selectItems, String firstLabel) {
		if (selectItems.isEmpty())
			selectItems.add(new SelectItem(0, "Not Available"));
		else
			selectItems.add(0, new SelectItem(0, firstLabel));
	}

	/*
	 * getters and setters
	 */

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public List<ProgramEvent> getEvents() {
		return events;
	}

	public HashMap<String, ProgramEvent> getSelectedEventsMap() {
		return selectedEventsMap;
	}

	public void setSelectAllEvents(boolean selectAllEve) {
		this.selectAllEvents = selectAllEve;
	}

	public boolean isSelectAllEvents() {
		return selectAllEvents;
	}

	public void setSelectAllAtd(boolean selectAllAtd) {
		this.selectAllAtd = selectAllAtd;
	}

	public boolean isSelectAllAtd() {
		return selectAllAtd;
	}

	public void setEventsAttended(HashMap<Long, StudentEvent> eventsAttended) {
		this.eventsAttended = eventsAttended;
	}

	public HashMap<Long, StudentEvent> getEventsAttended() {
		return eventsAttended;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setProgramEvent(ProgramEvent programEvent) {
		this.programEvent = programEvent;
	}

	public ProgramEvent getProgramEvent() {
		return programEvent;
	}

	public List<ProgramEvent> getTabs() {
		return tabs;
	}

	public void setStudentEvent(StudentEvent studentEvent) {
		this.studentEvent = studentEvent;
	}

	public StudentEvent getStudentEvent() {
		return studentEvent;
	}

	public Transaction getPaymentTx() {
		return paymentTx;
	}

	public void setPaymentTx(Transaction paymentTx) {
		this.paymentTx = paymentTx;
	}

	public boolean isVisiblePayment() {
		return visiblePayment;
	}

	public TimeBean getRosterStartTime() {
		/*
		 * if (rosterStartTime.getSession() == Calendar.PM ||
		 * rosterStartTime.getHours() == 12) {
		 * rosterEndTime.setSession(Calendar.PM);
		 * rosterEndTime.setSessionFixedToPM(true); } else if
		 * (rosterEndTime.isSessionFixedToPM()) {
		 * rosterEndTime.setSession(Calendar.AM);
		 * rosterEndTime.setSessionFixedToPM(false); }
		 */
		return rosterStartTime;
	}

	public void setRosterStartTime(TimeBean rosterStartTime) {
		this.rosterStartTime = rosterStartTime;
	}

	public TimeBean getRosterEndTime() {
		/*
		 * if (rosterEndTime.getHours() != -1 & rosterEndTime.getHours() <
		 * rosterStartTime.getHours()) { rosterEndTime.setSession(Calendar.PM);
		 * rosterEndTime.setSessionFixedToPM(true); }
		 */
		return rosterEndTime;
	}

	public void setRosterEndTime(TimeBean rosterEndTime) {
		this.rosterEndTime = rosterEndTime;
	}

	public TimeBean getActualStartTime() {
		/*
		 * if (actualStartTime.getSession() == Calendar.PM ||
		 * actualStartTime.getHours() == 12) {
		 * actualEndTime.setSession(Calendar.PM);
		 * actualEndTime.setSessionFixedToPM(true); } else if
		 * (actualEndTime.isSessionFixedToPM()) {
		 * actualEndTime.setSession(Calendar.AM);
		 * actualEndTime.setSessionFixedToPM(false); }
		 */
		return actualStartTime;
	}

	public void setActualStartTime(TimeBean actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public TimeBean getActualEndTime() {
		/*
		 * if (actualEndTime.getHours() != -1 & actualEndTime.getHours() <
		 * actualStartTime.getHours()) { actualEndTime.setSession(Calendar.PM);
		 * actualEndTime.setSessionFixedToPM(true); }
		 */
		return actualEndTime;
	}

	public void setActualEndTime(TimeBean actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	public List<SelectItem> getVehicleSelectItems() {
		return vehicleSelectItems;
	}

	public void setSelectedVehicleId(Long selectedVehicleId) {
		this.selectedVehicleId = selectedVehicleId;
	}

	public Long getSelectedVehicleId() {
		return selectedVehicleId;
	}

	public void setSetActualEventTimes(boolean setActualEventTimes) {
		this.setActualEventTimes = setActualEventTimes;
	}

	public boolean isSetActualEventTimes() {
		return setActualEventTimes;
	}

	public Long getSelectedLocationId() {
		return selectedLocationId;
	}

	public void setSelectedLocationId(Long selectedLocationId) {
		this.selectedLocationId = selectedLocationId;
	}

	public void setSelectedCoordinatorId(Long selectedCoordinatorId) {
		this.selectedCoordinatorId = selectedCoordinatorId;
	}

	public Long getSelectedCoordinatorId() {
		return selectedCoordinatorId;
	}

	public void setSelectedReturnCoordinatorId(Long selectedCoordinatorId) {
		this.selectedReturnCoordinatorId = selectedCoordinatorId;
	}

	public Long getSelectedReturnCoordinatorId() {
		return selectedReturnCoordinatorId;
	}

	public int getPhotoW() {
		return photoW;
	}

	public int getPhotoH() {
		return photoH;
	}

	public List<SelectItem> getLocationSelectItems() {
		return locationSelectItems;
	}

	public List<SelectItem> getCoordinatorSelectItems() {
		return coordinatorSelectItems;
	}

	public List<SelectItem> getReturnCoordinatorSelectItems() {
		return returnCoordinatorSelectItems;
	}

	public boolean isVisibleConfirmation() {
		return visibleConfirmation;
	}

	public void setVisibleConfirmation(boolean visibleConfirmation) {
		this.visibleConfirmation = visibleConfirmation;
	}

	public String getConfirmationMessage() {
		return confirmationMessage;
	}

	public void setProgramSelectItems(List<SelectItem> programSelectItems) {
		this.programSelectItems = programSelectItems;
	}

	public List<SelectItem> getProgramSelectItems() {
		return programSelectItems;
	}

	public void setSelectedProgramId(Long selectedProgramId) {
		this.selectedProgramId = selectedProgramId;
	}

	public Long getSelectedProgramId() {
		return selectedProgramId;
	}

	public void setGroupSelectItems(List<SelectItem> groupSelectItems) {
		this.groupSelectItems = groupSelectItems;
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

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public String getTimePeriod() {
		return timePeriod;
	}

	/*
	 * public void setSelectedStatus(String selectedStatus) {
	 * this.selectedStatus = selectedStatus; }
	 * 
	 * public String getSelectedStatus() { return selectedStatus; }
	 */

	public String getEventStatus() {
		return eventStatus;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getJsonString() {
		return jsonString;
	}

	public boolean isVisibleCharges() {
		return visibleCharges;
	}

	public void setChargeTx(Transaction chargeTx) {
		this.chargeTx = chargeTx;
	}

	public Transaction getChargeTx() {
		return chargeTx;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public double getChargeAmount() {
		return chargeAmount;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public String getTxRemarks() {
		return txRemarks;
	}

	public void setTxRemarks(String txRemarks) {
		this.txRemarks = txRemarks;
	}

	public void setPayType(EnumPaymentType payType) {
		this.payType = payType;
	}

	public EnumPaymentType getPayType() {
		return payType;
	}

	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	public void setCollection(List<Collection> collection) {
		this.collection = collection;
	}

	public List<Collection> getCollection() {
		return collection;
	}

	public List<StudentEvent> getTxStudentEvents() {
		return txStudentEvents;
	}

	public List<SelectItem> getPaymentTypes() {
		return paymentTypes;
	}

	public boolean isVisibleCharge() {
		return visibleCharge;
	}

	public boolean isVisibleAttendance() {
		return visibleAttendance;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public void setDataTable(HtmlDataTable dataTable) {
		this.dataTable = dataTable;
	}

	public HtmlDataTable getDataTable() {
		return dataTable;
	}

	public void setGstIncluded(boolean gstIncluded) {
		this.gstIncluded = gstIncluded;
	}

	public boolean isGstIncluded() {
		return gstIncluded;
	}

	private String getDateString(Date date) {
		if (date != null) {
			return mydateFormat.format(date);
		}
		return "-";
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
		if (returnEndTime.getHours() != -1 & returnEndTime.getHours() < returnStartTime.getHours()) {
			returnEndTime.setSession(Calendar.PM);
			returnEndTime.setSessionFixedToPM(true);
		}
		return returnEndTime;
	}

	public void setReturnEndTime(TimeBean returnEndTime) {
		this.returnEndTime = returnEndTime;
	}

	public Long getReturnVehicleId() {
		return returnVehicleId;
	}

	public void setReturnVehicleId(Long returnVehicleId) {
		this.returnVehicleId = returnVehicleId;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setSelectAllReturnAtd(boolean selectAllReturnAtd) {
		this.selectAllReturnAtd = selectAllReturnAtd;
	}

	public boolean isSelectAllReturnAtd() {
		return selectAllReturnAtd;
	}

	public void setTransportStdEvents(List<TransportEventTableRow> transportStdEvents) {
		this.transportStdEvents = transportStdEvents;
	}

	public List<TransportEventTableRow> getTransportStdEvents() {
		return transportStdEvents;
	}

	public HashMap<Long, StudentEvent> getReturnsAttended() {
		return returnsAttended;
	}

	public boolean isIndividual() {
		return individual;
	}

	public boolean isFilterCompleted() {
		return filterCompleted;
	}

	public void setFilterCompleted(boolean filterCompleted) {
		this.filterCompleted = filterCompleted;
	}

	public boolean isFilterBanked() {
		return filterBanked;
	}

	public void setFilterBanked(boolean filterBanked) {
		this.filterBanked = filterBanked;
	}

	public void setFilterNew(boolean filterNew) {
		this.filterNew = filterNew;
	}

	public boolean isFilterNew() {
		return filterNew;
	}

	public void setFilterGenerated(boolean filterGenerated) {
		this.filterGenerated = filterGenerated;
	}

	public boolean isFilterGenerated() {
		return filterGenerated;
	}

	public void setSelectAllStfAtd(boolean selectAllStfAtd) {
		this.selectAllStfAtd = selectAllStfAtd;
	}

	public boolean isSelectAllStfAtd() {
		return selectAllStfAtd;
	}

	public void setTransportStfEvents(List<TransportStaffEventTableRow> transportStfEvents) {
		this.transportStfEvents = transportStfEvents;
	}

	public List<TransportStaffEventTableRow> getTransportStfEvents() {
		return transportStfEvents;
	}

	public void setSelectAllStfReturnAtd(boolean selectAllStfReturnAtd) {
		this.selectAllStfReturnAtd = selectAllStfReturnAtd;
	}

	public boolean isSelectAllStfReturnAtd() {
		return selectAllStfReturnAtd;
	}

	public String getTotalMoneyCollected() {
		return totalMoneyCollected;
	}

	public String getStdEventType() {
		return stdEventType;
	}

	public TimeBean getActualReturnStartTime() {
		if (actualReturnStartTime.getSession() == Calendar.PM || actualReturnStartTime.getHours() == 12) {
			actualReturnEndTime.setSession(Calendar.PM);
			actualReturnEndTime.setSessionFixedToPM(true);
		} else if (actualReturnEndTime.isSessionFixedToPM()) {
			actualReturnEndTime.setSession(Calendar.AM);
			actualReturnEndTime.setSessionFixedToPM(false);
		}
		return actualReturnStartTime;
	}

	public void setActualReturnStartTime(TimeBean actualReturnStartTime) {
		this.actualReturnStartTime = actualReturnStartTime;
	}

	public TimeBean getActualReturnEndTime() {
		if (actualReturnEndTime.getHours() != -1 & actualReturnEndTime.getHours() < actualReturnStartTime.getHours()) {
			actualReturnEndTime.setSession(Calendar.PM);
			actualReturnEndTime.setSessionFixedToPM(true);
		}
		return actualReturnEndTime;
	}

	public void setActualReturnEndTime(TimeBean actualReturnEndTime) {
		this.actualReturnEndTime = actualReturnEndTime;
	}

	public boolean isSetActualReturnEventTimes() {
		return setActualReturnEventTimes;
	}

	public void setSetActualReturnEventTimes(boolean setActualReturnEventTimes) {
		this.setActualReturnEventTimes = setActualReturnEventTimes;
	}

	public Long getSelectedProTypeId() {
		return selectedProTypeId;
	}

	public void setSelectedProTypeId(Long selectedProTypeId) {
		this.selectedProTypeId = selectedProTypeId;
	}

	public List<ProgramType> getProgramTypes() {
		return programTypes;
	}

	public boolean isSetOverridePrice() {
		return setOverridePrice;
	}

	public void setSetOverridePrice(boolean setOverridePrice) {
		this.setOverridePrice = setOverridePrice;
	}

	public boolean isSetReturnOverridePrice() {
		return setReturnOverridePrice;
	}

	public void setSetReturnOverridePrice(boolean setReturnOverridePrice) {
		this.setReturnOverridePrice = setReturnOverridePrice;
	}

}